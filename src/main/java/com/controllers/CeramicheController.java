package com.controllers;

import com.entities.CeramicheEntity;
import com.services.CeramicheService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.util.Optional;

/**
 *
 * @author Jacopo Crocetta
 * @author Edoardo Laurini
 */
@RestController
@RequestMapping("/ceramiche")
public class CeramicheController {

    @Autowired
    CeramicheService ceramicheService;

    //GET
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllCeramiche", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CeramicheEntity> findAllCeramiche(){
        return ceramicheService.getAllCeramicheItems();
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findCeramicheByIds",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CeramicheEntity> findAllCeramicheByIds(@RequestBody Iterable<Integer> ids){
        return ceramicheService.getCeramicheItemsByIDS(ids);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<CeramicheEntity> findCeramicheById(@PathVariable int id){
        return ceramicheService.getCeramicheItemById(id);
    }

    //PUT
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertCeramiche",produces = MediaType.APPLICATION_JSON_VALUE)
    public CeramicheEntity saveCeramicaItem(@RequestBody CeramicheEntity item) {
        return ceramicheService.addCeramicheItem(item);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertLotOfCeramiche",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CeramicheEntity> saveAllCeramicaItem(@RequestBody Iterable<CeramicheEntity> items) {
        return ceramicheService.addCeramicheItems(items);
    }

    //DELETE
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteElementById(@PathVariable int id){
        try {
            ceramicheService.deleteCeramicaItemById(id);
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
    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity<String> deleteAllElement() {
        ceramicheService.deleteAllCeramicheItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteAllByIds")
    public ResponseEntity<String> deleteShoppingItem(@RequestBody Iterable<CeramicheEntity> ids){
        ceramicheService.deleteAllSelectedCeramicheItems(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteOneItem")
    public ResponseEntity<String> deleteShoppingItem(@RequestBody CeramicheEntity itemToDelete){
        try {
            ceramicheService.deleteCeramicheItemByEntity(itemToDelete);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
