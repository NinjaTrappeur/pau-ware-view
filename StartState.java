/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;


public class StartState extends State
{
     /**
     * This class represents a graphic start state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     */
      public StartState(String name)
    {
        super(30,30,name);
    }

      public StartState()
    {
        super(30,30,"Start");
    }
      
      @Override
      public void setWidth(float width){
          
      }
      
      @Override
      public void setLength(float length){
          
      }
      
      
      @Override
      public void draw(processing.core.PApplet applet)
      {
          applet.fill(0, 0, 0);
          applet.ellipse(15, 15, _width, _length);
      }
}
