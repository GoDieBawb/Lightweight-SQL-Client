package sqltestproject;

import  java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Bawb
 */
public class SQLUtil {
    
   //Create Driver Name 
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   //GUI For Updating the Outpit
   static SQLGui gui;
   //Sets the URL of the Database to be used by JDBC
   static String DB_URL;
   //Username and Password for the Database
   static String USER;
   static String PASS;
   
   //Connection and Statement for communicating
   static Connection conn = null;
   static Statement  stmt = null;
   
   //Line Count For Output
   private int outputCount = 1;
   
   public SQLUtil(SQLGui gui) {
       
       this.gui = gui;
       
   }
   
   public void login(String username, String pass, String ip, String dbName) {
   
       //Recieves the Strings From the GUI and Sets them to the Fields
       USER   = username;
       PASS   = pass;
       DB_URL = "jdbc:mysql://" + ip + "/" + dbName;
       
        //Try to Connect
        try {
            
            System.out.println("Attempting to Connect to Database");
            Class.forName(JDBC_DRIVER);
            
            conn         = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt         = conn.createStatement();
            gui.showUtilScreen();
            System.out.println("Connected!");
            
        }
        
        //Catch Exception For Connection Failure
        catch(SQLException | ClassNotFoundException conEx) {
        
            System.out.println("Connection Failed: " + conEx);
            
        }
       
   }
    
   public void queryDatabase(String sql){
    
        //Try to Connect
        try {
            
            Class.forName(JDBC_DRIVER);
            
            conn         = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt         = conn.createStatement();
            
        }
        
        //Catch Exception For Connection Failure
        catch(SQLException | ClassNotFoundException conEx) {
        
            gui.outputText.setText(System.getProperty("line.separator") + "Error: " + "You have lost connection to your database!");
            
        }
        
        //Try Statement as Query
        try {

            //Query the Database
            ResultSet rs = (stmt.executeQuery(sql));
            
            while (rs.next()) {

                //Create a list of strings to hold the info
                ArrayList<String> infoList = new ArrayList();
                //Get the list of columns relevent to the current Query
                int columns                  = getReleventColumnCount(rs);
                //Iteration Number
                int i                        = 1;
                
                //Iterate over all relevent columns
                while(i <= columns) {
                    
                    String info = rs.getMetaData().getColumnName(i) + ": " + rs.getObject(i);
                    infoList.add(info);
                    i++;
                    
                }
            
                //Print the information
                gui.outputText.setText(System.getProperty("line.separator") + outputCount + ": " + infoList.toString() + gui.outputText.getText());
                outputCount++;
                    
            }
                
        }        
        
        //Catches if there's Something wrong with the command
        catch (SQLException e) {
            
            //Try Statement as an Update
            updateDatabase(sql);
            
        }
        
        //Close the Connection to the Database
        finally {
        
            try {
                if (stmt != null)
                stmt.close();
            }
            
            catch (SQLException se) {
            
            }
            
            try {
                if (conn != null)
                stmt.close();
            }
            
            catch (SQLException se2) {
            
            }
            
        }
    
    }
    
    //Get Relevent Amount of Columns
    private int getReleventColumnCount(ResultSet rs) {
        
        int columnCount = 1;
        
        //Try to get an Object Out of a column Until Error
        try {
            
            while (true) {

                rs.getObject(columnCount);
                columnCount++;
                
            }
            
        }
        
        //Once errored the Column Index is out of bounds
        catch(SQLException e) {
            columnCount = columnCount - 1;
        }
        
        //Return the Column Count
        return columnCount;
        
    }

    //Update the Database
    private void updateDatabase(String sql) {
    
        //Try command as an Update Keep Track of the output count
        try {
            
            gui.outputText.setText(System.getProperty("line.separator") + outputCount + ": " + "Checking Priveledges For Update...");
            outputCount++;
            stmt.executeUpdate(sql);
            gui.outputText.setText(System.getProperty("line.separator") + outputCount + ": " + "Updated Database COMMAND: " + sql);
            outputCount++;
            
        }
        
        //Catch Failure
        catch (SQLException ex) {
           
            gui.outputText.setText(System.getProperty("line.separator") + "ERROR: " + "Query Failed: " + ex);
            outputCount++;
            
        }    
    
    }
    
}
