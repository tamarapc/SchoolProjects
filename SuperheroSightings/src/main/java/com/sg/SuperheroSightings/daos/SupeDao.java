/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Location;
import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Supe;
import java.util.List;

/**
 *
 * @author Tamara
 */
public interface SupeDao {
    
    Supe getSupeById(int id) throws DaoException;
    List<Supe> getAllSupes() throws DaoException;
    Supe addSupe(Supe supe) throws DaoException;
    void updateSupe(Supe updatedSupe) throws DaoException;
    void deleteSupe(int id) throws DaoException;
    
    List<Supe> getSupesByLoc(Location location) throws DaoException;
    List<Supe> getSupesByOrg(Org organization) throws DaoException;
    
    
    
}
