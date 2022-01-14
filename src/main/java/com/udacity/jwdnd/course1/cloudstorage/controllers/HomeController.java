package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;

    public HomeController(FileService fileService)
    {
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(Model model)
    {

        return "home";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model)
    {


        return "home";
    }
}
