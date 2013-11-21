/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

/**
 *
 * @author jaron
 */
public class Transition {
    private AbstractElement _origin;
    private AbstractElement _target;
    
    private void _preSetLength()
    {
        
    }
    
    private void _preSetWidth()
    {
        
    }
    
    private void _preSet()
    {
        
    }
    
    public Transition(AbstractElement origin, AbstractElement target)
    {
        _origin = origin;
        _target = target;
    }
    
    public void setOrigin(AbstractElement origin)
    {
        _origin = origin;
    }
    
    public void setTarget(AbstractElement target)
    {
        _target = target;
    }
    
    public AbstractElement origin()
    {
        return _origin;
    }
    
    public AbstractElement target()
    {
        return _target;
    }
}
