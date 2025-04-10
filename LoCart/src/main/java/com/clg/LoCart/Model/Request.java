package com.clg.LoCart.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "Requests")
public class Request {

    @Id
    private String id;
    private String useremail;
    private String requestname;
    private String requestdesc;
    private byte[] photo;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    private Date createdAt;
    private String category;

    // ✅ This will hold emails or IDs of shops who accepted the request
    private List<String> acceptedShops = new ArrayList<>();

    private List<String> prices = new ArrayList<>();

    @Transient
    private String base64Image;

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }


    @Transient
    private List<shopowner> acceptedShopDetails = new ArrayList<>();


}
