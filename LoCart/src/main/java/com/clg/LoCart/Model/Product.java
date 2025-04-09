package com.clg.LoCart.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {


    @Id
    private String productid;
    private String productname;
    private int productprice;
    private String productdescription;
    private String shopownerid;
    private String shopowneremail;
    private byte[] image;
    private Date createdAt;

    @Transient
    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    @Transient
    public String productshopname;

    public String getProductshopname() {
        return productshopname;
    }

    public void setProductshopname(String productshopname) {
        this.productshopname = productshopname;
    }
}
