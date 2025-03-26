package com.smc.journalApp.repository;


import com.smc.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class UserRepositoryImpl {

    /*
        This class is created to write the methods which has complex logic
        and are not present in JournalEntryRepository Interface
    */

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){

        Query query = new Query();

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        /*

        Above same query can be written using AND operator , OR operator as below

        Criteria criteria = new Criteria();
        query.addCriteria (criteria. andOperator(
        Criteria.where(key: "email").exists(value: true),
        Criteria.where(key: "sentiment Analysis").is(value: true))
        );
         */


        return mongoTemplate.find(query,User.class);

    }

}
