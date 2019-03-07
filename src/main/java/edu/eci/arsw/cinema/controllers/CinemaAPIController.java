/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        } 
    }
    
    @RequestMapping(value="/{name}",method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoCinemaName(@PathVariable String name) throws ResourceNotFoundException{
        try {            
            return new ResponseEntity<>(cs.getCinemaByName(name), HttpStatus.ACCEPTED);
        } catch (CinemaException e) {
            Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, e);            
            return new ResponseEntity<>(new ResourceNotFoundException(e.getMessage()).getMessage(), HttpStatus.NOT_FOUND);
        }   
    
    }
    
    @GetMapping("/{name}/{date}")
    public ResponseEntity<?> manejadorGetDatesMovies(@PathVariable String name, @PathVariable String date) throws ResourceNotFoundException{        
        if (cs.getFunctionsbyCinemaAndDate(name, date).isEmpty()){
            return new ResponseEntity<>(new ResourceNotFoundException("Lo sentimos, para esta fecha no hay funciones").getMessage(), HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(cs.getFunctionsbyCinemaAndDate(name, date), HttpStatus.ACCEPTED);
        }
    }
    
    @GetMapping("/{name}/{date}/{functionName}")
    public ResponseEntity<?> manejadorGetFuntions(@PathVariable String name, @PathVariable String date, @PathVariable String functionName) throws ResourceNotFoundException{        
        if (cs.getFunctionsbyCinemaAndDate(name, date).isEmpty()){
            return new ResponseEntity<>(new ResourceNotFoundException("Lo sentimos, esta funcion no existe en este horario.").getMessage(), HttpStatus.NOT_FOUND);
        }
        else{
             List<CinemaFunction> funciones = cs.getFunctionsbyCinemaAndDate(name, date);
             for (CinemaFunction i: funciones){
                 if(i.getMovie().getName().equals(functionName)){
                     return new ResponseEntity<>(i, HttpStatus.ACCEPTED);
                 }
             }
            return new ResponseEntity<>(new ResourceNotFoundException("Lo sentimos, esta funcion no existe en este horario.").getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    
    //curl -i -X POST -H "Content-Type:application/json" -HAccept:application/json http://localhost:8080/cinemas/cinemaX -d '{"movie":{"name":"Pelicula del intento","genre":"Action"},"seats":[[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true]],"date":"2018-12-18 14:30"}'

    @RequestMapping(value = "/{name}", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<CinemaFunction> manejadorPostFuntion( @PathVariable String name,@RequestBody CinemaFunction funcion) throws CinemaPersistenceException {
            try {
                cs.CreateFuncionInCinema(name, funcion);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (CinemaPersistenceException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }		
	}
        
    @RequestMapping(value = "/{name}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<CinemaFunction> manejadorPutFuntion( @PathVariable String name,@RequestBody CinemaFunction funcion) {
            try {
                cs.getCinemaByName(name).getFunctions().add(funcion);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (CinemaException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }		
	}    
    
}
