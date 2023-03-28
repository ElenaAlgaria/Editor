package jdraw.figures;

import jdraw.framework.DrawModel;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;
import jdraw.std.AbstractRectangularFigure;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Group extends AbstractRectangularFigure implements FigureGroup {
    private final List<Figure> parts;

    public Group(DrawModel model, List<Figure> parts) {
        super(0, 0);
        if (parts == null || parts.size() == 0){
            throw new IllegalArgumentException();
        }
        // entkoppellung, clone the list vom parametera
       // this.parts = new ArrayList<>(parts);

        // weg de order vom selektiere und de switchets süscht, same order and copy, model git reihefolg ah so
        this.parts = model.getFigures().filter(parts::contains).collect(Collectors.toList());
    }

    @Override
    public void draw(Graphics g) {
        for (Figure f : parts) {
            f.draw(g);
        }
    }

    @Override
    public void move(int dx, int dy) {
        // zyklus brechen mit em if
        if (dx != 0 || dy != 0) {
            for (Figure f : parts) {
                f.move(dx, dy);
                // gruppefigur im model aber die untere parts ned
                notifyFigureList();
            }
        }
    }

    @Override
    public boolean contains(int x, int y) {
        // erhalten gröpsstes rechteck zurück wenns dinne ish de true, bim klicke is teil
        return getBounds().contains(x, y);
    }

    @Override
    public Rectangle getBounds() {
        var rect = new Rectangle(parts.get(0).getBounds());
        for (int i = 1; i < parts.size(); i++) {
            rect.add(parts.get(i).getBounds());
        }
        return rect;
    }


    @Override
    public Stream<Figure> getFigureParts() {
        return parts.stream();
    }
}
