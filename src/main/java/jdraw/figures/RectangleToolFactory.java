package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;

public class RectangleToolFactory extends AbstractDrawToolFactory{


    @Override
    public DrawTool createTool(DrawContext context) {
        return new RectTool(context, getName(), getIconName());
    }
}
