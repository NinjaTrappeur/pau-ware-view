/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

public class State extends AbstractElement 
{
    /**
     * This class represents a graphic state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     */
    public State(float length, float width, String name)
    {
        super(length,width);
        _name = name;
    }
    
    
    
    /**
     * Name of the state.
     */
    protected String _name;
}
