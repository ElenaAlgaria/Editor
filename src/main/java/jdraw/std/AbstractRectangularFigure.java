package jdraw.std;

import jdraw.figures.AbstractFigure;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractRectangularFigure extends AbstractFigure {
    // wird nur do inne brucht nachem new nümme ändere darum final und private
    private final Rectangle rectangle;

    protected AbstractRectangularFigure(int x, int y) {
        this.rectangle = new Rectangle(x, y, 0, 0);
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

}
