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
    protected int _shallowContentSize;
    protected int _deepContentSize;
    protected AbstractElement _container;
    
    protected static final float _coveredAreaRatio;
    protected static final float _innerMarginRatio;
    protected static int _currentId;
    static{
        _coveredAreaRatio = 0.8F;
        _innerMarginRatio = 0.05F;
        _currentId = 0;
    }
    
    private void _init(String name, float width, float length, AbstractElement container)
    {
        _preSetMetric(width, "constructor, in init()", "width");
        _preSetMetric(length, "constructor, in init()", "length");

        _name = name;
        _width = width;
        _length = length;
        
        _id = _currentId;
        ++_currentId;
        
        _shallowContentSize = 0;
        _deepContentSize = 0;
        _container = container;
    }

    /**
     *
     * @param name label of the element
     * @param length metric for graphical display
     * @param width metric for graphical display
     * @param container optional container (like superstate or statechart)
     */
    public AbstractElement(String name, float width, float length, AbstractElement container)
    {
        _init(name, width, length, container);
    }

    /**
     *
     * @param name label of the element
     * @param length metric for graphical display
     * @param width metric for graphical display
     */
    public AbstractElement(String name, float width, float length)
    {
        _init(name, width, length, null);
    }

    /**
     *
     * @param name label of the element
     * @param container optional containg Element.
     */
    public AbstractElement(String name, AbstractElement container)
    {
        _init(name, 0F, 0F, container);
    }

    /**
     *
     * @param name label of the element
     */
    public AbstractElement(String name)
    {
        _init(name, 0F, 0F, null);
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
    
    public void setSize(float width, float length)
    {
        setWidth(width);
        setLength(length);
    }
    
    public void setContainer(AbstractElement container)
    {
        _preReference(container, "setContainer", "container");
        _container = container;
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
    
    public int shallowContentSize()
    {
        return _shallowContentSize;
    }
    
    public int deepContentSize()
    {
        return _deepContentSize;
    }
    
    public AbstractElement container()
    {
        return _container;
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
    
    private void _preReference(AbstractElement ref, String methodName, String argName) throws IllegalArgumentException
    {
        if(ref == null)
        {
            throw new IllegalArgumentException("AbstractElement._preInitReference (for "
                    + methodName
                    + "): null pointer given gor initilization of " + argName);
        }
    }

    private void _preSetMetric(float metric, String metricName, String methodName) throws IllegalArgumentException {
        if (metric < 0) {
            throw new IllegalArgumentException("AbstractElement._preSetMetric (for "
                    + methodName
                    + "): " + metricName
                    + " can not be negative");
        }
    }
}
