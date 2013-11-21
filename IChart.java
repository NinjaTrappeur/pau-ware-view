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
    void addElement(AbstractElement state);
    void removeElement(AbstractElement state);
    
    void addTransition(Transition trans);
    void addTransition(AbstractElement origin, AbstractElement target);
    void removeTransition(Transition trans);
    void removeTransition(AbstractElement origin, AbstractElement target);
    
    Collection<AbstractElement> elements();
    Collection<Transition> transitions();
    
    boolean isTransition(AbstractElement origin, AbstractElement target);
}
