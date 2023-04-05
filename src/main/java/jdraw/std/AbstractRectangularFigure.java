package jdraw.std;

import jdraw.figures.AbstractFigure;
import jdraw.figures.handles.EState;
import jdraw.figures.handles.Handle;
import jdraw.figures.handles.NEState;
import jdraw.figures.handles.NState;
import jdraw.figures.handles.NWState;
import jdraw.figures.handles.SEState;
import jdraw.figures.handles.SState;
import jdraw.figures.handles.SWState;
import jdraw.figures.handles.WState;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public abstract class AbstractRectangularFigure extends AbstractFigure {
    // wird nur do inne brucht nachem new nümme ändere darum final und private, für java cloning s final wegneh
    private final Rectangle rectangle;

    // initial state für diese handle
    private final Handle nw = new Handle(new NWState(this));
    private final Handle sw = new Handle(new SWState(this));
    private final Handle se = new Handle(new SEState(this));
    private final Handle ne = new Handle(new NEState(this));
    private final Handle w  = new Handle(new WState(this));
    private final Handle e = new Handle(new EState(this));
    private final Handle n = new Handle(new NState(this));
    private final Handle s = new Handle(new SState(this));


    private final List<FigureHandle> handles = List.of(nw, ne, sw, se,n, s, w, e);


    protected AbstractRectangularFigure(int x, int y) {
        this.rectangle = new Rectangle(x, y, 0, 0);
    }

    protected AbstractRectangularFigure(AbstractRectangularFigure source) {
        super(source);
        // copy des rectangles mit de handles wo ide liste dinne sind hemmer eso e copy vom clonable state
        this.rectangle = (Rectangle) source.rectangle.clone();
    }

    @Override
    // 3) cycle mit em if unterbroche, model sött nur notifye wenns model au wük veränderet worde isch
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
           rectangle.translate(dx,dy);
            notifyFigureList();
        }
    }
    @Override
    public Rectangle getBounds() {
        //getbounds git e copy zrugg wichtig
        return rectangle.getBounds();
    }

    @Override
    // 3)
    public void setBounds(Point origin, Point corner) {
        Rectangle original = new Rectangle(rectangle); // copy demit mer cha kontrolliere dass es en änderig geh hett
        rectangle.setFrameFromDiagonal(origin, corner); // wird do veränderet falls o u c anderst sind, cycle
        if (!original.equals(rectangle)){
        notifyFigureList();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }


    /**
     * Returns a list of 8 handles for this Rectangle.
     *
     * @return all handles that are attached to the targeted figure.
     * @see Figure#getHandles()
     */
    @Override
    public List<FigureHandle> getHandles() {
        return handles;
    }

    // setzten neue state wenns nötig ish, checket alli handles, gebet mir fh
    @Override
    public void swapVertical() {
        for (FigureHandle figureHandle : handles){
        Handle h = (Handle) figureHandle; // wegen setState get
        h.setState(h.getState().swapVertical());
        }
    }

    @Override
    public void swapHorizontal() {
        for (FigureHandle figureHandle : handles){
            Handle h = (Handle) figureHandle; // wegen setState get
            h.setState(h.getState().swapHorizontal());
        }
    }

//    java cloning, state copy, clonable state doh deep copy mache, super.clone = shallow copy, will clonable state hett attribut obe
//   überall wo nötig modifiziere für deep copy vo de attribut aso vo de states, clone nur dert wos clonbable state git oder
    // wo mer rückgabe type ienge muess, doh innebruchts will mer obe rect hend clonable state, will do nur shallow copy
    // süscht ohni das, will neus rect mache mer doh
//    @Override
//    public AbstractRectangularFigure clone(){
//        AbstractRectangularFigure f = (AbstractRectangularFigure) super.clone();
//        f.rectangle = (Rectangle) rectangle.clone();
    // demit gethandles richtig funktioniert
//        f.handles = null;
//        return f;
//    }
}
