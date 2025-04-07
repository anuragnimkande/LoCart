package com.clg.LoCart.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
