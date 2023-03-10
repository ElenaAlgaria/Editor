/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

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

	// für tests
	public Rect(int x, int y, int width, int height) {
		super(x,y);
	}

	/**
	 * Create a new rectangle of the given dimension.
	 * 
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 */
	public Rect(int x, int y) {
		this(x, y, 0, 0);
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

}
