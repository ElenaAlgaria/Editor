package jdraw.figures.handles;

import jdraw.figures.AbstractFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class NState implements HandleState {
    private final AbstractFigure owner;

    public NState(AbstractFigure owner){
        this.owner = owner;
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x + bounds.width / 2, bounds.y);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y,MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        owner.setBounds(new Point(bounds.x, y),
            new Point(bounds.x + bounds.width, bounds.y + bounds.height));
        if (y > bounds.height){
            owner.swapVertical();
        }
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapVertical() {
        return new SState(owner);
    }

    @Override
    public HandleState swapHorizontal() {
        return new SState(owner);
    }

}
