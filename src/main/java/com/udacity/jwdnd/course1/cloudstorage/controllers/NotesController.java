package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NotesController {
    private UserService userService;
    private NoteService noteService;

    public NotesController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/addnote")
    public String addNote(Authentication authentication, @ModelAttribute("newNote") NoteForm newNote, Model model)
    {
        String username = authentication.getName();
        User user = userService.getUser(username);

        Note note = new Note();
        System.out.println("Note Title: " + newNote.getTitle());
        String title = newNote.getTitle();
        note.setTitle(title);
        System.out.println("Note Description: " + newNote.getDescription());
        String description = newNote.getDescription();
        note.setDescription(description);
        note.setUserId(user.getUserId());

        System.out.println("Controller Layer: " + note.getTitle() + " --- " + note.getDescription() + " --- " + note.getUserId());

        noteService.addNote(note);

        model.addAttribute("addNoteSuccess", true);
        return "result";
    }
}
