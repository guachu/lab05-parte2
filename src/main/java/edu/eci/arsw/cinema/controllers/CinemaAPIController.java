/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author cristian
 */
@RestController
@RequestMapping(value = "/cinemas")
public class CinemaAPIController {
    
    @Autowired
    CinemaServices cs;    
    
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoCinemas(){
        try {
            
            Map<String,Cinema> cinemas = cs.getAllCinemas();
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(cinemas,HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            System.out.println("quiubosfrewfrewrewr");
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        } 
    }
    
//    @RequestMapping(value="/{name}",method = RequestMethod.GET)
//    public ResponseEntity<?> manejadorGetRecursoCinemaName(@PathVariable String name) throws CinemaException{
//        
//            Cinema cinema = cs.getCinemaByName(name.toString());
//            
//            HttpStatus status = HttpStatus.ACCEPTED;
//            
//            if (cinema == null) {
//                status = HttpStatus.NOT_FOUND;
//                  return new ResponseEntity<>("Error bla bla bla",status);
//            }
//            else{
//                return new ResponseEntity<Cinema>(cinema, status);
//            }
//    
//    }
    
}
