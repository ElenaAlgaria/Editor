/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * Listener interested in figure changes.
 * 
 * @see FigureEvent
 *
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
@FunctionalInterface
public interface FigureListener {

	/**
	 * Sent when a figure has changed.
	 * 
	 * @param e figure event
	 */
	void notifyObservers(FigureEvent e);
}
