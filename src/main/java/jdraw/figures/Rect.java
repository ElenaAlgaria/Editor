/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import jdraw.figures.handles.Handle;
import jdraw.figures.handles.NWState;
import jdraw.figures.handles.SEState;
import jdraw.figures.handles.SWState;
import jdraw.figures.handles.WState;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.HandleState;
import jdraw.std.AbstractRectangularFigure;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
// Figure = Subjekt
public class Rect extends AbstractRectangularFigure {
	private static final long seriralVersionUID = 9120181044386552132L;

	/**
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param width
	 * @param height
	 *
	 */
	// für tests
	public Rect(int x, int y, int width, int height) {
		super(x, y);
	}
/**
 * Create a new rectangle of the given dimension.
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle

 */
	public Rect(int x, int y) {
		this(x, y, 0,0);
	}

	// für copy, s git kein clonable state darum do nur super
	public Rect(Rect source) {
		super(source);
	}
	/**
	 * Draw the rectangle to the given graphics context.
	 * 
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		Rectangle rectangle = getBounds();
		g.setColor(Color.WHITE);
		g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		g.setColor(Color.BLACK);
		g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	@Override
	public Rect clone(){
		System.out.println("Rect copy");
		return new Rect(this);
	}

// java cloning, eif zrugg geh, doh bruchts es ned unbedingt weil mir do kein clonable state kopiere tüend u will niemmert
// clients es rect erwartet sondern figure darum ienge vo rückgabetyp ned nötig
//	@Override
//	public Rect clone(){
//		return (Rect) super.clone();
//	}


}
