/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PauWare.PauWare_view;

/**
 *
 * @author jaron
 */
public class Transition
{
    private AbstractElement _origin;
    private AbstractElement _target;
    
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
    
    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Transition))
        {
            return false;
        }

        else if(obj == this)
        {
            return true;
        }

        else
        {
            Transition trans = ((Transition) obj);
            return (this._origin.equals(trans._origin) &&
                    this._target.equals(trans._target)
                    );
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this._origin.hashCode();
        hash = 53 * hash + this._target.hashCode();
        return hash;
    }
}
