package com.clg.LoCart.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shopowner")
public class shopowner {

    @Id
    private String id;
    private String shopname;
    private String ownername;
    private String email;
    private String latitude;
    private String longitude;
    private String category;
    private byte[] image;
    private String password;

    @Transient
    private String mobile = "23414555555";


    @Transient
    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }


}
