/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tamara
 */
public class Supe {
    private int id;
    private String name;
    private String description;
    private Power power;
    private List<Org> orgs = new ArrayList();
    private List<Sighting> sightings = new ArrayList();
    
    public boolean inOrg(int orgId){
        return orgs.stream().anyMatch(o -> o.getId() == orgId);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the organizations
     */
    public List<Org> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the organizations to set
     */
    public void setOrgs(List<Org> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the sightings
     */
    public List<Sighting> getSightings() {
        return sightings;
    }

    /**
     * @param sightings the sightings to set
     */
    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    /**
     * @return the superpower
     */
    public Power getPower() {
        return power;
    }

    /**
     * @param power the superpower to set
     */
    public void setPower(Power power) {
        this.power = power;
    }
    
}
