package sqltestproject;

public class SQLTestProject {


   static SQLGui     gui  = null;
    
    //Main Method Creates the GUI
    public static void main(String[] args) {
        
        createGui();

    }
    
    //GUI Creation Method
    static private void createGui(){
    
        gui = new SQLGui();
    
    }
    
}
