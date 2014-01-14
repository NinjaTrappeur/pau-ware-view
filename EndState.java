/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;


public class EndState extends PseudoState implements Drawable
{
     /**
     * This class represents a graphic end state.
     * 
     * @param name Name of the state.
     */
      public EndState(String name)
    {
        super(name);
    }

      public EndState()
    {
        super("End");
    }
      @Override
      public void draw(processing.core.PApplet applet)
      {
          applet.pushMatrix();
          applet.fill(0, 0, 0);
          applet.ellipse(15, 15, _width-12, _length-12);
          applet.noFill();
          applet.ellipse(15,15,_width,_length);
          applet.popMatrix();
      }
}
