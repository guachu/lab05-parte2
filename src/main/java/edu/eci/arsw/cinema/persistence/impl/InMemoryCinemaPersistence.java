/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.objects.NativeArray;
import org.springframework.stereotype.Service;
import sun.security.pkcs11.wrapper.Functions;

/**
 *
 * @author cristian
 */

@Service
public class InMemoryCinemaPersistence implements CinemaPersitence{
    
    private final Map<String,Cinema> cinemas=new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("cinemaX",functions);
        cinemas.put("cinemaX", c);
        functionDate = "2018-12-18 15:30";
        functions= new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("Mora y el despertar de las moras","Action"),functionDate);
        funct2 = new CinemaFunction(new Movie("MoraYa","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        c=new Cinema("cineMorado",functions);
        cinemas.put("cineMorado", c);
        functionDate = "2018-12-18 15:30";
        functions= new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("Missigno","Action"),functionDate);
        funct2 = new CinemaFunction(new Movie("detective Pikachu","Aventura"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        c=new Cinema("cineDorado",functions);
        cinemas.put("cineDorado", c);
        
        
        
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
        Cinema cine = cinemas.get(cinema);
        List<CinemaFunction> funciones = cine.getFunctions();
        for(CinemaFunction i : funciones){
            if (i.getDate().equals(date) && i.getMovie().getName().equals(movieName)){
                i.buyTicket(row, col);
            }
        }

    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
        Cinema cine = cinemas.get(cinema);
        List<CinemaFunction> listaN= new ArrayList<>();
        List<CinemaFunction> funciones = cine.getFunctions();
        for(CinemaFunction i : funciones){
            if (i.getDate().equals(date)){
                listaN.add(i);
            }
        }
        return listaN;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {      
        if (cinemas.containsKey(name)) {
            return cinemas.get(name);
        }else{
            throw new CinemaPersistenceException("Lo sentimos, el cinema "+ name + " no exciste." );
        }
            

    }

    @Override
    public Map<String, Cinema> getCinemas() {
        return cinemas;
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinema(String cinema) {
        Cinema cine = cinemas.get(cinema);
        return cine.getFunctions();
    }

    @Override
    public void CreateFunctionInCinema(String Cinema, CinemaFunction funcion) throws CinemaPersistenceException{
        try {
            Cinema cine = getCinema(Cinema);
            List<CinemaFunction> funciones = cine.getFunctions();
            funciones.add(funcion);
            cine.setSchedule(funciones);
        } catch (CinemaPersistenceException ex) {
            throw new CinemaPersistenceException("Lo sentimos, el cinema "+ Cinema + " no exciste." );
        }
    }
    
    

}
