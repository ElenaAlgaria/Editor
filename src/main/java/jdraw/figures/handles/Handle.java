package jdraw.figures.handles;

import jdraw.commands.SetBoundsCommand;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.HandleState;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

// nach state is de kontext, nw immer nw wechselt zustand evt
// züg wo mit state ztue hett eif wiiterwiise uf de state
public class Handle implements FigureHandle {
    private HandleState state;
    private static final int HANDLE_SIZE = 6;
    private Rectangle startBounds;

    public Handle(HandleState handleState) {
     this.state = handleState;
    }

    // externes model, figur rüeft da vo use uf
    public HandleState getState() {
        return state;
    }

    public void setState(HandleState state) {
        this.state = state;
    }

    @Override
    public Figure getOwner() {
        return state.getOwner();
    }

    @Override
    public Point getLocation() {
        return state.getLocation();
    }

    public final void draw(Graphics g) {
        Point loc = getLocation();
        g.setColor(Color.WHITE);
        g.fillRect(loc.x - HANDLE_SIZE/2, loc.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(loc.x - HANDLE_SIZE/2, loc.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
    }

    @Override
    public Cursor getCursor() {
        return state.getCursor();
    }

    @Override
    public final boolean contains(int x, int y) {
        Point loc = getLocation();
        return Math.abs(x - loc.x) < HANDLE_SIZE / 2 && Math.abs(y - loc.y) < HANDLE_SIZE / 2;
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.dragInteraction(x,y,e,v);
    }

    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
         startBounds = getOwner().getBounds();
    }

    // für setBoundscommand
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        v.getModel().getDrawCommandHandler().addCommand(new SetBoundsCommand(getOwner(), startBounds));
    }

}
