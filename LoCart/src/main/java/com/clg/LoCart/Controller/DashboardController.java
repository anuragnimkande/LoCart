package com.clg.LoCart.Controller;

import com.clg.LoCart.Model.shopowner;
import com.clg.LoCart.Repository.ShopOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.clg.LoCart.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class DashboardController {



    @Autowired
    ShopOwnerRepository shopwnerrepository;
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {


        User user = (User) session.getAttribute("logged_user");
        double userlat = (Double) session.getAttribute("user_latitude");
        double userlong = (Double) session.getAttribute("user_longitude");

        System.out.println(userlat);
        System.out.println(userlong);

        List<shopowner> allshops = shopwnerrepository.findAll();
        List<shopowner> nearbyshops = new ArrayList<>();


        for (shopowner shop : allshops) {
            double distance = calculateDistance(
                    userlat,
                    userlong,
                    Double.parseDouble(shop.getLatitude()),
                    Double.parseDouble(shop.getLongitude())
            );
            System.out.print(shop.getOwnername()+" ");
            System.out.println(distance);
            if (distance <= 5.0) {
                nearbyshops.add(shop);
            }
        }
        session.setAttribute("nearbyshops", nearbyshops);

        for(shopowner  shop: nearbyshops)
        System.out.println(shop.getOwnername());

        System.out.println("Entered Dashboard.....................................");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "index"; // loads index.html from templates
    }


    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // result in km
    }


    @GetMapping("/nearbyshops")
    public String shownearshop(
            HttpSession session,
            Model model
    )
    {
        List<shopowner> nearshop = (List<shopowner>) session.getAttribute("nearbyshops");

        for(shopowner shop: nearshop)
        {
            if (shop.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(shop.getImage());
                shop.setBase64Image(base64Image);
            }

            model.addAttribute("shops", nearshop);
        }


        return "nearbyshop";

    }



}
