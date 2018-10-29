/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_4;

import com.sg.superhero_4.Dao.AlreadyExistsException;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author omish
 */
@Controller
// controller with no appending on start
public class SuperHeroStarterC {
    //this is a start redirect
    @RequestMapping(value="/")
    public String directToHome(){
        //redirects to where I want it
        return "redirect:/Hero/Home";
    }
    

}
