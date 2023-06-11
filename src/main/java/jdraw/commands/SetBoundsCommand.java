package jdraw.commands;

import jdraw.framework.DrawCommand;
import jdraw.framework.Figure;

import java.awt.Point;
import java.awt.Rectangle;

public class SetBoundsCommand implements DrawCommand {
    private final Rectangle start;
    private final Rectangle end;
    private final Figure figure;

    public SetBoundsCommand(Figure figure, Rectangle start) {
        this.start = start;
        this.end = figure.getBounds(); // getBounds macht auto e copy vo rect
        this.figure = figure;
    }

    @Override
    public void redo() {
        figure.setBounds(end.getLocation(), getCorner(end));

    }

    @Override
    public void undo() {
        figure.setBounds(start.getLocation(), getCorner(start));
    }

    private Point getCorner(Rectangle r){
        return new Point(r.x + r.width, r.y + r.height);
    }
}
