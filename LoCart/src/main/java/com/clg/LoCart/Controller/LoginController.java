package com.clg.LoCart.Controller;

import com.clg.LoCart.Model.User;
import com.clg.LoCart.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String saveData(
            @RequestParam("username") String username,
            @RequestParam("password") String pass,
            @RequestParam("mobile") String mobile,
            @RequestParam("email") String email
    ) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setMobile(mobile);
        user.setPassword(pass);

        userRepository.save(user);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String validate(
            @RequestParam("email") String email,
            @RequestParam("pass") String pass,
            Model model
    ) {
        User user = userRepository.findByemail(email);

        if (user == null) {
            model.addAttribute("message", "User Not Found");
            model.addAttribute("alertType", "error");
            return "login";
        }

        if (user.getPassword().equals(pass)) {
            model.addAttribute("message", "Login Successful");
            model.addAttribute("alertType", "success");
            return "index";
        } else {
            model.addAttribute("message", "Incorrect Password");
            model.addAttribute("alertType", "error");
            return "login";
        }
    }

}
