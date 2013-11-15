/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

import java.util.Collection;
    /**
     * This class represents a graphic super state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     * @param subgraphs Subgraphs contained in this super state.
     */
public class SuperState extends State{
    public SuperState(float length, float width, String name,
            Collection subgraphs)
    {
        super(length,width,name);
        _subGraphs = subgraphs;
    }
    
    /**
     * Graphs of states countained in this superstate.
     */
    protected Collection _subGraphs;
}
