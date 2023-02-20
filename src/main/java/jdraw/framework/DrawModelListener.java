/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * Listener interested in draw model changes.
 *
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
@FunctionalInterface
public interface DrawModelListener {

	/**
	 * Sent when a draw model has changed.
	 * 
	 * @param e draw model event
	 */
	void modelChanged(DrawModelEvent e);
}
