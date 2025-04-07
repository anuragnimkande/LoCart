package com.clg.LoCart.Controller;


import com.clg.LoCart.Model.shopowner;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopDashboardController {



    @GetMapping("/shopdashboard")
    public String showownerdashboard(
            Model model,
            HttpSession session
    )
    {
        System.out.println("Shop Dashboard...................");
        shopowner shop = (shopowner)  session.getAttribute("shop");
        model.addAttribute("shop",shop);
        return "shopindex";
    }
}
