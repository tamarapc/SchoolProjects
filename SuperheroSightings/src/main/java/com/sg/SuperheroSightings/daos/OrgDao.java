/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Supe;
import java.util.List;

/**
 *
 * @author Tamara
 */
public interface OrgDao {
    
    Org getOrgById(int id) throws DaoException;
    List<Org> getAllOrgs() throws DaoException;
    Org addOrg(Org org) throws DaoException;
    void updateOrg(Org org) throws DaoException;
    void deleteOrg(int id) throws DaoException;
    
    List<Org> getOrgsForSupe(Supe supe) throws DaoException;
    
}
