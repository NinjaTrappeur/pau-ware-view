/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.ArrayList;
    /**
     * This class represents a graphic super state.
     * 
     * @param length Length of the rectangle representing the state.
     * @param width Width of the rectangle representing the state.
     * @param name Name of the state.
     * @param subgraphs Subgraphs contained in this super state.
     */
public class SuperState extends State
{
    public SuperState(String name, float width, float length,
            Collection<State> subStates)
    {
        super(name, width, length);
        this._subStates = new ArrayList();
        _subStates.addAll(subStates);
    }
    
    public SuperState(String name)
    {
        super(name);
        this._subStates = new ArrayList();
    }
    
    public void addComponent(AbstractElement state)
    {
        _subStates.add(state);
    }

    public void removeComponent(AbstractElement state)
    {
        _subStates.remove(state);
    }

    public Collection<AbstractElement> substates()
    {
        return _subStates;
    }
 
    /*
    @Override
        public void draw(processing.core.PApplet applet)
    {
    }
    */
        
    /**
     * Graphs of states countained in this superstate.
     */
    protected ArrayList<AbstractElement> _subStates;
}
