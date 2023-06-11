package jdraw.commands;

import jdraw.framework.DrawModel;
import jdraw.framework.FigureGroup;

public class GroupFigureCommand extends AbstractFigureGroupCommand{

    public GroupFigureCommand(DrawModel model, FigureGroup group) {
        super(model, group);
    }

    @Override
    public void redo() {
        addGroup();
    }

    @Override
    public void undo() {
        removeGroup();
    }
}
