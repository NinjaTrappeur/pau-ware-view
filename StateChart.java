/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.Set;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author josuah
 */
public class StateChart extends AbstractElement implements IChart
{
    Set<AbstractElement> _elements;
    Set<Transition> _transitions;
    
    public StateChart(com.pauware.pauware_engine._Core.AbstractStatechart_monitor state_machine,
            String name, float length, float width)
    {
        super(name, length, width);
        
        Set<com.pauware.pauware_engine._Core.Transition> transitions;
        transitions = state_machine.transitions().keySet();
        com.pauware.pauware_engine._Core.AbstractStatechart from, to;
        
        Iterator<com.pauware.pauware_engine._Core.Transition> it = transitions.iterator();
        while(it.hasNext())
        {
            from = it.next().from();
            _elements.add( new State(from.name()) );
        }
    }
    
    @Override
    public void addElement(AbstractElement state)
    {
        //
    }
    
    @Override
    public void removeElement(AbstractElement state)
    {
        //
    }
    
    @Override
    public void addTransition(Transition trans)
    {
        //
    }
    
    @Override
    public void addTransition(AbstractElement origin, AbstractElement target)
    {
        //
    }
    
    @Override
    public void removeTransition(Transition trans)
    {
        //
    }
    
    @Override
    public void removeTransition(AbstractElement origin, AbstractElement target)
    {
        //
    }
    
    @Override
    public Collection<AbstractElement> elements()
    {
        return 
    }
    
    @Override
    public Collection<Transition> transitions()
    {
        
    }
    
    @Override
    public boolean isTransition(AbstractElement origin, AbstractElement target)
    {
        
    }

    @Override
    public void draw(processing.core.PApplet applet)
    {
        //
    }
}
