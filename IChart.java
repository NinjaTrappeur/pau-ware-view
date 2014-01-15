/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author jaron
 * Interface for all Graph type éléments.
 * This interface describes the required behaviour for graph-like objects
 * 
 */
public interface IChart
{
    void addElement(AbstractElement state, AbstractElement container);
    void addElement(AbstractElement state);
    boolean removeElement(AbstractElement state);
    
    void addTransition(AbstractElement origin, AbstractElement target);
    void addTransition(Transition trans);
    boolean removeTransition(AbstractElement origin, AbstractElement target);
    boolean removeTransition(Transition trans);
    
    public HashMap<Integer, HashSet<AbstractElement>> nestingLevels();
    
    Collection<AbstractElement> elements();
    Collection<Transition> transitions();
    
    boolean isTransition(AbstractElement origin, AbstractElement target);
    boolean hasElement(AbstractElement state);
}
