/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Tamara
 */
public interface SightingDao {
    
    Sighting getSightingById(int id) throws DaoException;
    List<Sighting> getAllSightings() throws DaoException;
    Sighting addSighting(Sighting sighting) throws DaoException;
    void updateSighting(Sighting sighting) throws DaoException;
    void deleteSighting(int id) throws DaoException;
    
    List<Sighting> getSightingsByDate(LocalDate date) throws DaoException;
    
}
