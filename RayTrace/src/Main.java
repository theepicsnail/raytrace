import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main implements Runnable {
	public static void main(String[] args) throws IOException {
		new Main();
	}
	
	Container display;
	Player player;
	Map map;
	Camera camera;
	JFrame window;
	
	public Main() throws IOException {
		display = new JPanel();
		window = new JFrame("test");
		window.addKeyListener(Controls.instance);
		window.setContentPane(display);
		window.setSize(1024, 768);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		player = new Player(1, 1, 1);
		//map = new Map(32);
		map = new Map(new String[]{
				"##########",
				"#        #",
				"#  #  #  #",
				"#        #",
				"#  #  #  #",
				"#        #",
				"##########",
		});
		camera = new Camera(window.getContentPane(), 768, Math.PI*.4);
		new Thread(this).start();
	}

	public void run() {
		long last = 0;
		BufferedImage buffer = new BufferedImage(display.getWidth(), display.getHeight(), BufferedImage.TYPE_INT_BGR);
		
		while(window.isVisible()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long cur = System.currentTimeMillis();
			long dt = cur - last;
			last = cur;
			if(dt == cur) continue;
			
			double seconds = dt / 1000.0;
			player.update(map, seconds);
			camera.render(player, map, buffer.getGraphics());
			
			display.getGraphics().drawImage(buffer, 0, 0, null);
		}
	}
}
