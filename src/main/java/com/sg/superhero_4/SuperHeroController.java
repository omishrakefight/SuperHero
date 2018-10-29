/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_4;

import com.sg.superhero_4.Dao.AlreadyExistsException;
import com.sg.superhero_4.Dao.heroDaoImpl;
import com.sg.superhero_4.model.Hero;
import com.sg.superhero_4.model.Location;
import com.sg.superhero_4.model.Organization;
import com.sg.superhero_4.model.Sighting;
import com.sg.superhero_4.model.SuperPower;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import static org.jboss.logging.MDC.put;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author omish
 */
@Controller
// this is main controller name.
@RequestMapping({"/Hero"})
public class SuperHeroController {

    heroDaoImpl daoImpl;// = new heroDaoImpl();
    JdbcTemplate jdbcTemplate;

    SuperHeroController() {
        //daoImpl = new heroDaoImpl();
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext(
                        "spring-persistence.xml");
        jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        daoImpl = new heroDaoImpl();
        daoImpl.setJdbcTemplate(jdbcTemplate);
        //this.daoImpl = ctx.getBean(DaoImpl.class);
    }

    //======================================
    // showing lists of info.
    //======================================
    // this is the endcut that it is being sent to.....
    @RequestMapping(value = "/InformationLocation", method = RequestMethod.GET)
    public String goToLocationInformation(Map<String, Object> model, HttpServletRequest request) throws AlreadyExistsException {
        String sLocId = request.getParameter("locationId");
        int locId;
        Location loc;
        try {
            locId = Integer.parseInt(sLocId);
            loc = daoImpl.getLocationById(locId);
        } catch (NumberFormatException ex) {
            loc = null;
        }
        model.put("loc", loc);
        return "InformationLocation";
    }

    @RequestMapping(value = "/HeroList", method = RequestMethod.GET)
    public String goToHeroList(Map<String, Object> model) throws AlreadyExistsException {
        List<Hero> heroy = daoImpl.getHeroList();

        model.put("heroy", heroy);
        return "HeroLookup";
    }

    @RequestMapping(value = "/Home", method = RequestMethod.GET)
    public String goToHome(Map<String, Object> model) {
        List<Hero> heroes = daoImpl.getHeroList();
        model.put("Heroes", heroes);
        List<Sighting> sights = daoImpl.getHeroesForHome();
        model.put("sights", sights);
        return "Home";
    }

    // this used to direct ?
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String startAtHome(Map<String, Object> model) {

        List<Hero> heroes = daoImpl.getHeroList();
        model.put("Heroes", heroes);
        List<Sighting> sights = daoImpl.getHeroesForHome();
        model.put("sights", sights);
        return "Home";
    }

    @RequestMapping(value = "/PowerList", method = RequestMethod.GET)
    public String goToPowerList(Map<String, Object> model) {
        List<SuperPower> pows = daoImpl.getHeroPowerList();
        model.put("Powers", pows);
        return "PowerLookup";
    }

    @RequestMapping(value = "/OrganizationList", method = RequestMethod.GET)
    public String goToOrganizationList(Map<String, Object> model) {
        List<Organization> orgs = daoImpl.getOrganizationList();
        model.put("orgs", orgs);
        return "OrganizationLookup";
    }

    @RequestMapping(value = "/PlacesList", method = RequestMethod.GET)
    public String goToPlacesList(Map<String, Object> model) {
        List<Location> locs = daoImpl.getLocationList();
        model.put("locs", locs);
        return "PlacesOfInterest";
    }

    @RequestMapping(value = "/SightingList", method = RequestMethod.GET)
    public String goToSightingsList(Map<String, Object> model) {
        List<Sighting> sights = daoImpl.getSightingList();
        model.put("sights", sights);
        return "Sightings";
    }

    // ===========================
    // sending information for new options
    //============================
    //  Create new Items pages
    @RequestMapping(value = "/CreateNewHero", method = RequestMethod.GET)
    public String goToCreateNewHero(Map<String, Object> model) {
        List<SuperPower> pows = daoImpl.getHeroPowerList();
        List<Organization> orgs = daoImpl.getOrganizationList();
        model.put("Powers", pows);
        model.put("Organizations", orgs);
        return "CreateNewHero";
    }

    @RequestMapping(value = "/CreateNewOrganization", method = RequestMethod.GET)
    public String goToCreateNewOrganization(Map<String, Object> model) {
        List<Location> locs = daoImpl.getLocationList();
        List<Hero> heroes = daoImpl.getHeroList();
        model.put("Heroes", heroes);
        model.put("Locations", locs);

        return "CreateNewOrganization";
    }

    @RequestMapping(value = "/CreateNewPlaceOfInterest", method = RequestMethod.GET)
    public String goToCreateNewPlaceOfInterest(Map<String, Object> model) {
        model.put("msg", model);
        return "CreateNewPlaceOfInterest";
    }

    @RequestMapping(value = "/CreateNewPower", method = RequestMethod.GET)
    public String goToCreateNewPower(Map<String, Object> model) {
        model.put("msg", model);
        return "CreateNewPower";
    }

    @RequestMapping(value = "/CreateNewSighting", method = RequestMethod.GET)
    public String goToCreateNewSighitng(Map<String, Object> model) {
        List<Location> locs = daoImpl.getLocationList();
        List<Hero> heroes = daoImpl.getHeroList();
        model.put("Heroes", heroes);
        model.put("Locations", locs);
        return "CreateNewSighting";
    }
    // ===============================
    // Createing new
    // ===============================

    @RequestMapping(value = "/createSuperHero", method = RequestMethod.POST)
    public String createNewHero(HttpServletRequest request) throws AlreadyExistsException {
        Hero hero = new Hero();
        String[] pows = request.getParameterValues("Powers[]");
        String[] orgs = request.getParameterValues("Affiliations");

        try {
            hero.setName(request.getParameter("Name"));
            hero.setDescription(request.getParameter("Description"));
            hero = daoImpl.createHero(hero);
            for (String id : pows) {
                int newId = Integer.parseInt(id);
                SuperPower pow = daoImpl.getPowerById(newId);
                daoImpl.createHeroPowerAssociation(pow, hero);
            }
            for (String id : orgs) {
                int newId = Integer.parseInt(id);
                Organization org = daoImpl.getOrganizationById(newId);
                daoImpl.createHeroAffiliation(hero, org);
            }
        } catch (Exception ex) {
            daoImpl.deleteHero(hero);
        }
        return "redirect:HeroList";
    }

    @RequestMapping(value = "/createSuperPower", method = RequestMethod.POST)
    public String createNewSuperPower(HttpServletRequest request) throws AlreadyExistsException {
        SuperPower pow = new SuperPower();
        pow.setName(request.getParameter("Name"));
        daoImpl.createSuperPower(pow);
        return "redirect:PowerList";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createNewLocation(HttpServletRequest request) throws AlreadyExistsException {
        Location loc = new Location();
        loc.setLatitude(Float.parseFloat(request.getParameter("Latitude")));
        loc.setLongitude(Float.parseFloat(request.getParameter("Longitude")));
        loc.setName(request.getParameter("Name"));
        loc.setDescription(request.getParameter("Description"));
        loc.setState(request.getParameter("State"));
        loc.setCounty(request.getParameter("County"));
        loc.setStreet(request.getParameter("Street"));
        loc.setHouseNumber(request.getParameter("HouseNumber"));
        loc.setZipCode(request.getParameter("ZipCode"));
        daoImpl.createLocation(loc);
        return "redirect:PlacesList";
    }

    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createNewSighting(HttpServletRequest request) throws AlreadyExistsException {
        Sighting sight;
        try {
            String[] heroes = request.getParameterValues("Heroes");
            // get location id - convert to location and add it for create organization.
            int locationId = Integer.parseInt(request.getParameter("LocationId"));
            Location loc = daoImpl.getLocationById(locationId);
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String dateTime = date + " " + time;
            sight = daoImpl.createSighting(loc, dateTime);
            for (String heroS : heroes) {
                //change heroid string into an int, get the real hero and pass it in for a sighting.
                Hero hero = daoImpl.getHeroById(Integer.parseInt(heroS));
                daoImpl.createHeroSighting(hero, sight);
            }
        } catch (Exception ex) {
            sight = null;

            // in the even creation failed, delete what data was persisted.
            try {
                daoImpl.deleteSighting(sight);
            } catch (EmptyResultDataAccessException empty) {
                // nothing.  If it fails it means it did not exist yet anyways.
            }
        }
        return "redirect:SightingList";
    }

    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public String createNewOrganization(HttpServletRequest request) throws AlreadyExistsException {
        String[] heroes = request.getParameterValues("OrgMembers");
        Organization org = new Organization();
        //create an organization, and get heroes (2 things needed for an association) and link them
        org.setName(request.getParameter("Name"));
        org.setDescription(request.getParameter("Description"));
        org.setPhone(request.getParameter("Phone"));
        int locationId = Integer.parseInt(request.getParameter("LocationId"));
        Location loc = daoImpl.getLocationById(locationId);
        org.setLocationId(locationId);
        org = daoImpl.createOrganization(org, loc);
        for (String heroS : heroes) {
            try {
                //change heroid string into an int, get the real hero and pass it in for asssociation.
                Hero hero = daoImpl.getHeroById(Integer.parseInt(heroS));
                // for each hero I update the bridge table with location and him to create a true 'sighting'
                daoImpl.createHeroAffiliation(hero, org);
                // todo -- dataintegrity? I think i need null data exception or something
            } catch (NumberFormatException | DataIntegrityViolationException ex) {
                System.out.println("Bad information on a hero");
            }
        }
        return "redirect:OrganizationList";
    }

    //========================================
    //  deleteing information
    //========================================
    @RequestMapping(value = "/deletePower", method = RequestMethod.GET)
    public String deletePower(HttpServletRequest request) {
        String sPowerId = request.getParameter("powerId");
        try {
            int powerId = Integer.parseInt(sPowerId);
            SuperPower pow = daoImpl.getPowerById(powerId);
            daoImpl.deleteHeroSuperPowers(pow);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be deleted");
        }
        return "redirect:PowerList";
    }

    @RequestMapping(value = "/deleteHero", method = RequestMethod.GET)
    public String deleteHero(HttpServletRequest request) {
        String sHeroId = request.getParameter("heroId");
        try {
            int heroId = Integer.parseInt(sHeroId);
            Hero hero = daoImpl.getHeroById(heroId);
            daoImpl.deleteHero(hero);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be deleted");
        }
        return "redirect:HeroList";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {
        try {
            String sLocId = request.getParameter("locationId");
            int locId = Integer.parseInt(sLocId);
            Location loc = daoImpl.getLocationById(locId);
            daoImpl.deleteLocation(loc);
        } catch (DataIntegrityViolationException ex) {
            System.out.println("Location being used try again");
        } catch (NumberFormatException ex) {
            System.out.println("Location ID does not exist");
        }
        return "redirect:PlacesList";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {
        String sSightingId = request.getParameter("sightingId");
        try {
            int sightId = Integer.parseInt(sSightingId);
            Sighting sigh = daoImpl.getSightingById(sightId);
            daoImpl.deleteSighting(sigh);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be deleted");
        }
        return "redirect:SightingList";
    }

    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request) {
        String sOrgId = request.getParameter("organizationId");
        try {
            int orgId = Integer.parseInt(sOrgId);
            Organization org = daoImpl.getOrganizationById(orgId);
            daoImpl.deleteOrganization(org);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be deleted");
        }
        return "redirect:OrganizationList";
    }

    //========================================
    //  Updating information page
    //========================================
    @RequestMapping(value = "/displayUpdateHero", method = RequestMethod.GET)
    public String displayUpdateHero(HttpServletRequest request, Model model) {
        String sHeroId = request.getParameter("heroId");
        Hero hero;
        try {
            int heroId = Integer.parseInt(sHeroId);
            hero = daoImpl.getHeroById(heroId);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be pulled");
            hero = null;
        }

        List<SuperPower> pows = daoImpl.getHeroPowerList();
        List<Organization> orgs = daoImpl.getOrganizationList();
        model.addAttribute("Powers", pows);
        model.addAttribute("Organizations", orgs);
        model.addAttribute("hero", hero);
        return "UpdateHero";
    }

    @RequestMapping(value = "/displayUpdateOrganization", method = RequestMethod.GET)
    public String displayUpdateOrganization(HttpServletRequest request, Model model) {
        String sOrgId = request.getParameter("organizationId");
        Organization org;
        try {
            int orgId = Integer.parseInt(sOrgId);
            org = daoImpl.getOrganizationById(orgId);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be pulled");
            org = null;
        }

        List<Location> locs = daoImpl.getLocationList();
        model.addAttribute("locs", locs);
        model.addAttribute("org", org);
        return "UpdateOrganization";
    }

    @RequestMapping(value = "/displayUpdateLocation", method = RequestMethod.GET)
    public String displayUpdateLocation(HttpServletRequest request, Model model) {
        String sLocationId = request.getParameter("locationId");
        Location loc;
        try {
            int locationId = Integer.parseInt(sLocationId);
            loc = daoImpl.getLocationById(locationId);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be pulled");
            loc = null;
        }

        model.addAttribute("loc", loc);
        return "UpdateLocation";
    }

    @RequestMapping(value = "/displayUpdatePower", method = RequestMethod.GET)
    public String displayUpdatePower(HttpServletRequest request, Model model) {
        String sPowerId = request.getParameter("powerId");
        SuperPower pow;
        try {
            int powerId = Integer.parseInt(sPowerId);
            pow = daoImpl.getPowerById(powerId);
        } catch (NumberFormatException | DataIntegrityViolationException ex) {
            System.out.println("Data doesn't exist to be pulled");
            pow = null;
        }

        model.addAttribute("power", pow);
        return "UpdatePower";
    }

    @RequestMapping(value = "/displayUpdateSighting", method = RequestMethod.GET)
    public String displayUpdateSighting(HttpServletRequest request, Model model) {
        String sSightId = request.getParameter("sightingId");
        Sighting sight;
        try {
            int sightId = Integer.parseInt(sSightId);
            sight = daoImpl.getSightingById(sightId);
            
            // throws a null pointer rather than emptyResultDataAcess
            List<Hero> heroesSighted = daoImpl.getHeroListBySighting(sight);
            List<Hero> heroes = daoImpl.getHeroList();
            List<Location> locs = daoImpl.getLocationList();

//            date and time are split because date-time in html does not handle seconds.
//          I have to manually cacatonate them.
            String dateTime = sight.getDateTime();
            String date = dateTime.substring(0, 10);
            String time = dateTime.substring(10);

            model.addAttribute("time", time);
            model.addAttribute("date", date);
            model.addAttribute("heroesSighted", heroesSighted);
            model.addAttribute("heroes", heroes);
            model.addAttribute("locs", locs);
            model.addAttribute("sight", sight);

        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException | NullPointerException ex) {
            System.out.println("Data doesn't exist to be pulled");
            sight = null;
        }

        return "UpdateSighting";
    }

    //========================================
    //  Updating information
    //========================================
    @RequestMapping(value = "/updateHero", method = RequestMethod.POST)
    public String updateHero(HttpServletRequest request) throws AlreadyExistsException {

        Hero hero = new Hero();
        String[] pows = request.getParameterValues("newPowers");
        String[] orgs = request.getParameterValues("Affiliations");
        String heroName = request.getParameter("Name");
        String heroDescription = request.getParameter("Description");

        int oldHeroId;
        try {
            oldHeroId = Integer.parseInt(request.getParameter("id"));
            Hero oldHero = daoImpl.getHeroById(oldHeroId);

            //give new hero old hero id to pass in
            hero.setId(oldHeroId);

            if (heroName == null) {
                hero.setName(oldHero.getName());
            } else {
                hero.setName(heroName);
            }

            if (heroDescription == null) {
                hero.setDescription(oldHero.getDescription());
            } else {
                hero.setDescription(heroDescription);
            }

            if (orgs == null) {
                hero.setOrganizations(oldHero.getOrganizations());
            } else {
                daoImpl.deleteAllOrganizationsForAHero(hero);
                for (String id : orgs) {
                    int newId = Integer.parseInt(id);
                    Organization org = daoImpl.getOrganizationById(newId);
                    daoImpl.createHeroAffiliation(hero, org);
                }
            }
            if (pows == null) {
                hero.setSuperPowers(oldHero.getSuperPowers());
            } else {
                daoImpl.deleteAllPowersForAHero(hero);
                for (String id : pows) {
                    int newId = Integer.parseInt(id);
                    SuperPower pow = daoImpl.getPowerById(newId);
                    daoImpl.createHeroPowerAssociation(pow, hero);
                }
            }
            // if everything else works wihtout a hitch, THEN update hero.
            daoImpl.updateHero(hero, hero.getId());
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            System.out.println("Data doesn't exist to be pulled"); //todo fix this when not on a plane
        }

        return "redirect:HeroList";

        //return "redirect:HeroList";
    }

    @RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
    public String updateOrganization(HttpServletRequest request) {
        String phone = request.getParameter("Phone");
        String orgName = request.getParameter("Name");
        String orgDescription = request.getParameter("Description");
        // keep it a string until I test for null
        String sLocationId = request.getParameter("Location");
        Location loc;

        try {
            String sOldOrgId = request.getParameter("id");
            int oldOrgId = Integer.parseInt(sOldOrgId);

            Organization oldOrg = daoImpl.getOrganizationById(oldOrgId);
            Organization newOrg = new Organization();

            if (orgName == null) {
                newOrg.setName(oldOrg.getName());
            } else {
                newOrg.setName(orgName);
            }

            if (orgDescription == null) {
                newOrg.setDescription(oldOrg.getDescription());
            } else {
                newOrg.setDescription(orgDescription);
            }

            if (phone == null) {
                newOrg.setPhone(oldOrg.getPhone());
            } else {
                newOrg.setPhone(phone);
            }

            if (sLocationId == null) {
                newOrg.setLocationId(oldOrg.getLocationId());
                loc = daoImpl.getLocationById(oldOrg.getLocationId());
            } else {
                int locationId = Integer.parseInt(sLocationId);
                loc = daoImpl.getLocationById(locationId);
                newOrg.setLocationId(locationId);
            }
            daoImpl.updateOrganization(newOrg, oldOrg.getId());
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            System.out.println("Data doesn't exist to be pulled"); //todo fix this when not on a plane
        }

        return "redirect:OrganizationList";
    }

    @RequestMapping(value = "/updateLocation", method = RequestMethod.POST)
    public String updateLocation(HttpServletRequest request) {
        String sLatitude = request.getParameter("Latitude");
        String sLongitude = request.getParameter("Longitude");

        String name = request.getParameter("Name");
        String description = request.getParameter("Description");
        String state = request.getParameter("State");
        String county = request.getParameter("County");
        String street = request.getParameter("Street");
        String houseNumber = request.getParameter("HouseNumber");
        String zipCode = request.getParameter("ZipCode");

        try {
            String sOldLocId = request.getParameter("id");
            int oldLocId = Integer.parseInt(sOldLocId);

            Location oldLoc = daoImpl.getLocationById(oldLocId);
            Location newLoc = new Location();

            if (sLatitude == null) {
                newLoc.setLatitude(oldLoc.getLatitude());
            } else {
                float lat = Float.parseFloat(sLatitude);
                newLoc.setLatitude(lat);
            }

            if (sLongitude == null) {
                newLoc.setLatitude(oldLoc.getLatitude());
            } else {
                float longi = Float.parseFloat(sLongitude);
                newLoc.setLongitude(longi);
            }

            if (name == null) {
                newLoc.setName(oldLoc.getName());
            } else {
                newLoc.setName(name);
            }

            if (description == null) {
                newLoc.setDescription(oldLoc.getDescription());
            } else {
                newLoc.setDescription(description);
            }

            if (state == null) {
                newLoc.setState(oldLoc.getState());
            } else {
                newLoc.setState(state);
            }

            if (county == null) {
                newLoc.setCounty(oldLoc.getCounty());
            } else {
                newLoc.setCounty(county);
            }

            if (street == null) {
                newLoc.setStreet(oldLoc.getStreet());
            } else {
                newLoc.setStreet(street);
            }

            if (houseNumber == null) {
                newLoc.setHouseNumber(oldLoc.getHouseNumber());
            } else {
                newLoc.setHouseNumber(houseNumber);
            }

            if (zipCode == null) {
                newLoc.setZipCode(oldLoc.getZipCode());
            } else {
                newLoc.setZipCode(zipCode);
            }

            daoImpl.updateLocation(newLoc, oldLocId);
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            System.out.println("Data doesn't exist to be pulled"); //todo fix this when not on a plane
        }

        return "redirect:PlacesList";
    }

    @RequestMapping(value = "/updatePower", method = RequestMethod.POST)
    public String updatePower(HttpServletRequest request) {
        String name = request.getParameter("Name");
        String sOldPowId = request.getParameter("id");

        try {
            int oldPowId = Integer.parseInt(sOldPowId);
            SuperPower oldPow = daoImpl.getPowerById(oldPowId);

            SuperPower newPow = new SuperPower();

            if (name == null) {
                newPow.setName(oldPow.getName());
            } else {
                newPow.setName(name);
            }

            daoImpl.updateHeroSuperPowers(newPow, oldPowId);
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            System.out.println("Data doesn't exist to be pulled"); //todo fix this when not on a plane
        }
        return "redirect:PowerList";
    }

    @RequestMapping(value = "/updateSighting", method = RequestMethod.POST)
    public String updateSighting(HttpServletRequest request) {
        String newLoc = request.getParameter("Location");
        String[] newHeroIds = request.getParameterValues("newHeroes");
        String sOldSightId = request.getParameter("id");

        String date = request.getParameter("date");
        String time = request.getParameter("time");

        Sighting sight = new Sighting();
        try {
            int oldSightingId = Integer.parseInt(sOldSightId);
            Sighting oldSight = daoImpl.getSightingById(oldSightingId);
            sight.setId(oldSightingId);

            String oldDateT = oldSight.getDateTime();
            try {
                if (newLoc == null) {
                    sight.setLocationID(oldSight.getLocationID());
                } else {
                    int newLocId = Integer.parseInt(newLoc);
                    sight.setLocationID(newLocId);
                }
                if (newHeroIds == null) {
                    sight.setHeroesSeen(oldSight.getHeroesSeen());
                } else {
                    daoImpl.deleteHeroesFromSighting(sight);
                    for (String heroId : newHeroIds) {
                        int hId = Integer.parseInt(heroId);
                        Hero hero = daoImpl.getHeroById(hId);
                        daoImpl.createHeroSighting(hero, sight);
                    }
                }
                if (date == "") {
                    String oldDate = oldDateT.substring(0, 10);
                    date = oldDate;
                } else {
                    // nothing merge datetime at end
                }
                if (time == null) {
                    String oldTime = oldDateT.substring(10);
                    time = oldTime;
                } else {
                    //nothing merge at end
                }

                // do time merge at end
                String dateTime = date + " " + time;
                sight.setDateTime(dateTime);
                daoImpl.updateSighting(sight, oldSightingId);
            } catch (AlreadyExistsException ex) {
                System.out.println("Not valid information -.-");
            }
        } catch (NumberFormatException | DataIntegrityViolationException | EmptyResultDataAccessException ex) {
            System.out.println("Data doesn't exist to be pulled"); //todo fix this when not on a plane
        }

        return "redirect:SightingList";
    }
}
