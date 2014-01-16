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
    
    private Integer _clusterize(com.pauware.pauware_engine._Core.AbstractStatechart l,
                             com.pauware.pauware_engine._Core.AbstractStatechart r,
                             Integer cluster)
    {
        if(l.isAndWith(r))
        {
            ++cluster;
        }
        
        return cluster;
    }

    private AbstractElement _addState(com.pauware.pauware_engine._Core.AbstractStatechart state,
            Integer nestLevel, AbstractElement container, Integer cluster)
    {
        AbstractElement added;
        if (state.leaf())
        {
            added = new State(state, container);
        }
        
        else if(state.name().equals("pseudo-state"))
        {
            added = null;
            _addState(state.left(), nestLevel, container, cluster);
            _addState(state.right(), nestLevel, container, cluster);
        }
        
        else //compositeState
        {
            SuperState composite;
            com.pauware.pauware_engine._Core.AbstractStatechart l, r;
            AbstractElement left, right;
            Integer childNestLevel = nestLevel + 1;
            Integer comp_cluster = 0;

            composite = new SuperState(state, container);
            // add CompositeState components : use left() and right()
            l = state.left();
            r = state.right();
            left = _addState(l, childNestLevel, composite, comp_cluster);
            composite.addSubState(left, comp_cluster);
            comp_cluster = _clusterize(l, r, comp_cluster);
            while( r.name().equals("pseudo-state") )
            {
                l = r.left();
                r = r.right();
                
                left = _addState(l, childNestLevel, composite, comp_cluster);
                composite.addSubState(left, comp_cluster);
                comp_cluster = _clusterize(l, r, comp_cluster);
            }
            right = _addState(r, childNestLevel, composite, comp_cluster);
            
            composite.addSubState(right, comp_cluster);

            added = composite;
        }

        if(added != null){
            this.addElement(added);
            if(container instanceof SuperState)
            {
                SuperState sup = (SuperState)container;
                sup.addSubState(added, cluster);
            }
            
            _fromOriginal.put(state, added);
            _registerNestLevel(added, nestLevel);
        }

        if (state.isInputState())
        {
            AbstractElement start = new StartState("start_to_"+state.name(), container);

            this.addElement(start, container);
            if(container instanceof SuperState)
            {
                SuperState sup = (SuperState)container;
                sup.addSubState(start, cluster);
            }
            
            _transitions.add(new Transition(start, added));
            _registerNestLevel(start, nestLevel);
        }
        else if (state.isOutputState())
        {
            AbstractElement end = new EndState("end_from_"+state.name(), container);

            this.addElement(end, container);
            if(container instanceof SuperState)
            {
                SuperState sup = (SuperState)container;
                sup.addSubState(end, cluster);
            }
            
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
        
        myLeft = _addState(left, 0, this, 0);
        myRight = _addState(right, 0, this, 0);
        
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
    public void addElement(AbstractElement state, AbstractElement container)
    {
        _preReference(state, "addElement", "state");
        _preReference(container, "addElement", "container");
        _preContainer(container);
        
        //state.setContainer(container);
        
        boolean added = _elements.add(state);
        if(added)
        {
            if(container == this)
                ++_shallowContentSize;
            ++_deepContentSize;
        }
    }

    @Override
    public void addElement(AbstractElement state)
    {
        addElement(state, this);
    }

    @Override
    public boolean removeElement(AbstractElement state)
    {
        _preReference(state, "removeElement", "state");
        boolean removed = _elements.remove(state);
        
        if(removed)
        {
            state.setContainer(null);
            if(state.container() == this)
                --_shallowContentSize;
            --_deepContentSize;
        }
        
        return removed;
    }

    @Override
    public void addTransition(AbstractElement origin, AbstractElement target)
    {
        _preReference(origin, "addTransition", "origin");
        _preReference(target, "addTransition", "target");
        
        if(!this.hasElement(origin))
        {
            this.addElement(origin);
        }
        
        if(!this.hasElement(target))
        {
            this.addElement(target);
        }

        //need a pre for prensence in chart (or just add the new elements)
        _transitions.add(new Transition(origin, target));
    }

    @Override
    public void addTransition(Transition trans)
    {
        this.addTransition(trans.origin(), trans.target());
    }

    @Override
    public boolean removeTransition(AbstractElement origin, AbstractElement target)
    {
        _preReference(origin, "removeTransition", "origin");
        _preReference(target, "removeTransition", "target");
        
        Transition trans = new Transition(origin, target);
        return _transitions.remove(trans);
    }

    @Override
    public boolean removeTransition(Transition trans) throws IllegalArgumentException
    {
        if(trans == null)
        {
            throw new IllegalArgumentException("StateChart.removeTransition:"+
                    "null pointer given for argument trans");
        }

        return _transitions.remove(trans);
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

    @Override
    public boolean hasElement(AbstractElement state)
    {
        return _elements.contains(state);
    }
    
    private void _preReference(AbstractElement ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("StateChart._preReference (for "
                    + methodName
                    + "): null pointer given for parameter of " + argName);
        }
    }
    
    private void _preContainer(AbstractElement container) throws IllegalArgumentException
    {
//        if(!this.hasElement(container) && container != this)
//        {
//            throw new IllegalArgumentException("StateChart._preContainer:"+
//                    " given container is not in the chart"+
//                    "\n\t given container is "+container.name()
//            );
//        }

        if(! (container instanceof SuperState || container instanceof StateChart))
        {
            throw new IllegalArgumentException("StateChart._preContainer:"+
                    " given container is not a container"+
                    "\n\t given container is "+container.name()
            );
        }
    }
}
