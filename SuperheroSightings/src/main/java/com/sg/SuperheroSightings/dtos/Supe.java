/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.dtos;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Tamara
 */
public class Supe {
    private int id;
    private String name;
    private String description;
    private Power superpower;
    private List<Org> organizations;
    private List<Sighting> sightings;

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
    public List<Org> getOrganizations() {
        return organizations;
    }

    /**
     * @param organizations the organizations to set
     */
    public void setOrganizations(List<Org> organizations) {
        this.organizations = organizations;
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
    public Power getSuperpower() {
        return superpower;
    }

    /**
     * @param superpower the superpower to set
     */
    public void setSuperpower(Power superpower) {
        this.superpower = superpower;
    }
    
}
