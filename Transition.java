/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 * 
 * Code by Aron Josuah and Baylac-Jacque Felix.
 */
package com.PauWare.PauWare_view;

public class Transition
{
    private AbstractElement _origin;
    private AbstractElement _target;
    
    public Transition(AbstractElement origin, AbstractElement target)
    {
        _preReference(origin, "constructor", "origin");
        _preReference(target, "constructor", "target");
        _origin = origin;
        _target = target;
    }
    
    public void setOrigin(AbstractElement origin)
    {
        _preReference(origin, "setOrigin", "origin");
        _origin = origin;
    }
    
    public void setTarget(AbstractElement target)
    {
        _preReference(target, "setOrigin", "target");
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
    
    private void _preReference(AbstractElement ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("Transition._preReference (for "
                    + methodName
                    + "): null pointer given for parameter of " + argName);
        }
    }
}
