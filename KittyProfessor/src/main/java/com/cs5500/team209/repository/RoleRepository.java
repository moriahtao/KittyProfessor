package com.cs5500.team209.repository;

import com.cs5500.team209.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by mengtao on 2/26/18.
 *
 * the java repo for mongodb operations
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    /**
     * find the role
     * @param roleName the role for query
     * @return the role
     */
    Role findByAuthority(@Param("authority") String roleName);


}
