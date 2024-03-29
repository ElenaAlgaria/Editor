/*
 * Copyright (c) 2022 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdraw.commands.GroupFigureCommand;
import jdraw.commands.UngroupFigureCommand;
import jdraw.decorators.AbstractDecorator;
import jdraw.decorators.BorderDecorator;
import jdraw.decorators.GreenDecorator;
import jdraw.figures.Group;
import jdraw.figures.LineTool;
import jdraw.figures.OvalTool;
import jdraw.figures.RectTool;
import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;
import jdraw.grid.SimpleGrid;

/**
 * Standard implementation of interface DrawContext.
 *
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.6, 24.09.09
 * @see DrawView
 */
@SuppressWarnings("serial")
public class StdContext extends AbstractContext {

    private final List<Figure> clipboard;
    private List<Integer> bra = new ArrayList<>(){{
        add(1);
        add(2);
        add(3);
    }};

    /**
     * Constructs a standard context with a default set of drawing tools.
     *
     * @param view the view that is displaying the actual drawing.
     *             <p>
     *             mit this aufruf gehts zum andere constructor wiiter
     */
    public StdContext(DrawView view) {
        this(view, null);
    }

    /**
     * Constructs a standard context. The drawing tools available can be
     * parameterized using <code>toolFactories</code>.
     *
     * @param view          the view that is displaying the actual drawing.
     * @param toolFactories a list of DrawToolFactories that are available to the
     *                      user
     */
    public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
        super(view, toolFactories);
        clipboard = new ArrayList<>();
    }

    /**
     * Creates and initializes the "Edit" menu.
     *
     * @return the new "Edit" menu.
     */
    @Override
    protected JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        final JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        editMenu.add(undo);
        undo.addActionListener(e -> {
            final DrawCommandHandler h = getModel().getDrawCommandHandler();
            if (h.undoPossible()) {
                h.undo();
            }
        });

        final JMenuItem redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
        editMenu.add(redo);
        redo.addActionListener(e -> {
            final DrawCommandHandler h = getModel().getDrawCommandHandler();
            if (h.redoPossible()) {
                h.redo();
            }
        });
        editMenu.addSeparator();

        JMenuItem sa = new JMenuItem("SelectAll");
        sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
        editMenu.add(sa);
        sa.addActionListener(e -> {
            getModel().getFigures().forEachOrdered(f -> getView().addToSelection(f));
            getView().repaint();
        });

        editMenu.addSeparator();

        ActionListener cutAction = e -> {
            clipboard.clear();
            for (Figure f : sortInModelOrder(getView().getSelection())) {
                clipboard.add(f);
                getModel().removeFigure(f);
            }
        };

        editMenu.add(createMenuItem("Cut", cutAction, "control X"));

        ActionListener copyAction = e -> {
            clipboard.clear();
            for (Figure f : sortInModelOrder(getView().getSelection())) {
                clipboard.add(f.clone()); // dieser clone ist der prototype
                System.out.println("Copy");
            }
        };

        editMenu.add(createMenuItem("Copy", copyAction, "control C"));

        ActionListener pasteAction = e -> {
            for (Figure f : clipboard){
                getModel().addFigure(f.clone());
                getView().addToSelection(f.clone());
                System.out.println("paste");
            }
        };

        editMenu.add(createMenuItem("Paste", pasteAction, "control V"));

        editMenu.addSeparator();
        JMenuItem clear = new JMenuItem("Clear");
        editMenu.add(clear);
        clear.addActionListener(e -> {
            getModel().removeAllFigures();
        });

        editMenu.addSeparator();
        JMenuItem group = new JMenuItem("Group");
        group.addActionListener(e -> {
            List<Figure> selection = getView().getSelection();
            if (selection != null && selection.size() >= 2) {
                Group groupFigure = new Group(getModel(), selection);
                // entferne fig vo model, demit mer 1 figur ned 2 mal dinne chönd ha
                for (Figure f : selection) {
                    //Remove parts from model
                    getModel().removeFigure(f);
                }
                // gruppef hinzuefüege um so kei zykle zha mit dem kommentar vo vorher
                getModel().addFigure(groupFigure);
                getView().addToSelection(groupFigure);
                getModel().getDrawCommandHandler().addCommand(new GroupFigureCommand(getModel(), groupFigure));
            }
        });
        editMenu.add(group);

        JMenuItem ungroup = new JMenuItem("Ungroup");
        ungroup.addActionListener(e -> {
            // selection und de luege obs en groupfigure ish und de, if instanceof, getFigureParts,
            List<Figure> selection = getView().getSelection();
            for (Figure g : selection) {
//              // uf FG will allgemeine Typ für alli
                if (g instanceof FigureGroup ) {
                    getModel().removeFigure(g);
                    ((FigureGroup) g).getFigureParts().forEach(f -> {
                        getModel().addFigure(f);
                        getView().addToSelection(f);
                    });
                    getModel().getDrawCommandHandler().addCommand(new UngroupFigureCommand(getModel(), (FigureGroup) g));
                }
            }
        });
        editMenu.add(ungroup);

        editMenu.addSeparator();

        JMenu orderMenu = new JMenu("Order...");
        JMenuItem frontItem = new JMenuItem("Bring To Front");
        frontItem.addActionListener(e -> {
            bringToFront(getView().getModel(), getView().getSelection());
        });
        orderMenu.add(frontItem);
        JMenuItem backItem = new JMenuItem("Send To Back");
        backItem.addActionListener(e -> {
            sendToBack(getView().getModel(), getView().getSelection());
        });
        orderMenu.add(backItem);
        editMenu.add(orderMenu);

        JMenu grid = new JMenu("Grid...");
        editMenu.add(grid);

        // Rollen: stdcontext = client, stddrawview = context, simplegrid = strategy
        grid.add(addFixedGridMenu("No Grid", null));
        grid.add(addFixedGridMenu("Fixed 20", new SimpleGrid(getView(), 20)));
        grid.add(addFixedGridMenu("Fixed 100", new SimpleGrid(getView(), 100)));

        editMenu.addSeparator();

        JMenu descMenu = new JMenu("Decorators...");
        editMenu.add(descMenu);
        JMenuItem addGreen = new JMenuItem("Toggle Green Decorator");
        descMenu.add(addGreen);
        addGreen.addActionListener(e -> {
            List<Figure> s = getView().getSelection();
            getView().clearSelection();
            for (Figure f : s){
                Figure newFigure = null;
                if (f instanceof GreenDecorator){
                    newFigure = ((GreenDecorator)f).getInner(); // auspacken
                } else {
                    newFigure = new GreenDecorator(f); // einpacken
                }
                getModel().removeFigure(f);
                getModel().addFigure(newFigure);
                getView().addToSelection(newFigure);

            }
        });

        JMenuItem addBorder = new JMenuItem("Add Border Decorator");
        descMenu.add(addBorder);
        addBorder.addActionListener(e -> {
            List<Figure> s = getView().getSelection();
            getView().clearSelection();
            for (Figure f : s){
                AbstractDecorator dec = new BorderDecorator(f);
                getModel().removeFigure(f);
                getModel().addFigure(dec);
                getView().addToSelection(dec);

            }
        });

        JMenuItem removeBorder = new JMenuItem("Remove Border Decorator");
        descMenu.add(removeBorder);
        removeBorder.addActionListener(e -> {
            List<Figure> s = getView().getSelection();
            getView().clearSelection();
            for (Figure f : s){
                if (f instanceof BorderDecorator){
                AbstractDecorator borderDecorator =  (BorderDecorator) f;
                getModel().removeFigure(borderDecorator);
                getModel().addFigure(borderDecorator.getInner());
                getView().addToSelection(borderDecorator.getInner());
                }

            }
        });

        return editMenu;
    }

    private JMenuItem createMenuItem(String label, ActionListener listener, String shortCut) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(listener);
        item.setAccelerator(KeyStroke.getKeyStroke(shortCut));
        return item;

    }

    // zum zeichnigs ordnig becho wo im model dinne ish
    private List<Figure> sortInModelOrder(List<Figure> selection) {
        return getModel().getFigures().filter(selection::contains).collect(Collectors.toList());
    }

    // refactoring
    private JMenuItem addFixedGridMenu(String label, SimpleGrid grid) {
        JMenuItem entry = new JMenuItem(label);
        // auf context d strategy setzte aso uf d view s grid
        entry.addActionListener(e -> getView().setGrid(grid));
        return entry;
    }

    /**
     * Creates and initializes items in the file menu.
     *
     * @return the new "File" menu.
     */
    @Override
    protected JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        fileMenu.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke("control O"));
        open.addActionListener(e -> doOpen());

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke("control S"));
        fileMenu.add(save);
        save.addActionListener(e -> doSave());

        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(e -> System.exit(0));

        return fileMenu;
    }

    // factory
    @Override
    protected void doRegisterDrawTools() {
        //zum alli factories tools regristiere, s einte null zeigt ah dass mer separator wönd
        // au im spring null dinne
      getToolFactories().forEach(f -> addTool((f != null) ? f.createTool(this) : null));

    }

    /**
     * Changes the order of figures and moves the figures in the selection to the
     * front, i.e. moves them to the end of the list of figures.
     *
     * @param model     model in which the order has to be changed
     * @param selection selection which is moved to front
     */
    public void bringToFront(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in the model
        List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f))
            .collect(Collectors.toList());
        Collections.reverse(orderedSelection);
        int pos = (int) model.getFigures().count();
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, --pos);
        }
    }

    /**
     * Changes the order of figures and moves the figures in the selection to the
     * back, i.e. moves them to the front of the list of figures.
     *
     * @param model     model in which the order has to be changed
     * @param selection selection which is moved to the back
     */
    public void sendToBack(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in the model
        List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f))
            .collect(Collectors.toList());
        int pos = 0;
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, pos++);
        }
    }

    /**
     * Handles the saving of a drawing to a file., schreibt
     */
    private void doSave() {
        JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
        chooser.setDialogTitle("Save Graphic");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        chooser.setFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.draw)", "draw"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.xml)", "xml"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.json)", "json"));

        int res = chooser.showSaveDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            // save graphic
            File file = chooser.getSelectedFile();
            FileFilter filter = chooser.getFileFilter();
            if (filter instanceof FileNameExtensionFilter && !filter.accept(file)) {
                file = new File(chooser.getCurrentDirectory(),
                    file.getName() + "." + ((FileNameExtensionFilter) filter).getExtensions()[0]);
            }
            // foreach f clone, save, null = end marker, close, out -> geht ins file rein
            try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file))){
                for (Figure f: getModel().getFigures().toList()) {
                    o.writeObject(f.clone()); // clone keine listener dabei so will die wemmer ned
                }
                o.writeObject(null); // end marker setze fertig
            }catch (IOException e){
                e.printStackTrace();
            }


            System.out.println("save current graphic to file " + file.getName() + " using format "
                + ((FileNameExtensionFilter) filter).getExtensions()[0]);
        }
    }

    /**
     * Handles the opening of a new drawing from a file., liest
     */
    private void doOpen() {
        JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
        chooser.setDialogTitle("Open Graphic");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public String getDescription() {
                return "JDraw Graphic (*.draw)";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".draw");
            }
        });
        int res = chooser.showOpenDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            // read jdraw graphic
            System.out.println("read file " + chooser.getSelectedFile().getName());

            try(ObjectInputStream o = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))){
                getModel().removeAllFigures(); // alli entferne wenn mers file ufmacht
                Object f = o.readObject();
                while (f != null){
                    if (f instanceof Figure){
                        getModel().addFigure((Figure) f);
                    }
                    f = o.readObject();
                }
                System.out.println(f);
            }catch (IOException  | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

}
