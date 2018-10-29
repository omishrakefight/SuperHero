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
import java.util.List;

/**
 *
 * @author omish
 */
public interface heroDao {
   //Create
   public Hero createHero(Hero hero) throws AlreadyExistsException;
   public Location createLocation(Location location) throws AlreadyExistsException;
   public Organization createOrganization(Organization organization, Location location) throws AlreadyExistsException;
   public Sighting createSighting(Location location, String timeStamp) throws AlreadyExistsException;
   public SuperPower createSuperPower(SuperPower power) throws AlreadyExistsException;
   public void createHeroAffiliation(Hero hero, Organization organization) throws AlreadyExistsException;
   public void createHeroPowerAssociation(SuperPower power, Hero hero) throws AlreadyExistsException;
   public void createHeroSighting(Hero hero, Sighting sight)throws AlreadyExistsException;
   
   //Read Lists
   public List<Hero> getHeroList();
   public List<Location> getLocationList();
   public List<Sighting> getHeroesForHome();
   public List<Organization> getOrganizationList();
   public List<Sighting>  getSightingList();
   public List<SuperPower> getHeroPowerList();
   public void getHeroAssociationList(Hero hero);
   // Read singular
   public Hero getHeroById(int id);
   public Location getLocationById(int id);
   public Organization getOrganizationById(int id);
   public Sighting getSightingById(int id);
   public SuperPower getPowerById(int id);
   //Read specifics
   public List<Hero> getHeroListBySighting(Sighting sigh);
   public List<Location> getLocationsForAHero(Hero hero);
   public List<Sighting> getAllSightingForADate(String date);
   public List<Hero> getOrganizationMembers(int id);
   public List<Organization> getOrganizationsByHero(int id);
   
   //Update
   public Hero updateHero(Hero hero, int id);
   public Location updateLocation(Location loc, int id);
   public Organization updateOrganization(Organization org, int id);
   public Sighting updateSighting(Sighting sigh, int id);
   public SuperPower updateHeroSuperPowers(SuperPower pow, int id);
   public void deleteHeroesFromSighting(Sighting sight);
   
   //Delete
   public void deleteHero(Hero hero);
   public void deleteLocation(Location location);
   public void deleteOrganization(Organization org);
   public void deleteSighting(Sighting sigh);
   public void deleteHeroSuperPowers(SuperPower pow);
   public void deleteHeroAssociation(Hero hero, Organization org);
   public void deleteAllPowersForAHero (Hero hero);
   public void deleteAllOrganizationsForAHero(Hero hero);
   //Other
   public void addOrganizationToHero(Hero hero, Organization org);
}
