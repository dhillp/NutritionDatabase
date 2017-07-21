import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public final class UserWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private String myUsername;
	private JTable myTable;
	private DefaultTableModel myTableModel;
	private JButton myNewEntry;
	private JButton myEditEntry;
	private JButton myDeleteEntry;
	private Timer myTimer;
	private UserWindow myWindow;
	
	public UserWindow(String theUsername) {
		myWindow = this;
		myFrame = new JFrame();
		createTimer();
		myTimer.start();
		myFrame.setTitle(theUsername + " - Diet Diary");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myUsername = theUsername;
		createDiary();
		myFrame.setSize(800, 500);
		myFrame.setResizable(false);
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent theEvent) {
				myTimer.stop();
			}
		});
		myFrame.setVisible(true);
	}
	
	private void createTimer() {
		myTimer = new Timer(100,new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent theEvent) {
	            int t = myTable.getSelectedRow();
	            if (t > -1) {
	            	myEditEntry.setEnabled(true);
	            	myDeleteEntry.setEnabled(true);
	            } else {
	            	myEditEntry.setEnabled(false);
	            	myDeleteEntry.setEnabled(false);
	            }
	        }
	    });
	}
	
	private void createDiary() {
		Container pane = myFrame.getContentPane();
		JScrollPane sp = new JScrollPane();
		pane.add(sp, BorderLayout.CENTER);
		sp.setPreferredSize(new Dimension(350, 50));
		myTableModel = new DefaultTableModel(null, new String[] {"ID", "Date", "Food", 
				"Calories", "Protein", "Carbs", "Fat", "Quantity"});
		myTable = new JTable();
		sp.setViewportView(myTable);
		myTable.setModel(myTableModel);
		createTable();
		myTable.removeColumn(myTable.getColumn("ID"));
		JPanel panel1 = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		pane.add(panel1, BorderLayout.EAST);
		myNewEntry = new JButton("New Entry");
		panel1.add(myNewEntry, constraints);
		constraints.gridy = 1;
		myNewEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				new NewEntryWindow(myUsername, myWindow);
			}
		});
		myEditEntry = new JButton("Edit Entry");
		myEditEntry.setEnabled(false);
		panel1.add(myEditEntry, constraints);
		constraints.gridy = 2;
		myEditEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				int row = myTable.getSelectedRow();
				new EditDietDiaryEntryWindow((Integer) myTableModel.getValueAt(row, 0), 
						myWindow);
			}
		});
		myDeleteEntry = new JButton("Delete Entry");
		myDeleteEntry.setEnabled(false);
		panel1.add(myDeleteEntry, constraints);
		myDeleteEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				int row = myTable.getSelectedRow();
				new DietDiaryDeleteConfirmWindow((Integer) myTableModel.getValueAt(row, 0), 
						myWindow);
			}
		});
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JButton viewPlan = new JButton("View Plan");
		viewPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				new UserPlanWindow(myUsername);
			}
		});
		JButton viewWater = new JButton("Water Diary");
		viewWater.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				new WaterDiaryWindow(myUsername);
			}
		});
		panel2.add(viewPlan);
		panel2.add(viewWater);
		pane.add(panel2, BorderLayout.NORTH);
	}
	
	public void createTable() {
		DietDiary[] myDiaryTable = DatabaseAccess.getMyDietDiaries(myUsername);
		myTableModel.setRowCount(0);
		if (myDiaryTable != null) {
			for (int i = 0; i < myDiaryTable.length; i++) {
				DietDiary d = myDiaryTable[i];
				myTableModel.addRow(new Object[] {d.id, d.entry_date, d.food, d.calories, 
						d.protein, d.carbs, d.fat, d.quantity});
			}
		}
	}
}
