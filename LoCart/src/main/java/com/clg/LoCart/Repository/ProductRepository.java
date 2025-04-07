package com.clg.LoCart.Repository;

import com.clg.LoCart.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    Product findByshopowneremail(String email);

}
