package com.bayou.controllers;

import com.bayou.utils.Mocks;
import com.bayou.utils.Server;
import com.bayou.views.impl.UserView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * File: UserControllerTests
 * Package: com.bayou.controllers
 * Author: Stefan Haselwanter
 * Created on: 2/21/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTests {
    private static final String RESOURCE_URL = "/users";

    private TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void testGetUserById() {
        // Create user view and add user to db.
        UserView view = Mocks.createUserView();
        ResponseEntity<Long> entity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);
        view.setId(entity.getBody());

        // Get user by id.
        ResponseEntity<UserView> responseEntity = rest.getForEntity(
                Server.url() + RESOURCE_URL + "/" + view.getId(), UserView.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() != null);

        rest.delete(Server.url() + RESOURCE_URL + "/" + view.getId() + "/delete", String.class);
    }

    @Test
    public void testGetUserByAccountName() {
        // Create user view and add user to db.
        UserView view = Mocks.createUserView();
        ResponseEntity<Long> entity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);
        view.setId(entity.getBody());

        // Get user by account name.
        ResponseEntity<UserView> responseEntity = rest.getForEntity(
                Server.url() + RESOURCE_URL + "/accountName/" + view.getAccountName(), UserView.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() != null);

        rest.delete(Server.url() + RESOURCE_URL + "/" + view.getId() + "/delete", String.class);
    }

    @Test
    public void testGetUserByEmail() {
        // Create user view and add user to db.
        UserView view = Mocks.createUserView();
        ResponseEntity<Long> entity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);
        view.setId(entity.getBody());

        // Get user by email.
        ResponseEntity<UserView> responseEntity = rest.getForEntity(
                Server.url() + RESOURCE_URL + "/email/" + view.getEmail(), UserView.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() != null);

        rest.delete(Server.url() + RESOURCE_URL + "/" + view.getId() + "/delete", String.class);
    }

    @Test
    public void testAddUser() {
        // Create user view and add user to db.
        UserView view = Mocks.createUserView();
        ResponseEntity<Long> responseEntity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() != null);
    }

    @Test
    public void testDeleteUser() {
        // Create user view and add user to db.
        UserView view = Mocks.createUserView();
        ResponseEntity<Long> entity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);
        view.setId(entity.getBody());

        // Delete user by id.
        ResponseEntity responseEntity = rest.exchange(
                Server.url() + RESOURCE_URL + "/" + view.getId() + "/delete",
                HttpMethod.DELETE, new HttpEntity<>(view, Server.createHeadersJson()), String.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}