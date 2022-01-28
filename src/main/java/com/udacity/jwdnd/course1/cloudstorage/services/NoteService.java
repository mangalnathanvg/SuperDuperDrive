package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getUserNotes(Integer UserId)
    {
        List<Note> result = noteMapper.findAllUserNotes(UserId);
        return result;
    }

    public void updateNote(Note note)
    {
        noteMapper.updateUserNote(note);
        ArrayList<Note> result = noteMapper.displayAll();

        System.out.println("Database values");
        for(int i=0; i<result.size(); i++)
        {
            System.out.println(result.get(i).getNoteId() + "-" + result.get(i).getNoteTitle() + "-" + result.get(i).getNoteDescription() + "-" + result.get(i).getUserId());
        }
    }

    public void addNote(Note note)
    {
        System.out.println("Service Layer: " + note.getUserId() + "--" + note.getNoteTitle() + "--" + note.getNoteDescription());

        noteMapper.addUserNote(note);
        // Display all notes in database to check if note is added successfully
        ArrayList<Note> result = noteMapper.displayAll();

        System.out.println("Database values");
        for(int i=0; i<result.size(); i++)
        {
            System.out.println(result.get(i).getNoteId() + "-" + result.get(i).getNoteTitle() + "-" + result.get(i).getNoteDescription() + "-" + result.get(i).getUserId());
        }
    }

    public int deleteNote(int noteId)
    {
        return noteMapper.deleteNote(noteId);
    }
}
