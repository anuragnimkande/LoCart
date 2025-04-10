package com.clg.LoCart.Controller;

import com.clg.LoCart.Model.User;
import com.clg.LoCart.Model.shopowner;
import com.clg.LoCart.Repository.ShopOwnerRepository;
import com.clg.LoCart.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopOwnerRepository shopownerrepository;

    @Autowired
    private JavaMailSender mailSender;



    private boolean verified = false;
    String generatedOtp = "";

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
            @RequestParam("email") String email,
            Model model
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

    @PostMapping("/login")
    public String validate(
            @RequestParam("email") String email,
            @RequestParam("pass") String pass,
            Model model,
            HttpSession session
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
            session.setAttribute("logged_user", user);

            return "fetchlocation";
        } else {
            model.addAttribute("message", "Incorrect Password");
            model.addAttribute("alertType", "error");
            return "login";
        }
    }





    @GetMapping("/shopownerlogin")
    public String showshopownerlogin()
    {
        return "shopownerlogin";
    }

    @GetMapping("/shopowner/register")
    public String showshopregister()
    {
        return "shopownerregister";
    }

    @PostMapping("/registershop")
    public String saveshopowner(
            @RequestParam("shopName") String shopname,
            @RequestParam("ownerName") String ownername,
            @RequestParam("email") String email,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitutde,
            @RequestParam("category") String category,
            @RequestParam("shopImage") MultipartFile image,
            @RequestParam("pass") String pass,
            @RequestParam("phone") String phone
            ) throws IOException {

        shopowner shopown = new shopowner();
        shopown.setShopname(shopname);
        shopown.setOwnername(ownername);
        shopown.setEmail(email);
        shopown.setLatitude(latitude);
        shopown.setLongitude(longitutde);
        shopown.setCategory(category);
        shopown.setImage(image.getBytes());
        shopown.setPassword(pass);
        shopown.setMobile(phone);

        shopownerrepository.save(shopown);

        return "redirect:/shopownerlogin";
    }

    @PostMapping("/shopowner/login")
    public String loginshop(
            @RequestParam("email") String email,
            @RequestParam("password") String pass,
            Model model,
            HttpSession session

    )
    {

        shopowner shop = shopownerrepository.findByemail(email);


            if(shop != null && pass.equals(shop.getPassword()))
            {

                session.setAttribute("shop", shop);

                return "redirect:/shopdashboard";
            }



        return "shopownerlogin";

    }


    @PostMapping("/save-user-location")
    public ResponseEntity<Void> saveUserLocation(
            @RequestParam double latitude,
            @RequestParam double longitude,
            HttpSession session
    ) {
        session.setAttribute("user_latitude", latitude);
        session.setAttribute("user_longitude", longitude);
        return ResponseEntity.ok().build();
    }






}
