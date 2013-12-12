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
public class StateChart extends AbstractElement implements IChart {

    HashSet<AbstractElement> _elements;
    HashSet<Transition> _transitions;
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
            Integer parentNestLevel)
    {
        AbstractElement added;
        Integer childNestLevel;

        childNestLevel = parentNestLevel + 1;
        
        if (state.leaf())
        {
            added = new State(state.name());
        }
        
        else //compositeState
        {
            SuperState composite;
            com.pauware.pauware_engine._Core.AbstractStatechart l, r;
            AbstractElement left, right;
            Integer granChildNestLevel = childNestLevel + 1;

            composite = new SuperState(state.name());
            // add CompositeState components : use left() and right()
            l = state.left();
            r = state.right();
            left = _addState(l, granChildNestLevel);
            composite.addComponent(left);
            while( r.name().equals("pseudo-state") )
            {
                l = r.left();
                r = r.right();
                
                left = _addState(l, granChildNestLevel);
                composite.addComponent(left);
            }
            right = _addState(r, granChildNestLevel);
            composite.addComponent(right);
            
            added = composite;
        }

        _elements.add(added);
        _registerNestLevel(added, childNestLevel);

        if (state.isInputState())
        {
            AbstractElement start = new StartState();

            _elements.add(start);
            _transitions.add(new Transition(start, added));
            _registerNestLevel(start, childNestLevel);
        }
        else if (state.isOutputState())
        {
            AbstractElement end = new EndState();

            _elements.add(end);
            _transitions.add(new Transition(added, end));
            _registerNestLevel(end, childNestLevel);
        }

        return added;
    }

    public StateChart(com.pauware.pauware_engine._Core.AbstractStatechart_monitor state_machine,
            String name, float length, float width) {
        super(name, length, width);
        
        Integer nestLevel;

        Set<com.pauware.pauware_engine._Core.Transition> transitions;
        com.pauware.pauware_engine._Core.Transition edge;
        com.pauware.pauware_engine._Core.AbstractStatechart from, to;
        AbstractElement origin, target;

        transitions = state_machine.transitions().keySet();
        
        nestLevel = 0;

        Iterator<com.pauware.pauware_engine._Core.Transition> it = transitions.iterator();
        while (it.hasNext()) {
            edge = it.next();
            from = edge.from();
            to = edge.to();

            origin = _addState(from, nestLevel);
            target = _addState(to, nestLevel);

            _transitions.add(new Transition(origin, target));
        }
    }

    public StateChart(String name, float length, float width) {
        super(name, length, width);
    }

    public StateChart(String name) {
        super(name, 100, 80);
    }

    @Override
    public void addElement(AbstractElement state) {
        _elements.add(state);
    }

    @Override
    public void removeElement(AbstractElement state) {
        _elements.remove(state);
    }

    @Override
    public void addTransition(Transition trans) {
        _transitions.add(trans);
    }

    @Override
    public void addTransition(AbstractElement origin, AbstractElement target) {
        _transitions.add(new Transition(origin, target));
    }

    @Override
    public void removeTransition(Transition trans) {
        _transitions.remove(trans);
    }

    @Override
    public void removeTransition(AbstractElement origin, AbstractElement target) {
        Transition trans = new Transition(origin, target);
        _transitions.remove(trans);
    }

    @Override
    public HashMap<Integer, HashSet<AbstractElement>> getNestingLevels()
    {
        return _nestingLevels;
    }
    @Override
    public Collection<AbstractElement> elements() {
        return _elements;
    }

    @Override
    public Collection<Transition> transitions() {
        return _transitions;
    }

    @Override
    public boolean isTransition(AbstractElement origin, AbstractElement target) {
        Transition trans = new Transition(origin, target);
        return _transitions.contains(trans);
    }
}
