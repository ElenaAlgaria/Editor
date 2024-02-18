package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;

public class RectangleToolFactory extends AbstractDrawToolFactory{
    // factory method, rect tool wird jetzt vo usse gsetzt d parameter vo spring vorher
    // vo hand im rectTool constr dinne gsi
    @Override
    public DrawTool createTool(DrawContext context) {
        return new RectTool(context, getName(), getIconName());
    }
}
