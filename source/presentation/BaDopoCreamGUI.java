/**
 * The GUI follows a classic MVC-inspired separation: this component represents
 * the View and provides the container in which all screens (views) are displayed.
 * It is responsible for:
 *
 * - Managing the lifecycle of the Swing JFrame that holds the entire interface.
 *
 * - Acting as a navigation controller through a {@link CardLayout} stack, ensuring
 * views are created only once and retrieved from their corresponding
 * Singleton factories (e.g., {@link HomeView}, {@link SelectModeView}).
 *
 * - Relaying interactions to the logic layer through an associated
 * {@link Control} instance.
 *
 * - Handling global behaviors such as window closing, glass-pane overlays,
 * dialog notifications, and view-specific title updates.
 *
 * The class is implemented as a Singleton to guarantee that only one GUI frame
 * exists during the program’s execution.
 *
 * @author juad - 2025
 */
package presentation;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import domain.Control;
import domain.map.chars.CharType;
import exceptions.BLogger;
import exceptions.ProgrammerException;
import presentation.render.LevelView;
import presentation.selecting.DoubleIceCreamView;
import presentation.selecting.OppVsIceView;
import presentation.selecting.SingleIceCreamView;

public class BaDopoCreamGUI extends JFrame implements Intermediary {
    private static BaDopoCreamGUI INSTANCE = null;
    private static final String WINDOW_TITLE = presentation.constants.Titles.HOME;

    public static final int WINDOW_HEIGHT = 672;
    public static final int WINDOW_WIDTH  = 645;

    private Dimension windowDimension;
    private CardLayout viewStack;
    private JPanel panel;
    private Control control;

    /**
     * Private constructor. Initializes the view stack, loads the first screen,
     * and sets all window-level configuration parameters.
     *
     * @param control the controller connecting the view to the logic layer.
     */
    private BaDopoCreamGUI (final Control control) {
        /*
         * Initializates the logger since errors can happen anywhere
         * in the program
         */
        BLogger.initLogger();
        this.control = control;
        this.initObjects();
        this.setFrameDefaults();
        this.setVisible(true);
    }

    /**
     * Initializes the base components for the GUI:
     *
     * - Window dimensions
     * - CardLayout navigation stack
     * - Root content panel
     * - etc
     */
    private void initObjects () {
        this.windowDimension = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.viewStack = new CardLayout();
        this.panel = new JPanel(this.viewStack);

        this.setView(ViewsId.HOME_VIEW);
        this.add(this.panel);
    }

    /**
     * Configures the main JFrame settings, including:
     *
     * - Fixed size
     * - Window title
     * - Centered screen position
     * - Disabled resizing
     * - Custom closing behavior (saving progress before exit)
     */
    private void setFrameDefaults() {
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setPreferredSize(this.windowDimension);
        this.setTitle(WINDOW_TITLE);
        this.setResizable(false);
        
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                System.out.println("New window size: " + width + " x " + height);
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                control.pleaseSaveChangesOnLevelAvailableness();
                dispose();
                System.exit(0);
            }
        });
    }

    /**
     * Loads the requested view into the CardLayout stack. If the view does not
     * exist yet, it is created using the corresponding Singleton view factory.
     * Duplicate panels are avoided.
     *
     * @param viewId identifier of the view to load (see {@link ViewsId})
     * @return the loaded or retrieved JComponent view.
     */
    private JComponent setView(final String viewId) {
        JComponent view = null;

        switch (viewId) {
            case ViewsId.HOME_VIEW: view = HomeView.getInstance(this); break;
            case ViewsId.SELECT_MODE: view = SelectModeView.getInstance(this); break;
            case ViewsId.ONE_ICE_CREAM: view = SingleIceCreamView.getInstance(this); break;
            case ViewsId.SELECT_LEVEL: view = SelectLevelView.getInstance(this); break;
            case ViewsId.LEVEL: view = LevelView.getInstace(this); break;
            case ViewsId.TWO_ICE_CREAM: view = DoubleIceCreamView.getInstance(this); break;
            case ViewsId.OPP_VS_ICE: view = OppVsIceView.getInstance(this); break;
            case ViewsId.SIMULATION: view = MachinesView.getInstance(this); break;

            default: {
                BLogger.logError(
                    BLogger.SEVERE,
                    new ProgrammerException(ProgrammerException.unknownViewId(viewId))
                );
            }
        }

        if (view != null) {
            this.panel.add(view, viewId);
        }
        return view;
    }

    /**
     * Allows views (such as {@link HomeView}) to install a temporary glass pane
     * for modal effects or overlays.
     *
     * @param glassPane component to set as the frame's glass pane
     */
    @Override
    public void addAGlassPane(final JPanel glassPane) {
        this.setGlassPane(glassPane);
    }

    /**
     * Requests the main frame to switch to a specific view. This method is invoked
     * by view button listeners to navigate through the GUI. It now ensures the
     * target view requests focus immediately after being shown.
     *
     * @param viewId identifier of the view to display (see {@link ViewsId})
     */
    @Override
    public void showThisView(final String viewId) {
        JComponent view = this.setView(viewId); // Get the component instance
        this.viewStack.show(this.panel, viewId);
        
        // Request focus on the newly visible component immediately after the switch
        if (view != null) {
            view.requestFocusInWindow();
        }
    }

    /**
     * Displays a user-facing error message when a recoverable exception occurs.
     *
     * @param message human-readable description of the issue
     */
    @Override
    public void indicateUserException(final String message) {
        JOptionPane.showInternalMessageDialog(
            null,
            message,
            "user exception ,':/",
            JOptionPane.ERROR_MESSAGE,
            null
        );
    }

    /**
     * Updates the window title according to the active view.
     *
     * @param title the new title text
     */
    @Override
    public void setViewTitle(final String title) {
        this.setTitle(title);
    }

    /**
     * Provides access to the controller so views can send requests
     * to the logic layer.
     *
     * @return the controller instance
     */
    @Override
    public Control getController() {
        return this.control;
    }

    /**
     * Returns the fixed window dimensions used across all views.
     *
     * @return dimension of the main frame
     */
    public Dimension getDimensions() {
        return this.windowDimension;
    }

    /**
     * Retrieves the Singleton instance of the main GUI. The controller must
     * be supplied the first time the instance is created.
     *
     * @param controller the controller linking UI and logic
     * @return the unique GUI instance
     */
    public static BaDopoCreamGUI getInstance(final Control controller) {
        if (INSTANCE == null) {
            INSTANCE = new BaDopoCreamGUI(controller);
        }
        return INSTANCE;
    }
}
