/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Power;
import com.sg.SuperheroSightings.dtos.Sighting;
import com.sg.SuperheroSightings.dtos.Supe;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class OrgDBDaoTest {

    @Autowired
    SupeDao supeDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrgDao orgDao;

    Org org = new Org();
    List<Org> orgsToSet = new ArrayList();
    Supe firstSupe = new Supe();
    List<Supe> supes = new ArrayList();

    public OrgDBDaoTest() {
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
        
        List<Org> newOrgs = new ArrayList();
        Org orgForSupe = new Org();
        orgForSupe.setName("SupeOrg");
        orgForSupe.setDescription("SupeOrg");
        orgForSupe.setContact("SupeOrg");
        Org addedOrgForSupe = orgDao.addOrg(orgForSupe);
        newOrgs.add(addedOrgForSupe);
        
        
        firstSupe.setName("QuickSilver");
        firstSupe.setDescription("Super super fast");
        Power power = new Power();
        power.setId(1);
        firstSupe.setSuperpower(power);
        firstSupe.setOrganizations(newOrgs);
        Supe addedSupe = supeDao.addSupe(firstSupe);
        supes.add(addedSupe);
        
        org.setName("SuperLeague");
        org.setDescription("SuperSuper");
        org.setContact("Call 555-555-5555");
        Org addedOrg = orgDao.addOrg(org);
        orgsToSet.add(addedOrg);
        addedOrg.setAllSupers(supes);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getOrgById method, of class OrgDBDao.
     */
    @Test
    public void testGetOrgById() {

        try {
            Org org2 = new Org();
            org2.setName("SuperLeague2");
            org2.setDescription("SuperSuper");
            org2.setContact("Call 555-555-5555");
            Org addedOrg = orgDao.addOrg(org2);
            Org toTest = orgDao.getOrgById(addedOrg.getId());

            assertEquals(addedOrg.getId(), toTest.getId());
            assertEquals(addedOrg.getDescription(), toTest.getDescription());
            assertEquals(addedOrg.getName(), toTest.getName());
            assertEquals(addedOrg.getContact(), toTest.getContact());
        } catch (DaoException ex) {
            fail();
        }

    }

    /**
     * Test of getAllOrgs method, of class OrgDBDao.
     */
    @Test
    public void testGetAllOrgs() {

        try {
            
            Org org2 = new Org();
            org2.setName("SuperLeague2");
            org2.setDescription("SuperSuper");
            org2.setContact("Call 555-555-5555");
            Org addedOrg = orgDao.addOrg(org2);
            
            List<Org> allOrgs = orgDao.getAllOrgs();
            assertEquals(3, allOrgs.size());
            Org toTest = allOrgs.get(2);
            assertEquals(addedOrg.getId(), toTest.getId());
            assertEquals(addedOrg.getDescription(), toTest.getDescription());
            assertEquals(addedOrg.getName(), toTest.getName());
            assertEquals(addedOrg.getContact(), toTest.getContact());
            
        } catch (DaoException ex) {
            fail();
        }

    }

    /**
     * Test of addOrg method, of class OrgDBDao.
     */
    @Test
    public void testAddOrg(){
    }

    /**
     * Test of updateOrg method, of class OrgDBDao.
     */
    @Test
    public void testUpdateOrg(){
        
        try {
            org.setName("New Name");
            orgDao.updateOrg(org);
            Org toTest = orgDao.getOrgById(org.getId());
            
            assertEquals(org.getId(), toTest.getId());
            assertEquals(org.getDescription(), toTest.getDescription());
            assertEquals(org.getName(), toTest.getName());
            assertEquals(org.getContact(), toTest.getContact());
        } catch (DaoException ex) {
            fail();
        }
        
    }

    /**
     * Test of deleteOrg method, of class OrgDBDao.
     */
    @Test
    public void testDeleteOrg(){
        try {
            Org org2 = new Org();
            org2.setName("SuperLeague2");
            org2.setDescription("SuperSuper");
            org2.setContact("Call 555-555-5555");
            Org addedOrg = orgDao.addOrg(org2);
            orgDao.deleteOrg(addedOrg.getId());
            List<Org> allOrgs = orgDao.getAllOrgs();
            assertEquals(2, allOrgs.size());
        } catch (DaoException ex) {
            fail();
        }
        
    }

    /**
     * Test of getOrgsForSupe method, of class OrgDBDao.
     */
    @Test
    public void testGetOrgsForSupe() {
        
        try {
            List<Org> orgsForSupe = orgDao.getOrgsForSupe(firstSupe);
            assertEquals(1, orgsForSupe.size());
            Org toTest = orgsForSupe.get(0);
            assertEquals("SupeOrg", toTest.getName());
            
        } catch (DaoException ex) {
            fail();
        }
        
        
        
    }

}
