/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.service;

import com.sg.SuperheroSightings.daos.DaoException;
import com.sg.SuperheroSightings.daos.OrgDao;
import com.sg.SuperheroSightings.daos.SightingDao;
import com.sg.SuperheroSightings.daos.SupeDao;
import com.sg.SuperheroSightings.dtos.Location;
import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Power;
import com.sg.SuperheroSightings.dtos.Sighting;
import com.sg.SuperheroSightings.dtos.Supe;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tamara
 */
@Component
public class SuperService {

    @Autowired
    SupeDao superDao;

    @Autowired
    SightingDao sightDao;

    @Autowired
    OrgDao orgDao;

    public Response<List<Sighting>> getLastSightings() {
        Response<List<Sighting>> response = new Response();
        try {
            List<Sighting> sightings = sightDao.getAllSightings();
            for(Sighting sight : sightings){
                List<Supe> supers = superDao.getSupesBySighting(sight.getId());
                sight.setSupers(supers);
            }
            sightings.sort(Comparator.comparing(Sighting::getDate, (s1, s2) -> s1.compareTo(s2)).reversed());
            sightings = sightings.stream().limit(10).collect(Collectors.toList());
            response.setData(sightings);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Org> getOrgById(int id) {
        Response<Org> response = new Response();

        try {
            Org org = orgDao.getOrgById(id);
            org.setAllSupers(superDao.getSupesByOrg(org));
            response.setData(org);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Supe> addSuper(Supe supe) {
        Response<Supe> response = new Response();

        try {
            supe = superDao.addSupe(supe);
            response.setSuccess(true);
            response.setData(supe);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<Supe>> getAllSupers() {
        Response<List<Supe>> response = new Response();

        try {
            List<Supe> supers = superDao.getAllSupes();
            response.setData(supers);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response<List<Power>> getAllPowers() {
        Response<List<Power>> response = new Response();

        try {
            List<Power> powers = superDao.getAllPowers();
            response.setData(powers);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<Org>> getAllOrgs() {
        Response<List<Org>> response = new Response();

        try {
            List<Org> orgs = orgDao.getAllOrgs();
            response.setData(orgs);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response<Supe> getSupeById(int id) {
        Response<Supe> response = new Response();

        try {
            Supe supe = superDao.getSupeById(id);
            supe.setOrgs(orgDao.getOrgsForSupe(supe));
            response.setData(supe);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<Supe> updateSuper(Supe supe) {
        Response<Supe> response = new Response();

        try {
            superDao.updateSupe(supe);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response deleteSuper(int id) {
        Response response = new Response();

        try {
            superDao.deleteSupe(id);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response updateOrg(Org org) {
        Response response = new Response();

        try {
            orgDao.updateOrg(org);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response<Org> addOrg(Org org) {
        Response<Org> response = new Response();

        try {
            org = orgDao.addOrg(org);
            response.setData(org);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response deleteOrg(int id) {
        Response response = new Response();

        try {
            orgDao.deleteOrg(id);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<Sighting>> getSightings() {
        Response<List<Sighting>> response = new Response();

        try {
            List<Sighting> sights = sightDao.getAllSightings();
            for(Sighting sight : sights){
                List<Supe> supers = superDao.getSupesBySighting(sight.getId());
                sight.setSupers(supers);
            }
            response.setData(sights);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<Location>> getAllLocs() {
        Response<List<Location>> response = new Response();

        try {
            List<Location> locs = sightDao.getAllLocations();
            response.setData(locs);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;

    }

    public Response<Sighting> addSighting(Sighting sight) {
        Response<Sighting> response = new Response();

        try {
            sight = sightDao.addSighting(sight);
            response.setData(sight);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response<Location> getLocById(int id) {
        Response<Location> response = new Response();

        try {
            Location loc = sightDao.getLocById(id);
            response.setData(loc);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response deleteSighting(int id) {
        Response response = new Response();

        try {
            sightDao.deleteSighting(id);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response updateSighting(Sighting sight) {
        Response response = new Response();

        try {
            sightDao.updateSighting(sight);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response<Sighting> getSightById(int id) {
        Response<Sighting> response = new Response();

        try {
            Sighting sight = sightDao.getSightingById(id);
            sight.setSupers(superDao.getSupesBySighting(id));
            response.setData(sight);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response deletePower(int id) {
        Response response = new Response();
        
        try {
            superDao.deletePower(id);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        
        return response;
    }

    public Response<Power> addPower(Power power) {
        Response<Power> response = new Response();
        
        try {
            power = superDao.addPower(power);
            response.setData(power);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        
        return response;
    }

    public Response<Location> addLocation(Location loc) {
        Response<Location> response = new Response();
        
        try {
            loc = sightDao.addLocation(loc);
            response.setData(loc);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        
        return response;
    }

    public Response deleteLocation(int id) {
        Response response = new Response();
        
        try {
            sightDao.deleteLocation(id);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        
        return response;
    }

    public Response updateLocation(Location loc) {
        Response response = new Response();

        try {
            sightDao.updateLocation(loc);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
        
    }

}
