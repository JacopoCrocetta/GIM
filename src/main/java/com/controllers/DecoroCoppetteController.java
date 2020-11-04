package com.controllers;

import com.entities.DecoroCoppetteEntity;
import com.services.DecoroCoppetteService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/decoro-coppette")
public class DecoroCoppetteController {
    @Autowired
    DecoroCoppetteService decoroService;

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroCoppetteEntity> getShoppingItems(){
        return decoroService.getShoppingItems();
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DecoroCoppetteEntity addShoppingItem(@RequestBody DecoroCoppetteEntity item) {
        return decoroService.addShoppingItem(item);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteShoppingItem(@PathVariable int id) throws NotFoundException {
        try {
            decoroService.deleteShoppingItem(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
}