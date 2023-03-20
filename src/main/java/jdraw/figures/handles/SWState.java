package jdraw.figures.handles;

import jdraw.figures.AbstractFigure;
import jdraw.figures.Rect;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class SWState implements HandleState {
    private final AbstractFigure owner;

    public SWState(AbstractFigure owner) {
        this.owner = owner;
    }

    public final Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapVertical() {
        return new NWState(owner);
    }

    @Override
    public HandleState swapHorizontal() {
        return new SEState(owner);
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x, bounds.y + bounds.height);
    }

    @Override
    public Cursor getCursor() {
        return  Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        if (y < bounds.y){
            owner.swapVertical();
        }
        if (x > bounds.x + bounds.width){
            owner.swapHorizontal();
        }

        owner.setBounds(new Point(x,y),
            new Point(bounds.x + bounds.width, bounds.y));
    }


}
