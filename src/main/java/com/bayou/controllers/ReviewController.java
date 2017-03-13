package com.bayou.controllers;

import com.bayou.managers.impl.ReviewManager;
import com.bayou.views.ReviewView;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * File: ReviewController
 * Package: com.bayou.controllers
 * Author: Stefan Haselwanter
 * Created on: 3/13/17
 */
@RestController
@RequestMapping("service/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewManager manager = new ReviewManager();

    @ApiOperation(value = "Get a list of reviews", response = ResponseEntity.class)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewView>> getAll() throws NotFoundException {
        ResponseEntity<List<ReviewView>> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(manager.getAll(), HttpStatus.OK);
        } catch (NotFoundException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    @ApiOperation(value = "Get an review by id", response = ResponseEntity.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReviewView> get(@PathVariable("id") Long id) throws NotFoundException {
        ResponseEntity<ReviewView> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(manager.get(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    @ApiOperation(value = "Get a list of user's given reviews", response = ResponseEntity.class)
    @RequestMapping(value = "/reviewer/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewView>> getReviewerReviews(@PathVariable("id") Long id) throws NotFoundException {
        ResponseEntity<List<ReviewView>> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(manager.getAllByReviewer(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    @ApiOperation(value = "Get a list of user's received reviews", response = ResponseEntity.class)
    @RequestMapping(value = "/reviewee/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewView>> getRevieweeReviews(@PathVariable("id") Long id) throws NotFoundException {
        ResponseEntity<List<ReviewView>> responseEntity;
        try {
            responseEntity = new ResponseEntity<>(manager.getAllByReviewee(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    @ApiOperation(value = "Add a review", response = ResponseEntity.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Long> add(@RequestBody ReviewView view) {
        Long id = manager.add(view);

        ResponseEntity<Long> responseEntity;
        if (id > 0)
            responseEntity = new ResponseEntity<>(id, HttpStatus.OK);
        else
            responseEntity = new ResponseEntity<>(id, HttpStatus.CONFLICT);

        return responseEntity;
    }

    @ApiOperation(value = "Update a review", response = ResponseEntity.class)
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public ResponseEntity update(@RequestBody ReviewView view) {
        manager.update(view);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Delete a review", response = ResponseEntity.class)
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        manager.delete(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}