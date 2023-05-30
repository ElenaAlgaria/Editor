/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Stream;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;

/**
 * Provide a standard behavior for the drawing model. This class initially does
 * not implement the methods in a proper way. It is part of the course
 * assignments to do so.
 *
 * @author Elena Algaria
 */
// drawModel = Subjekt, FigureListener = Observer
public class StdDrawModel implements DrawModel, FigureListener {
   // 4) ob model für beobachter immer konsistent isch, veränderet sich d liste de werdet beobachter benachrichtigt und
   // nur wenns vollständig gmacht wurde isch aso d veränderig ned in between 50 mal benachrichtige
    private List<Figure> figures;
    // liste vo observers vom view
    private List<DrawModelListener> drawModelListeners;


    public StdDrawModel() {
        figures = new ArrayList<>();
        drawModelListeners = new ArrayList<>();
    }

    // 5) figurelistener notifiziert nur observer vo sich selbst gut so
    @Override
    public void addFigure(Figure f) {
        if (f != null && !figures.contains(f) ){
        // rolle vo subjekt
        figures.add(f);
        // demit mer mitbekummt was mit figure passiert und this ish en figurelistener will
        // mir hends ja implemented jetzt simmer beobachter
        f.addFigureListener(this);
        // jetzt wieder subjekt mir informieret s view drübert
        notifyObservers(f, DrawModelEvent.Type.FIGURE_ADDED);
        }
        System.out.println("StdDrawModel.addFigure");
    }


    @Override
    public Stream<Figure> getFigures() {
        if (figures == null){
            throw new NullPointerException();
        }
        System.out.println("StdDrawModel.getFigures");
        // mit .stream de stream becho vo de collection
        return figures.stream();
    }

    // bruchts fürs 2) GC demit de lauft gleert wird
    @Override
    public void removeFigure(Figure f) {
        // subjekt task, hett boolean dinne im remove wenn true de i liste dinne und removed
        if (figures.remove(f)){
        // observer
        f.removeFigureListener(this);
        // subjekt
        notifyObservers(f, DrawModelEvent.Type.FIGURE_REMOVED);
        }
        System.out.println("StdDrawModel.removeFigure");
    }

    // subjekt alli observer adde
    @Override
    public void addModelChangeListener(DrawModelListener listener) {
        drawModelListeners.add(listener);
        System.out.println("StdDrawModel.addModelChangeListener");
    }

    @Override
    public void removeModelChangeListener(DrawModelListener listener) {
        drawModelListeners.remove(listener);
        System.out.println("StdDrawModel.removeModelChangeListener has to be implemented");
    }

    /**
     * The draw command handler. Initialized here with a dummy implementation.
     */
    // TODO initialize with your implementation of the undo/redo-assignment.
    private DrawCommandHandler handler = new EmptyDrawCommandHandler();

    /**
     * Retrieve the draw command handler in use.
     *
     * @return the draw command handler.
     */
    @Override
    public DrawCommandHandler getDrawCommandHandler() {
        return handler;
    }

    // figur ide liste a andere ort tue
    // index neh f dert uselösche us liste und de neu index innetue mit de f
    @Override
    public void setFigureIndex(Figure f, int index) {
        // stoht im drawmodel dinne, Precoditions
        if (!figures.contains(f)){
            throw new IllegalArgumentException();
        }
        if (index < 0 || index >= figures.size()){
            throw new IndexOutOfBoundsException();
        }

        int currentIndex = figures.indexOf(f);
        if (currentIndex != index){
            figures.remove(currentIndex);
            figures.add(index, f);
            // änderig = alli informiere
            notifyObservers(f, DrawModelEvent.Type.DRAWING_CHANGED);
        }
        System.out.println("StdDrawModel.setFigureIndex");
    }

    // rolle als beobachter
    @Override
    public void removeAllFigures() {
        for (Figure f : figures) {
            // will mir wönd nümme beobachte
            f.removeFigureListener(this);
           // figures.remove(f);, goht ned eso kratzt ab
        }
        // list lösche
         figures.clear();
        notifyObservers(null, DrawModelEvent.Type.DRAWING_CLEARED);
        System.out.println("StdDrawModel.removeAllFigures");
    }


    // observers notifye nach buech
    private void notifyObservers(Figure f, DrawModelEvent.Type type) {
        for (DrawModelListener dml: drawModelListeners) {
            // update () vo modellistener, drawmodel und etc mitgeh
              dml.modelChanged(new DrawModelEvent(this, f, type));
        }
    }


    // muess mer neh wegem interface vo figurelisteners
    @Override
    public void notifyObservers(FigureEvent e) {
        notifyObservers(e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED);
    }



}
