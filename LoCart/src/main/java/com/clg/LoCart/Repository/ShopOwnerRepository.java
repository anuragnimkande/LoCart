package com.clg.LoCart.Repository;

import com.clg.LoCart.Model.User;
import com.clg.LoCart.Model.shopowner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopOwnerRepository extends MongoRepository<shopowner,String> {

    shopowner findByemail(String email);
}
