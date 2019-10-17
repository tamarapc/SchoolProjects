/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperheroSightings.daos;

import com.sg.SuperheroSightings.dtos.Location;
import com.sg.SuperheroSightings.dtos.Org;
import com.sg.SuperheroSightings.dtos.Power;
import com.sg.SuperheroSightings.dtos.Sighting;
import com.sg.SuperheroSightings.dtos.Supe;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
public class SupeDBDaoTest {

    @Autowired
    SupeDao supeDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrgDao orgDao;

    Supe firstSupe = new Supe();
    List<Supe> supes = new ArrayList();
    List<Org> orgsToSet = new ArrayList();

    public SupeDBDaoTest() {
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

        firstSupe.setName("QuickSilver");
        firstSupe.setDescription("Super super fast");
        Power power = new Power();
        power.setId(1);
        firstSupe.setSuperpower(power);

        Org org = new Org();
        org.setName("SuperLeague");
        org.setDescription("SuperSuper");
        org.setContact("Call 555-555-5555");
        Org addedOrg = orgDao.addOrg(org);
        orgsToSet.add(addedOrg);
        firstSupe.setOrganizations(orgsToSet);
        Supe addedSupe = supeDao.addSupe(firstSupe);
        supes.add(addedSupe);
        
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSupeById method, of class SupeDBDao.
     */
    @Test
    public void testGetSupeById() {

        try {
            Supe supe = new Supe();
            supe.setName("Flash");
            Power power = new Power();
            power.setId(1);
            supe.setSuperpower(power);

            supe.setOrganizations(orgsToSet);
            supe.setDescription("Can go back in time thanks to SuperSpeed");

            Supe addedSupe = supeDao.addSupe(supe);
            Supe toTest = supeDao.getSupeById(addedSupe.getId());

            assertEquals(addedSupe.getId(), toTest.getId());
            assertEquals(addedSupe.getName(), toTest.getName());
            assertEquals(addedSupe.getSuperpower().getId(), toTest.getSuperpower().getId());
            assertEquals(addedSupe.getDescription(), toTest.getDescription());
        } catch (DaoException ex) {
            fail();
        }

    }

    /**
     * Test of getAllSupes method, of class SupeDBDao.
     */
    @Test
    public void testGetAllSupes() {
        try {
            Supe supe = new Supe();
            supe.setName("Flash");
            Power power = new Power();
            power.setId(1);
            supe.setSuperpower(power);
            
            supe.setOrganizations(orgsToSet);
            supe.setDescription("Can go back in time thanks to SuperSpeed");

            Supe addedSupe = supeDao.addSupe(supe);
            List<Supe> supes = supeDao.getAllSupes();

            assertEquals(2, supes.size());
            Supe toTest = supes.get(1);

            assertEquals(addedSupe.getId(), toTest.getId());
            assertEquals(addedSupe.getName(), toTest.getName());
            assertEquals(addedSupe.getSuperpower().getId(), toTest.getSuperpower().getId());
            assertEquals(addedSupe.getDescription(), toTest.getDescription());
        } catch (DaoException ex) {
            fail();
        }
    }

    /**
     * Test of addSupe method, of class SupeDBDao.
     */
    @Test
    public void testAddSupe() {
    }

    /**
     * Test of updateSupe method, of class SupeDBDao.
     */
    @Test
    public void testUpdateSupe() {
        try {
            Supe supe = new Supe();
            supe.setName("Flash");
            Power power = new Power();
            power.setId(1);
            supe.setSuperpower(power);
           
            supe.setOrganizations(orgsToSet);
            supe.setDescription("Can go back in time thanks to SuperSpeed");

            Supe updateSupe = supeDao.addSupe(supe);
            updateSupe.setName("Flashy");
            supeDao.updateSupe(updateSupe);
            Supe toTest = supeDao.getSupeById(updateSupe.getId());

            assertEquals(updateSupe.getId(), toTest.getId());
            assertEquals(updateSupe.getName(), toTest.getName());
            assertEquals(updateSupe.getSuperpower().getId(), toTest.getSuperpower().getId());
            assertEquals(updateSupe.getDescription(), toTest.getDescription());
        } catch (DaoException ex) {
            fail();
        }

    }

    /**
     * Test of deleteSupe method, of class SupeDBDao.
     */
    @Test
    public void testDeleteSupe() {

        try {
            Supe supe = new Supe();
            supe.setName("Flash");
            Power power = new Power();
            power.setId(1);
            supe.setSuperpower(power);

            supe.setOrganizations(orgsToSet);
            supe.setDescription("Can go back in time thanks to SuperSpeed");

            Supe addedSupe = supeDao.addSupe(supe);
            supeDao.deleteSupe(addedSupe.getId());

            List<Supe> supes2 = supeDao.getAllSupes();
            assertEquals(1, supes2.size());
        } catch (DaoException ex) {
            fail();
        }
    }

    /**
     * Test of getSupesByLoc method, of class SupeDBDao.
     */
    @Test
    public void testGetSupesByLoc() {
        try {


            List<Sighting> sightings = new ArrayList();
            Sighting sight = new Sighting();
            Location loc = new Location();
            loc.setId(1);
            sight.setLocation(loc);
            sight.setDate(LocalDate.now());
            sight.setSupers(supes);

            Sighting addedSight = sightingDao.addSighting(sight);
            sightings.add(addedSight);

            List<Supe> supesByLoc = supeDao.getSupesByLoc(loc);

//            assertEquals(1, supesByLoc.size());
            Supe toTest = supesByLoc.get(0);

            assertEquals(firstSupe.getId(), toTest.getId());
            assertEquals(firstSupe.getName(), toTest.getName());
            assertEquals(firstSupe.getSuperpower().getId(), toTest.getSuperpower().getId());
            assertEquals(firstSupe.getDescription(), toTest.getDescription());

        } catch (DaoException ex) {
            fail();
        }

    }

    /**
     * Test of getSupesByOrg method, of class SupeDBDao.
     */
    @Test
    public void testGetSupesByOrg() {

        try {
            Supe supe = new Supe();
            supe.setName("Flash");
            Power power = new Power();
            power.setId(1);
            supe.setSuperpower(power);
            Org org = new Org();
            org.setName("SuperLeague2");
            org.setDescription("SuperSuper");
            org.setContact("Call 555-555-5555");
            Org addedOrg = orgDao.addOrg(org);
            orgsToSet.add(addedOrg);
            supe.setOrganizations(orgsToSet);
            supe.setDescription("Can go back in time thanks to SuperSpeed");

            Supe addedSupe = supeDao.addSupe(supe);
            List<Supe> supesByOrg = supeDao.getSupesByOrg(addedOrg);

//            assertEquals(1, supesByOrg.size());
            Supe toTest = supesByOrg.get(0);

            assertEquals(addedSupe.getId(), toTest.getId());
            assertEquals(addedSupe.getName(), toTest.getName());
            assertEquals(addedSupe.getSuperpower().getId(), toTest.getSuperpower().getId());
            assertEquals(addedSupe.getDescription(), toTest.getDescription());

        } catch (DaoException ex) {
            fail();
        }

    }

}
