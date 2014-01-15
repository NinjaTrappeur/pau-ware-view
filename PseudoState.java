/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PauWare.PauWare_view;

/**
 *
 * @author josuah
 */
public class PseudoState extends AbstractElement
{
     /**
     * This class represents a graphic start state.
     * 
     * @param name Name of the state.
     * @param container  optional container (like superstate or statechart)
     */
      public PseudoState(String name, AbstractElement container)
    {
        super(name, 30, 30, container);
    }
      public PseudoState(String name)
    {
        super(name, 30, 30);
    }
}
