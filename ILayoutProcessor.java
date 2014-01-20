/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

/**
 * Interface for layout processor components.
 * Our approch is component-oriented. A LayoutProcessor object,
 * meaning a subclass of this interface, is passed a a graph and "outputs"
 * a layout.
 * A loyout is an object that olds a mapping of "Elements" to a 2D positionning.
 */
public interface ILayoutProcessor
{
    void init(IChart chart, ITransitionLayoutProcessor transitionProcessor);
    void init(IChart chart);
    
    void processLayout();
    
    void setTransitionLayoutProcessor(ITransitionLayoutProcessor transitionProcessor);
    void setLayout(ILayout layout);
    void setChart(IChart chart);

    ILayout getLayout();
    IChart getChart();
}
