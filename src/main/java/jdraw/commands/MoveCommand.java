/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.commands;

import jdraw.framework.DrawCommand;
import jdraw.framework.Figure;

/**
 * Provides a command for move operations of figures.
 * 
 * @author Christoph Denzler
 *
 */
public class MoveCommand implements DrawCommand {
	private static final long serialVersionUID = 6532903929787642788L;

	/** The figure being moved. */
	private final Figure f;

	/** The amount of pixels that the figure is moved in horizontal direction. */
	private final int dx;

	/** The amount of pixels that the figure is moved in vertical direction. */
	private final int dy;

	/**
	 * A move command contains the information about the figure and its relative
	 * distance in the move operation.
	 * 
	 * @param aFigure the figure that was moved by this command.
	 * @param dx      the number of pixels the figure was moved along the x-axis.
	 * @param dy      the number of pixels the figure was moved along the y-axis.
	 */
	public MoveCommand(Figure aFigure, int dx, int dy) {
		this.f = aFigure;
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Apply the stored movement again.
	 */
	@Override
	public void redo() {
		f.move(dx, dy);
	}

	/**
	 * Undo the stored movement.
	 */
	@Override
	public void undo() {
		f.move(-dx, -dy);
	}

}
