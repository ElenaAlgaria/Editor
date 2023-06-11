package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.Figure;
import jdraw.commands.AddFigureCommand;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

// command teil rolle client
public abstract class AbstractTool implements DrawTool {
    /**
     * The context we use for drawing.
     */
    private final DrawContext context;

    /**
     * the image resource path.
     */
    private static final String IMAGES = "/images/";

    private final String name;
    private final String iconName;

    /**
     * Temporary variable. During rectangle creation (during a mouse down - mouse
     * drag - mouse up cycle) this variable refers to the new rectangle that is
     * inserted.
     */
    // rect zu figure mache, weil jetzt gegen interface programmiere
    private Figure newFigure = null;

    /**
     * Temporary variable. During rectangle creation this variable refers to the
     * point the mouse was first pressed.
     */
    private Point anchor = null;


    protected AbstractTool(DrawContext context, String name, String iconName) {
        this.context = context;
        this.name = name;
        this.iconName = iconName;
    }


    /**
     * Deactivates the current mode by resetting the cursor and clearing the status
     * bar.
     *
     * @see jdraw.framework.DrawTool#deactivate()
     */
    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    /**
     * Activates the Rectangle Mode. There will be a specific menu added to the menu
     * bar that provides settings for Rectangle attributes
     */
    @Override
    public void activate() {
        this.context.showStatusText(name + "Mode");
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + iconName));
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * During a mouse drag, the Rectangle will be resized according to the mouse
     * position. The status bar shows the current size.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were
     *          pressed.
     *
     * @see jdraw.framework.DrawTool#mouseDrag(int, int, MouseEvent)
     */
    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newFigure.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = newFigure.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    /**
     * When the user releases the mouse, the Rectangle object is updated according
     * to the color and fill status settings.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were
     *          pressed.
     *
     * @see jdraw.framework.DrawTool#mouseUp(int, int, MouseEvent)
     */
    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        //hier existiert eine neue Figur
        context.getModel().getDrawCommandHandler().addCommand(new AddFigureCommand(context.getModel(),newFigure));
        newFigure = null;
        anchor = null;
        this.context.showStatusText("Rectangle Mode");
    }

    //abstrakti methode für mouseDown will dert inne e konkreti figure erstellt wird mit new ... aber de rest immer glich
    protected abstract Figure createFigureAt(int x, int y);

    /**
     * Initializes a new Rectangle object by setting an anchor point where the mouse
     * was pressed. A new Rectangle is then added to the model.
     *
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were
     *          pressed.
     *
     * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
     */
    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (newFigure != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x, y);
        // anstatt new Rect
        newFigure = createFigureAt(x, y);
        // figur wird dem model hinzugefügt
        context.getView().getModel().addFigure(newFigure);
    }


}
