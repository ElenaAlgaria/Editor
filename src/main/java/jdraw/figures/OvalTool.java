/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * This tool defines a mode for drawing Ovalss.
 *
 * @see jdraw.framework.Figure
 *
 * @author Christoph Denzler
 */
public final class OvalTool extends AbstractTool {

	public OvalTool(DrawContext context) {
		super(context, "Oval", "oval.png");
	}

	@Override
	protected Figure createFigureAt(int x, int y) {
		return new Oval(x, y, 0, 0);
	}

}
