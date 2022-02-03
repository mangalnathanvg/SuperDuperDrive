package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private NoteService noteService;
    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, NoteService noteService, UserService userService, CredentialService credentialService, EncryptionService encryptionService)
    {
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("newNote") NoteForm newNote,Authentication authentication, Model model)
    {
        String username = authentication.getName();
        User user = userService.getUser(username);
        List<File> userFiles = fileService.getAllFiles(user.getUserId());
        List<Note> userNotes = noteService.getUserNotes(user.getUserId());
        List<Credential> userCredentials = credentialService.getUserCredentials(user.getUserId());

        model.addAttribute("userfiles" , userFiles);
        model.addAttribute("usernotes", userNotes);
        model.addAttribute("usercredentials", userCredentials);
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    @GetMapping("/file-download/{file_name}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("file_name") String filename, Authentication authentication)
    {
        String username = authentication.getName();
        User user = userService.getUser(username);

        File file = fileService.getFile(filename, user.getUserId());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() +"\"")
                .body(file.getFileData());
    }

    @GetMapping("/file-delete/{file_id}")
    public String deleteFile(@PathVariable("file_id") int fileId, Model model)
    {
        model.addAttribute("deleteSuccess", fileService.deleteFile(fileId));
        return "result";
    }

    @PostMapping("/file-upload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model)
    {
        String username = authentication.getName();
        User user = userService.getUser(username);

        if(fileUpload.isEmpty())
        {
            System.out.println("Upload Failure");
            model.addAttribute("uploadFailure", true);
            return "result";
        }

        // Check if a file already exists for this user with the same name.
        if(fileService.fileSameNameExists(user, fileUpload.getOriginalFilename()))
        {
            System.out.println("Same Filename exists");
            model.addAttribute("sameNameExists", true);
            return "result";
        }

        File file = new File();

        try {
            file.setFileData(fileUpload.getBytes());
            file.setFileName(fileUpload.getOriginalFilename());
            file.setFileSize(String.valueOf(fileUpload.getSize()));
            file.setContentType(fileUpload.getContentType());
            file.setUserId(user.getUserId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileService.uploadFile(file);

        model.addAttribute("uploadSuccess", true);
        return "result";
    }
}
