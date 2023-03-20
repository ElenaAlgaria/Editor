package jdraw.figures.handles;

import jdraw.figures.AbstractFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class SState implements HandleState {
    private final AbstractFigure owner;

    public SState(AbstractFigure owner) {
        this.owner = owner;
    }

    @Override
    public final Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapVertical() {
        return new NState(owner);
    }

    @Override
    public HandleState swapHorizontal() {
        return new NState(owner);
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height );
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        if (y < bounds.y){
            owner.swapVertical();
        }

        if (x > bounds.x + bounds.width){
            owner.swapVertical();
        }
        owner.setBounds(new Point(bounds.x,y),
            new Point(bounds.x + bounds.width, bounds.y));
    }

}
