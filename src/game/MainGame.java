package game;
import menu.MainMenu;
import menu.ServerMenu;
import controller.GameController;
import server.Server;
import ucigame.Sprite;
import ucigame.Ucigame;


/**
 * Main game class for Pacman. Inspired by https://github.com/webdestroya/pacman
 * 
 * @author Siyuan Zhang
 *
 */
public class MainGame extends Ucigame{
   /**
	 * 
	 */
	private static final long serialVersionUID = -8001318729185074318L;
	Sprite ball;
    Sprite paddle;
    private ServerMenu menu;
    private SceneMode currentScene;
    private GameController control;
    
    private final int port = 8899;
    private Server server;
    
    
    public static final int windowWidth = 600;
    public static final int windowHeight = 600;
    public static final int windowCentreX = windowWidth/2;
    public static final int windowCentreY = windowHeight/2;
    		
    
    /**
	 * This was added to override the stupid UCI game thing that requires the param twice,
	 * this allows me to put the whole game into a JAR file.
	 * @param args the list of command line args
	 */
	public static void main(String[] args)
	{
		String[] args2 = new String[1];
		args2[0] = "game.MainGame";
		System.out.println("Current User: " + System.getProperty("user.name") );
		Ucigame.main(args2);
	}
	
    @Override
    public void setup()
    {
    	initializeWindow();

    	showServermenu();
//	        ball = makeSprite(getImage("resources/ball.png"));
//	        paddle = makeSprite(getImage("resources/paddle.png"));
//
//	        ball.position(canvas.width()/2 - ball.width()/2,
//	                      canvas.height()/2 - ball.height()/2);
//	        paddle.position(canvas.width() - paddle.width() - 10,
//	                       (canvas.height() - paddle.height()) / 2);
    }

    private void initializeWindow() {
		// set frame rate
		this.framerate(60);
		//instantiate a global game controller
		control = GameController.setInstance(this);
		// set window size
		window.size(windowWidth, windowHeight);
		// set window title
		window.title("Pac Man Fever");
	}
    
    private void showServermenu(){
    	// initialize menu
		menu = new ServerMenu(this);
		server = new Server(port);
		showScene(SceneMode.SERVERMENU);
    }
    
    private void showGameScreen(){   	
    	control.startGame(); // start the game
		showScene(SceneMode.GAME);
    }
    
    public void drawServermenu(){
    	canvas.clear();
        menu.draw();
    }

    /**
	 * Draws the "Game" scene
	 */
	public void drawGame() {
		canvas.clear();
		control.nextMove();
		control.drawState();
	}
	
	/**
	 * Capture the key actions when in Game mode
	 */
    public void onKeyPressGame()
    {
        // Arrow keys and WASD keys move the paddle
    	if (keyboard.isDown(keyboard.UP, keyboard.W)){
    		
			control.setPacManDirection(Direction.UP);
			
		}else if (keyboard.isDown(keyboard.DOWN, keyboard.S)){
			
			control.setPacManDirection(Direction.DOWN);
			
		}else if (keyboard.isDown(keyboard.LEFT, keyboard.A)){
			
			control.setPacManDirection(Direction.LEFT);
			
		}else if (keyboard.isDown(keyboard.RIGHT, keyboard.D)){
			
			control.setPacManDirection(Direction.RIGHT);
			
		}
    }
    
    /**
	 * 
	 * starts a single-player game.
	 * 
	 */
	public void onClickStart() {
		if (isShowingScene(SceneMode.SERVERMENU)) {
			System.out.println("Single player game");
			//startPacManServer();
			showGameScreen();
		}
	}
    
    /**
     * Quit the game
     */
    public void onClickQuit(){
    	System.exit(0);
    }
    
	/**
	 * Displays the specified scene onto the window, while also storing the
	 * scene for later reference
	 * 
	 * @param scene		the scene to display
	 */
	public void showScene(SceneMode scene) {
		this.currentScene = scene;
		String sceneName = capitalize(scene.toString().toLowerCase());
		super.startScene(sceneName);
	}

	/**
	 * Check if the current scene is currently displayed
	 */
	public boolean isShowingScene(SceneMode scene) {
		return currentScene.equals(scene);
	}
	
	/**
	 * Capitalize the given word
	 * @param s
	 * @return
	 */
	private String capitalize(String s) {
		if (s.length() != 0 && s != null){
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}
		
		return s;
	}
}
