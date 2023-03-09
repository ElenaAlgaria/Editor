/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author Christoph Denzler
 */
public final class RectTool extends AbstractTool {

	/**
	 * Create a new rectangle tool for the given context.
	 * 
	 * @param context a context to use this tool in.
	 */
	public RectTool(DrawContext context) {
		// super für die parent class wo en parameter verlangt
		super(context, "Rectangle", "rectangle.png");
	}

	// vo abstrakte () wegem new ...
	@Override
	protected Figure createFigureAt(int x, int y) {
		return new Rect(x,y,0,0);
	}
}
