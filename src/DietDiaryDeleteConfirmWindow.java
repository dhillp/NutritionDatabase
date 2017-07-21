import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DietDiaryDeleteConfirmWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();	
	private int myDiaryID;	
	private JFrame myFrame;	
	private JTable myTable;	
	private DefaultTableModel myTableModel;	
	private UserWindow myWindow;
	
	public DietDiaryDeleteConfirmWindow(int theDiaryID, UserWindow theWindow) {
		myWindow = theWindow;
		myDiaryID = theDiaryID;
		myFrame = new JFrame();
		myFrame.setTitle("Confirm Entry Deletion");
		createPanel();
		myFrame.setSize(750, 130);
		myFrame.setResizable(false);
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.setVisible(true);
	}
	
	private void createPanel() {
		Container pane = myFrame.getContentPane();
		JPanel p1 = new JPanel();
		JLabel l1 = new JLabel("Are you sure you want to delete this entry?");
		p1.add(l1);
		pane.add(p1, BorderLayout.NORTH);
		
		JScrollPane sp = new JScrollPane();
		pane.add(sp, BorderLayout.CENTER);
		sp.setPreferredSize(new Dimension(250, 50));
		myTableModel = new DefaultTableModel(null, new String[] {"Date", "Food", 
				"Calories", "Protein", "Carbs", "Fat", "Quantity"});
		myTable = new JTable();
		sp.setViewportView(myTable);
		myTable.setModel(myTableModel);
		DietDiary d = DatabaseAccess.getDietDiaryEntry(myDiaryID);
		myTableModel.setRowCount(0);
		if (d != null) {
			myTableModel.addRow(new Object[] {d.entry_date, d.food, d.calories, 
					d.protein, d.carbs, d.fat, d.quantity});
		}
		
		JPanel p2 = new JPanel();
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				DatabaseAccess.deleteDietDiaryEntry(myDiaryID);
				myWindow.createTable();
				myFrame.dispose();
			}	
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				myFrame.dispose();
			}	
		});
		p2.add(delete);
		p2.add(cancel);
		pane.add(p2, BorderLayout.SOUTH);
	}
}
