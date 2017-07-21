import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class AddClientWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private String myUsername;
	private JTable myTable;
	private DefaultTableModel myTableModel;
	private JButton myAddClient;
	@SuppressWarnings("rawtypes")
	private JComboBox myAllClientsList;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel myModel;
	private Timer myTimer;
	private TrainerWindow myWindow;
	
	public AddClientWindow(String theTrainer, TrainerWindow theWindow) {
		myWindow = theWindow;
		myFrame = new JFrame();
		createPanels();
		createTimer();
		myTimer.start();
		myFrame.setTitle(theTrainer + " - Add Client");
		myUsername = theTrainer;
		myFrame.setSize(500, 139);
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
	        	createTable();
	        }
	    });
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createPanels() {
		Container pane = myFrame.getContentPane();
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel();
		l1.setText("Users without trainers: ");
		panel1.add(l1);
		myModel = new DefaultComboBoxModel(DatabaseAccess.getAllUsersWithoutTrainer());
		myAllClientsList = new JComboBox();
		myAllClientsList.setModel(myModel);
		panel1.add(myAllClientsList);
		pane.add(panel1, BorderLayout.NORTH);
		JScrollPane sp = new JScrollPane();
		pane.add(sp, BorderLayout.CENTER);
		sp.setPreferredSize(new Dimension(350, 50));
		myTableModel = new DefaultTableModel(null, new String[] {"ID", "First Name", 
				"Last Name", "Username", "Trainer ID"});
		myTable = new JTable();
		sp.setViewportView(myTable);
		myTable.setModel(myTableModel);
		myTable.removeColumn(myTable.getColumn("ID"));
		myTable.removeColumn(myTable.getColumn("Trainer ID"));
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		myAddClient = new JButton("Add");
		myAddClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				if (myAllClientsList.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null, "No user selected.", 
							"Failed to add client", JOptionPane.INFORMATION_MESSAGE);
				} else {
					User u = (User) myAllClientsList.getSelectedItem();
					DatabaseAccess.addTrainer(u.id, DatabaseAccess.getMyTrainerID(myUsername));
					myWindow.updateList();
					myWindow.createTable();
					JOptionPane.showMessageDialog(null, "Client added.", "", 
							JOptionPane.INFORMATION_MESSAGE);
					myFrame.dispose();
				}
			}			
		});
		panel2.add(myAddClient);
		pane.add(panel2, BorderLayout.SOUTH);
		createTable();
	}
	
	public void createTable() {
		User u = (User) myAllClientsList.getSelectedItem();
		myTableModel.setRowCount(0);
		if (u != null) {
			myTableModel.addRow(new Object[] {u.id, u.fname, u.lname, u.username, 
					u.trainer_id});
		}
	}
}
