package com.smc.journalApp.repository;

import com.smc.journalApp.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, ObjectId> {

    Users findByusername(String username);

    void deleteByusername(String username);

}
