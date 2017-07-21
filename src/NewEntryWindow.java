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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

public class NewEntryWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private String myUsername;
	private JTable myTable;
	private JTextField mySearchField;
	private JButton mySearchButton;
	private JSpinner myQuantityField;
	private JButton myAddButton;
	private DefaultTableModel myTableModel;
	private FoodDB[] mySearchResults;
	private UserWindow myWindow;
	
	public NewEntryWindow(String theUsername, UserWindow theWindow) {
		myWindow = theWindow;
		myFrame = new JFrame();
		myFrame.setTitle("New Diary Entry");
		myUsername = theUsername;
		createPanels();
		myFrame.setSize(900, 500);
		myFrame.setResizable(false);
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.setVisible(true);
	}
	
	private void createPanels() {
		Container pane = myFrame.getContentPane();
		JPanel panel1 = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		panel1.setLayout(fl);
		pane.add(panel1, BorderLayout.NORTH);
		JLabel l1 = new JLabel("Search for food: ");
		panel1.add(l1);
		mySearchField = new JTextField();
		panel1.add(mySearchField);		
		mySearchField.setPreferredSize(new Dimension(192, 23));
		mySearchButton = new JButton("Search");
		panel1.add(mySearchButton);
		mySearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				mySearchResults = DatabaseAccess.searchFoods(mySearchField.getText());
				setTable(mySearchResults);
			}
		});
		JScrollPane sp = new JScrollPane();
		pane.add(sp, BorderLayout.CENTER);
		sp.setPreferredSize(new Dimension(350, 50));

		myTableModel = new DefaultTableModel(null, new String[] {"Item #", "Food", 
				"Calories", "Protein", "Carbs", "Fat", "Portion Size"});
		myTable = new JTable();
		sp.setViewportView(myTable);
		myTable.setModel(myTableModel);
		FoodDB[] foods = DatabaseAccess.getFoods();
		setTable(foods);
		FlowLayout fl2 = new FlowLayout(FlowLayout.RIGHT);
		JPanel panel2 = new JPanel();
		panel2.setLayout(fl2);
		pane.add(panel2, BorderLayout.SOUTH);
		JLabel l2 = new JLabel("Quantity: ");
		panel2.add(l2);
		SpinnerNumberModel nmQuantity = new SpinnerNumberModel(
				new Integer(0),
				new Integer(0),
				new Integer(50),
				new Integer(1)
				);
		myQuantityField = new JSpinner(nmQuantity);
		panel2.add(myQuantityField);
		myAddButton = new JButton("Add");
		panel2.add(myAddButton);
		myAddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				Integer tempQ = (Integer) myQuantityField.getValue();
				short quantity = tempQ.shortValue();
				int row = myTable.getSelectedRow();
				DatabaseAccess.addDietDiaryEntry((Integer)myTableModel.getValueAt(row, 0), 
						quantity, myUsername);
				myWindow.createTable();
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
	
	private void setTable(FoodDB[] theTable) {
		FoodDB[] foodTable = theTable.clone();
		myTableModel.setRowCount(0);
		if (foodTable != null) {
			for (int j = 0; j < foodTable.length; j++) {
				FoodDB f = foodTable[j];
				myTableModel.addRow(new Object[] {f.id, f.name, f.calories, 
						f.protein, f.carbs, f.fat, f.portion_size});
			}
		}
	}
}
