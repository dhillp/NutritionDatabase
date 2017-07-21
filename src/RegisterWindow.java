import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class RegisterWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	private JTextField myFirstName;
	private JTextField myLastName;
	private JLabel myUsernameWarning;
	private JLabel myPasswordWarning;	
    private JButton myRegisterButton;    
    private JTextField myUsername;    
    private JPasswordField myPassword;    
    private JPasswordField myConfirmPassword;
    private JFrame myFrame;    
	private Timer myTimer;
	
	public RegisterWindow() {
		myFrame = new JFrame();
		myFrame.setTitle("Nutrition Database - Create Account");
		myFrame.setSize(450, 250);
		myFrame.setResizable(false);
		createPanel();
		createTimer();
		myTimer.start();
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
	
	private void createPanel() {
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints constraint = new GridBagConstraints();
    	constraint.fill = GridBagConstraints.HORIZONTAL;
    	myUsername = new JTextField(20);
    	myPassword = new JPasswordField(20);
    	myFirstName = new JTextField(20);
    	myLastName = new JTextField(20);
    	myConfirmPassword = new JPasswordField(20);
    	JLabel fname = new JLabel("Enter First Name: ");
    	JLabel lname = new JLabel("Enter Last Name: ");
    	JLabel user = new JLabel("Enter Username: ");
    	JLabel pass = new JLabel("Enter Password: ");
    	JLabel pass2 = new JLabel("Confirm Password: ");
    	myUsernameWarning = new JLabel("");
    	myUsernameWarning.setForeground(Color.RED);
    	myUsernameWarning.setHorizontalAlignment(SwingConstants.CENTER);
    	myPasswordWarning = new JLabel("");
    	myPasswordWarning.setForeground(Color.RED);
    	myPasswordWarning.setHorizontalAlignment(SwingConstants.CENTER);
    	
    	constraint.gridx = 0;
    	constraint.gridy = 0;
    	constraint.gridwidth = 1;
    	panel.add(fname, constraint);
    	constraint.gridx = 1;
    	constraint.gridy = 0;
    	constraint.gridwidth = 2;
    	panel.add(myFirstName, constraint);
    	
    	constraint.gridx = 0;
    	constraint.gridy = 1;
    	constraint.gridwidth = 1;
    	panel.add(lname, constraint);
    	constraint.gridx = 1;
    	constraint.gridy = 1;
    	constraint.gridwidth = 2;
    	panel.add(myLastName, constraint);
    	
    	constraint.gridx = 0;
    	constraint.gridy = 2;
    	constraint.gridwidth = 1;
    	panel.add(user, constraint);
    	constraint.gridx = 1;
    	constraint.gridy = 2;
    	constraint.gridwidth = 2;
    	panel.add(myUsername, constraint);
    	
    	constraint.gridx = 0;
    	constraint.gridy = 3;
    	constraint.gridwidth = 3;
    	panel.add(myUsernameWarning, constraint);
    	
    	constraint.gridx = 0;
    	constraint.gridy = 4;
    	constraint.gridwidth = 1;
    	panel.add(pass, constraint);
    	constraint.gridx = 1;
    	constraint.gridy = 4;
    	constraint.gridwidth = 2;
    	panel.add(myPassword, constraint);
    	constraint.gridx = 0;
    	constraint.gridy = 5;
    	constraint.gridwidth = 1;
    	panel.add(pass2, constraint);
    	constraint.gridx = 1;
    	constraint.gridy = 5;
    	constraint.gridwidth = 2;
    	panel.add(myConfirmPassword, constraint);
    	
    	constraint.gridx = 0;
    	constraint.gridy = 6;
    	constraint.gridwidth = 3;
    	panel.add(myPasswordWarning, constraint);
    	
    	createButtons();
    	JPanel panel2 = new JPanel();
    	panel2.add(myRegisterButton);
    	JPanel panel3 = new JPanel();
    	myFrame.getContentPane().add(panel3, BorderLayout.NORTH);
    	myFrame.getContentPane().add(panel, BorderLayout.CENTER);
    	myFrame.getContentPane().add(panel2, BorderLayout.SOUTH);
    }
	
    private void createButtons() {
    	myRegisterButton = new JButton("Register");
    	myRegisterButton.setEnabled(false);
    	myRegisterButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent theEvent) {
				DatabaseAccess.newUser(getFirstName(), getLastName(), getUsername(), 
						getPassword());
				myTimer.stop();
				myFrame.dispose();
    		}
    	});
    }
    
    private void createTimer() {
		myTimer = new Timer(100,new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent theEvent) {
	        	//myFrame.repaint();
	        	if (userNameTaken()) {
	        		myUsernameWarning.setText("Username already in use. Choose another.");
	        	} else {
	        		myUsernameWarning.setText("");
	        	}
	        	if (!(Arrays.equals(myPassword.getPassword(), 
	        			myConfirmPassword.getPassword()))) {
	        		myPasswordWarning.setText("Passwords do not match.");
	        	} else {
	        		myPasswordWarning.setText("");
	        	}
	        	if (myUsername.getText().length() > 20 || myUsername.getText().length() < 4) {
	        		myUsernameWarning.setText("Username must be between 4 and 20 characters.");
	        	}
	        	if (myPassword.getPassword().length > 20 
	        			|| myPassword.getPassword().length < 8) {
	        		myPasswordWarning.setText("Password must be between 8 and 20 characters.");
	        	}
	        	if (!(myUsername.getText().length() < 4) 
	        			&& !(myPassword.getPassword().length < 8) 
	        			&& myUsername.getText().length() <= 20 
	        			&& myPassword.getPassword().length <= 20
	        			&& myFirstName.getText().length() > 0
	        			&& myLastName.getText().length() > 0
	        			&& myFirstName.getText().length() <= 20
	        			&& myLastName.getText().length() <= 20
	        			&& Arrays.equals(myPassword.getPassword(), 
	        					myConfirmPassword.getPassword())) {
	        		myRegisterButton.setEnabled(true);
	        	} else {
	        		myRegisterButton.setEnabled(false);
	        	}
	        }
	    });
	}
    
    private String getFirstName() {
    	return myFirstName.getText().trim();
    }
    
    private String getLastName() {
    	return myLastName.getText().trim();
    }
    
    private String getUsername() {
    	return myUsername.getText().trim();
    }
    
    private String getPassword() {
    	return new String(myPassword.getPassword());
    }
    
    private boolean userNameTaken() {
    	User users[] = DatabaseAccess.getAllUsers();
    	for (int i = 0; i < users.length; i++) {
    		if (getUsername().equals(users[i].username)) {
    			return true;
    		}
    	}
    	return false;
    }
}
