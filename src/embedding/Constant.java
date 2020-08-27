package embedding;

/**
 * Class to save some global values.
 * @author tommy
 *
 */
public class Constant {
	// interval to print progress when calculating positions and labels of the drawnigs
	public static final int DRAWING_PROGRESS = 500;
	

	// window
	public static final int WINDOW_WIDTH  = 1920; // 1200
	public static final int WINDOW_HEIGHT = 1050;
	// canvas, where the drawing is shown (calculate positions for the drawings again after changing this!!!)
	public static final int CANVAS_WIDTH  = 1600;  // 800
	public static final int CANVAS_HEIGHT = 940;  // 800
	// the size (higth and width) of a node in the window (calculate positions for the drawings again after changing this!!!)
	public static final int NODE_SIZE     = 18;
	// text on the information bar
	public static final String CROSSING_COLOR = "Crossings are lilac.";
	public static final String SETS_COLOR     = "Nodes of the bip. sets are blue and red.";
	public static final String NEW_COLOR      = "Currently added nodes are green.";
	
	// text for file chooser
	public static final String CONFIRM_FILE_OVERWRITE_TEXT  = "Are you sure you want to overwrite the file?";
	public static final String CONFIRM_FILE_OVERWRITE_TITLE = "Warning";
	public static final String TITLE_FILE_CHOOSER           = "Export as GML";
	
	
}
