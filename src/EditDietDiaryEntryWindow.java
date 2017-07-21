import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class EditDietDiaryEntryWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private int myDiaryID;
	private JTable myTable;
	private DefaultTableModel myTableModel;
	private UserWindow myWindow;
	private JButton myUpdateButton;
	
	public EditDietDiaryEntryWindow(int theDiaryID, UserWindow theWindow) {
		myWindow = theWindow;
		myFrame = new JFrame();
		myFrame.setTitle("Edit Diary Entry");
		myDiaryID = theDiaryID;
		createPanels();
		myFrame.setSize(900, 250);
		myFrame.setResizable(false);
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.setVisible(true);
	}
	
	private void createPanels() {
		Container pane = myFrame.getContentPane();
		
		JScrollPane sp = new JScrollPane();
		pane.add(sp, BorderLayout.CENTER);
		sp.setPreferredSize(new Dimension(250, 50));
		myTableModel = new DefaultTableModel(null, new String[] {"Date", "Food", 
				"Calories", "Protein", "Carbs", "Fat", "Quantity"});
		myTable = new JTable();
		sp.setViewportView(myTable);
		myTable.setModel(myTableModel);
		createTable();
		
		JPanel panel1 = new JPanel();
		FlowLayout f1 = new FlowLayout(FlowLayout.LEFT);
		panel1.setLayout(f1);
		pane.add(panel1, BorderLayout.NORTH);
		JLabel l1 = new JLabel("Enter new values for this entry: ");
		panel1.add(l1);
		
		JPanel panel2 = new JPanel();
		FlowLayout f2 = new FlowLayout(FlowLayout.LEADING);
		panel2.setLayout(f2);
		pane.add(panel2, BorderLayout.SOUTH);
		JLabel l2 = new JLabel("Name of food:");
		DietDiary d = DatabaseAccess.getDietDiaryEntry(myDiaryID);
		SpinnerNumberModel nmCalories = new SpinnerNumberModel(
				new Integer(d.calories),
				new Integer(0),
				new Integer(2000),
				new Integer(1)
				);
		SpinnerNumberModel nmProtein = new SpinnerNumberModel(
				new Integer(d.protein),
				new Integer(0),
				new Integer(50),
				new Integer(1)
				);
		SpinnerNumberModel nmCarbs = new SpinnerNumberModel(
				new Integer(d.carbs),
				new Integer(0),
				new Integer(50),
				new Integer(1)
				);
		SpinnerNumberModel nmFat = new SpinnerNumberModel(
				new Integer(d.fat),
				new Integer(0),
				new Integer(50),
				new Integer(1)
				);
		SpinnerNumberModel nmQuantity = new SpinnerNumberModel(
				new Integer(d.quantity),
				new Integer(0),
				new Integer(50),
				new Integer(1)
				);
		panel2.add(l2);
		JTextField name = new JTextField();
		name.setText(d.food);
		panel2.add(name);
		name.setPreferredSize(new Dimension(192, 23));
		JLabel l3 = new JLabel("Calories:");
		panel2.add(l3);
		JSpinner calories = new JSpinner(nmCalories);
		panel2.add(calories);
		JLabel l4 = new JLabel("Protein:");
		panel2.add(l4);
		JSpinner protein = new JSpinner(nmProtein);
		panel2.add(protein);
		JLabel l5 = new JLabel("Carbs:");
		panel2.add(l5);
		JSpinner carbs = new JSpinner(nmCarbs);
		panel2.add(carbs);
		JLabel l6 = new JLabel("Fat:");
		panel2.add(l6);
		JSpinner fat = new JSpinner(nmFat);
		panel2.add(fat);
		JLabel l7 = new JLabel("Quantity:");
		panel2.add(l7);
		JSpinner quantity = new JSpinner(nmQuantity);
		panel2.add(quantity);
		
		JPanel panel3 = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		pane.add(panel3, BorderLayout.EAST);
		myUpdateButton = new JButton("Update");
		panel3.add(myUpdateButton, constraints);
		constraints.gridy = 1;
		myUpdateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				Integer theCalories = (Integer) calories.getValue();
				Integer theProtein = (Integer) protein.getValue();
				Integer theCarbs = (Integer) carbs.getValue();
				Integer theFat = (Integer) fat.getValue();
				Integer theQuantity = (Integer) quantity.getValue();
				DatabaseAccess.editDietDiaryEntry(myDiaryID, name.getText().trim(), 
						theCalories.shortValue(), theProtein.shortValue(), 
						theCarbs.shortValue(), theFat.shortValue(), theQuantity.shortValue());
				myWindow.createTable();
				myFrame.dispose();
			}
		});
		JButton cancel = new JButton("Cancel");
		panel3.add(cancel, constraints);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				myFrame.dispose();
			}
		});
	}
	
	public void createTable() {
		DietDiary d = DatabaseAccess.getDietDiaryEntry(myDiaryID);
		myTableModel.setRowCount(0);
		if (d != null) {
			myTableModel.addRow(new Object[] {d.entry_date, d.food, d.calories, 
					d.protein, d.carbs, d.fat, d.quantity});
		}
	}
}
