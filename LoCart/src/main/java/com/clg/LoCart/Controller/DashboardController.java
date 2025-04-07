package com.clg.LoCart.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.clg.LoCart.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {


    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {


        User user = (User) session.getAttribute("logged_user");
        System.out.println("Entered Dashboard.....................................");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "index"; // loads index.html from templates
    }
}
