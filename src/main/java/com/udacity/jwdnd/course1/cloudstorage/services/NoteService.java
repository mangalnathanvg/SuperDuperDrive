package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note)
    {
        System.out.println("Service Layer: " + note.getUserId() + "--" + note.getTitle() + "--" + note.getDescription());

        noteMapper.addUserNote(note);
        // Display all notes in database to check if note is added successfully
        ArrayList<Note> result = noteMapper.displayAll();

        System.out.println("Database values");
        for(int i=0; i<result.size(); i++)
        {
            System.out.println(result.get(i).getNoteId() + "-" + result.get(i).getTitle() + "-" + result.get(i).getDescription() + "-" + result.get(i).getUserId());
        }
    }
}
