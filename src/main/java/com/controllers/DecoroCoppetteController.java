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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DecoroCoppette")
public class DecoroCoppetteController {

    /*
    TODO Finire i Javadoc
    TODO Finire di definire cosa ritornare nei metodi deleteDecoroItemById e deleteDecorItemByEntity
    TODO Finire di aggiornare e da fare il refactor delle ApiResponse
    TODO Testati 6 su 9
    */

    @Autowired
    DecoroCoppetteService decoroService;

    //GET
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
    @GetMapping(value = "/findAllDecoriCoppette",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroCoppetteEntity> getAllDecoriCoppette(){
        return decoroService.getAllDecoriItems();
    }

    /**
     *
     * <p>
     *     Microservices drop method
     *     that returns all elements of the ids' list passed in input
     * </p>
     * @param idsEntity the list of id to search in database
     * @return all elements of the table whit the ids passed in the parameter
     */
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Restituisce tutti i prodotti che ci sono a DB in formato json"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/findAllDecoriByIds",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroCoppetteEntity> findAllDecoriByIds(@RequestBody Iterable<DecoroCoppetteEntity> idsEntity){
        List<Integer> ids = new ArrayList<>();
        for (DecoroCoppetteEntity idsToExtract:idsEntity){
            ids.add(idsToExtract.getId());
        }
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
    public Optional<DecoroCoppetteEntity> findDecoroById(@PathVariable int id){
        return decoroService.getDecoriItemById(id);
    }

    //PUT
    /**
     *
     * <p>
     *     This method allow us to save an item into the database
     * </p>
     * @param item the item to save in database
     * @return the saved item
     */
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/insertOneCeramicheItem",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DecoroCoppetteEntity addDecoroItem(@RequestBody DecoroCoppetteEntity item) {
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
    @PutMapping(value = "/insertAllCeramicheItem",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DecoroCoppetteEntity> addDecoriCoppette(@RequestBody Iterable<DecoroCoppetteEntity> itemsToAdd) {
        return decoroService.addDecoriItems(itemsToAdd);
    }

    //DELETE
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
    public ResponseEntity<String> deleteDecoroItemById(@PathVariable int id) throws NotFoundException {
        //Da capire se sel body della request possiamo fare il return delle eccezioni
        Boolean hasBeenFound = decoroService.deleteDecoroItemById(id);
        ResponseEntity<String> ret = hasBeenFound? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ret;
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
     * @deprecated
     */
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/deleteDecoroCoppetteItemByEntity")
    public ResponseEntity<String> deleteDecorItemByEntity(@RequestBody DecoroCoppetteEntity itemToDelete) throws NotFoundException, NullPointerException{
        //Da capire se sel body della request possiamo fare il return delle eccezioni
        decoroService.deleteDecoroItemByEntity(itemToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO: Inserire controllo su futura superutenza!
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
    @DeleteMapping(value = "/deleteAllDecoriCoppette")
    public ResponseEntity<String> deleteAllDecoroItem(){
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
    public ResponseEntity<String> deleteAllSelectedDecoroItems(@RequestBody Iterable<DecoroCoppetteEntity> itemsToDelete) {
        decoroService.deleteAllSelectedDecoroItems(itemsToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}