/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.util.stream.Stream;

/**
 * The class DrawModel represents the model of a drawing, i.e. all figures
 * stored in a graphic. Every draw view refers to a model.
 *
 * @see DrawView
 *
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
// command rolle receiver
public interface DrawModel {

	/**
	 * Adds a new figure to the draw model.
	 * 
	 * @param f figure to be added to draw model.
	 */
	void addFigure(Figure f);

	/**
	 * Removes a given figure from the draw model.
	 * 
	 * @param f figure to be removed from draw model.
	 */
	void removeFigure(Figure f);

	/**
	 * Remove all figures from the draw model. This method is used when loading a
	 * drawing from a file in order to clear the old drawing before loading the new
	 * one.
	 */
	void removeAllFigures();

	/**
	 * Returns a sequential {@code Stream} of figures with this draw model as its
	 * source. This stream can be used to iterate over all figures contained in this
	 * model. The order of the figures is the order in which the figures were added
	 * to the model (insertion-order). The order is interpreted by the view as
	 * "back-to-front".
	 * 
	 * @return a sequential {@code Stream} over the figures in this draw model
	 */
	Stream<? extends Figure> getFigures();

	/**
	 * Adds the specified model listener to the set of listeners for this model,
	 * provided that is not the same as some listener already in the set.
	 * The order in which the model events will be delivered to multiple
     * listeners is not specified.
	 * If listener is null, no exception is thrown and no action is performed.
	 * 
	 * @param listener the draw model listener to be added.
	 * @see DrawModelListener
	 */
	void addModelChangeListener(DrawModelListener listener);

	/**
	 * Removes the specified model listener from the set of listeners of this model.
	 * After invocation of this method, the listener no longer receives model events
	 * from this model. This method performs no function, nor does it throw
	 * an exception, if the listener specified by the argument was not previously
	 * added to this model. If listener is null, no exception is thrown and no
	 * action is performed.
	 * 
	 * @param listener the draw model listener.
	 * @see DrawModelListener
	 */
	void removeModelChangeListener(DrawModelListener listener);

	/**
	 * Returns the draw command handler provided by this model.
	 * 
	 * @see DrawCommandHandler
	 * @return the draw command handler used.
	 */
	DrawCommandHandler getDrawCommandHandler();

	/**
	 * Sets the index of a given figure. The order of the other figures in the model
	 * remains unchanged.
	 * 
	 * If the figure is moved to a new place, then the model sends a
	 * <code>DRAWING_CHANGED</code> event to all registered model listeners.
	 * 
	 * @param f     the figure whose index has to be set
	 * @param index the position at which the new figure should appear. The other
	 *              figures are moved away.
	 * 
	 * @throws IllegalArgumentException  if the figure is not contained in the
	 *                                   model.
	 * @throws IndexOutOfBoundsException if the index is out of range (
	 *                                   <code>index &lt; 0 || index &ge; size()</code>)
	 *                                   where size() is the number of figures
	 *                                   contained in the model.
	 */
	void setFigureIndex(Figure f, int index) throws IllegalArgumentException, IndexOutOfBoundsException;

}
