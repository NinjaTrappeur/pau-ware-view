/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */

package com.PauWare.PauWare_view;

public class PseudoState extends AbstractElement
{
     /**
     * This class represents a graphic start state.
     * 
     * @param name Name of the state.
     * @param container  optional container (like superstate or statechart)
     */
      public PseudoState(String name, AbstractElement container)
    {
        super(name, 30, 30, container);
    }
      public PseudoState(String name)
    {
        super(name, 30, 30);
    }
}
