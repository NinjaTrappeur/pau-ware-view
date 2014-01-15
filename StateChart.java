/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;

/**
 *
 * @author josuah
 */
public class StateChart extends AbstractElement implements IChart
{

    HashSet<AbstractElement> _elements;
    HashSet<Transition> _transitions;
    HashMap<com.pauware.pauware_engine._Core.AbstractStatechart, AbstractElement> _fromOriginal;
    HashMap<Integer, HashSet<AbstractElement> > _nestingLevels;
    
    private void _registerNestLevel(AbstractElement added, Integer nestLevel)
    {
        if(_nestingLevels.containsKey(nestLevel))
        {
            _nestingLevels.get(nestLevel).add(added);
        }
        
        else
        {
            HashSet<AbstractElement> elements = new HashSet();
            elements.add(added);
            _nestingLevels.put(nestLevel, elements);
        }
    }

    private AbstractElement _addState(com.pauware.pauware_engine._Core.AbstractStatechart state,
            Integer nestLevel)
    {
        AbstractElement added;
        
        if (state.leaf())
        {
            added = new State(state.name());
        }
        
        else if(state.name().equals("pseudo-state"))
        {
            added = null;
            _addState(state.left(), nestLevel);
            _addState(state.right(), nestLevel);
        }
        
        else //compositeState
        {
            SuperState composite;
            com.pauware.pauware_engine._Core.AbstractStatechart l, r;
            AbstractElement left, right;
            Integer childNestLevel = nestLevel + 1;

            composite = new SuperState(state.name());
            // add CompositeState components : use left() and right()
            l = state.left();
            r = state.right();
            left = _addState(l, childNestLevel);
            composite.addSubState(left);
            while( r.name().equals("pseudo-state") )
            {
                l = r.left();
                r = r.right();
                
                left = _addState(l, childNestLevel);
                composite.addSubState(left);
            }
            right = _addState(r, childNestLevel);
            
            composite.addSubState(right);

            added = composite;
        }

        if(added != null){
            _elements.add(added);
            _fromOriginal.put(state, added);
            _registerNestLevel(added, nestLevel);
        }

        if (state.isInputState())
        {
            AbstractElement start = new StartState("start_to_"+state.name());

            _elements.add(start);
            _transitions.add(new Transition(start, added));
            _registerNestLevel(start, nestLevel);
        }
        else if (state.isOutputState())
        {
            AbstractElement end = new EndState("end_from_"+state.name());

            _elements.add(end);
            _transitions.add(new Transition(added, end));
            _registerNestLevel(end, nestLevel);
        }

        return added;
    }
    
    private void _init()
    {
        _elements = new HashSet();
        _transitions = new HashSet() ;
        _fromOriginal = new HashMap();
        _nestingLevels = new HashMap();
    }

    public StateChart(com.pauware.pauware_engine._Core.AbstractStatechart_monitor state_machine,
            String name, float width, float length) throws IllegalStateException
    {
        super(name, width, length);        
        _init();

        Set<com.pauware.pauware_engine._Core.Transition> transitions;
        com.pauware.pauware_engine._Core.Transition edge;
        com.pauware.pauware_engine._Core.AbstractStatechart left, right, from, to;
        AbstractElement myLeft, myRight, origin, target;
        
        //add States
        left = state_machine.left();
        right = state_machine.right();
        
        myLeft = _addState(left, 0);
        myRight = _addState(right, 0);
        
        _fromOriginal.put(left, myLeft);
        _fromOriginal.put(right, myRight);
        
        //Add Transitions
        transitions = state_machine.transitions().keySet();
        
        Iterator<com.pauware.pauware_engine._Core.Transition> it = transitions.iterator();
        while (it.hasNext())
        {
            edge = it.next();
            from = edge.from();
            to = edge.to();

            origin = _fromOriginal.get(from);
            target = _fromOriginal.get(to);

            _transitions.add(new Transition(origin, target));
        }
        
        //we can empty _fromOriginal now
    }

    public StateChart(String name, float width, float length)
    {
        super(name, width, length);
    }

    public StateChart(String name) {
        super(name, 300, 300);
    }

    @Override
    public void addElement(AbstractElement state)
    {
        _elements.add(state);
    }

    @Override
    public void removeElement(AbstractElement state)
    {
        _elements.remove(state);
    }

    @Override
    public void addTransition(Transition trans)
    {
        _transitions.add(trans);
    }

    @Override
    public void addTransition(AbstractElement origin, AbstractElement target)
    {
        _transitions.add(new Transition(origin, target));
    }

    @Override
    public void removeTransition(Transition trans)
    {
        _transitions.remove(trans);
    }

    @Override
    public void removeTransition(AbstractElement origin, AbstractElement target)
    {
        Transition trans = new Transition(origin, target);
        _transitions.remove(trans);
    }

    @Override
    public HashMap<Integer, HashSet<AbstractElement>> nestingLevels()
    {
        return _nestingLevels;
    }
    @Override
    public Collection<AbstractElement> elements()
    {
        return _elements;
    }

    @Override
    public Collection<Transition> transitions()
    {
        return _transitions;
    }

    @Override
    public boolean isTransition(AbstractElement origin, AbstractElement target)
    {
        Transition trans = new Transition(origin, target);
        return _transitions.contains(trans);
    }
}
