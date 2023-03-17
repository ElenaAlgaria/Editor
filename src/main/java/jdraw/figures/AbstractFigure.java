package jdraw.figures;

import jdraw.figures.handles.NEState;
import jdraw.figures.handles.SWState;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;
import jdraw.framework.HandleState;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class  AbstractFigure implements Figure {
    //add/remove unabhängig von der notifikation
    private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

    // listener hinzuefüege zu de observer liste
    @Override
    public void addFigureListener(FigureListener listener) {
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    }
    @Override
    public void removeFigureListener(FigureListener listener) {
        listeners.remove(listener);
    }

    /**
     * Returns a list of 8 handles for this Rectangle.
     *
     * @return all handles that are attached to the targeted figure.
     * @see Figure#getHandles()
     */
    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    @Override
    public Figure clone() {
        return null;
    }

    // private wills sowieso nur do inne brucht wird
    // figure observer und changed = update
    protected void notifyFigureList(){
        for (FigureListener listener: listeners){
            if (listener != null){
                listener.notifyObservers(new FigureEvent(this));
            }
        }
    }


    public  void  swapVertical() {}
    public void  swapHorizontal(){}



}
