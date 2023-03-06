/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

/**
 * This tool defines a mode for drawing Ovalss.
 *
 * @see jdraw.framework.Figure
 *
 * @author Christoph Denzler
 */
public class OvalTool implements DrawTool {

	/**
	 * the image resource path.
	 */
	private static final String IMAGES = "/images/";

	/**
	 * The context we use for drawing.
	 */
	private final DrawContext context;

	/**
	 * The context's view. This variable can be used as a shortcut, i.e. instead of
	 * calling context.getView().
	 */
	private final DrawView view;

	/**
	 * Temporary variable. During oval creation (during a mouse down - mouse
	 * drag - mouse up cycle) this variable refers to the new oval that is
	 * inserted.
	 */
	private Oval newOval = null;

	/**
	 * Temporary variable. During oval creation this variable refers to the
	 * point the mouse was first pressed.
	 */
	private Point anchor = null;

	/**
	 * Create a new oval tool for the given context.
	 *
	 * @param context a context to use this tool in.
	 */
	public OvalTool(DrawContext context) {
		this.context = context;
		this.view = context.getView();
	}

	/**
	 * Deactivates the current mode by resetting the cursor and clearing the status
	 * bar.
	 * 
	 * @see DrawTool#deactivate()
	 */
	@Override
	public void deactivate() {
		this.context.showStatusText("");
	}

	/**
	 * Activates the oval Mode. There will be a specific menu added to the menu
	 * bar that provides settings for oval attributes
	 */
	@Override
	public void activate() {
		this.context.showStatusText("Oval Mode");
	}

	/**
	 * Initializes a new Oval object by setting an anchor point where the mouse
	 * was pressed. A new Oval is then added to the model.
	 * 
	 * @param x x-coordinate of mouse
	 * @param y y-coordinate of mouse
	 * @param e event containing additional information about which keys were
	 *          pressed.
	 * 
	 * @see DrawTool#mouseDown(int, int, MouseEvent)
	 */
	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newOval != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newOval = new Oval(x, y, 0, 0);
		view.getModel().addFigure(newOval);
	}

	/**
	 * During a mouse drag, the Oval will be resized according to the mouse
	 * position. The status bar shows the current size.
	 * 
	 * @param x x-coordinate of mouse
	 * @param y y-coordinate of mouse
	 * @param e event containing additional information about which keys were
	 *          pressed.
	 * 
	 * @see DrawTool#mouseDrag(int, int, MouseEvent)
	 */
	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		newOval.setBounds(anchor, new Point(x, y));
		Rectangle oval = newOval.getBounds();
		this.context.showStatusText("w: " + oval.width + ", h: " + oval.height);
	}

	/**
	 * When the user releases the mouse, the Oval object is updated according
	 * to the color and fill status settings.
	 * 
	 * @param x x-coordinate of mouse
	 * @param y y-coordinate of mouse
	 * @param e event containing additional information about which keys were
	 *          pressed.
	 * 
	 * @see DrawTool#mouseUp(int, int, MouseEvent)
	 */
	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		newOval = null;
		anchor = null;
		this.context.showStatusText("Oval Mode");
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + "oval.png"));
	}

	@Override
	public String getName() {
		return "Oval";
	}

}
