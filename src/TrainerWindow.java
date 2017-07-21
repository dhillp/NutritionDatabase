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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class TrainerWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JFrame myFrame;
	private String myUsername;
	private JTable myTable;
	private DefaultTableModel myTableModel;
	private JButton myViewPlan;
	private JButton myAddClient;
	@SuppressWarnings("rawtypes")
	private JComboBox myClients;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel myModel;
	private Timer myTimer;
	private TrainerWindow myWindow;
	
	public TrainerWindow(String theUsername) {
		myWindow = this;
		myFrame = new JFrame();
		createTimer();
		myTimer.start();
		myFrame.setTitle(theUsername + " - Client Diaries");
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
	        	createTable();
	        	int t = myClients.getSelectedIndex();
	        	if (t > -1) {
	        		myViewPlan.setEnabled(true);
	        	} else {
	        		myViewPlan.setEnabled(false);
	        	}
	        }
	    });
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		myTable.removeColumn(myTable.getColumn("ID"));
		JPanel panel1 = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		pane.add(panel1, BorderLayout.EAST);
		myViewPlan = new JButton("View Plan");
		panel1.add(myViewPlan, constraints);
		constraints.gridy = 1;
		myViewPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				int myID = DatabaseAccess.getMyTrainerID(myUsername);
				User u = (User) myClients.getSelectedItem();
				new TrainerPlanWindow(u.username, myID);
			}
		});
		myAddClient = new JButton("Add Client");
		panel1.add(myAddClient, constraints);
		myAddClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				new AddClientWindow(myUsername, myWindow);
			}
		});
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel("Client: ");
		panel2.add(l1);
		myModel = new DefaultComboBoxModel(DatabaseAccess.getMyUsers(
				DatabaseAccess.getMyTrainerID(myUsername)));
		myClients = new JComboBox();
		myClients.setModel(myModel);
		panel2.add(myClients);
		pane.add(panel2, BorderLayout.NORTH);
		createTable();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateList() {
		myModel = new DefaultComboBoxModel(DatabaseAccess.getMyUsers(
				DatabaseAccess.getMyTrainerID(myUsername)));
		myClients.setModel(myModel);
	}
	
	public void createTable() {
		User u = (User) myClients.getSelectedItem();
		if (u != null) {
			DietDiary[] myDiaryTable = DatabaseAccess.getMyDietDiaries(u.username);
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
}
