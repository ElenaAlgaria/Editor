package jdraw.framework;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

// alli () wo zustands abhängig sind
public interface HandleState {
    /**
     * Returns the location of this handle. The result depends on the location of
     * the owning figure.
     *
     * @return location of this handle
     */
    Point getLocation();

    /**
     * Returns a curser which should be displayed when the mouse is over the handle.
     * Signals the type of operation which can be performed using this handle.
     * <P>
     * A default implementation may return Cursor.getDefaultCursor().
     *
     * @return handle's Cursor
     */
    Cursor getCursor();

    /**
     * Tracks a step of a started interaction.
     *
     * @param x the current x position
     * @param y the current y position
     * @param e the mouse event which initiated the drag interaction
     * @param v the view in which the interaction is performed
     */
    void dragInteraction(int x, int y, MouseEvent e, DrawView v);

    Figure getOwner();

    // werden vom client aufgerufen = abstractRectFig bi eus, demit client s swape mache chan
    HandleState swapVertical();

    HandleState swapHorizontal();

}
