/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero_4.Dao;

/**
 *
 * @author omish
 */
public class AlreadyExistsException extends Exception{
        public AlreadyExistsException(String msg){
        super(msg);
    }
    public AlreadyExistsException(String msg, Throwable cause){
        super(msg, cause);
    }
}
