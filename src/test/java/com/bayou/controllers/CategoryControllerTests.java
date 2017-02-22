package com.bayou.controllers;

import com.bayou.utils.Mocks;
import com.bayou.utils.Server;
import com.bayou.views.impl.CategoryView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * File: CategoryControllerTests
 * Package: com.bayou.controllers
 * Author: Stefan Haselwanter
 * Created on: 2/21/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CategoryControllerTests {
    private static final String RESOURCE_URL = "/categories";

    private TestRestTemplate rest = new TestRestTemplate();

    @Test
    public void testGetCategories() {
        // Create category view and add category to db.
        CategoryView view = Mocks.createCategoryView();
        ResponseEntity<Long> entity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);
        view.setId(entity.getBody());

        // Get list of categories.
        ResponseEntity<List> responseEntity = rest.getForEntity(
                Server.url() + RESOURCE_URL, List.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() != null);

        rest.delete(Server.url() + RESOURCE_URL + "/" + view.getId() + "/delete", String.class);
    }

    @Test
    public void testGetCategoryById() {
        // Create category view and add category to db.
        CategoryView view = Mocks.createCategoryView();
        ResponseEntity<Long> entity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);
        view.setId(entity.getBody());

        // Get category by id.
        ResponseEntity<CategoryView> responseEntity = rest.getForEntity(
                Server.url() + RESOURCE_URL + "/" + view.getId(), CategoryView.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() != null);

        rest.delete(Server.url() + RESOURCE_URL + "/" + view.getId() + "/delete", String.class);
    }

    @Test
    public void testAddCategory() {
        // Create category view and add category to db.
        CategoryView view = Mocks.createCategoryView();
        ResponseEntity<Long> responseEntity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() != null);
    }

    @Test
    public void testUpdateCategory() {
        // TODO Implement
    }

    @Test
    public void testDeleteCategory() {
        // Create category view and add category to db.
        CategoryView view = Mocks.createCategoryView();
        ResponseEntity<Long> entity = rest.postForEntity(
                Server.url() + RESOURCE_URL + "/add", new HttpEntity<>(view, Server.createHeadersJson()), Long.class);
        view.setId(entity.getBody());

        // Delete category by id.
        ResponseEntity responseEntity = rest.exchange(
                Server.url() + RESOURCE_URL + "/" + view.getId() + "/delete",
                HttpMethod.DELETE, new HttpEntity<>(view, Server.createHeadersJson()), String.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
