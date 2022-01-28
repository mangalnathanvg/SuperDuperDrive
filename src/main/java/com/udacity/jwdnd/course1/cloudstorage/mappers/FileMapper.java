package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES")
    ArrayList<File> displayAll();

    @Select("SELECT * from FILES WHERE fileName = #{fileName} AND userId = #{userId}")
    ArrayList<File> findUserFile(String fileName, int userId);

    @Select("SELECT * from FILES WHERE fileName = #{fileName} AND userId = #{userId}")
    File findFile(String fileName, int userId);

    @Select("SELECT * FROM FILES WHERE userId = #{userid}")
    ArrayList<File> findAllUserFiles(int userid);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, fileData, userId) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addUserFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);
}
