package com.controllers;

import com.entities.DecoroVassoiEntity;
import com.services.DecoroVassoiService;
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
@RequestMapping("/vassoi")
public class DecoroVassoiController {

    /*
    TODO Finire i Javadoc
    TODO Finire di definire cosa ritornare nei metodi deleteDecoroVassoiById e deleteDecoroItemByEntity
    TODO Finire di aggiornare e da fare il refactor delle ApiResponse
    */

    @Autowired
    DecoroVassoiService decoroService;

    /**
     *
     * <p>
     *     Microservices drop method
     *     that returns all elements of the table
     * </p>
     * @return all elements of the table
     */
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllDecoriVassoi", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroVassoiEntity> getAllDecoriItems(){
        return decoroService.getAllDecoriItems();
    }

    /**
     *
     * <p>
     *     Microservices drop method
     *     that returns all elements of the ids' list passed in input
     * </p>
     * @param ids the list of id to search in database
     * @return all elements of the table whit the ids passed in the parameter
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllDecoriVassoiByIds", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroVassoiEntity> findAllDecoriByIds(@RequestBody Iterable<Integer> ids){
        return decoroService.getDecoriItemsByIDS(ids);
    }

    /**
     *
     * <p>
     *     Microservices drop method
     *     that returns all elements of the ids' list passed in input
     * </p>
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
    public Optional<DecoroVassoiEntity> findDecoroById(@PathVariable int id){
        return decoroService.getDecoriItemById(id);
    }

    /**
     *
     * <p>
     *     This method allow us to save an item into the database
     * </p>
     * @param item the item to save in database
     * @return the saved item
     */
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertDecoriVassoiItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public DecoroVassoiEntity addShoppingItem(@RequestBody DecoroVassoiEntity item) {
        return decoroService.addDecoroItem(item);
    }

    /**
     *
     * <p>
     *     This method allow us to save some items into the database
     * </p>
     * @param itemsToAdd the items to save in database
     * @return the saved items
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{\"id\": 0,\"product\": \"string\",\"quantity\": 0}"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertAllDecoriVassoiItem",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroVassoiEntity> addDecoriVassoi(@RequestBody Iterable<DecoroVassoiEntity> itemsToAdd) {
        return decoroService.addDecoriItems(itemsToAdd);
    }

    /**
     *
     * <p>
     *     This method allow us to delete one item into the database by his id
     * </p>
     * @param id the items to delete in database
     * @return the response code
     * @exception NotFoundException in case the id is not found in the database
     */
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDecoroVassoiById(@PathVariable int id) throws NotFoundException {
        decoroService.deleteShoppingItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * <p>
     *     This method allow us to delete an item into the database
     * </p>
     * @param itemToDelete the items to delete in database
     * @return the response code
     * @exception NotFoundException in case the item to delete not exist
     * @exception NullPointerException in case the item to delete is not exist
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteDecoroItemByEntity")
    public ResponseEntity<String> deleteDecoroItemByEntity(@RequestBody DecoroVassoiEntity itemToDelete) throws NotFoundException, NullPointerException {
        decoroService.deleteDecoroItemByEntity(itemToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * <p>
     *     This method allow us to delete all into the database
     * </p>
     * @return the response code
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity<String> deleteAllDecoriVassoi() throws NotFoundException {
        decoroService.deleteAllDecoroItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * <p>
     *     This method allow us to delete some items into the database
     * </p>
     * @param itemsToDelete the items to delete in database
     * @return the response code
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteAllSelectedDecoroItems")
    public ResponseEntity<String> deleteDecoriVassoiByIds(@RequestBody Iterable<DecoroVassoiEntity> itemsToDelete) {
        decoroService.deleteAllSelectedDecoroItems(itemsToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}