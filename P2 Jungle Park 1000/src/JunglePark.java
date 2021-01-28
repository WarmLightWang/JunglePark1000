import java.util.Random;

/**
 * The class JunglePark develop the basis for an interactive graphical
 * application with predefined objects and work with callback methods to
 * responds to mouse-based input. This program provides 6 methods for users,
 * each of the methods in turn has following functions:
 * 
 * setup: Setting up the initial environment and background properties of this
 * application. This method will only run once.
 * 
 * update: Updating the images and display windows after the new tigers have
 * been draw and moved. This method will run multiple times.
 * 
 * isMouseOver: This method help the application to check if the tigers have
 * been dragged by the user. It will return boolean values to mouseDown and
 * mouseUp methods.
 * 
 * mouseDown: This method checks if the mouse is over one of the tiger objects
 * stored in the tigers array and allows the user to move the tigers with mouse
 * input.
 * 
 * mouseUp: This method stops any movements of the tigers when the mouse is
 * released.
 * 
 * keyPressed: KeyPressed method allowed user to add or erase tigers in the
 * display window.
 * 
 * The class start with main method, in which calling the "startApplication" in
 * Utility class to start running the application. The
 * Utility.startApplication() creates the display window, sets its dimension,
 * and checks for all the callback methods. setup (callback for setting up
 * initial display window), update (callback for updating the new display),
 * isMouseOver (if the mouse is over the image), mouseDown (the user can move
 * the tiger if the isMouseOver is the true), mouseUp (Stop dragging the tiger
 * if the mouse is released), keyPressed (Add new the tiger if "T" or "t", and
 * erase the tiger if "R" or "r"),
 */
public class JunglePark {

	private static PApplet processing; // PApplet object that represents the graphic
	// interface of the JunglePark application

	private static PImage backgroundImage; // PImage object that represents the
	// background image

	private static Tiger[] tigers; // array storing the current tigers present
	// in the Jungle Park

	private static Random randGen; // Generator of random numbers

	/**
	 * The "setup" method defined to visualize the jungle park area. It defines the
	 * initial environment properties of the application.
	 * 
	 * @param processingObj represents a reference to the graphical interface of the
	 *                      application
	 * 
	 *                      This callback method will be only executed once.
	 */
	public static void setup(PApplet processingObj) {

		processing = processingObj; // initialize the processing field to the one passed into
		// the input argument parameter
		// Set the color used for the background of the Processing window
		backgroundImage = processing.loadImage("images/background.png");
		// Draw the background image at the center of the screen

		randGen = new Random(); // create a Random object and store its reference in randGen

		tigers = new Tiger[8]; // create a new Tiger[] object and store its reference in tigers

	}

	/**
	 * Updating the images and the background displayed. It draws the new tigers
	 * after the last one has been properly drew and placed and updates the display
	 * window.
	 * 
	 * This callback method will be repeatedly executed.
	 */
	public static void update() {

		processing.background(245, 255, 250); // Mint cream color
		// initialize and load the image of the background
		processing.image(backgroundImage, processing.width / 2, processing.height / 2);
		// width [resp.height]: System variable of the processing library that stores
		// the width
		// [resp.height] of the display window.

		for (int i = 0; i < tigers.length; i++) {
			if (tigers[i] != null)
				tigers[i].draw();
		}
	}

	/**
	 * This method will check if the mouse is still over the tigers when the user is
	 * dragging the images. The range of the difference between the mouse and the
	 * center of the image depends on the dimensions of the image. It will return
	 * true when the mouse is still over the image and return false otherwise.
	 * 
	 * This callback method will be repeatedly executed.
	 */
	public static boolean isMouseOver(Tiger tiger) {
		float halfWidth = tiger.getImage().width / 2; // get the width of the image that display and
		// give the half of this value to the new float variable halfWidth
		float halfHeight = tiger.getImage().height / 2;
		if (tiger.getPositionX() - processing.mouseX > -halfWidth
				&& tiger.getPositionX() - processing.mouseX < halfWidth
				&& tiger.getPositionY() - processing.mouseY > -halfHeight
				&& tiger.getPositionY() - processing.mouseY < halfHeight) { // check if the difference between
			// the position of mouse and the position of the center of the image is in the
			// range, and the
			// properly ranges are the half of the width and the half of the height
			return true;
		} else
			return false;
	}

	/**
	 * Whenever the user clicks the mouse and maintain that state continuously (the
	 * mouse is at "down" state), mouseDown() method will be repeatedly executed and
	 * program will select the one with the lowest index among all Tiger objects
	 * that are being overlapped by mouse pointer to "is_dragged" state. And the
	 * position of that specific Tiger object will be upgraded to where mouse
	 * pointer lies.
	 * 
	 * This callback method will be repeatedly executed.
	 */
	public static void mouseDown() {
		for (int i = 0; i < tigers.length; i++)
			if (tigers[i] != null) {
				if (isMouseOver(tigers[i])) {
					tigers[i].setDragging(true); // set isDragging field of the tiger object stored in
					// the tigers array to true if the mouse is over the image
					tigers[i].setPositionX(processing.mouseX);
					tigers[i].setPositionY(processing.mouseY);
					break;
				}
			}
	}

	/**
	 * Whenever the user doesn't click the mouse (the mouse is at "up" state),
	 * mouseUp() method will be executed and program will set all Tiger object to
	 * "not_dragged" state.
	 * 
	 * This callback method will be repeatedly executed.
	 */
	public static void mouseUp() {
		for (int i = 0; i < tigers.length; i++) {
			if (tigers[i] != null)
				tigers[i].setDragging(false); // set isDragging field of every tiger object stored in
			// the tigers array to false
		}
	}

	/**
	 * Each time the user presses any key, the keyPressed() callback method will be
	 * executed once and program will check which key was pressed using the
	 * processing.key field. If the key is 'T or 't', a for loop will run to check
	 * if there is any null object in the tigers array. If so, a new Tiger object
	 * will be initialized and replace that null object in the tigers array, then
	 * callback method update() will detect this change and draw the new Tiger
	 * object on the screen. If the key is 'R or 'r', a for loop will run to check
	 * ALL the Tiger object in the tigers array and delete ALL Tiger objects which
	 * have mouse over them. This step can deletes multiple Tiger objects if the
	 * mouse is over more than one Tiger objects.
	 */
	public static void keyPressed() {

		if (processing.key == 'T' || processing.key == 't') {
			for (int i = 0; i < tigers.length; i++) {
				if (tigers[i] == null) {
					tigers[i] = new Tiger(processing, (float) randGen.nextInt(processing.width),
							(float) randGen.nextInt(processing.height));
					break;
				}
			}
		}

		if (processing.key == 'R' || processing.key == 'r') {
			for (int i = 0; i < tigers.length; i++) {
				if (tigers[i] != null) {
					if (isMouseOver(tigers[i])) {
						tigers[i] = null;
						break;
					}
				}
			}
		}
	}

	/**
	 * The class start with main method, in which the startApplication() method from
	 * the provided Utility class was called. The method creates the main window for
	 * the application, and then repeatedly updates its appearance and checks for
	 * user input. It also checks if specific callback methods have been defined in
	 * the JunglePark class.
	 */
	public static void main(String arg[]) {

		Utility.startApplication();

	}
}
