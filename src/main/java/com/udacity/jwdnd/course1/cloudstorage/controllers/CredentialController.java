package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    private List<String> encryptPassword(String password)
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String secretKey = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = encryptionService.encryptValue(password, secretKey);

        List<String> result = new ArrayList<>();

        result.add(secretKey);
        result.add(hashedPassword);

        return result;
    }

    @PostMapping("/addcredential")
    public String addCredential(Authentication authentication, Credential credential, Model model)
    {
        String username = authentication.getName();
        User user = userService.getUser(username);

        List<String> result = encryptPassword(credential.getPassword());

        credential.setKey(result.get(0));
        credential.setPassword(result.get(1));
        credential.setUserId(user.getUserId());

        if(credential.getCredentialId() != null)
        {
            credentialService.updateCredential(credential);
            model.addAttribute("updateCredentialSuccess", true);
        }else{
            credentialService.addUserCredential(credential);
            model.addAttribute("addCredentialSuccess", true);
        }
        return "redirect:/home";
    }

    @GetMapping("/credential-delete/{credential_id}")
    public String deleteNote(@PathVariable("credential_id") int credentialId, Model model)
    {
        model.addAttribute("deleteCredentialSuccess", credentialService.deleteCredential(credentialId));
        return "redirect:/home";
    }
}
