/**
 * PauWare view software (http://www.PauWare.com). Use of this software is
 * subject to the restrictions of the LGPL license version 3
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 */
package com.PauWare.PauWare_view;

/**
 * Abstract graphic element.
 *
 */
public abstract class AbstractElement
{

    protected String _name;
    protected float _width; /** !< bounding rectangle width (x axis) */
    protected float _length; /** !< bounding rectangle length (y axis) */
    protected int _id;
    
    static protected int _currentId;
    static{
        _currentId = 0;
    }

    private void _preSetMetric(float metric, String metricName, String methodName) throws IllegalArgumentException {
        if (metric < 0) {
            throw new IllegalArgumentException("AbstractElement._preSetMetric (for "
                    + methodName
                    + "): " + metricName
                    + " can not be negative");
        }
    }

    /**
     *
     * @param name label of the element
     * @param length metric for graphical display
     * @param width metric for graphical display
     */
    public AbstractElement(String name, float width, float length) {
        _preSetMetric(width, "constructor", "width");
        _preSetMetric(length, "constructor", "length");

        _name = name;
        _width = width;
        _length = length;
        
        _id = _currentId;
        ++_currentId;
    }

    /**
     *
     * @param name label of the element
     */
    public AbstractElement(String name) {
        _name = name;
        _length = 0;
        _width = 0;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setWidth(float width) {
        _preSetMetric(width, "setWidth", "width");
        _width = width;
    }

    public void setLength(float length) {
        _preSetMetric(length, "setLength", "length");
        _length = length;
    }

    public String name() {
        return _name;
    }

    public float width() {
        return _width;
    }

    public float length() {
        return _length;
    }
    
    public int id()
    {
        return _id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof AbstractElement))
        {
            return false;
        }
        else if(obj == this)
        {
            return true;
        }
        else
        {
            AbstractElement elt = ((AbstractElement) obj);
            return (this._name.equals(elt.name()) &&
                    this._length == elt.length() &&
                    this._width == elt.width()
                    );
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this._name.hashCode();
        hash = 23 * hash + String.valueOf(this._length).hashCode();
        hash = 23 * hash + String.valueOf(this._width).hashCode();
        return hash;
    }

}
