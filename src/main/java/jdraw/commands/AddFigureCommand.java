/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.commands;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Removes a figure from the drawing model. This removal can be undone.
 * 
 * @author Christoph Denzler
 * 
 */
// command rolle concrete command
public class AddFigureCommand implements DrawCommand {

	/** The model from which to remove the figure. */
	private final DrawModel model;
	/** The figure to remove. */
	private final Figure figure;


	public AddFigureCommand(DrawModel model, Figure figure) {
		this.model = model;
		this.figure = figure;
	}

	@Override
	public void redo() {
		model.addFigure(figure);
	}


	@Override
	public void undo() {
		model.removeFigure(figure);
	}

}
