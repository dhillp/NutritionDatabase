import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class WaterDiaryWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private String myUsername;
	private JTable myTable;
	private DefaultTableModel myTableModel;
	private Timer myTimer;
	private JButton myDelete;
	private JButton myUpdate;
	
	public WaterDiaryWindow(String theUsername) {
		myFrame = new JFrame();
		myFrame.setTitle(theUsername + " - Water Diary");
		myUsername = theUsername;
		createPanels();
		createTimer();
		myTimer.start();
		myFrame.setSize(450, 350);
		myFrame.setResizable(false);
		myFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent theEvent) {
				myTimer.stop();
			}
		});
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.setVisible(true);
	}
	
	private void createTimer() {
		myTimer = new Timer(100,new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent theEvent) {
	            int t = myTable.getSelectedRow();
	            if (t > -1) {
	            	myUpdate.setEnabled(true);
	            	myDelete.setEnabled(true);
	            } else {
	            	myUpdate.setEnabled(false);
	            	myDelete.setEnabled(false);
	            }
	        }
	    });
	}
	
	private void createPanels() {
		Container pane = myFrame.getContentPane();
		JScrollPane sp = new JScrollPane();
		pane.add(sp, BorderLayout.CENTER);
		sp.setPreferredSize(new Dimension(350, 50));

		myTableModel = new DefaultTableModel(null, new String[] {"ID", "Date",  
				"Quantity"});
		myTable = new JTable();
		sp.setViewportView(myTable);
		myTable.setModel(myTableModel);
		createTable();
		myTable.removeColumn(myTable.getColumn("ID"));
		pane.add(sp, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		SpinnerNumberModel nmQ = new SpinnerNumberModel(
				new Integer(0),
				new Integer(0),
				new Integer(50),
				new Integer(1)
				);
		JSpinner q1 = new JSpinner(nmQ);
		JLabel l2 = new JLabel("Quantity:");
		panel1.add(l2);
		panel1.add(q1);
		panel1.setLayout(new FlowLayout(FlowLayout.LEADING));
		JButton add = new JButton("Add Entry");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				Integer theQ = (Integer) q1.getValue();
				DatabaseAccess.addWaterDiaryEntry(myUsername, theQ.shortValue());
				createTable();
			}
		});
		panel1.add(add);
		pane.add(panel1, BorderLayout.NORTH);
		
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		SpinnerNumberModel nmQuantity = new SpinnerNumberModel(
				new Integer(0),
				new Integer(0),
				new Integer(50),
				new Integer(1)
				);
		JSpinner quantity = new JSpinner(nmQuantity);
		JLabel l3 = new JLabel("Update quantity:");
		panel2.add(l3);
		panel2.add(quantity);
		myUpdate = new JButton("Update");
		myUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				Integer theQuantity = (Integer) quantity.getValue();
				int row = myTable.getSelectedRow();
				int theID = (Integer) myTableModel.getValueAt(row, 0);
				DatabaseAccess.editWaterDiaryEntry(theID, theQuantity.shortValue());
				createTable();
			}
		});
		panel2.add(myUpdate);
		myDelete = new JButton("Delete Entry");
		myDelete.setEnabled(false);
		myDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				int row = myTable.getSelectedRow();
				int theID = (Integer) myTableModel.getValueAt(row, 0);
				DatabaseAccess.deleteWaterDiaryEntry(theID);
				createTable();
			}
		});
		panel2.add(myDelete);
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				myTimer.stop();
				myFrame.dispose();
			}
		});
		panel2.add(close);
		pane.add(panel2, BorderLayout.SOUTH);
		
	}
	
	public void createTable() {
		WaterDiary[] waterTable = DatabaseAccess.viewMyWaterDiary(myUsername);
		myTableModel.setRowCount(0);
		if (waterTable != null) {
			for (int i = 0; i < waterTable.length; i++) {
				WaterDiary w = waterTable[i];
				myTableModel.addRow(new Object[] {w.id, w.entry_date, w.quantity});
			}
		}
	}
}
