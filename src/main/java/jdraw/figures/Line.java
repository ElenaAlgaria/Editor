/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import jdraw.figures.handles.Handle;
import jdraw.figures.handles.NEState;
import jdraw.figures.handles.NWState;
import jdraw.figures.handles.SEState;
import jdraw.figures.handles.SWState;
import jdraw.framework.FigureHandle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.List;

/**
 * Represents rectangles in JDraw.
 *
 * @author Christoph Denzler
 */
// Figure = Subjekt
public class Line extends AbstractFigure {
    private static final long seriralVersionUID = 9120181044386552132L;
    private List<FigureHandle> handles = null;

    /**
     * Use the java.awt.Rectangle in order to save/reuse code.
     */
    private final Line2D line;

    /**
     * Create a new rectangle of the given dimension.
     *
     * @param x the x-coordinate of the upper left corner of the rectangle
     * @param y the y-coordinate of the upper left corner of the rectangle
     */
    public Line(int x, int y) {
        super();
        line = new Line2D.Double(x, y, x, y);
    }

    /**
     * Draw the rectangle to the given graphics context.
     *
     * @param g the graphics context to use for drawing.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
    }

    // zum vo constructor us d linie ufzieh
    @Override
    public void setBounds(Point origin, Point corner) {
        if (line.getX1() != origin.x || line.getY1() != origin.y || line.getX2() != corner.x ||
            line.getY2() != corner.y) {
            line.setLine(origin, corner);
            notifyFigureList();
        }
    }

    // setLine, vo de aktuelle linie pos hole mit x1 u y1 und mit x2 u y2
    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            line.setLine(line.getX1() + dx, line.getY1() + dy,
                line.getX2() + dx, line.getY2() + dy);
            notifyFigureList();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        // zum bereich um linie ume mache ish selektierbar
        return line.ptSegDistSq(x, y) < 16;
    }

    @Override
    public Rectangle getBounds() {
        return line.getBounds();
    }

    @Override
    public void swapVertical(){
        for (FigureHandle f :  handles){
            Handle h = (Handle) f;
            h.setState(h.getState().swapVertical());
        }
    }

    @Override
    public void swapHorizontal(){
        for (FigureHandle f :  handles){
            Handle h = (Handle) f;
            h.setState(h.getState().swapHorizontal());
        }
    }

    @Override
    public List<FigureHandle> getHandles(){
        Point start = new Point((int) line.getX1(), (int) line.getY1());
        Point end = new Point((int) line.getX2(), (int) line.getY2());
        if (handles == null){
            if ((end.x - start.x) * (end.y - start.y) > 0){
                handles = List.of(new Handle(new NWState(this)),
                    new Handle(new SEState(this)));

            }else {
                handles = List.of(new Handle(new NEState(this)),
                    new Handle(new SWState(this)));
            }
        }
     return handles;
    }


}
