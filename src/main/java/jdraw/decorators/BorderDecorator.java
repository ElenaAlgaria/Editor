package jdraw.decorators;

import jdraw.framework.Figure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BorderDecorator extends AbstractDecorator {
    private static final int GROWTH = 3;

     public BorderDecorator(Figure figure) {
         super(figure);
    }

    BorderDecorator(BorderDecorator source) {
        super(source);
    }

    // für de rahmen grow, überschriebe will de brucht sin platz doh
    @Override
    public Rectangle getBounds(){
         var bounds = super.getBounds();
         bounds.grow(GROWTH,GROWTH);
         return bounds;
    }

    // rahme zeichne
    @Override
    public void draw(Graphics g) {
        var b = getBounds();
        // wenn do obe super de würd graphics zerst zeichne wege inner
        g.setColor(Color.lightGray);
        g.drawLine(b.x,b.y,b.x + b.width,b.y); // nord
        g.drawLine(b.x,b.y,b.x,b.y + b.height); // west

        g.setColor(Color.GRAY);
        g.drawLine(b.x + b.width,b.y,b.x + b.width,b.y + b.height); // east
        g.drawLine(b.x,b.y + b.height,b.x + b.width,b.y + b.height); // south

        super.draw(g); // weiter leiten an innere figur
    }

    // deep copy mit constructor
    @Override
    public BorderDecorator clone() {
        return new BorderDecorator(this);
    }

    // demit de rahme selektierbar ish
    @Override
    public boolean contains (int x, int y){
         return  getBounds().contains(x,y);
    }

    // weg de handles und em bim lösche ned weggoh, getOwner
    @Override
    public boolean equals(Object that){
        return this == that || super.getInner().equals(that);
    }

}
