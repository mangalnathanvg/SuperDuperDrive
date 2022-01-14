package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper)
    {
        this.fileMapper = fileMapper;
    }

    public ArrayList<String> getUserUploadedFileNames()
    {
        return null;
    }
}
