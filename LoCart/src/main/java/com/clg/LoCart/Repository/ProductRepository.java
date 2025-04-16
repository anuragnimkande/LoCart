package com.clg.LoCart.Repository;

import com.clg.LoCart.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    Product findByshopowneremail(String email);

    ArrayList<Product> findAllByshopowneremailIn(ArrayList<String> shopemails);

    List<Product> findAllByshopowneremail(String email);
}
