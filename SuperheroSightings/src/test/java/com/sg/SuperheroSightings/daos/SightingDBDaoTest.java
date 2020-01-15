/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Location;
import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Sighting;
import com.sg.SuperheroSightings.dtos.Supe;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tamara
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDBDaoTest {
    
    @Autowired
    SupeDao supeDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrgDao orgDao;
    
    Location firstLoc = new Location();
    
    public SightingDBDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws DaoException {
        
        List<Supe> allSupers = supeDao.getAllSupes();
        for (Supe supe : allSupers) {
            supeDao.deleteSupe(supe.getId());
        }

        List<Org> allOrgs = orgDao.getAllOrgs();
        for (Org org : allOrgs) {
            orgDao.deleteOrg(org.getId());
        }

        List<Sighting> allSighting = sightingDao.getAllSightings();
        for (Sighting sight : allSighting) {
            sightingDao.deleteSighting(sight.getId());
        }
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSightingById method, of class SightingDBDao.
     */
    @Test
    public void testGetSightingById() throws Exception {
        
        
        
    }

    /**
     * Test of getAllSightings method, of class SightingDBDao.
     */
    @Test
    public void testGetAllSightings() throws Exception {
    }

    /**
     * Test of addSighting method, of class SightingDBDao.
     */
    @Test
    public void testAddSighting() throws Exception {
    }

    /**
     * Test of updateSighting method, of class SightingDBDao.
     */
    @Test
    public void testUpdateSighting() throws Exception {
    }

    /**
     * Test of deleteSighting method, of class SightingDBDao.
     */
    @Test
    public void testDeleteSighting() throws Exception {
    }

    /**
     * Test of getSightingsByDate method, of class SightingDBDao.
     */
    @Test
    public void testGetSightingsByDate() throws Exception {
    }
    
}
