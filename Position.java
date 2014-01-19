/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;
import java.awt.geom.Point2D;
/**
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
