/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;


public class StartState extends PseudoState implements Drawable
{
     /**
     * This class represents a graphic start state.
     * 
     * @param name Name of the state.
     */
      public StartState(String name)
    {
        super(name);
    }

      public StartState()
    {
        super("Start");
    }      
      
      @Override
      public void draw(processing.core.PApplet applet)
      {
          applet.pushMatrix();
          applet.fill(0, 0, 0);
          applet.ellipse(15, 15, _width, _length);
          applet.popMatrix();
      }
}
