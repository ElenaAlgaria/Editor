package jdraw.figures.handles;

import jdraw.figures.AbstractFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class WState implements HandleState {
    private final AbstractFigure owner;

    public WState(AbstractFigure owner) {
        this.owner = owner;
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x, bounds.y + bounds.height / 2);
    }

    @Override
    public final Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapVertical() {
        return new EState(owner);
    }

    @Override
    public HandleState swapHorizontal() {
        return new EState(owner);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
       var bounds = owner.getBounds();
        owner.setBounds(new Point(x, bounds.y),
            new Point(bounds.x + bounds.width, bounds.y + bounds.height));
        if (x > bounds.width){
            owner.swapHorizontal();
        }
    }
}
