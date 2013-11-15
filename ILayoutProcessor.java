/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

/**
 *
 * @author jaron
 * Interface for layout processor components.
 * Our approch is component-oriented. A LayoutProcessor object,
 * meaning a subclass of this interface, is passed a a graph and "outputs"
 * a layout.
 * A loyout is an object that olds a mapping of "Elements" to a 2D positionning.
 */
public interface ILayoutProcessor {
    void init(IChart chart);
    
    ILayout getLayout();
}
