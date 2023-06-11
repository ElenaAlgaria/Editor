package jdraw.commands;

import jdraw.framework.DrawModel;
import jdraw.framework.FigureGroup;

public class UngroupFigureCommand extends AbstractFigureGroupCommand {

    public UngroupFigureCommand(DrawModel model, FigureGroup group) {
        super(model, group);
    }

    @Override
    public void redo() {
        removeGroup();
    }

    @Override
    public void undo() {
        addGroup();
    }
}
