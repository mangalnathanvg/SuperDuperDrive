package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES")
    ArrayList<Note> displayAll();

    @Select("SELECT * from NOTES WHERE noteTitle = #{title} AND userId = #{userId}")
    ArrayList<Note> findUserNote(String title, int userId);

    @Select("SELECT * from NOTES WHERE noteTitle = #{title} AND userId = #{userId}")
    Note findNote(String title, int userId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userid}")
    ArrayList<Note> findAllUserNotes(int userid);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addUserNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(int noteId);
}
