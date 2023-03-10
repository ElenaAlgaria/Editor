package jdraw.figures.handles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

public abstract class AbstractFigureHandle implements FigureHandle {

	private static final int HANDLE_SIZE = 6;
	private final Figure owner;

	protected AbstractFigureHandle(Figure owner) {
		this.owner = owner;
	}

	@Override
	public final Figure getOwner() {
		return owner;
	}

	@Override
	public final void draw(Graphics g) {
		Point loc = getLocation();
		g.setColor(Color.WHITE);
		g.fillRect(loc.x - HANDLE_SIZE/2, loc.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(loc.x - HANDLE_SIZE/2, loc.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
	}

	@Override
	public final boolean contains(int x, int y) {
		Point loc = getLocation();
		return Math.abs(x - loc.x) < HANDLE_SIZE / 2 && Math.abs(y - loc.y) < HANDLE_SIZE / 2;
	}

}
