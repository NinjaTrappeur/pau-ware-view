/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */

package com.PauWare.PauWare_view;

/**
 *
 * @author josuah
 */
public interface ITransitionLayoutProcessor
{
    
    void computeTransitionsLayout();
    
    void setLayout(ILayout layout);
    void setChart(IChart chart);
    
    ILayout getLayout();
    IChart getChart();
    
}
