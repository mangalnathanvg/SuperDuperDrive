package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper)
    {
        this.fileMapper = fileMapper;
    }

    public boolean fileSameNameExists(User user, String filename)
    {
        ArrayList<File> result = fileMapper.findUserFile(filename, user.getUserId());

        return !result.isEmpty();
    }

    public List<File> getAllFiles(Integer userId)
    {
        return fileMapper.findAllUserFiles(userId);
    }
    public File getFile(String username, Integer userId)
    {
        return fileMapper.findFile(username, userId);
    }

    public void uploadFile(File file)
    {
        fileMapper.addUserFile(file);
        ArrayList<File> temp = fileMapper.displayAll();
        for (File value : temp) {
            System.out.println(value.getFileId().toString() + " - " + value.getFileName() + " - " + value.getFileId());
        }
    }

    public int deleteFile(int fileId)
    {
        return fileMapper.deleteFile(fileId);
    }
}
