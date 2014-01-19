/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;

/**
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
