import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class EditPlanWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private TrainerPlanWindow myWindow;
	private int myID;
	private String myClientUsername;
	private JButton myUpdateButton;
	
	public EditPlanWindow(int theTrainerID, String theUsername, TrainerPlanWindow theWindow) {
		myID = theTrainerID;
		myClientUsername = theUsername;
		myWindow = theWindow;
		myFrame = new JFrame();
		myFrame.setTitle("Edit Plan");
		createPanels();
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.setVisible(true);
	}
	
	private void createPanels() {
		Container pane = myFrame.getContentPane();
		JPanel panel = new JPanel();
		FlowLayout f2 = new FlowLayout(FlowLayout.LEADING);
		panel.setLayout(f2);
		pane.add(panel, BorderLayout.NORTH);
		Plan p = DatabaseAccess.getUserPlan(myClientUsername);
		SpinnerNumberModel nmCalories = new SpinnerNumberModel(
				new Integer (p.calories),
				new Integer(0),
				new Integer(15000),
				new Integer(5)
				);
		SpinnerNumberModel nmProtein = new SpinnerNumberModel(
				new Integer(p.protein),
				new Integer(0),
				new Integer(500),
				new Integer(1)
				);
		SpinnerNumberModel nmCarbs = new SpinnerNumberModel(
				new Integer(p.carbs),
				new Integer(0),
				new Integer(500),
				new Integer(1)
				);
		SpinnerNumberModel nmFat = new SpinnerNumberModel(
				new Integer(p.fat),
				new Integer(0),
				new Integer(500),
				new Integer(1)
				);
		SpinnerNumberModel nmWater = new SpinnerNumberModel(
				new Integer(p.water),
				new Integer(0),
				new Integer(500),
				new Integer(1)
				);
		JLabel l3 = new JLabel("Calories: ");
		panel.add(l3);
		JSpinner calories = new JSpinner(nmCalories);
		panel.add(calories);
		JLabel l4 = new JLabel("Protein: ");
		panel.add(l4);
		JSpinner protein = new JSpinner(nmProtein);
		panel.add(protein);
		JLabel l5 = new JLabel("Carbs: ");
		panel.add(l5);
		JSpinner carbs = new JSpinner(nmCarbs);
		panel.add(carbs);
		JLabel l6 = new JLabel("Fat: ");
		panel.add(l6);
		JSpinner fat = new JSpinner(nmFat);
		panel.add(fat);
		JLabel l7 = new JLabel("Water: ");
		panel.add(l7);
		JSpinner water = new JSpinner(nmWater);
		panel.add(water);
		
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pane.add(panel2, BorderLayout.EAST);
		myUpdateButton = new JButton("Update");
		panel2.add(myUpdateButton);
		myUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				Integer theCalories = (Integer) calories.getValue();
				Integer theProtein = (Integer) protein.getValue();
				Integer theCarbs = (Integer) carbs.getValue();
				Integer theFat = (Integer) fat.getValue();
				Integer theWater = (Integer) water.getValue();
				DatabaseAccess.updateUserPlan(myID, myClientUsername, theCalories.shortValue(),
						theProtein.shortValue(), theCarbs.shortValue(), theFat.shortValue(), 
						theWater.shortValue());
				myWindow.createTable1();
				myFrame.dispose();
			}
		});
		JButton cancel = new JButton("Cancel");
		panel2.add(cancel);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				myFrame.dispose();
			}
		});
	}
}
