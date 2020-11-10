package com.controllers;

import com.entities.DecoroVassoiEntity;
import com.entities.MerceEntity;
import com.services.MerceService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/merce")
public class MerceController {

    @Autowired
    MerceService service;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<MerceEntity> getShoppingItems(){
        return service.getShoppingItems();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<MerceEntity> findAllMerceByIDS(@RequestBody Iterable<Integer> ids){
        return service.getDecoriItemsByIDS(ids);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<MerceEntity> findAllMerceById(@PathVariable int id){
        return service.getDecoriItemById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MerceEntity addShoppingItem(@RequestBody MerceEntity item) {
        return service.addMerceItem(item);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<MerceEntity> addMerceItems(@RequestBody Iterable<MerceEntity> item) {return service.addMerceItems(item);}

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteShoppingItem(@PathVariable int id) throws NotFoundException {
        try {
            service.deleteShoppingItem(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteDecoroItemByEntity")
    public ResponseEntity<String> deleteDecoroItemByEntity(@RequestBody MerceEntity item) throws NotFoundException {
        try {
            service.deleteDecoroItemByEntity(item);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity<String> deleteShoppingItem() throws NotFoundException {
        service.deleteAllDecoroItems();
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteAllSelectedDecoroItems")
    public ResponseEntity<String> deleteShoppingItem(@RequestBody Iterable<MerceEntity> ids) {
        service.deleteAllSelectedDecoroItems(ids);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
