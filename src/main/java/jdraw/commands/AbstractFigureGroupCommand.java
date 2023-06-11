package jdraw.commands;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;

public abstract class AbstractFigureGroupCommand implements DrawCommand {
    private final DrawModel model;
    private final FigureGroup group;

    protected AbstractFigureGroupCommand(DrawModel model, FigureGroup group) {
        this.model = model;
        this.group = group;
    }

    protected void addGroup(){
        group.getFigureParts().forEach(model::removeFigure);
        model.addFigure((Figure) group);
    }

    public void removeGroup(){
        model.removeFigure((Figure) group);
        group.getFigureParts().forEach(model::addFigure);
    }


}
