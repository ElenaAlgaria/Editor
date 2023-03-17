package jdraw.figures.handles;

import jdraw.figures.AbstractFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class NEState implements HandleState {
    private final AbstractFigure owner;

    public NEState(AbstractFigure owner) {
        this.owner = owner;
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x + bounds.width, bounds.y );
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        owner.setBounds(new Point(x,y),
            new Point(bounds.x, bounds.y + bounds.height));
        if (x > bounds.x + bounds.width) {
            owner.swapHorizontal();
        }
        if (y > bounds.y + bounds.height) {
            owner.swapHorizontal();
        }
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapVertical() {
        return new SEState(owner);
    }

    @Override
    public HandleState swapHorizontal() {
        return new NWState(owner);
    }

}
