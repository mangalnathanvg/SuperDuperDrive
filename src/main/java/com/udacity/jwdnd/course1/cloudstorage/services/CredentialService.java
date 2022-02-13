package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;


    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getUserCredentials(Integer userId)
    {
        return credentialMapper.findAllUserCredentials(userId);
    }

    public void updateCredential(Credential credential)
    {
        credentialMapper.updateUserCredential(credential);

    }

    public void addUserCredential(Credential credential)
    {
        credentialMapper.addUserCredential(credential);
    }

    public int deleteCredential(int credentialId)
    {
        return credentialMapper.deleteCredential(credentialId);
    }
}
