package jdraw.figures.handles;

import jdraw.figures.AbstractFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class NWState implements HandleState {
    private final AbstractFigure owner;

    public NWState(AbstractFigure owner) {
        this.owner = owner;
    }

    @Override
    public Point getLocation() {
        return owner.getBounds().getLocation();
    }

    @Override
    public final Figure getOwner() {
        return owner;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        // auf owner neue grösse setzen, corner gegeteil vo origin wo mer drufklickt
        var bounds = owner.getBounds();
        // origin mit x u y
        owner.setBounds(new Point(x, y),
            new Point(bounds.x + bounds.width, bounds.y + bounds.height));
        if (x > bounds.x + bounds.width) {
            owner.swapHorizontal();
        }
        if (y > bounds.y + bounds.height) {
            owner.swapHorizontal();
        }

    }

    @Override
    public HandleState swapVertical() {
        return new SWState(owner);
    }

    @Override
    public HandleState swapHorizontal() {
        return new NEState(owner);
    }

}
