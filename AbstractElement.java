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
    protected float _length;
    /**
     * !< bounding recteangle length
     */
    protected float _width;

    /**
     * !< bounding recteangle width
     */

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
    public AbstractElement(String name, float length, float width) {
        _preSetMetric(length, "constructor", "length");
        _preSetMetric(width, "constructor", "width");

        _name = name;
        _length = length;
        _width = width;
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

    public void setLength(float length) {
        _preSetMetric(length, "setLength", "length");
        _length = length;
    }

    public void setWidth(float width) {
        _preSetMetric(width, "setWidth", "width");
        _width = width;
    }

    public String name() {
        return _name;
    }

    public float length() {
        return _length;
    }

    public float width() {
        return _width;
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
        hash = 23 * hash + Objects.hashCode(this._name);
        hash = 23 * hash + Float.floatToIntBits(this._length);
        hash = 23 * hash + Float.floatToIntBits(this._width);
        return hash;
    }

}
