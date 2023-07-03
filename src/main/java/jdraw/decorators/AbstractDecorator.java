package jdraw.decorators;

import jdraw.figures.AbstractFigure;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// abstractFigur will die alles beinhaltet zum observer notify und mir das do bruchet weg de tests
public abstract class AbstractDecorator extends AbstractFigure {
    private final Figure inner;

    protected AbstractDecorator(AbstractDecorator source) {
        // deep copy, zum ned de glichi rahme zwoi mal zmache
        this.inner = source.inner.clone();
    }

    protected AbstractDecorator(Figure figure) {
        this.inner = figure;
    }

    // weg de handles und em bim lösche ned weggoh, getOwner
    @Override
    public boolean equals(Object that) {
        return this == that || inner.equals(that);
    }

    @Override
    public void draw(Graphics g) {
        inner.draw(g);
    }

    @Override
    public void move(int dx, int dy) {
        inner.move(dx, dy);
    }

    @Override
    public boolean contains(int x, int y) {
        return inner.contains(x, y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        inner.setBounds(origin, corner);
    }

    @Override
    public Rectangle getBounds() {
        return inner.getBounds();
    }

    // handle

    private Map<FigureListener, FigureListener> listeners = new HashMap<>();

    @Override
    public void addFigureListener(FigureListener listener) {
        if (listener == null || listeners.containsKey(listener)) {
            return;
        }
        listeners.put(listener, e -> listener.notifyObservers(new FigureEvent(this)));
        inner.addFigureListener(listeners.get(listener));
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        if (listeners.containsKey(listener)) {
            inner.removeFigureListener(listeners.remove(listener));
        }
    }

    // deep copy ?
    @Override
    public abstract Figure clone();


    @Override
    public List<? extends FigureHandle> getHandles() {
        return inner.getHandles();
    }


    public Figure getInner() {
        return inner;
    }

}
