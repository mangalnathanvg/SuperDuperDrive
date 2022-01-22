package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper)
    {
        this.fileMapper = fileMapper;
    }

    public ArrayList<String> getUserUploadedFileNames(User user, File file)
    {
        return null;
    }

    public boolean fileSameNameExists(User user, String filename)
    {
        ArrayList<File> result = fileMapper.findUserFile(filename, user.getUserId());

        if(result.isEmpty())
            return false;

        return true;
    }

    public List<File> getAllFiles(Integer userId)
    {
        List<File> results = fileMapper.findAllUserFiles(userId);
        return results;
    }
    public File getFile(String username, Integer userId)
    {
        return fileMapper.findFile(username, userId);
    }

    public void uploadFile(User user, File file)
    {
        fileMapper.addUserFile(file, user.getUserId());
        ArrayList<File> temp = fileMapper.displayAll();
        for(int i=0; i<temp.size(); i++)
        {
            System.out.println(temp.get(i).getFileId().toString() + " - " + temp.get(i).getFileName() + " - " + temp.get(i).getFileId());
        }
    }

    public int deleteFile(int fileId)
    {
        return fileMapper.deleteFile(fileId);
    }
}
