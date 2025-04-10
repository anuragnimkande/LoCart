package com.clg.LoCart.Repository;


import com.clg.LoCart.Model.Request;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository

public interface RequestRepository extends MongoRepository<Request,String> {

    Request findByuseremail(String email);

    List<Request> findAllByuseremail(String email);



    List<Request> findByLocationNear(org.springframework.data.geo.Point shopLocation, Distance radius);
}
