/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * An event which indicates that a figure event occurred in a figure. This may
 * be a change of position or a change of the figure's size.
 * 
 * @see FigureListener
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public class FigureEvent {

	/**
	 * The figure on which the Event initially occurred.
	 */
	private final Figure source;

	/**
	 * Constructs a FigureEvent object with the specified figure.
	 * 
	 * @param source figure which changed
	 */
	public FigureEvent(Figure source) {
		this.source = source;
	}

	/**
	 * Returns the figure which changed.
	 * 
	 * @return changed figure
	 */
	public Figure getFigure() {
		return source;
	}
}
