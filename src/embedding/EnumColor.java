package embedding;

import java.awt.Color;

/**
 * Definition for colors in the window.
 * @author tommy
 *
 */
public enum EnumColor {

	SET1,			// vertex in set1
	SET2,			// vertex in set2
	CROSSING,		// crossing
	NEW_NODE,		// newly inserted vertex
	EDGE,			// edge
	INFO,			// information text
	DRAWING_BACK,	// background of a drawing
	VERTEX_TEXT, 	// text for a vertex
	EDGE_TEXT;		// text for an edge
	
	/**
	 * Returns the color.
	 * @return a color
	 */
	public Color getColor() {
		switch(this) {
		case SET1: default: return new Color(0,0,255);
		case SET2:  	    return new Color(255,0,0);
		case CROSSING:	    return new Color(210,40,200);
		case NEW_NODE:      return new Color(0,160,0);
		case EDGE:          return new Color(255,255,255);//(0,0,0);
		case INFO:          return new Color(0,0,0);
		case DRAWING_BACK:  return new Color(0,0,0);//(180,180,180);
		case VERTEX_TEXT:   return new Color(255,255,255);
		case EDGE_TEXT:  	return new Color(50,255,50);//(255,255,255);
		}
	}
	
	/**
	 * Returns the name of this element.
	 * @return name
	 */
	public String getString() {
		return this.name();
	}
	
	/**
	 * Returns the value corresponding to the specified name. 
	 * @param name 	name of a color
	 * @return		the <code>EnumColor</code>
	 */
	public static EnumColor getEnumColor(String name) {
		return EnumColor.valueOf(name);
	}
}