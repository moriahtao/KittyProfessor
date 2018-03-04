package com.cs5500.team209.repository;

/**
 * Created by mengtao on 2/25/18.
 */

import com.cs5500.team209.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(@Param("username") String name);


}
