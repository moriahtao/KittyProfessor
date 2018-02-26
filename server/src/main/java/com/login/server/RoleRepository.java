package com.login.server;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by mengtao on 2/26/18.
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByAuthority(@Param("authority") String roleName);


}
