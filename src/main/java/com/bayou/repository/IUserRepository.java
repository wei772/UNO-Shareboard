package com.bayou.repository;

import com.bayou.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by joshuaeaton on 1/31/17.
 */
@Repository
public interface IUserRepository extends CrudRepository <User, Long>{

 //  User findByaccount_name(String account_name);
    User findByemail(String email);
}
