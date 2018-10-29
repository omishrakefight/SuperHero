/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_4.Dao;

import com.sg.superhero_4.model.Hero;
import com.sg.superhero_4.model.Location;
import com.sg.superhero_4.model.Organization;
import com.sg.superhero_4.model.Sighting;
import com.sg.superhero_4.model.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author omish
 */
public class heroDaoImpl implements heroDao {

    // Imports
    private JdbcTemplate jdbcTemplate;

    public heroDaoImpl() {
    }

    //Prepaired Statements
    private static final class SqlQueries {

        private static final String SQL_INSERT_NEW_HERO
                = "INSERT INTO hero "
                + "(heroName, heroDescription) "
                + "VALUES (?, ?)";
        private static final String SQL_INSERT_NEW_LOCATION
                = "INSERT INTO location "
                + "(latitude, longitude, placeDescription, placeName, state, county, street, houseNumber, zipCode) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        private static final String SQL_INSERT_NEW_ORGANIZATION
                = "INSERT INTO organization "
                + "(organizationName, organizationDescription, organizationPhone, FK_Location) "
                + "VALUES (?, ?, ?, ?)";
        private static final String SQL_INSERT_NEW_SIGHTING
                = "INSERT INTO sighting "
                + "(FK_Location, times) "
                + "VALUES (?, ?)";
        private static final String SQL_INSERT_NEW_POWER
                = "INSERT INTO SuperPowers "
                + "(powerName) "
                + "VALUES (?)";
        private static final String SQL_INSERT_NEW_AFFILIATION
                = "INSERT INTO HeroesToOrganization "
                + "(FK_Hero, FK_Organization) "
                + "VALUES (?, ?)";
        private static final String SQL_INSERT_NEW_POWER_ASSOCIATION
                = "INSERT INTO PowerToHeroes "
                + "(FK_Power, FK_Hero) "
                + "VALUES (?, ?)";
        private static final String SQL_INSERT_NEW_HERO_SIGHITNG
                = "INSERT INTO SightingToHero "
                + "(FK_Hero, FK_Sighting) "
                + "VALUES (?, ?)";

        //   READ METHODS
        private static final String SQL_GET_SIGHTINGS_HOME_PAGE
                = "SELECT * FROM sighting "
                + "GROUP BY times "
                + "ORDER by times DESC "
                + "LIMIT 10";
        private static final String SQL_GET_ALL_HEROES
                = "SELECT * FROM hero ";
        private static final String SQL_GET_ALL_LOCATIONS
                = "SELECT * FROM location";
        private static final String SQL_GET_ALL_ORGANIZATIONS
                = "SELECT * FROM organization";
        private static final String SQL_GET_ALL_SIGHTINGS
                = "SELECT * FROM sighting";
        private static final String SQL_GET_ALL_POWER_LIST
                = "SELECT * FROM SuperPowers";
        private static final String SQL_GET_ALL_HERO_POWER_LIST
                = "SELECT * FROM SuperPowers "
                + "INNER JOIN PowerToHeroes on FK_Power = SuperPowers.id "
                + "INNER JOIN hero on FK_Hero = hero.id "
                + "WHERE hero.id = ?";
        private static final String SQL_GET_ALL_HERO_ASSOCIATIONS
                = "SELECT * FROM organization "
                + "INNER JOIN HeroesToOrganization on HeroesToOrganization.FK_Organization = organization.id"
                + "WHERE hero.id = ?";

        private static final String SQL_GET_HERO_BY_ID
                = "SELECT * FROM hero "
                + "WHERE id = ?";
        private static final String SQL_GET_LOCATION_BY_ID
                = "SELECT * FROM location "
                + "WHERE id = ?";
        private static final String SQL_GET_ORGANIZATION_BY_ID
                = "SELECT * FROM organization "
                + "WHERE id = ?";
        private static final String SQL_GET_SIGHTING_BY_ID
                = "SELECT * FROM sighting "
                + "WHERE id = ?";
        private static final String SQL_GET_POWER_BY_ID
                = "SELECT * FROM SuperPowers "
                + "WHERE id = ?";
        private static final String SQL_GET_HEROES_AND_POWERS
                = "SELECT * FROM hero "
                + "INNER JOIN PowerToHeroes ON PowerToHeroes.FK_Hero = hero.id "
                + "INNER JOIN SuperPowers ON SuperPowers.id = PowerToHeroes.FK_Power";
        private static final String SQL_GET_LOCATION_BY_ADDRESS
                = "SELECT location.id FROM location "
                + "WHERE placeName = ? AND state = ? AND county = ? AND street = ? AND houseNumber = ? AND zipCode = ?";
        //speacialty searches
        private static final String SQL_GET_ORGANIZATION_BY_LOCATION
                = "SELECT organization.id FROM organization "
                + "INNER JOIN location ON organization.FK_Location = location.id "
                + "WHERE location.id = ?";
        private static final String SQL_GET_HERO_ASSOCIATION_BY_ID
                = "SELECT * FROM organization "
                + "INNER JOIN HeroesToOrganization ON organization.id = HeroesToOrganization.FK_Organization "
                + "INNER JOIN Hero on Hero.id = HeroesToOrganization.FK_Hero "
                + "WHERE hero.id = ?";
        private static final String SQL_GET_HERO_LIST_BY_SIGHTING
                = "SELECT * FROM hero "
                + "INNER JOIN SightingToHero ON Hero.id = SightingToHero.FK_Hero "
                + "INNER JOIN Sighting on Sighting.id = SightingToHero.FK_Sighting "
                + "WHERE Sighting.id = ?";
        private static final String SQL_GET_LOCATIONS_FOR_A_HERO
                = "SELECT * FROM location "
                + "INNER JOIN sighting on location.id = sighting.FK_Location "
                + "INNER JOIN SightingToHero on SightingToHero.FK_Sighting = sighting.id "
                + "WHERE sightingToHero.FK_hero = ?";

        /// MAYBE DO THIS ONE IN 2 PARTS? HAVE TO GET HEROS AND LOCATIONS.
        private static final String SQL_GET_ALL_SIGHTING_FOR_A_DATE
                = "SELECT * FROM Sighting "
                + "WHERE times = ?";
        //WORK ON IT

        private static final String SQL_GET_MEMBERS_OF_ORGANIZATION
                = "SELECT * FROM hero "
                + "INNER JOIN HeroesToOrganization on HeroesToOrganization.FK_Hero = hero.id "
                + "INNER JOIN Organization on Organization.id = HeroesToOrganization.FK_Organization "
                + "WHERE organization.id = ?";
        private static final String SQL_GET_ORGANIZATIONS_FOR_A_HERO
                = "SELECT * from organization "
                + "INNER JOIN HeroesToOrganization ON HeroesToOrganization.FK_Organization = organization.id "
                + "INNER JOIN hero on hero.id = HeroesToOrganization.FK_Hero "
                + "WHERE hero.id = ?";
        //  DELETE METHODS
        private static final String SQL_DELETE_HERO
                = "DELETE FROM hero WHERE id = ?";
        private static final String SQL_DELETE_LOCATION
                = "DELETE FROM location WHERE id = ?";
        private static final String SQL_DELETE_ORGANIZATION
                = "DELETE FROM organization WHERE id = ?";
        private static final String SQL_DELETE_SIGHTING
                = "DELETE FROM sighting WHERE id = ?";
        private static final String SQL_DELETE_SUPERPOWER
                = "DELETE FROM SuperPowers WHERE id = ?";
        private static final String SQL_DELETE_POWERS_TO_A_HERO
                = "DELETE FROM PowerToHeroes WHERE FK_Hero = ?";
        private static final String SQL_DELETE_POWER_FROM_HEROES
                = "DELETE FROM PowerToHeroes WHERE FK_Power = ?";
        private static final String SQL_DELETE_SIGHTING_TO_HERO_BY_HERO
                = "DELETE FROM SightingToHero WHERE FK_Hero = ?";
        private static final String SQL_DELETE_SIGHTING_TO_HERO_BY_SIGHTING
                = "DELETE FROM SightingToHero WHERE FK_Sighting = ?";
        private static final String SQL_DELETE_SIGHTING_BY_LOCATION
                = "DELETE FROM sighting WHERE FK_location = ?";
        private static final String SQL_DELETE_SIGHTING_BY_DATE
                = "DELETE FROM sighting WHERE date = ?";
        private static final String SQL_DELETE_HERO_FROM_ALL_ORGANIZATIONS
                = "DELETE FROM HeroesToOrganization WHERE FK_Hero = ?";
        private static final String SQL_DELETE_ORGANIZATION_BY_LOCATION
                = "DELETE FROM Organization WHERE FK_Location = ?";
        private static final String SQL_DELETE_EVERYONE_FROM_ORGANIZATION
                = "DELETE FROM HeroesToOrganization WHERE FK_Organization = ?";
        private static final String SQL_DELETE_HERO_FROM_AN_ORGANIZATION
                = "DELETE FROM HeroesToOrganization WHERE FK_Hero = ? AND FK_Organization = ?";
        //    
        //==============================
        // UPDATE METHODS
        //==============================
        private static final String SQL_UPDATE_HERO
                = "UPDATE hero SET "
                + "heroName = ?, heroDescription = ? "
                + "WHERE id = ?";
        private static final String SQL_UPADATE_LOCATION
                = "UPDATE location SET "
                + "latitude = ?, longitude = ?, placeDescription = ?, placeName = ?, state = ?, county = ?, street = ?, houseNumber = ?, zipCode = ? "
                + "where id = ?";
        private static final String SQL_UPDATE_ORGANIZATION
                = "UPDATE organization SET "
                + "organizationName = ?, organizationDescription = ?, organizationPhone = ?, FK_Location = ? "
                + "WHERE id = ?";
        private static final String SQL_UPDATE_SIGHTING
                = "UPDATE sighting SET "
                + "FK_Location = ?, times = ? "
                + "WHERE id = ?";
        private static final String SQL_UPDATE_POWER
                = "UPDATE SuperPowers SET "
                + "powerName = ? "
                + "WHERE id = ?";

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        System.err.println("WE HAVE ARE PUTTING IN A JDBCTEMPLATE");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero createHero(Hero hero) throws AlreadyExistsException {
        try {
            jdbcTemplate.update(
                    SqlQueries.SQL_INSERT_NEW_HERO,
                    hero.getName(), 
                    hero.getDescription()
            );
            hero.setId(getLastInsertId());
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("That Power already exists");
        }
        return hero;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createHeroSighting(Hero hero, Sighting sight) throws AlreadyExistsException {
        try {
            jdbcTemplate.update(
                    SqlQueries.SQL_INSERT_NEW_HERO_SIGHITNG,
                    hero.getId(),
                    sight.getId()
            );
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("That Power already exists");
        }
    }

//  C-r-u-d Create opperations
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location createLocation(Location location) {
        jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_LOCATION,
                location.getLatitude(),
                location.getLongitude(),
                location.getDescription(),
                location.getName(),
                location.getState(),
                location.getCounty(),
                location.getStreet(),
                location.getHouseNumber(),
                location.getZipCode()
        );
        location.setId(getLastInsertId());
        return location;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization createOrganization(Organization organization, Location location) throws AlreadyExistsException {

//         this method handles checking / creating a location.  Its cool.  Yay.
        int addressId = getOrCreateNewLocation(location);
        organization.setLocationId(addressId);
        try {
            //once the organization location is added, add the organization itself and key to location.
            jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_ORGANIZATION,
                    organization.getName(),
                    organization.getDescription(),
                    organization.getPhone(),
                    organization.getLocationId()
            );
            organization.setId(getLastInsertId());
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("That Power already exists");
        }
        return organization;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting createSighting(Location location, String timeStamp) {
        int addressId = getOrCreateNewLocation(location);

        jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_SIGHTING,
                addressId,
                timeStamp
        );
        //create a sighting here with locatino and timestamp.
        Sighting sigh = new Sighting();
        sigh.setDateTime(timeStamp);
        sigh.setLocation(location);
        sigh.setLocationID(addressId);
        //give it the ID and return
        int newId = getLastInsertId();
        sigh.setId(newId);
        return sigh;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperPower createSuperPower(SuperPower power) throws AlreadyExistsException {
        try {
            jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_POWER,
                    power.getName()
            );
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("That Power already exists");
        }

        int newId = getLastInsertId();
        power.setId(newId);
        return power;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createHeroAffiliation(Hero hero, Organization organization) throws AlreadyExistsException {
        try {
            jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_AFFILIATION,
                    hero.getId(),
                    organization.getId()
            );
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("That association already exists");
        }
        hero.addOrganization(organization);
    }

    @Override
    public void createHeroPowerAssociation(SuperPower power, Hero hero) throws AlreadyExistsException {
        try {
            jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_POWER_ASSOCIATION,
                    power.getId(),
                    hero.getId());
            hero.addSuperPower(power);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("That Power already exists");
        }
    }

//  c-R-u-d  Read opperations
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Hero> getHeroList() {
        List<Hero> heroList; // = new ArrayList<>();
        Map<Integer, SuperPower> superPowers = new HashMap<>();
        try {
            heroList = jdbcTemplate.query(SqlQueries.SQL_GET_ALL_HEROES,
                    new HeroMapper()
            );
            for (Hero hero : heroList) {
                // for each hero get a list of all his powers.
                try {
                    List<SuperPower> powers = jdbcTemplate.query(
                            SqlQueries.SQL_GET_ALL_HERO_POWER_LIST,
                            new PowerMapper(),
                            hero.getId()
                    );
                    hero.setSuperPowers(powers);
                } catch (EmptyResultDataAccessException | java.lang.NullPointerException ex) {
                    //nothing, null value in powers is ok
                }
                // second try catch because he can have 0 powers and stil lan organization
                try {
                    List<Organization> orgs = jdbcTemplate.query(
                            SqlQueries.SQL_GET_HERO_ASSOCIATION_BY_ID,
                            new OrganizationMapper(),
                            hero.getId()
                    );
                    hero.setOrganizations(orgs);
                } catch (EmptyResultDataAccessException | java.lang.NullPointerException ex) {
                    //nothing, null value in powers is ok
                }
            }
            // errors EmptyResultDataAccessException | java.lang.NullPointerException
        } catch (Exception ex) {
            return null;
        }
        return heroList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Location> getLocationList() {
        List<Location> locations;
        try {
            locations = jdbcTemplate.query(
                    SqlQueries.SQL_GET_ALL_LOCATIONS, new LocationMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            //nothing, null value in powers is ok
            locations = null;
        }
        return locations;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Organization> getOrganizationList() {
        List<Organization> organizations;
        try {
            organizations = jdbcTemplate.query(
                    SqlQueries.SQL_GET_ALL_ORGANIZATIONS, new OrganizationMapper()
            );
            for (Organization org : organizations) {
                org.setLocation(getLocationById(org.getLocationId()));
            }
        } catch (EmptyResultDataAccessException ex) {
            //nothing, null value in powers is ok
            organizations = null;
        }
        return organizations;
    }
//   todo   come back and add heroes / location?

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Sighting> getSightingList() {
        List<Sighting> sightings;
        try {
            sightings = jdbcTemplate.query(
                    SqlQueries.SQL_GET_ALL_SIGHTINGS, new SightingMapper()
            );
            // for each sighting use the ID's to fill in lcoatino and heroes.
            for (Sighting sigh : sightings) {
                sigh.setLocation(getLocationById(sigh.getLocationID()));
                List<Hero> heroes = getHeroListBySighting(sigh);
                for (Hero hero : heroes) {
                    sigh.addHeroSeen(hero);
                }
            }
        } catch (EmptyResultDataAccessException ex) {
            //nothing, null value in powers is ok
            sightings = null;
        }
        return sightings;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<SuperPower> getHeroPowerList() {
        List<SuperPower> powers;
        try {
            powers = jdbcTemplate.query(
                    SqlQueries.SQL_GET_ALL_POWER_LIST,
                    new PowerMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            //nothing, null value in powers is ok
            powers = null;
        }
        return powers;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void getHeroAssociationList(Hero hero) {
        List<Organization> organizations;
        try {
            //get all organizations
            organizations = jdbcTemplate.query(SqlQueries.SQL_GET_ORGANIZATIONS_FOR_A_HERO,
                    new OrganizationMapper(),
                    hero.getId()
            );
            //add them to the specific hero.
            for (Organization org : organizations) {
                hero.addOrganization(org);
            }
        } catch (EmptyResultDataAccessException ex) {
            organizations = null;
        }
    }

    public List<SuperPower> getPowersForAHero(Hero hero) {
        List<SuperPower> powers;
        try {
            powers = jdbcTemplate.query(SqlQueries.SQL_GET_ALL_HERO_POWER_LIST,
                    new PowerMapper(),
                    hero.getId()
            );
            for (SuperPower pow : powers) {
                hero.addSuperPower(pow);
            }
        } catch (EmptyResultDataAccessException ex) {
            powers = null;
        }
        return powers;
    }

    // single reads
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero getHeroById(int id) {
        Hero hero;
        try {
            hero = jdbcTemplate.queryForObject(SqlQueries.SQL_GET_HERO_BY_ID,
                    new HeroMapper(),
                    id);
            // pass in hero and this adds things to the DTO's
            getHeroAssociationList(hero);
            getPowersForAHero(hero);
        } catch (EmptyResultDataAccessException ex) {
            hero = null;
        }
        return hero;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location getLocationById(int id) {
        Location loc;
        try {
            loc = jdbcTemplate.queryForObject(SqlQueries.SQL_GET_LOCATION_BY_ID,
                    new LocationMapper(),
                    id);
        } catch (EmptyResultDataAccessException ex) {
            loc = null;
        }
        return loc;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization getOrganizationById(int id) {
        Organization org;
        try {
            org = jdbcTemplate.queryForObject(SqlQueries.SQL_GET_ORGANIZATION_BY_ID,
                    new OrganizationMapper(),
                    id);
            org.setLocation(getLocationById(org.getLocationId()));
        } catch (EmptyResultDataAccessException ex) {
            org = null;
        }
        return org;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting getSightingById(int id) {
        Sighting sigh;
        try {
            sigh = jdbcTemplate.queryForObject(SqlQueries.SQL_GET_SIGHTING_BY_ID,
                    new SightingMapper(),
                    id);
            Location loc = getLocationById(sigh.getLocationID());
            sigh.setLocation(loc);
            sigh.setLocationID(loc.getId());
        } catch (EmptyResultDataAccessException ex) {
            sigh = null;
        }
        return sigh;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperPower getPowerById(int id) {
        SuperPower pow;
        try {
            pow = jdbcTemplate.queryForObject(SqlQueries.SQL_GET_POWER_BY_ID,
                    new PowerMapper(),
                    id);
        } catch (EmptyResultDataAccessException ex) {
            pow = null;
        }
        return pow;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Sighting> getHeroesForHome() {
        List<Sighting> sights;
        sights = jdbcTemplate.query(SqlQueries.SQL_GET_SIGHTINGS_HOME_PAGE,
                new SightingMapper());
        for(Sighting sight: sights) {
            List<Hero> heroes = jdbcTemplate.query(SqlQueries.SQL_GET_HERO_LIST_BY_SIGHTING,
                    new HeroMapper(),
                    sight.getId()
                    );
            sight.setLocation(getLocationById(sight.getLocationID()));
            sight.setHeroesSeen(heroes);
        }
        return sights;
    }

    //speacialty reads
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Hero> getHeroListBySighting(Sighting sigh) {
        List<Hero> heroesSighted;
        try {
            // links to sights by sigh ID to heroID , lists heroes
            heroesSighted = jdbcTemplate.query(SqlQueries.SQL_GET_HERO_LIST_BY_SIGHTING,
                    new HeroMapper(),
                    sigh.getId());

            // fill in hero super powers
            for (Hero hero : heroesSighted) {
                getHeroAssociationList(hero);
                getPowersForAHero(hero);
            }

        } catch (EmptyResultDataAccessException ex) {
            heroesSighted = null;
        }
        return heroesSighted;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Location> getLocationsForAHero(Hero hero) {
        List<Location> locs;
        try {
            // links locations by bridge to sightings to heroes to get locations.
            locs = jdbcTemplate.query(SqlQueries.SQL_GET_LOCATIONS_FOR_A_HERO,
                    new LocationMapper(),
                    hero.getId());

        } catch (EmptyResultDataAccessException ex) {
            locs = null;
        }
        return locs;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Sighting> getAllSightingForADate(String date) {
        List<Sighting> sigh;
        Map<Integer, Hero> heroesSeen = new HashMap<>();
        try {
            // get all sightings based off date
            sigh = jdbcTemplate.query(SqlQueries.SQL_GET_ALL_SIGHTING_FOR_A_DATE,
                    new SightingMapper(),
                    date);
            for (Sighting sight : sigh) {
                //for each sighting set location
                sight.setLocation(
                        jdbcTemplate.queryForObject(SqlQueries.SQL_GET_LOCATION_BY_ID,
                                new LocationMapper(),
                                sight.getLocationID()));
                // then set each hero at the sighting.
                List<Hero> heroes = new ArrayList<>();
                try {
                    heroes = jdbcTemplate.query(SqlQueries.SQL_GET_HERO_LIST_BY_SIGHTING,
                            new HeroMapper(),
                            sight.getId());
                    //put into map
//                    for (Hero hero : heroes) {
//                        heroesSeen.put(hero.getId(), hero);
//                    }
                    //null is OK your cool, man.
                } catch (EmptyResultDataAccessException ex) {
                    heroesSeen = null;
                }
                sight.setHeroesSeen(heroes);
            }
        } catch (EmptyResultDataAccessException ex) {
            sigh = null;
        }
        return sigh;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Hero> getOrganizationMembers(int id) {
        List<Hero> heroes;
        try {
            heroes = jdbcTemplate.query(SqlQueries.SQL_GET_MEMBERS_OF_ORGANIZATION,
                    new HeroMapper(),
                    id);
            for (Hero hero : heroes) {
                getHeroAssociationList(hero);
                getPowersForAHero(hero);
            }
        } catch (EmptyResultDataAccessException ex) {
            heroes = null;
        }
        return heroes;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Organization> getOrganizationsByHero(int id) {
        List<Organization> orgs;
        try {
            orgs = jdbcTemplate.query(SqlQueries.SQL_GET_ORGANIZATIONS_FOR_A_HERO,
                    new OrganizationMapper(),
                    id);
            for (Organization org : orgs) {
                org.setLocation(getLocationById(org.getLocationId()));
            }
        } catch (EmptyResultDataAccessException ex) {
            orgs = null;
        }
        return orgs;
    }

//  c-r-U-d  update opperations  
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Hero updateHero(Hero hero, int id) {
        jdbcTemplate.update(SqlQueries.SQL_UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                id);

        return hero;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location updateLocation(Location loc, int id) {
        jdbcTemplate.update(SqlQueries.SQL_UPADATE_LOCATION,
                loc.getLatitude(),
                loc.getLongitude(),
                loc.getDescription(),
                loc.getName(),
                loc.getState(),
                loc.getCounty(),
                loc.getStreet(),
                loc.getHouseNumber(),
                loc.getZipCode(),
                id
        );
        return loc;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization updateOrganization(Organization org, int id) {
        jdbcTemplate.update(SqlQueries.SQL_UPDATE_ORGANIZATION,
                org.getName(),
                org.getDescription(),
                org.getPhone(),
                org.getLocationId(),
                id
        );
        return org;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting updateSighting(Sighting sigh, int id) {
        jdbcTemplate.update(SqlQueries.SQL_UPDATE_SIGHTING,
                sigh.getLocationID(),
                sigh.getDateTime(),
                id
        );
        return sigh;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public SuperPower updateHeroSuperPowers(SuperPower pow, int id) {
        jdbcTemplate.update(SqlQueries.SQL_UPDATE_POWER,
                pow.getName(),
                id
        );
        return pow;
    }

// c-r-u-D  delete opperations  
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHero(Hero hero) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_HERO_FROM_ALL_ORGANIZATIONS,
                hero.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_POWERS_TO_A_HERO,
                hero.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_SIGHTING_TO_HERO_BY_HERO,
                hero.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_HERO,
                hero.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteLocation(Location location) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_SIGHTING_BY_LOCATION,
                location.getId());
        int orgId = getOrganizationByLocationId(location.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_EVERYONE_FROM_ORGANIZATION,
                orgId);
        //delete connections from organization.
        jdbcTemplate.update(SqlQueries.SQL_DELETE_ORGANIZATION_BY_LOCATION,
                location.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_LOCATION,
                location.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteOrganization(Organization org) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_EVERYONE_FROM_ORGANIZATION,
                org.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_ORGANIZATION,
                org.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSighting(Sighting sigh) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_SIGHTING_TO_HERO_BY_SIGHTING,
                sigh.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_SIGHTING,
                sigh.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHeroSuperPowers(SuperPower pow) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_POWER_FROM_HEROES,
                pow.getId());
        jdbcTemplate.update(SqlQueries.SQL_DELETE_SUPERPOWER,
                pow.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHeroAssociation(Hero hero, Organization org) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_HERO_FROM_AN_ORGANIZATION,
                hero.getId(),
                org.getId()
        );
        //hero.removeOrganization(org);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHeroesFromSighting(Sighting sight) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_SIGHTING_TO_HERO_BY_SIGHTING,
                sight.getId());
    }

    @Override
    public void deleteAllPowersForAHero(Hero hero) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_POWERS_TO_A_HERO,
                hero.getId()
        );
    }

    @Override
    public void deleteAllOrganizationsForAHero(Hero hero) {
        jdbcTemplate.update(SqlQueries.SQL_DELETE_HERO_FROM_ALL_ORGANIZATIONS,
                hero.getId()
        );
    }

    // This checks to see if a location referenced exists, if not it creates it and either way it passes back a location ID.
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private int getOrCreateNewLocation(Location location) {
        int addressId = -1;
        try {
            //helper method gets an id from location parameters.
            addressId = getLocationByParameter(location);
//            );
//            addressId = loc.getId();
        } catch (EmptyResultDataAccessException ex) {
            jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_LOCATION,
                    null,
                    null,
                    location.getDescription(),
                    location.getName(),
                    location.getState(),
                    location.getCounty(),
                    location.getStreet(),
                    location.getHouseNumber(),
                    location.getZipCode()
            );
        }
        // if address is not 'found' get the one from the new insert
        if (addressId == -1) {
            addressId = getLastInsertId();
        }
        return addressId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganizationToHero(Hero hero, Organization org) {
        hero.addOrganization(org);
        // add it to bridge table and to get table
        jdbcTemplate.update(SqlQueries.SQL_INSERT_NEW_AFFILIATION,
                hero.getId(),
                org.getId()
        );

    }

    //  for multi-teired work.  Gets last thing used, only use in transactions.
    private int getLastInsertId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    private int getOrganizationByLocationId(int id) {
        return jdbcTemplate.queryForObject(SqlQueries.SQL_GET_ORGANIZATION_BY_LOCATION, Integer.class, id);
    }

    private int getLocationByParameter(Location loc) {

        return jdbcTemplate.queryForObject(SqlQueries.SQL_GET_LOCATION_BY_ADDRESS,
                Integer.class,
                loc.getName(),
                loc.getState(),
                loc.getCounty(),
                loc.getStreet(),
                loc.getHouseNumber(),
                loc.getZipCode()
        );
    }

    // MAPPERS     ===========================================================
    private static final class HeroMapper implements RowMapper<Hero> {

        public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hero hero = new Hero();

            hero.setName(rs.getString("heroName"));
            hero.setDescription(rs.getString("heroDescription"));
            hero.setId(rs.getInt("id"));

            return hero;
        }
    }

    // hero power mapper - maybe use this to add powers?
    private static final class PowerMapper implements RowMapper<SuperPower> {

        public SuperPower mapRow(ResultSet rs, int rowNum) throws SQLException {
            SuperPower power = new SuperPower();

            power.setName(rs.getString("powerName"));
            power.setId(rs.getInt("id"));

            return power;
        }
    }

    //sighting mapper
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sighting = new Sighting();

            sighting.setId(rs.getInt("id"));
            sighting.setLocationID(rs.getInt("FK_Location"));
            String time = rs.getTimestamp("times").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
            sighting.setDateTime(time);
// .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss"));
            return sighting;
        }
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location();

            location.setId(rs.getInt("id"));
            location.setLatitude(rs.getFloat("latitude"));
            location.setLongitude(rs.getFloat("longitude"));
            location.setDescription(rs.getString("placeDescription"));
            location.setName(rs.getString("placeName"));
            location.setState(rs.getString("state"));
            location.setCounty(rs.getString("county"));
            location.setStreet(rs.getString("street"));
            location.setHouseNumber(rs.getString("houseNumber"));
            location.setZipCode(rs.getString("zipCode"));

            return location;
        }
    }

    private static final class OrganizationMapper implements RowMapper<Organization> {

        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization organization = new Organization();

            // this gets the base information from the dataTable, still need location info.
            organization.setId(rs.getInt("id"));
            organization.setDescription(rs.getString("organizationDescription"));
            organization.setName(rs.getString("organizationName"));
            organization.setLocationId(rs.getInt("FK_Location"));
            organization.setPhone(rs.getString("organizationPhone"));

            return organization;
        }
    }
}
