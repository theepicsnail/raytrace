import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Controls implements KeyListener {
	
	public static final Controls instance = new Controls();
	
	private boolean up, down, left, right, space;
	
	private Controls() {
		reset();
	}
	
	private void reset() {
		up = false;
		down = false;
		left = false;
		right = false;
		space = false;
	}
	
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_UP: up = true; break;
		case KeyEvent.VK_DOWN: down = true; break;
		case KeyEvent.VK_LEFT: left= true; break;
		case KeyEvent.VK_RIGHT: right= true; break;
		case KeyEvent.VK_SPACE: space = true; break;
		}
	}

	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_UP: up = false; break;
		case KeyEvent.VK_DOWN: down = false; break;
		case KeyEvent.VK_LEFT: left= false; break;
		case KeyEvent.VK_RIGHT: right= false; break;
		case KeyEvent.VK_SPACE: space = false; break;
		}
	}

	public void keyTyped(KeyEvent arg0) {}

	public static boolean forward() {
		return instance.up;
	}

	public static boolean backward() {
		return instance.down;
	}

	public static boolean left() {
		return instance.left;
	}

	public static boolean right() {
		return instance.right;
	}

	public static boolean debug() {
		return instance.space;
	}
	
	
	
}
