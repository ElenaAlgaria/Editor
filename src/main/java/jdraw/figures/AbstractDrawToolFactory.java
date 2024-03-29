package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;

// brucht mer will süscht ide andere factories überall s gliche dinnestoht
// ohne create ish im Interface und ide konkrete klasse dinne
public abstract class AbstractDrawToolFactory implements DrawToolFactory {

    private String iconName;
    private String name;

    // bi bean name
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    //bi bean icon
    @Override
    public String getIconName() {
        return iconName;
    }

    @Override
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
