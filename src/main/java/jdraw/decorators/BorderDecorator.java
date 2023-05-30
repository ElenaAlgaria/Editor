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

    // für de rahmen grow
    @Override
    public Rectangle getBounds(){
         var bounds = super.getBounds();
         bounds.grow(GROWTH,GROWTH);
         return bounds;
    }

    @Override
    public void draw(Graphics g) {
        var b = getBounds();

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

    @Override
    public boolean contains (int x, int y){
         return  getBounds().contains(x,y);
    }

    @Override
    public boolean equals(Object that){
        return this == that || super.getInner().equals(that);
    }

}
