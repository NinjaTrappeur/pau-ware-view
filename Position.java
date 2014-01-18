/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;
import java.awt.geom.Point2D;
/**
 *
 * @author jaron
 * A simple representaiton 2D coordinates
 */
public class Position
{
    protected float _x;
    protected float _y;
    
    public Position(float x, float y){
      _x = x;
      _y = y;
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
        return _y;
    }
    
    public Point2D getPoint2D(){
        return new Point2D.Double(_x,_y);
    }
}
