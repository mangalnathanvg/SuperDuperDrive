package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String addNote(Authentication authentication, NoteForm newNote, Model model)
    {
        String username = authentication.getName();
        User user = userService.getUser(username);

        Note note = new Note();

        String title = newNote.getTitle();
        note.setNoteTitle(title);
        String description = newNote.getDescription();
        note.setNoteDescription(description);
        note.setUserId(user.getUserId());

        if(newNote.getNoteId()!=null)
        {
            System.out.println("Update note Id " + newNote.getNoteId());
            note.setNoteId(newNote.getNoteId());
            noteService.updateNote(note);
            model.addAttribute("updateNoteSuccess", true);
        }else {
            noteService.addNote(note);
            model.addAttribute("addNoteSuccess", true);
        }
        model.addAttribute("noteSelected", true);
        return "redirect:/home";
    }

    @GetMapping("/note-delete/{note_id}")
    public String deleteNote(@PathVariable("note_id") int noteId, Model model)
    {
        model.addAttribute("deleteNoteSuccess", noteService.deleteNote(noteId));
        model.addAttribute("noteSelected", true);
        return "redirect:/home";
    }
}
