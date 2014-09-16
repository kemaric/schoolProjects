package applet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import searchTree.SearchTreeMap;

public class WordCountApplet extends JApplet implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField urlTextField;

	JTextArea textArea;

	private void CreateWordGUI() {
		resize(650, 300);

		/* Name label and textfield */
		JLabel urlLabel = new JLabel("URL");
		int nameFieldLength = 40;
		urlTextField = new JTextField(nameFieldLength);
		urlTextField.setText("http://www.cs.umd.edu/class/spring2009/cmsc132/projects/Bst/data/beatles.txt");
		urlTextField.addActionListener(this);

		/* Adding Post button */
		JButton refreshButton = new JButton("Count!");
		refreshButton.addActionListener(this);

		/* Text Area to type info */
		textArea = new JTextArea(10, 40);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		/* Adding the scrollPane to the panel */

		// Lay out the GUI.
		BorderLayout layout = new BorderLayout();

		Container contentPane = getContentPane();
		contentPane.setLayout(layout);

		JPanel top = new JPanel(new FlowLayout());
		top.add(urlLabel);
		top.add(urlTextField);
		top.add(refreshButton);
		contentPane.add(top, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	// Called when this applet is loaded into the browser.
	public void init() {

		// Execute a job on the event-dispatching thread:
		// creating this applet's GUI.
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					CreateWordGUI();
				}
			});
		} catch (Exception e) {
			System.err.println("CreateWordGUI didn't successfully complete");
		}

	}

	public void actionPerformed(ActionEvent e) {
		String urlStr = urlTextField.getText();
		textArea.selectAll();

		URL userUrl = null;
		boolean UrlOnError = false;

		// Transform the String to a real URL
		try {
			userUrl = new URL(urlStr);
		} catch (Exception exc) {
			UrlOnError = true;
		}

		SearchTreeMap<String, Integer> tree = new SearchTreeMap<String, Integer>();

		if (!UrlOnError && (userUrl != null)) {
			try {
				Scanner sc = new Scanner(userUrl.openStream());
				Pattern p = Pattern.compile("[a-zA-Z]+");
				String s;
				while (sc.hasNextLine()) {
					// find all words in line
					while ((s = sc.findInLine(p)) != null) {
						Integer i = tree.get(s);
						if (i == null)
							tree.put(s, 1);
						else
							tree.put(s, i + 1);
					}
					sc.nextLine(); // go to next line
				}
			} catch (Exception exc) {
				UrlOnError = true;
			}

		}
		if (UrlOnError) {
			textArea.setText("Problem reading URL: " + urlStr);
		} else {
			String curStr = "URL = " + urlStr + "\nWords Counted = " + tree.size()
					+ "\n<Word #>\n";
			for (String str : tree.keyList()) {
				curStr = curStr + str + " " + tree.get(str) + "\n";
			}
			textArea.setText(curStr);
		}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WordCountApplet();
			}
		});
	}

	/* Static block for feel and look */
	/* Comment this block out and see how the feel and look changes */
	/* You can also try this experiment in a Mac */

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}