package com.bayou.managers.impl;

import com.bayou.converters.LoginConverter;
import com.bayou.converters.UserConverter;
import com.bayou.domains.User;
import com.bayou.exceptions.VerificationException;
import com.bayou.managers.IManager;
import com.bayou.ras.impl.UserResourceAccessor;
import com.bayou.views.impl.LoginView;
import com.bayou.views.impl.UserView;
import com.bayou.views.impl.VerifyUserView;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by joshuaeaton on 1/31/17.
 */
@Service
public class UserManager implements IManager<UserView> {
    @Autowired
    UserResourceAccessor ras = new UserResourceAccessor();

    @Autowired
    UserConverter converter = new UserConverter();

    @Autowired
    LoginConverter loginConverter = new LoginConverter();

    public LoginView login(VerifyUserView verifyUserView) throws NotFoundException, VerificationException {
        User returnedUser;

        if(verifyUserView.getEmail() != null) { //if email field is not null, get the user by email
            returnedUser = ras.findByEmail(verifyUserView.getEmail());
        } else if(verifyUserView.getAccountName() != null) { //if account name is not null, get the user by account name
            returnedUser = ras.findByAccountName(verifyUserView.getAccountName());
        } else {
            throw new NotFoundException("Values of email or account name not found" + "email: " + verifyUserView.getEmail() + " account name: " + verifyUserView.getAccountName());
        }
        if (returnedUser == null) {
            throw new NotFoundException("The requested user does not exist in the database");
        } else {
            verifyUserView.setPasswordHash(returnedUser.getPasswordHash());
        }
        if(verifyUserView.login()) {
            return loginConverter.convertToLoginView(returnedUser);
        } else {
            throw new VerificationException("password");
        }
    }

    public UserView get(Long id) throws NotFoundException {
        UserView userView;
        User user = ras.find(id);

        if (user == null) {
            throw new NotFoundException(String.valueOf(id));
        } else {
            userView = converter.convertToView(user);
        }

        return userView;
    }

    @Override
    public List<UserView> getAll() throws NotFoundException {
        return null;
    }

    public UserView getByAccountName(String accountName) throws NotFoundException {
        User returnedUser;
        UserView newUserView;

        returnedUser = ras.findByAccountName(accountName);

        if (returnedUser == null) {
            throw new NotFoundException("The requested user does not exist in the database");
        } else {
            newUserView = converter.convertToView(returnedUser);
        }

        return newUserView;
    }

    public UserView getByEmail(String email) throws NotFoundException {
        User returnedUser;
        UserView newUserView;

        returnedUser = ras.findByEmail(email);

        if (returnedUser == null) {
            throw new NotFoundException("The requested user does not exist in the database");
        } else {
            newUserView = converter.convertToView(returnedUser);
        }

        return newUserView;
    }

    @Override
    public Long add(UserView userView) {
        Long id = -1L;
        try {
            id = ras.add(converter.convertToDomain(userView));
        } catch (DataIntegrityViolationException e) {
            System.err.println("User: " + userView.getAccountName() + " already exist");
        }

        return id;
    }

    //TODO implement
    @Override
    public UserView update(UserView userView) {
        return null;
    }

    @Override
    public void delete(Long id) {
        try {
            ras.delete(id);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("The user with ID:" + id + " does not exist in the database");
        }
    }


}
