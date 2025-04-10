package com.clg.LoCart.Controller;

import com.clg.LoCart.Model.Request;
import com.clg.LoCart.Model.User;
import com.clg.LoCart.Model.shopowner;
import com.clg.LoCart.Repository.RequestRepository;
import com.clg.LoCart.Repository.ShopOwnerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;


@Controller
public class RequestController {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    ShopOwnerRepository shopOwnerRepository;

    @PostMapping("/sendrequest")
    public String saverequest(
            HttpSession session,
            @RequestParam("item") String name,
            @RequestParam("description") String des,
            @RequestParam("category") String category,
            @RequestParam("photo") MultipartFile image

    ) throws IOException {
        User user = (User) session.getAttribute("logged_user");
        Request req = new Request();
        req.setRequestname(name);
        req.setPhoto(image.getBytes());
        req.setCategory(category);
        req.setRequestdesc(des);
        req.setUseremail(user.getEmail());
        req.setCreatedAt(new Date());

        double latitude = (Double) session.getAttribute("user_latitude");
        double longitude = (Double) session.getAttribute("user_longitude");


        req.setLocation(new GeoJsonPoint(longitude, latitude));

        requestRepository.save(req);


        return "redirect:/dashboard";
    }


    @GetMapping("/seerequests")
    public String seereq(

            HttpSession session,
            Model model
    )
    {

        shopowner shop = (shopowner) session.getAttribute("shop");

        double longitude = Double.parseDouble(shop.getLongitude());
        double latitude = Double.parseDouble(shop.getLatitude());

        Point shopLocation = new Point(longitude, latitude); // Remember: (lon, lat)
        Distance radius = new Distance(5, Metrics.KILOMETERS); // 5km

        List<Request> nearbyRequests = requestRepository.findByLocationNear(shopLocation, radius);

        for(Request neareq: nearbyRequests)
        {
            if (neareq.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(neareq.getPhoto());
                neareq.setBase64Image(base64Image);
            }
        }

        model.addAttribute("requests", nearbyRequests);
        model.addAttribute("shop",shop);

        return "seerequests";
    }


    @PostMapping("/acceptrequest")
    public String accept(
            HttpSession session,
            @RequestParam("id") String id,
            @RequestParam("price") String price
    )
    {


        shopowner shop = (shopowner) session.getAttribute("shop");
        Optional<Request> optionalreq = requestRepository.findById(id);
        Request req = optionalreq.get();

        if( !req.getAcceptedShops().contains(shop.getEmail()))
        {

            req.getAcceptedShops().add(shop.getEmail());
            req.getPrices().add(price);
            requestRepository.save(req);
        }

        return "redirect:/shopdashboard";
    }




    @GetMapping("/your_requests")
    public String seeyourreq(
            HttpSession session,
            Model model
    )
    {
        User user = (User) session.getAttribute("logged_user");
        List<Request> yourrequests = requestRepository.findAllByuseremail(user.getEmail());



        for (Request req : yourrequests) {

            if (req.getPhoto() != null) {
                String base64Image = Base64.getEncoder().encodeToString(req.getPhoto());
                req.setBase64Image(base64Image);
            }

            for (String email : req.getAcceptedShops()) {
                    shopowner shop = shopOwnerRepository.findByemail(email);
                    req.getAcceptedShopDetails().add(shop);
            }
        }
        model.addAttribute("requests", yourrequests);
        return "your_requests";
    }
}
