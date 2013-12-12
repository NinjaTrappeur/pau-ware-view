/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;


public class EndState extends State
{
     /**
     * This class represents a graphic end state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     */
      public EndState(String name)
    {
        super(30,30,name);
    }

      public EndState()
    {
        super(30,30,"End");
    }
      @Override
      public void draw(processing.core.PApplet applet)
      {
          applet.fill(0, 0, 0);
          applet.ellipse(0, 0, _width-12, _length-12);
          applet.noFill();
          applet.ellipse(0,0,_width,_length);
      }
}
