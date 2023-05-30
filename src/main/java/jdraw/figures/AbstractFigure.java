package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// java cloning do no , Cloneable
public abstract class  AbstractFigure implements Figure {
    //add/remove unabhängig von der notifikation, die wömer ned mitkopiere
    private final List<FigureListener> listeners = new CopyOnWriteArrayList<>();

    protected AbstractFigure() {

    }

    // in jedem teil e copy ctr, was kopiere? attribut aluege, listeners werden nachträglich hinzugefügt bei add im model
    // f bekommt immer nachem clone en listener daher will nacher mumer do de listener ned clone
    protected AbstractFigure(AbstractFigure source) {

    }

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
    public List<? extends FigureHandle> getHandles() {
        return null;
    }

    // eig kei konkreti klass mit new aber muess glich do stoh will süscht problem mit copiler,
    // löst konflikt, klasse gewinnt, versteckt object clone () ab jetzt giltet die geg abe und löst problem
    @Override
    public abstract Figure clone();

//    java cloning, das immer public so sichtbar, grad try catch mache so bald wie möglich aso zoberst, return typ ienge
    // aso do mit em nur AbstractFigures dörfet gclonet werde, leeri liste vo listeners wömer do mit em af
//    @Override
//    public AbstractFigure clone(){
//        try {
    // geht auf object.clone rauf, gitt immer dynamische typ zrugg aso s new blabla
//          var af = (AbstractFigure) super.clone();
    // jede kopie hat jetzt ihre neue leere liste von listeners
            //af.listeners = new CopyOnWriteArrayList<>();
//            return af;
//        }catch (CloneNotSupportedException e){
//            throw new AssertionError(); // will never happen
//        }
//    }

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
