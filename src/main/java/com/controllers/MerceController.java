package com.controllers;

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

    //TODO: Generare value su ogni f...... metodo

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllMerce",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<MerceEntity> getAllMerci(){
        return service.getAllMerceItems();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllMerceByIds",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<MerceEntity> findAllMerciByIDS(@RequestBody Iterable<Integer> ids){
        return service.getMerceItemsByIDS(ids);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllDecoriVassoiById",produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<MerceEntity> findMerceById(@PathVariable int id){
        return service.getMerceItemById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertMerceItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public MerceEntity addMerceItem(@RequestBody MerceEntity item) {
        return service.addMerceItem(item);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertMoreMerceItems",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<MerceEntity> addMerceItems(@RequestBody Iterable<MerceEntity> items) {return service.addMerceItems(items);}

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMerceItemById(@PathVariable int id) throws NotFoundException {
        try {
            service.deleteMerceItemById(id);
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
    public ResponseEntity<String> deleteMerceItemByEntity(@RequestBody MerceEntity item) throws NotFoundException {
        try {
            service.deleteMerceItemByEntity(item);
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
    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity<String> deleteAllMerceItems() throws NotFoundException {
        service.deleteAllMerceItems();
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteAllSelectedDecoroItems")
    public ResponseEntity<String> deleteMerceItemByIds(@RequestBody Iterable<MerceEntity> ids) {
        service.deleteAllSelectedMerceItems(ids);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
