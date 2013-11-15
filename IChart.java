/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

import java.util.Collection;

/**
 *
 * @author jaron
 * Interface for all Graph type éléments.
 * This interface describes the required behaviour for graph-like objects
 * 
 */
public interface IChart
{
    void addState(AbstractElement state);
    void removeState(AbstractElement state);
    
    void addTransition(Transition trans);
    void removeTransition(Transition trans);
    
    Collection<Transition> getStates();
    Collection<Transition> getTransitions();
}
