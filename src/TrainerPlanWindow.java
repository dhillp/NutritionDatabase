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
import javax.swing.table.DefaultTableModel;

public class TrainerPlanWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private String myClientUsername;
	private int myTrainerID;
	private JTable myTable;
	private DefaultTableModel myTableModel;
	private JTable myTable2;
	private DefaultTableModel myTableModel2;
	private TrainerPlanWindow myWindow;
	
	public TrainerPlanWindow(String theClientUsername, int theTrainerID) {
		myFrame = new JFrame();
		myWindow = this;
		myFrame.setTitle(theClientUsername + " - Plan");
		myClientUsername = theClientUsername;
		myTrainerID = theTrainerID;
		createPanels();
		myFrame.setSize(500, 175);
		myFrame.setResizable(false);
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	private void createPanels() {
		Container pane = myFrame.getContentPane();
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		JLabel l1 = new JLabel(myClientUsername + "'s Current Plan: ");
		panel1.add(l1, BorderLayout.NORTH);
		myTableModel = new DefaultTableModel(null, new String[] {"Plan ID", "Trainer ID",  
				"Calories", "Protein", "Carbs", "Fat", "Water"});
		myTable = new JTable();
		myTable.setModel(myTableModel);
		createTable1();
		myTable.removeColumn(myTable.getColumn("Plan ID"));
		myTable.removeColumn(myTable.getColumn("Trainer ID"));
		panel1.add(myTable.getTableHeader(), BorderLayout.CENTER);
		panel1.add(myTable, BorderLayout.SOUTH);
		pane.add(panel1, BorderLayout.NORTH);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		JLabel l2 = new JLabel(myClientUsername + "'s Daily Totals: ");
		panel2.add(l2, BorderLayout.NORTH);
		myTableModel2 = new DefaultTableModel(null, new String[] {"Calories", 
				"Protein", "Carbs", "Fat", "Water"});
		myTable2 = new JTable();
		myTable2.setModel(myTableModel2);
		createTable2();
		panel2.add(myTable2.getTableHeader(), BorderLayout.CENTER);
		panel2.add(myTable2, BorderLayout.SOUTH);
		pane.add(panel2, BorderLayout.CENTER);
		
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton editPlan = new JButton("Edit Plan");
		editPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				new EditPlanWindow(myTrainerID, myClientUsername, myWindow);
			}
		});
		panel3.add(editPlan);
		JButton close = new JButton("Close");
		panel3.add(close);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				myFrame.dispose();
			}
		});
		pane.add(panel3, BorderLayout.SOUTH);
		
	}
	
	public void createTable1() {
		Plan p = DatabaseAccess.getUserPlan(myClientUsername);
		myTableModel.setRowCount(0);
		if (p != null) {
			myTableModel.addRow(new Object[] {p.id, p.trainer_id, p.calories, 
					p.protein,p.carbs, p.fat, p.water});
		}
	}
	
	public void createTable2() {
		DietDiary d = DatabaseAccess.viewDailyPortions(myClientUsername);
		WaterDiary w = DatabaseAccess.viewDailyWater(myClientUsername);
		myTableModel2.setRowCount(0);
		if (d != null && w != null) {
			myTableModel2.addRow(new Object[] {d.calories, 
					d.protein,d.carbs, d.fat, w.quantity});
		}
	}
}
