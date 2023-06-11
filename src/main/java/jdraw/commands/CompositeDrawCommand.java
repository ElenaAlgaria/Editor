package jdraw.commands;

import jdraw.framework.DrawCommand;

import java.util.ArrayList;
import java.util.List;

public class CompositeDrawCommand implements DrawCommand {

    private final List<DrawCommand> commands = new ArrayList<>();

    public void addCommand(DrawCommand command){
        commands.add(command);
    }


    @Override
    public void redo() {
        commands.stream().forEach(DrawCommand::redo);
    }

    @Override
    public void undo() {
        for (int i = commands.size() - 1; i >= 0 ; i--) {
            commands.get(i).undo();
        }
    }
}
