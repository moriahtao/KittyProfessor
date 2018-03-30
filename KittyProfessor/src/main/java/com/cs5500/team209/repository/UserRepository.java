package com.cs5500.team209.repository;

import com.cs5500.team209.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * Created by mengtao on 2/25/18.
 *
 * java repo for User in mongodb User table
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * find user by username
     * @param name the username for query
     * @return the user found
     */
    User findByUsername(@Param("username") String name);

}
