package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class NorthWestHandle extends AbstractFigureHandle {

	public NorthWestHandle(Figure owner) {
		super(owner);
	}

	@Override
	public Point getLocation() {
		return getOwner().getBounds().getLocation();
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
	}

	private Point anchor;

	@Override
	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		Rectangle r = getOwner().getBounds();
		anchor = new Point(r.x + r.width, r.y + r.height);
	}

	@Override
	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		getOwner().setBounds(new Point(x, y), anchor);
	}

	@Override
	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		anchor = null;
	}

}
