package com.smc.journalApp.repository;

import com.smc.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByusername(String username);

    void deleteByusername(String username);

}
