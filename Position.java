/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

/**
 *
 * @author jaron
 * A simple representaiton 2D coordinates
 */
public class Position
{
    float _x;
    float _y;
    
    public void Position(float x, float y){
        _x = x;
        _y = y;
    }
    
    public Position(float x, float y){
      _x=x;
      _y=y;
    }
    
    public void setX(float x){
        _x = x;
    }
    
    public void setY(float y){
        _y = y;
    }
    
    public void set(float x, float y){
        _x = x;
        _y = y;
    }
    
    public float x(){
        return _x;
    }
    
    public float y(){
        return _x;
    }
}
