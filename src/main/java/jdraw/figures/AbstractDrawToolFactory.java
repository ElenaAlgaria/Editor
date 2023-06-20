package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;

// brucht mer will süscht ide andere factories überall s gliche dinnestoht
public abstract class AbstractDrawToolFactory implements DrawToolFactory {

    private String iconName;
    private String name;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getIconName() {
        return null;
    }

    @Override
    public void setIconName(String name) {

    }

}
