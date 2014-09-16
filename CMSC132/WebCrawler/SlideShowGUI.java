import javax.swing.*;

import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.*;
import java.net.URL;
import java.awt.*;

public class SlideShowGUI extends JPanel{
	
	public static final long serialVersionUID = -1;
	
	JFrame frame;
	Image image;
	MyQueue<URL> queue;
	
	static final int DELAY_MS = 500;   // how long to wait between pics
	static final int MIN_HEIGHT = 30;   // minimum picture height (pixels)
	static final int MIN_WIDTH = 30;    // minimum picture width (pixels)
	
	public SlideShowGUI(MyQueue<URL> queue) {
		this.queue = queue;
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
				createAndShowGUI();
			}
		});
	}
	
	private void loadImage() {
		URL imageName;
		
		while(true) {
			
			if (queue.size() == 0)
				break;
			imageName = (URL)queue.dequeue();
			
			
			Image newestImage = Toolkit.getDefaultToolkit().getImage(
					imageName);
			try {
				MediaTracker tracker = new MediaTracker(new Panel());
				tracker.addImage(newestImage, 0);
				tracker.waitForID(0);
				if (tracker.statusID(0, true) != MediaTracker.COMPLETE) {
					// couldn't load it -- oh well...
				}
			} catch (InterruptedException e) {}
			
			if (newestImage != null && newestImage.getHeight(null) >= MIN_HEIGHT &&
					newestImage.getWidth(null) >= MIN_WIDTH) {
				image = newestImage;
				
				int preferredWidth = Math.max(800, image.getWidth(null));
				this.setPreferredSize(new Dimension(preferredWidth, image.getHeight(null)));
				setSize(image.getWidth(null), image.getHeight(null));
				frame.pack();
				frame.setTitle(imageName.toString());
				break;
			}
		}
		this.repaint();
	}
	
	private void createAndShowGUI() {
		
		//Create and set up the window.
		frame = new JFrame("Slide Show");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadImage();
			}
		};
		new Timer(DELAY_MS, taskPerformer).start();
		
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	/*
	 *  Called by java when this panel needs to be re-drawn.  
	 *  DO NOT EVER CALL THIS METHOD DIRECTLY!
	 */
	public void paint(Graphics g) {
		
		super.paint(g);        // draws everything but the picture
		if (image != null) {
			g.drawImage(image, 0, 0, this);  // draws the picture
		}
	}
}
