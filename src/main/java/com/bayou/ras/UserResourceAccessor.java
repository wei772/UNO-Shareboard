package com.bayou.ras;

import com.bayou.domains.User;
import com.bayou.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by joshuaeaton on 1/31/17.
 */
@Service    //registers this java class as a Service bean so that the container is aware of it for injection
public class UserResourceAccessor {

    @Autowired
    IUserRepository userRepo;

    public User getUserByUserEmail(String email) {

        return userRepo.findByemail(email);//access repo to get user
    }
/*
    public User getUserByUserAccountName(String account_name) {

         return userRepo.findByaccount_name(account_name);//access repo to get user
    }
*/
    public User getUserByUserId(Long user_id) {
        return userRepo.findOne(user_id);//access repo to get user
    }

    public User addUser(User user) {
        return userRepo.save(user);//access repo to add user
    }
}
