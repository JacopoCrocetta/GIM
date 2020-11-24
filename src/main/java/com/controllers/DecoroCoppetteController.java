package com.controllers;

import com.entities.DecoroCoppetteEntity;
import com.services.DecoroCoppetteService;
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
@RequestMapping("/DecoroCoppette")
public class DecoroCoppetteController {
    @Autowired
    DecoroCoppetteService decoroService;

    //GET
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllDecoriCoppette",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroCoppetteEntity> getAllDecoriCoppette(){
        return decoroService.getAllDecoriItems();
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllDecoriByIds",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroCoppetteEntity> findAllDecoriByIds(@RequestBody Iterable<Integer> ids){
        return decoroService.getDecoriItemsByIDS(ids);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<DecoroCoppetteEntity> findDecoroById(@PathVariable int id){
        return decoroService.getDecoriItemById(id);
    }

    //PUT
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertOneCeramicheItem",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DecoroCoppetteEntity addDecoroItem(@RequestBody DecoroCoppetteEntity item) {
        return decoroService.addDecoroItem(item);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertAllCeramicheItem",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroCoppetteEntity> addDecoriCoppette(@RequestBody Iterable<DecoroCoppetteEntity> itemsToAdd) {
        return decoroService.addDecoriItems(itemsToAdd);
    }

    //DELETE
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDecoroItemById(@PathVariable int id) {
        try {
            decoroService.deleteDecoroItemById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteDecoroCoppetteItemByEntity")
    public ResponseEntity<String> deleteDecorItemByEntity(@RequestBody DecoroCoppetteEntity item){
        try {
            decoroService.deleteDecoroItemByEntity(item);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/deleteAllDecoriCoppette")
    public ResponseEntity<String> deleteShoppingItem(){
        decoroService.deleteAllDecoroItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteAllSelectedDecoroItems")
    public ResponseEntity<String> deleteShoppingItem(@RequestBody Iterable<DecoroCoppetteEntity> ids) {
        decoroService.deleteAllSelectedDecoroItems(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}