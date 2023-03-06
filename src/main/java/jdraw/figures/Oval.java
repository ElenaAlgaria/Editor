/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
// Figure = Subjekt
public class Oval implements Figure {
	private static final long seriralVersionUID = 9120181044386552132L;
	// Listener = Observer, gegen ein interface programmiert List isch es interface
	private final List<FigureListener> listeners;


	/**
	 * Use the java.awt.Rectangle in order to save/reuse code.
	 */
	private final Ellipse2D oval;

	/**
	 * Create a new rectangle of the given dimension.
	 *
	 * @param x the x-coordinate of the upper left corner of the rectangle
	 * @param y the y-coordinate of the upper left corner of the rectangle
	 * @param w the rectangle's width
	 * @param h the rectangle's height
	 */
	public Oval(int x, int y, int w, int h) {
		oval = new Ellipse2D.Double();
		listeners = new CopyOnWriteArrayList<>();
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * 
	 * @param g the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int) oval.getX(),(int) oval.getY(),(int) oval.getWidth(), (int)oval.getHeight());
		g.setColor(Color.BLACK);
		g.drawOval((int) oval.getX(),(int) oval.getY(),(int) oval.getWidth(),(int) oval.getHeight());
	}

	// notify für änderige, alli beobachter informieren
	@Override
	public void setBounds(Point origin, Point corner) {
		oval.setFrameFromDiagonal(origin, corner);
		notifyFigureList();
	}

	// notify für änderige, alli beobachter informieren
	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0){
		oval.setFrame(oval.getX() + dx, oval.getY() + dy, oval.getWidth(), oval.getHeight());
		notifyFigureList();
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return oval.contains(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return oval.getBounds();
	}

	/**
	 * Returns a list of 8 handles for this Rectangle.
	 * 
	 * @return all handles that are attached to the targeted figure.
	 * @see Figure#getHandles()
	 */
	@Override
	public List<FigureHandle> getHandles() {
		return null;
	}

	// listener hinzuefüege zu de observer liste
	@Override
	public void addFigureListener(FigureListener listener) {
		if (!listeners.contains(listener)){
		listeners.add(listener);
		}
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Figure clone() {
		return null;
	}

	// private wills sowieso nur do inne brucht wird
	// figure observer und changed = update
	private void notifyFigureList(){
		for (FigureListener listener: listeners){
			if (listener != null){
			listener.notifyObservers(new FigureEvent(this));
			}
		}
	}


}
