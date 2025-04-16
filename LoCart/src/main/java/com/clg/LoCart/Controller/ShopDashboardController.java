package com.clg.LoCart.Controller;


import com.clg.LoCart.Model.Product;
import com.clg.LoCart.Model.shopowner;
import com.clg.LoCart.Repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class ShopDashboardController {

    @Autowired
    ProductRepository productRepository;

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


    @PostMapping("/shop/upload-product")
    public String uploadproduct(
            @RequestParam("name") String productname,
            @RequestParam("price") int productprice,
            @RequestParam("description") String productdescription,
            @RequestParam("image") MultipartFile image,
            HttpSession session
            ) throws IOException {

        shopowner shop = (shopowner) session.getAttribute("shop");
        Product pro = new Product();
        pro.setProductname(productname);
        pro.setProductprice(productprice);
        pro.setProductdescription(productdescription);
        pro.setShopowneremail(shop.getEmail());
        pro.setShopownerid(shop.getId());
        pro.setImage(image.getBytes());
        pro.setCreatedAt(new Date());

        productRepository.save(pro);
        System.out.println("Product Saved");

        return "redirect:/shopdashboard";

    }





    @GetMapping("/shopproducts")
    public String showShopProducts(HttpSession session, Model model) {
        shopowner shop = (shopowner) session.getAttribute("shop");
        List<Product> products = productRepository.findAllByshopowneremail(shop.getEmail());

        for (Product product : products) {
            if (product.getImage() != null) {
                product.setBase64Image(Base64.getEncoder().encodeToString(product.getImage()));
            }
        }
        model.addAttribute("shop",shop);
        model.addAttribute("products", products);
        return "shopproducts";
    }


}
