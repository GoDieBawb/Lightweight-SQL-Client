package sqltestproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
/**
 *
 * @author Bawb
 */
public class SQLGui extends JFrame implements ActionListener {
    
    //Create and Panel and SQL Method Utility
    private JPanel      panel;
    private SQLUtil     util;
    
    //Components for the login GUI
    private JTextField userField;
    private JTextField passwordField;
    private JTextField ipField;
    private JTextField dbNameField;
    private JButton    logButton;
    
    //Components For the SQL Gui
    private JTextField  queryText;
    public  JEditorPane outputText;
    private JButton     queryButton;
    private JScrollPane scroll;
    
    //Constructor method shows UI aftercreating the SQL Method Utility and Components
    public SQLGui() {

        util = new SQLUtil(this);
        createQueryComponents();
        createLoginComponents();
        createPanel();
        this.add(panel);
        setTitle("SQL GUI");
        setSize(400,125);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    
    ///Creates the Panel that will hold both the login and util screen
    private void createPanel() {

        panel    = new JPanel(new BorderLayout());
        panel.setBackground(Color.yellow);
        showLoginScreen();

    }
    
    //Login screen will show on start and on logout
    public void showLoginScreen(){
    
        panel.removeAll();
        panel.add(ipField,       BorderLayout.PAGE_START);
        panel.add(dbNameField,   BorderLayout.LINE_END);
        panel.add(userField,     BorderLayout.LINE_START);
        panel.add(passwordField, BorderLayout.CENTER);
        panel.add(logButton,     BorderLayout.PAGE_END);
        
    }
    
    //If login is successful the util will show the utility screen!
    public void showUtilScreen() {
    
        panel.removeAll();
        panel.add(queryButton, BorderLayout.PAGE_END);
        panel.add(scroll,      BorderLayout.CENTER);
        panel.add(queryText,   BorderLayout.PAGE_START);
        setSize(400,200);
        panel.updateUI();
        
    }
    
    //Creates the components for the login screen
    private void createLoginComponents(){
    
        userField     = new JTextField("Enter Username");
        passwordField = new JTextField("Enter Password");
        ipField       = new JTextField("Enter IP");
        dbNameField   = new JTextField("Enter Database Name");
        logButton     = new JButton("Connect");
        logButton.addActionListener(this);
        
        
    }
    
    //Creates the components for the query tool
    private void createQueryComponents() {

        queryText   = new JTextField();
        queryButton = new JButton("Check");   
        outputText  = new JEditorPane();
        scroll      = new JScrollPane(outputText);    
        outputText.setEditable(false);
        queryButton.addActionListener(this);

    }
    
    //Action Listener listens for the buttons Actions
    @Override
    public void actionPerformed(ActionEvent event) {
        
        //Checks for clicks on the Query button to query the database
        if (event.getSource() == queryButton) {

            util.queryDatabase(queryText.getText());
            queryText.setText("");
            
        }
        
        //Checks for clicks on the login button to connect to the database
        else if (event.getSource() == logButton) {
        
            util.login(userField.getText(), passwordField.getText(), ipField.getText(), dbNameField.getText());
        
        }
    
    }
    
}
