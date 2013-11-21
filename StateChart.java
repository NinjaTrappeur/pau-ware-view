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

/**
 *
 * @author josuah
 */
public class StateChart extends AbstractElement implements IChart {

    HashSet<AbstractElement> _elements;
    HashSet<Transition> _transitions;

    private AbstractElement _addState(com.pauware.pauware_engine._Core.AbstractStatechart state) {
        AbstractElement added;

        if (state.leaf()) {
            added = new State(state.name());
        } else //compositeState
        {
            com.pauware.pauware_engine._Core.AbstractStatechart l, r;
            AbstractElement left, right;

            added = new SuperState(state.name());
            // add CompositeState components : use left() and right
            l = state.left();
            r = state.right();
            left = _addState(l);
            added.addComponent(left);
            while( r.name().equals("pseudo-state") )
            {
                l = r.left();
                r = r.right();
                
                left = _addState(l);
                added.addComponent(left);
            }
            right = _addState(r);
            added.addComponent(right);
            
        }

        _elements.add(added);

        if (state.isInputState()) {
            AbstractElement start = new StartState();

            _elements.add(start);
            _transitions.add(new Transition(start, added));
        } else if (state.isOutputState()) {
            AbstractElement end = new EndState();

            _elements.add(end);
            _transitions.add(new Transition(added, end));
        }

        return added;
    }

    public StateChart(com.pauware.pauware_engine._Core.AbstractStatechart_monitor state_machine,
            String name, float length, float width) {
        super(name, length, width);

        Set<com.pauware.pauware_engine._Core.Transition> transitions;
        com.pauware.pauware_engine._Core.Transition edge;
        com.pauware.pauware_engine._Core.AbstractStatechart from, to;
        AbstractElement origin, target;

        transitions = state_machine.transitions().keySet();

        Iterator<com.pauware.pauware_engine._Core.Transition> it = transitions.iterator();
        while (it.hasNext()) {
            edge = it.next();
            from = edge.from();
            to = edge.to();

            origin = _addState(from);
            target = _addState(to);

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

    @Override
    public void draw(processing.core.PApplet applet) {
        //
    }
}
