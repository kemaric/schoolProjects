import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class CrawlerGUI {
	
	private static final int UPDATE_DELAY_MS = 777;  // time to wait between updates
	
	private MyQueue<URL> linkQueue;
	private MyQueue<URL> picQueue;
	private MySet<URL> beenThere;
	private MySet<URL> doneThat;
	private ExtractorThread[] extractors;
	private JTextField startEntry = new JTextField("http://photobucket.com/findstuff/", 60);
	private JButton goButton = new JButton("GO!");
	private JLabel picQueueCount = new JLabel("Pics in Queue: 0");
	private JLabel linkQueueCount = new JLabel("Links in Queue: 0");
	private JLabel beenThereCount = new JLabel("Links Discovered: 0");
	private JLabel doneThatCount = new JLabel("Pics Discovered: 0");
	private JLabel caption = new JLabel("The following URLS are currently being scanned by ExtractorThreads:");
	private JLabel[] threadLocations;
	
	public CrawlerGUI(MyQueue<URL> linkQueue, MyQueue<URL> picQueue, MySet<URL> beenThere, MySet<URL> doneThat, ExtractorThread [] extractors) {
		
		this.linkQueue = linkQueue;
		this.picQueue = picQueue;
		this.beenThere = beenThere;
		this.doneThat = doneThat;
		this.extractors = extractors;
		
		threadLocations = new JLabel[extractors.length];
		for (int i = 0; i < extractors.length; i++)
			threadLocations[i] = new JLabel("Not used");   // hint
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
				createAndShowGUI();
			}
		});
	}
	
	private void createAndShowGUI() {
		
		JFrame frame = new JFrame("Crawler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				synchronized(extractors) {
					
					for (int i = 0; i < extractors.length; i++) {
						if (extractors[i] != null) {
							try {
								extractors[i].join();
							}
							catch(InterruptedException xx) {}
						}
					}
					
					beenThere.clear();
					doneThat.clear();
					picQueue.clear();
					linkQueue.clear();
					
					for (int j = 0; j < extractors.length; j++) 
						extractors[j] = null;
					
					try {
						linkQueue.enqueue(new URL(startEntry.getText()));
						beenThere.add(new URL(startEntry.getText()));
					}
					catch(MalformedURLException ex) {}
					
				}
				
			}
		});
		
		ActionListener listener = new ActionListener() {        //hints
			public void actionPerformed(ActionEvent event) {
				beenThereCount.setText("Links Discovered: " + beenThere.size());
				doneThatCount.setText("Pics Discovered: " + doneThat.size());
				picQueueCount.setText("Pics in Queue: " + picQueue.size());
				linkQueueCount.setText("Links in Queue: " + linkQueue.size());
				for (int i = 0; i < extractors.length; i++) {
					if (extractors[i] != null && extractors[i].isAlive())   // HINT!!
						threadLocations[i].setText(extractors[i].getCurrentURL());
					else
						threadLocations[i].setText("Unused");
				}
			}
		};
		
		new javax.swing.Timer(UPDATE_DELAY_MS, listener).start();  // hint
		
		
		frame.getContentPane().setLayout(new GridLayout(3 + extractors.length, 1));  // hint
		
		JPanel top = new JPanel();
		top.add(startEntry);
		top.add(goButton);
		frame.getContentPane().add(top);
		
		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(2, 2));
		middle.add(beenThereCount);
		middle.add(doneThatCount);
		middle.add(linkQueueCount);
		middle.add(picQueueCount);
		frame.getContentPane().add(middle);
		
		frame.getContentPane().add(caption);
		
		for (int i = 0; i < extractors.length; i++) {
			frame.getContentPane().add(threadLocations[i]);
		}
		frame.pack();
		frame.move(100,100);
		frame.setVisible(true);
	}
}
