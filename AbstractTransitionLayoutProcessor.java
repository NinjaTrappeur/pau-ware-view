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
public abstract class AbstractTransitionLayoutProcessor implements ITransitionLayoutProcessor
{
    protected ILayout _layout;
    protected IChart _chart;
    
    public AbstractTransitionLayoutProcessor(ILayout layout, IChart chart)
    {
        _layout = layout;
        _chart = chart;
    }

    @Override
    public void setLayout(ILayout layout)
    {
        _layout = layout;
    }

    @Override
    public void setChart(IChart chart)
    {
        _chart = chart;
    }
    
    @Override
    public ILayout getLayout()
    {
        return _layout;
    }
    
    @Override
    public IChart getChart()
    {
        return _chart;
    }
}
