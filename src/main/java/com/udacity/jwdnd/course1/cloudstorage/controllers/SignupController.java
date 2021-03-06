package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signUpView(){
        return "signup";
    }

    @PostMapping()
    public String signUpUser(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
        String signupError = null;

        if(!userService.isUsernameAvailable(user.getUsername()))
            signupError = "The username already exists.";

        if (signupError == null)
        {
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0)
            {
                signupError = "There was an error signing you up. Please try again";
            }
        }

        if(signupError == null)
        {
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/";
        }else{
            redirectAttributes.addFlashAttribute("signupError", signupError);
            return "redirect:/signup";
        }
    }
}
