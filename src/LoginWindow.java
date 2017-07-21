import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JRadioButton;

public final class LoginWindow {
	
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();	
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();	
    private JButton myLoginButton;
    private JButton myRegisterButton;    
    private JRadioButton myUserButton;    
    private JRadioButton myTrainerButton;    
    private final ButtonGroup myButtonGroup;    
    private JTextField myUsername;    
    private JPasswordField myPassword;
    private JFrame myFrame;
	private Timer myTimer;
    
    public LoginWindow() {
		myFrame = new JFrame();
		myButtonGroup = new ButtonGroup();
		myFrame.setTitle("Nutrition Database - Login");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createPanel();
		createTimer();
		myTimer.start();
		myFrame.setSize(350, 175);
		myFrame.setResizable(false);
		myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
		myFrame.setVisible(true);
    }
    
    private void createPanel() {
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints constraint = new GridBagConstraints();
    	constraint.fill = GridBagConstraints.HORIZONTAL;
    	myUsername = new JTextField(20);
    	myPassword = new JPasswordField(20);
    	JLabel user = new JLabel("Username: ");
    	JLabel pass = new JLabel("Password: ");
    	constraint.gridx = 0;
    	constraint.gridy = 0;
    	constraint.gridwidth = 1;
    	panel.add(user, constraint);
    	constraint.gridx = 1;
    	constraint.gridy = 0;
    	constraint.gridwidth = 2;
    	panel.add(myUsername, constraint);
    	constraint.gridx = 0;
    	constraint.gridy = 1;
    	constraint.gridwidth = 1;
    	panel.add(pass, constraint);
    	constraint.gridx = 1;
    	constraint.gridy = 1;
    	constraint.gridwidth = 2;
    	panel.add(myPassword, constraint);
    	createButtons();
    	JPanel panel2 = new JPanel();
    	panel2.add(myLoginButton);
    	panel2.add(myRegisterButton);
    	myUserButton = new JRadioButton("User");
    	myTrainerButton = new JRadioButton("Trainer");
    	myButtonGroup.add(myUserButton);
    	myButtonGroup.add(myTrainerButton);
    	myUserButton.setSelected(true);
    	JPanel panel3 = new JPanel();
    	panel3.add(myUserButton);
    	panel3.add(myTrainerButton);
    	myFrame.getContentPane().add(panel3, BorderLayout.NORTH);
    	myFrame.getContentPane().add(panel, BorderLayout.CENTER);
    	myFrame.getContentPane().add(panel2, BorderLayout.SOUTH);
    }
    
    private void createButtons() {
    	myLoginButton = new JButton("Login");
    	myLoginButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent theEvent) {
				if (authenticate()) {
					if (myTrainerButton.isSelected()) {
						new TrainerWindow(getUsername());
						myFrame.dispose();
					} else {
						new UserWindow(getUsername());
						myFrame.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect username or password.", 
							"", JOptionPane.INFORMATION_MESSAGE);
				}
    		}
    	});
    	myRegisterButton = new JButton("Register");
    	myRegisterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
					new RegisterWindow();
			}
    	});
    }
    
    private void createTimer() {
		myTimer = new Timer(100,new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent theEvent) {
	        	if (myUserButton.isSelected()) {
	        		myRegisterButton.setEnabled(true);
	        	} else if (myTrainerButton.isSelected()) {
	        		myRegisterButton.setEnabled(false);
	        	}
	        }
	    });
	}
    
    private String getUsername() {
    	return myUsername.getText().trim();
    }
    
    private String getPassword() {
    	return new String(myPassword.getPassword());
    }
    
    private boolean authenticate() {
    	if (myUserButton.isSelected()) {
	    	User users[] = DatabaseAccess.getAllUsers();
	    	for (int i = 0; i < users.length; i++) {
	    		if (getUsername().equals(users[i].username) 
	    				&& getPassword().equals(users[i].password)) {
	    			return true;
	    		}
	    	}
    	} else if (myTrainerButton.isSelected()) {
    		Trainer trainers[] = DatabaseAccess.getTrainers();
    		for (int j = 0; j < trainers.length; j++) {
    			if (getUsername().equals(trainers[j].username) 
    					&& getPassword().equals(trainers[j].password)
    					&& !getUsername().equals("no1")) {
    				return true;
    			}
    		}
    	}
    	return false;
    }

}
