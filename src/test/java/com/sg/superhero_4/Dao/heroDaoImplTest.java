/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_4.Dao;

import com.sg.superhero_4.model.Hero;
import com.sg.superhero_4.model.Organization;
import com.sg.superhero_4.model.Sighting;
import com.sg.superhero_4.model.SuperPower;
import com.sg.superhero_4.model.Location;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author omish
 */
public class heroDaoImplTest {

    JdbcTemplate jdbcTemplate;
    heroDaoImpl dao;

    public heroDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "test-applicationContext.xml");
        jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        dao = new heroDaoImpl();
        dao.setJdbcTemplate(jdbcTemplate);

        jdbcTemplate.execute("DELETE FROM PowerToHeroes WHERE 1 = 1");
        jdbcTemplate.execute("DELETE FROM SuperPowers WHERE 1 = 1");
        jdbcTemplate.execute("DELETE FROM HeroesToOrganization WHERE 1 = 1");
        jdbcTemplate.execute("DELETE FROM SightingToHero WHERE 1 = 1");
        jdbcTemplate.execute("DELETE FROM Sighting WHERE 1 = 1");
        jdbcTemplate.execute("DELETE FROM Organization WHERE 1 = 1");
        jdbcTemplate.execute("DELETE FROM Location WHERE 1 = 1");
        jdbcTemplate.execute("DELETE FROM Hero WHERE 1 = 1");
    }

    @After
    public void tearDown() {
    }

    //====================================================================================================================
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        //    public RpgDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Test of createHero method, of class Dao.
     */
    @Test
    public void testCreateHero() throws AlreadyExistsException {
        Hero hero = new Hero();
        hero.setName("Dresden");
        hero.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero);

        assertEquals(hero, dao.getHeroById(hero.getId()));
    }

    @Test
    public void testCreateLocation() {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        assertEquals(loc, dao.getLocationById(loc.getId()));
    }

    @Test
    public void testCreateOrganization() throws AlreadyExistsException {
        //create the location

        // MAIN PROBLEM IN LINE 256 DAOIMPL GETORCREATENEWLOCATION
        ///////////////////////////////////
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());

        dao.createOrganization(org, loc);

        assertEquals(org, dao.getOrganizationById(org.getId()));
    }

    @Test
    public void testCreateSighting() {
        //formatted String date-time for sighting;
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        Sighting sight = new Sighting();
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
        sight.setDateTime(timeStamp);
        sight.setLocationID(loc.getId());
    }

    @Test
    public void testCreateSuperPower() throws AlreadyExistsException {
        SuperPower pow = new SuperPower();
        pow.setName("Fuego");

        dao.createSuperPower(pow);

        assertEquals(pow, dao.getPowerById(pow.getId()));
    }

    @Test
    public void testCreateHeroAssociation() throws AlreadyExistsException {
        //location
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        //organization
        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());
        dao.createOrganization(org, loc);

        //get a hero last and add org.
        Hero hero1 = new Hero();
        hero1.setName("Harry Dresden");
        hero1.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero1);

        dao.createHeroAffiliation(hero1, org);

        assertEquals(hero1, dao.getHeroById(hero1.getId()));
    }

    @Test
    public void testGetHeroList() throws AlreadyExistsException {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());

        org = dao.createOrganization(org, loc);
        
        
        Hero hero1 = new Hero();
        hero1.setName("Dresden");
        hero1.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero1);
        dao.createHeroAffiliation(hero1, org);

        Hero hero2 = new Hero();
        hero2.setName("Dresden");
        hero2.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero2);
        dao.createHeroAffiliation(hero2, org);

        assertEquals(2, dao.getHeroList().size());
    }

    @Test
    public void testGetLocations() {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        Location loc2 = new Location();
        loc2.setName("there");
        loc2.setState("Florida-ish");
        loc2.setLatitude(12.29832f);
        loc2.setLongitude(13.323232f);
        loc2.setDescription("it's bleh here");
        loc2.setCounty("Hillsbouroughooooo");
        loc2.setStreet("FiddleSticky");
        loc2.setHouseNumber("777");
        loc2.setZipCode("32152");
        dao.createLocation(loc2);

        assertEquals(2, dao.getLocationList().size());
    }

    @Test
    public void testGetOrganizations() throws AlreadyExistsException {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());

        dao.createOrganization(org, loc);

        assertEquals(org, dao.getOrganizationById(org.getId()));
    }

    @Test
    public void testGetSightings() {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        //Sighting sight = new Sighting();
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
        // sight.setDateTime(timeStamp);
        //sight.setLocationID(loc.getId());     
        dao.createSighting(loc, timeStamp);

        Location loc2 = new Location();
        loc2.setName("re");
        loc2.setState("Florida");
        loc2.setLatitude(12.29832f);
        loc2.setLongitude(13.323232f);
        loc2.setDescription("it's bad here");
        loc2.setCounty("Hillsbofrough");
        loc2.setStreet("Fidicks");
        loc2.setHouseNumber("676");
        loc2.setZipCode("32142");
        dao.createLocation(loc2);

        /// Sighting sight2 = new Sighting();
        String timeStamp2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
        // sight2.setDateTime(timeStamp2);
        // sight2.setLocationID(loc2.getId());   
        dao.createSighting(loc2, timeStamp2);
        assertEquals(2, dao.getSightingList().size());
    }

    @Test
    public void testGetHeroPowers() throws AlreadyExistsException {
        SuperPower pow = new SuperPower();
        pow.setName("Fuego");
        dao.createSuperPower(pow);

        SuperPower pow2 = new SuperPower();
        pow2.setName("PyroFuego");
        dao.createSuperPower(pow2);

        assertEquals(2, dao.getHeroPowerList().size());
    }

    @Test
    public void testGetHeroAssociations() throws AlreadyExistsException {
        //location
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        //organization
        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());
        dao.createOrganization(org, loc);

        //location
        Location loc2 = new Location();
        loc2.setName("re");
        loc2.setState("Florida");
        loc2.setLatitude(12.29832f);
        loc2.setLongitude(13.323232f);
        loc2.setDescription("it's bad here");
        loc2.setCounty("Hillsbofrough");
        loc2.setStreet("Fidicks");
        loc2.setHouseNumber("676");
        loc2.setZipCode("32142");
        dao.createLocation(loc2);

        //organization
        Organization org2 = new Organization();
        org2.setName("Leage of Moral Ambiguity");
        org2.setDescription("We do questionable things");
        org2.setPhone("813-822-2222");
        org2.setLocationId(loc.getId());
        dao.createOrganization(org2, loc2);

        //get a hero last and add org.
        Hero hero1 = new Hero();
        hero1.setName("Harry Dresden");
        hero1.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero1);

        dao.createHeroAffiliation(hero1, org);
        dao.createHeroAffiliation(hero1, org2);

        assertEquals(2, dao.getOrganizationsByHero(hero1.getId()).size());
    }

    @Test
    public void testUpdateHero() throws AlreadyExistsException {
        Hero hero1 = new Hero();
        hero1.setName("Harry Dresden");
        hero1.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero1);

        Hero hero2 = new Hero();
        hero2.setName("Dresden");
        hero2.setDescription("Winter Court knight");
        hero2.setId(hero1.getId());

        dao.updateHero(hero2, hero1.getId());

        assertEquals(dao.getHeroById(hero1.getId()), hero2);
    }

    //todo change better
    @Test
    public void testUpdateLocation() {
        //location
        Location loc2 = new Location();
        loc2.setName("re");
        loc2.setState("Florida");
        loc2.setLatitude(12.29832f);
        loc2.setLongitude(13.323232f);
        loc2.setDescription("it's bad here");
        loc2.setCounty("Hillsbofrough");
        loc2.setStreet("Fidicks");
        loc2.setHouseNumber("676");
        loc2.setZipCode("32142");
        dao.createLocation(loc2);

        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        dao.updateLocation(loc, loc2.getId());
        assertEquals(dao.getLocationById(loc2.getId()), loc);
    }

    @Test
    public void testUpdateOrganization() throws AlreadyExistsException {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());
        dao.createOrganization(org, loc);

        Organization org2 = new Organization();
        org2.setName("League of Moral Ambiguity");
        org2.setDescription("We do questionable things");
        org2.setPhone("813-822-3333");
        org2.setLocationId(loc.getId());
        org2.setId(org.getId());

        dao.updateOrganization(org2, org.getId());
        Organization newOrg = dao.getOrganizationById(org.getId());

        assertEquals(org2, dao.getOrganizationById(org.getId()));
    }

    @Test
    public void testUpdateSighting() {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
        Sighting sigh = dao.createSighting(loc, timeStamp);

        Location loc2 = new Location();
        loc2.setName("here");
        loc2.setState("Florida");
        loc2.setLatitude(12.29832f);
        loc2.setLongitude(13.323232f);
        loc2.setDescription("it's bad here");
        loc2.setCounty("jk");
        loc2.setStreet("Potatoe");
        loc2.setHouseNumber("123");
        loc2.setZipCode("32142");
        dao.createLocation(loc2);

        String timeStamp2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
        Sighting sigh2 = dao.createSighting(loc2, timeStamp2);
        sigh2.setId(sigh.getId());

        dao.updateSighting(sigh2, sigh.getId());
        Sighting newSight = dao.getSightingById(sigh.getId());
        assertEquals(sigh2, newSight);
    }

    @Test
    public void testUpdateHeroSuperPowers() throws AlreadyExistsException {
        SuperPower pow = new SuperPower();
        pow.setName("Fuego");
        dao.createSuperPower(pow);

        SuperPower pow2 = new SuperPower();
        pow2.setName("Blizaga");
        dao.createSuperPower(pow2);
        pow2.setId(pow.getId());

        assertEquals(pow, dao.getPowerById(pow.getId()));
        dao.updateHeroSuperPowers(pow2, pow.getId());

        SuperPower newPower = dao.getPowerById(pow.getId());
        assertEquals(pow2, newPower);
    }

    // not needed just delete and and correct,
//    @Test
//    public void testUpdateHeroAssociation() throws AlreadyExistsException {
//        //location
//        Location loc = new Location();
//        loc.setName("here");
//        loc.setState("Florida");
//        loc.setLatitude(12.29832f);
//        loc.setLongitude(13.323232f);
//        loc.setDescription("it's ok here");
//        loc.setCounty("Hillsbourough");
//        loc.setStreet("FiddleSticks");
//        loc.setHouseNumber("666");
//        loc.setZipCode("32142");
//        dao.createLocation(loc);
//
//        //organization
//        Organization org = new Organization();
//        org.setName("Leage of Moral Ambiguity");
//        org.setDescription("We do questionable things");
//        org.setPhone("813-822-2222");
//        org.setLocationId(loc.getId());
//        dao.createOrganization(org, loc);
//
//        //get a hero last and add org.
//        Hero hero1 = new Hero();
//        hero1.setName("Harry Dresden");
//        hero1.setDescription("Hes awesome, and a wizard");
//        dao.createHero(hero1);
//
//        dao.createHeroAffiliation(hero1, org);
//
//        assertEquals(hero1, dao.getHeroById(hero1.getId()));
//    }
    @Test //(expected = EmptyResultDataAccessException.class)
    public void testDeleteHero() throws AlreadyExistsException {
        Hero hero1 = new Hero();
        hero1.setName("Dresden");
        hero1.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero1);

        assertEquals(hero1, dao.getHeroById(hero1.getId()));

        dao.deleteHero(hero1);
        assertNull(dao.getHeroById(hero1.getId()));
    }

    // why do i need to expect exception here, but not above?
    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteLocation() {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        assertEquals(loc, dao.getLocationById(loc.getId()));

        dao.deleteLocation(loc);
        assertNull(dao.getLocationById(loc.getId()));
    }

    @Test //(expected = EmptyResultDataAccessException.class)
    public void testDeleteOrganization() throws AlreadyExistsException {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());

        dao.createOrganization(org, loc);

        assertEquals(org, dao.getOrganizationById(org.getId()));

        dao.deleteOrganization(org);
        assertNull(dao.getOrganizationById(org.getId()));
    }

    @Test
    public void testDeleteSighting() {
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
        Sighting sight = dao.createSighting(loc, timeStamp);

        dao.deleteSighting(sight);
        assertNull(dao.getSightingById(sight.getId()));
    }

    @Test
    public void testDeleteHeroSuperPowers() throws AlreadyExistsException {
        SuperPower pow = new SuperPower();
        pow.setName("Fuego");

        dao.createSuperPower(pow);

        assertEquals(pow, dao.getPowerById(pow.getId()));

        dao.deleteHeroSuperPowers(pow);
        assertNull(dao.getPowerById(pow.getId()));
    }

    @Test
    public void testDeleteHeroAssociation() throws AlreadyExistsException {
        //location
        Location loc = new Location();
        loc.setName("here");
        loc.setState("Florida");
        loc.setLatitude(12.29832f);
        loc.setLongitude(13.323232f);
        loc.setDescription("it's ok here");
        loc.setCounty("Hillsbourough");
        loc.setStreet("FiddleSticks");
        loc.setHouseNumber("666");
        loc.setZipCode("32142");
        dao.createLocation(loc);

        //organization
        Organization org = new Organization();
        org.setName("Leage of Moral Ambiguity");
        org.setDescription("We do questionable things");
        org.setPhone("813-822-2222");
        org.setLocationId(loc.getId());
        dao.createOrganization(org, loc);

        //get a hero last and add org.
        Hero hero1 = new Hero();
        hero1.setName("Harry Dresden");
        hero1.setDescription("Hes awesome, and a wizard");
        dao.createHero(hero1);

        dao.createHeroAffiliation(hero1, org);

        assertEquals(hero1, dao.getHeroById(hero1.getId()));

        // checks the used hero does not have the association
        dao.deleteHeroAssociation(hero1, org);
        hero1 = dao.getHeroById(hero1.getId());
        assertEquals(0, hero1.getOrganizations().size());

        // checks that the database hero does not have the association.
        assertEquals(0, dao.getHeroById(hero1.getId()).getOrganizations().size());
    }

}
