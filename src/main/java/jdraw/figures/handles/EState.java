package jdraw.figures.handles;

import jdraw.figures.AbstractFigure;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class EState implements HandleState {
    private final AbstractFigure owner;

    public EState(AbstractFigure owner) {
        this.owner = owner;
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x + bounds.width, bounds.y + bounds.height / 2);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
    }

    @Override
    public void dragInteraction(int x, int y,MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        if (x < bounds.x){
            owner.swapHorizontal();
        }
        owner.setBounds(new Point(x, bounds.y),
            new Point(bounds.x, bounds.y + bounds.height));
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapVertical() {
        return new WState(owner);
    }

    @Override
    public HandleState swapHorizontal() {
        return new WState(owner);
    }

}
