package jdraw.commands;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawCommandHandler;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyDrawCommandHandler implements DrawCommandHandler {
    private final Deque<DrawCommand> redoStack = new ArrayDeque<>();
    private final Deque<DrawCommand> undoStack = new ArrayDeque<>();

    private CompositeDrawCommand script;

// fügt command in undostack willer do im do stack ish
    @Override
    public void addCommand(DrawCommand cmd) {
        if (script != null){
            script.addCommand(cmd);
        }else{
        undoStack.push(cmd);
        }
        redoStack.clear(); // wird geleert
    }

    // bi addFC wirds rückgängig gmacht wege pop, figur wird vom model entfärnt
    @Override
    public void undo() {
    DrawCommand cmd = undoStack.pop();
    redoStack.push(cmd);
    cmd.undo();
    }

    @Override
    public void redo() {
        DrawCommand cmd = redoStack.pop();
        cmd.redo();
        undoStack.push(cmd);

    }

    @Override
    public boolean undoPossible() {
        return !undoStack.isEmpty();
    }

    @Override
    public boolean redoPossible() {
        return !redoStack.isEmpty();
    }


    @Override
    public void beginScript() {
        if (script != null){
            throw new IllegalStateException();
        }
        script = new CompositeDrawCommand();
    }

    @Override
    public void endScript() {
        if (script == null){
            throw new IllegalStateException();
        }
        CompositeDrawCommand s = script;
        script = null; // signal aufzeichnugn fertig goht in undo stack
        addCommand(s);
    }

    @Override
    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }
}
