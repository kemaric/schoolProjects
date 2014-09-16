package calculator;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class InterestGUI extends JPanel{
	private JFrame frame;
	private JLabel principle, rate, year, intrest;
	private JTextField principleTextField, rateTextField, yearTextField;
	private JButton simpleInterestButton, compoundInterestButton;
	private Double principleNumber, ratePercent, intresetYears, totalIntrest;  

	public InterestGUI(){
		frame = new JFrame("Interest Calculator");
		principle = new JLabel("Principle:");
		rate = new JLabel("Rate(Percentage):");
		year = new JLabel("Years:");
		intrest = new JLabel("");
		principleTextField = new JTextField(10);
		rateTextField = new JTextField(8);
		yearTextField = new JTextField(3);

		simpleInterestButton = new JButton("Compute Simple Interest");
		compoundInterestButton = new JButton("Compute Compound Interest");

		add(principle);
		add(principleTextField);
		add(rate);
		add(rateTextField);
		add(year);
		add(yearTextField);
		add(simpleInterestButton);
		add(compoundInterestButton);
		this.add(intrest);
		frame.pack();
		frame.setVisible(true);


		simpleInterestButton.addActionListener(new SimpleInterestListener());

		compoundInterestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				principleNumber = Double.parseDouble(principleTextField.getText()) ;
				ratePercent = Double.parseDouble(rateTextField.getText());
				intresetYears = Double.parseDouble(yearTextField.getText());
				totalIntrest = principleNumber;

				for(int index = 0; index < intresetYears; index++){
					totalIntrest *= (1 + (ratePercent/100));
				}
				String formattedValue = NumberFormat.getCurrencyInstance().format(totalIntrest);
				intrest.setText("Computed compound interest is: " + formattedValue);
			}
		});
	}
		
		private class SimpleInterestListener implements ActionListener {
			public void actionPerformed(ActionEvent arg0) {
				principleNumber = Double.parseDouble(principleTextField.getText()) ;
				ratePercent = Double.parseDouble(rateTextField.getText());
				intresetYears = Double.parseDouble(yearTextField.getText());
				totalIntrest = 0.0;

				totalIntrest = principleNumber + (principleNumber * (ratePercent/100) * intresetYears);
				String formattedValue = NumberFormat.getCurrencyInstance().format(totalIntrest);
				intrest.setText("Computed simple interest is: " + formattedValue);

			}
		}

		public static void createAndShowGUI(){
			JFrame frame = new JFrame("Interest Calculator");
			frame.setContentPane( new InterestGUI());
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setSize(500,300);
			frame.setVisible(true);
		}

		public static void main(String[] arg){
			javax.swing.SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					createAndShowGUI();
				}
			});
		}

}

