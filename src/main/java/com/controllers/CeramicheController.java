package com.controllers;

import com.entities.CeramicheEntity;
import com.services.CeramicheService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Jacopo Crocetta
 * @author Edoardo Laurini
 */
@RestController
@RequestMapping("/ceramiche")
public class CeramicheController {

    /*
    TODO Finire i Javadoc
    TODO Finire di definire cosa ritornare nei metodi deleteElementById e deleteCeramicheItem
    TODO Finire di aggiornare e da fare il refactor delle ApiResponse
    */

    @Autowired
    CeramicheService ceramicheService;

    /**
     * <p>
     * Microservices drop method
     * that returns all elements of the table
     * </p>
     *
     * @return all elements of the table
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllCeramiche", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CeramicheEntity> findAllCeramiche() {
        return ceramicheService.getAllCeramicheItems();
    }

    /**
     * <p>
     * Microservices drop method
     * that returns all elements of the ids' list passed in input
     * </p>
     *
     * @param ids the list of id to search in database
     * @return all elements of the table whit the ids passed in the parameter
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findCeramicheByIds", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CeramicheEntity> findAllCeramicheByIds(@RequestBody Iterable<Integer> ids) {
        return ceramicheService.getCeramicheItemsByIDS(ids);
    }

    /**
     * <p>
     * Microservices drop method
     * that returns all elements of the ids' list passed in input
     * </p>
     *
     * @param id the id's item to search in database
     * @return the element with the id passed in the parameter
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<CeramicheEntity> findCeramicheById(@PathVariable int id) {
        return ceramicheService.getCeramicheItemById(id);
    }

    //PUT

    /**
     * <p>
     * This method allow us to save an item into the database
     * </p>
     *
     * @param item the item to save in database
     * @return the saved item
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertCeramiche", produces = MediaType.APPLICATION_JSON_VALUE)
    public CeramicheEntity saveCeramicaItem(@RequestBody CeramicheEntity item) {
        return ceramicheService.addCeramicheItem(item);
    }

    /**
     * <p>
     * This method allow us to save some items into the database
     * </p>
     *
     * @param items the items to save in database
     * @return the saved items
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertLotOfCeramiche", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<CeramicheEntity> saveAllCeramicaItem(@RequestBody Iterable<CeramicheEntity> items) {
        return ceramicheService.addCeramicheItems(items);
    }

    //DELETE

    /**
     * <p>
     * This method allow us to delete one item into the database
     * </p>
     *
     * @param id the items to delete in database
     * @return the response code
     * @throws NotFoundException in case the id is not found in the database
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteElementById(@PathVariable int id) throws NotFoundException {
        ceramicheService.deleteCeramicaItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     * This method allow us to delete all into the database
     * </p>
     *
     * @return the response code
     */
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

    /**
     * <p>
     * This method allow us to delete some items into the database
     * </p>
     *
     * @param itemsToDelete the items to delete in database
     * @return the response code
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteAllByIds")
    public ResponseEntity<String> deleteShoppingItem(@RequestBody Iterable<CeramicheEntity> itemsToDelete) {
        ceramicheService.deleteAllSelectedCeramicheItems(itemsToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>
     * This method allow us to delete an item into the database
     * </p>
     *
     * @param itemToDelete the items to delete in database
     * @return the response code
     * @throws NotFoundException    in case the item to delete not exist
     * @throws NullPointerException in case the item to delete is not exist
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteOneItem")
    public ResponseEntity<String> deleteCeramicheItem(@RequestBody CeramicheEntity itemToDelete) throws NotFoundException, NullPointerException {
        ceramicheService.deleteCeramicheItemByEntity(itemToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
