/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author ninjatrappeur
 */
public class TestChart implements IChart {
    HashSet<AbstractElement> _states;
    
    public TestChart(){
    _states = new HashSet();
    }
    
    @Override
    public void addElement(AbstractElement state){
        System.out.println(state.toString());
        _states.add(state);
    }
    
    @Override
    public boolean removeElement(AbstractElement state){
        return _states.remove(state);
    }
    
    @Override
    public void addTransition(Transition trans){
        
    }
    
    @Override
    public void addTransition(AbstractElement origin, AbstractElement target){
        
    }
    
    @Override
    public boolean removeTransition(Transition trans){
        // à faire
        return false;
    }
    
    @Override
    public boolean removeTransition(AbstractElement origin, AbstractElement target){
        // à faire
        return false;
    }
    
    @Override
    public HashMap<Integer, HashSet<AbstractElement>> nestingLevels(){
        return new HashMap();
    }
    
    @Override
    public Collection<AbstractElement> elements(){
        return _states;
    }
    
    @Override
    public Collection<Transition> transitions(){
        return new HashSet();
    }
    
    @Override
    public boolean isTransition(AbstractElement origin, AbstractElement target){
        return false;
    }

    @Override
    public boolean hasElement(AbstractElement state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
