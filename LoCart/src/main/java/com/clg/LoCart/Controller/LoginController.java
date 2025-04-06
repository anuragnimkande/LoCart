package com.clg.LoCart.Controller;

import com.clg.LoCart.Model.User;
import com.clg.LoCart.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // this will return login.html from templates
    }

    @GetMapping("/register")
    public String showregisterpage()
    {
        return "register";
    }

    @PostMapping("/register")
    public String Savedata(
            @RequestParam("username") String username,
            @RequestParam("password") String pass,
            @RequestParam("mobile") String mobile,
            @RequestParam("email") String email
    )
    {


       User user = new User();

       user.setEmail(email);
       user.setUsername(username);
       user.setMobile(mobile);
       user.setPassword(pass);

        userRepository.save(user);

        return "redirect:/login";

    }
}
