package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class WestHandle extends AbstractFigureHandle {

	public WestHandle(Figure owner) {
		super(owner);
	}

	@Override
	public Point getLocation() {
		Rectangle bounds = getOwner().getBounds();
		return new Point(bounds.x, bounds.y + bounds.height / 2);
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
	}
	
	private Point anchor;
	private int height;

	@Override
	public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
		Rectangle r = getOwner().getBounds();
		height = r.height;
		anchor = new Point(r.x + r.width, r.y + r.height / 2);
	}

	@Override
	public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
		getOwner().setBounds(new Point(x, anchor.y - height / 2), new Point(anchor.x, anchor.y + height / 2));
	}

	@Override
	public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
		anchor = null;
	}

}
