/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.std.AbstractRectangularFigure;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
// Figure = Subjekt
public class Oval extends AbstractRectangularFigure {
	private static final long seriralVersionUID = 9120181044386552132L;

	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	private final Ellipse2D oval;

	/**
	 * Create a new rectangle of the given dimension.
	 *
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 */
	public Oval(int x, int y, int with, int height) {
		super(x, y);
		oval = new Ellipse2D.Double();
	}

	public Oval(Oval source) {
		super(source);
		oval = (Ellipse2D) source.oval.clone();
	}

	@Override
	public Oval clone(){
		return new Oval(this);
	}

//	java cloning
//	@Override
//	public Oval clone(){
//		Oval o = (Oval) super.clone();
//		o.oval = (Ellipse2D.Float) oval.clone();
//		return o;
//	}


	/**
	 * Draw the rectangle to the given graphics context.
	 * 
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		Rectangle bounds = getBounds();
		// for contains, settet s rectangle frame ums oval
		oval.setFrame(bounds.x, bounds.y, bounds.width, bounds.height);

		g.setColor(Color.WHITE);
		g.fillOval(bounds.x,bounds.y,bounds.width, bounds.height);
		g.setColor(Color.BLACK);
		g.drawOval((int) oval.getX(),(int) oval.getY(),(int) oval.getWidth(),(int) oval.getHeight());
	}

	@Override
	public boolean contains(int x, int y) {
		return oval.contains(x, y);
	}


}
