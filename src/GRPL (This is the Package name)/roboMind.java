/**
 * Graphical-based Robotics Programming Language
 * ( roboMind class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * LICENSE: GNU General Public License v3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */
package GRPL;

import java.awt.Component;
import java.io.*;
import javax.swing.JOptionPane;

public class roboMind {
    private String Conversion[][];  // The conversion of the Workspace's commands into irobo
    private String Analysis[];      // The analysis of the irobo syntax
    private Component parent;
    private ErrMsgClassifier ErrMsg;
    
    /**
     * Constructor of the class
     * @param prt The pointer to the Controller, used with ErrMsgClassifier class
     */
    public roboMind(Component prt){
        Conversion = new String[10][7];
        Analysis = new String[10];
        parent = prt;       
        ErrMsg = new ErrMsgClassifier(parent);
    }
    
    // ============================================================================    
    /**
     * Exporting to the roboMind Script file for the simulator:
     * @param filename The name of the exported script file
     * @param Commands[][] The commands in the Workspace
     * @return NONE
     */
    void doExport(String filename, String Commands[][])
    {
        // The conversion/compatibility phase:
        doConversion(Commands);
        
        // The analysis phase:
        boolean retval = doAnalysis();
        if( !retval)
            return;
        
        // The saving phase:
        try
        {
            PrintWriter p = new PrintWriter( new File(filename),"UTF-16BE" );
            p.println("# The GRPL Tool / RoboMind export file ...\n");
            
            // Collecting the data to be saved from the Commands array:
            for(int row=0 ; row<10 ; row++){
                p.println(Analysis[row]);
            }
            p.println("\n# End of file ...");
            p.close();
            JOptionPane.showMessageDialog(parent, "The file: "+filename+
                                          "\nis successfully exported ...", "DONE ...", 
                                          JOptionPane.INFORMATION_MESSAGE);
            System.out.println("INFO: The file: "+filename+" is successfully exported ...");
        }
        catch (FileNotFoundException | UnsupportedEncodingException e) 
        {
            String str = "Can't write to the file:\n"+filename+"\nThe exporting will be ignored ...";            
            ErrMsg.print(0,str,-1,-1);
        }
    }
// ============================================================================    
    /**
     * Conversion between the GRPL Tool and the roboMind Script file
     * @param Commands[][] The commands in the Workspace
     * @return NONE
     */
    private void doConversion(String Commands[][])
    {        
        for(int row=0 ; row<10 ; row++){
            for(int col=0 ; col<7 ; col++)
            {       
                // Empty:
                if( Commands[row][col].length() < 1 )
                    Conversion[row][col] = "";
                // Directions:
                else if( Commands[row][col].compareTo("FORWARD") == 0 )
                    Conversion[row][col] = " forward ";
                else if( Commands[row][col].compareTo("BACKWARD") == 0 )
                    Conversion[row][col] = " backward ";
                else if( Commands[row][col].compareTo("RIGHT") == 0 )
                    Conversion[row][col] = " right ";
                else if( Commands[row][col].compareTo("LEFT") == 0 )
                    Conversion[row][col] = " left ";
                // Numbers:
                else if( Character.isDigit( Commands[row][col].charAt(0) ) )                    
                    Conversion[row][col] = " ("+Commands[row][col]+") ";
                else if( Commands[row][col].compareTo("INFINITY") == 0 )
                    Conversion[row][col] = "";
                // Actuators:
                else if( Commands[row][col].compareTo("PICK") == 0 )
                    Conversion[row][col] = " pickUp ";
                else if( Commands[row][col].compareTo("DROP") == 0 )
                    Conversion[row][col] = " putDown ";
                // Sensors:
                else if( Commands[row][col].compareTo("SEE") == 0 )
                    Conversion[row][col] = " see ";
                else if( Commands[row][col].compareTo("TOUCH") == 0 )
                    Conversion[row][col] = " touch ";
                // Flow Controls:
                else if( Commands[row][col].compareTo("LOOP") == 0 )
                    Conversion[row][col] = "repeat ";
                else if( Commands[row][col].compareTo("BREAK") == 0 )
                    Conversion[row][col] = " break ";
                else if( Commands[row][col].compareTo("CHECK") == 0 )
                    Conversion[row][col] = " if ";
                else if( Commands[row][col].compareTo("STOP") == 0 )
                    Conversion[row][col] = "}";
                // Functions:
                else if( Commands[row][col].compareTo("CALL") == 0 )
                    Conversion[row][col] = " call ";
                else if( Commands[row][col].compareTo("MOD1") == 0 )
                    Conversion[row][col] = " module1 ";
                else if( Commands[row][col].compareTo("MOD2") == 0 )
                    Conversion[row][col] = " module2 ";
                // Math/Logic:
                else if( Commands[row][col].compareTo("FALSE") == 0 )
                    Conversion[row][col] = " ~ ";
                else if( Commands[row][col].compareTo("TRUE") == 0 )
                    Conversion[row][col] = "";
                else if( Commands[row][col].compareTo("=")  == 0 ||
                         Commands[row][col].compareTo(">=") == 0 || 
                         Commands[row][col].compareTo("<=") == 0 )
                    Conversion[row][col] = "";
                else if( Commands[row][col].compareTo("!=") == 0 ||
                         Commands[row][col].compareTo(">")  == 0 || 
                         Commands[row][col].compareTo("<")  == 0 )
                    Conversion[row][col] = " =/= ";
                // Colors:
                else if( Commands[row][col].compareTo("BLACK") == 0 )
                    Conversion[row][col] = "black";// Black Line
                else if( Commands[row][col].compareTo("WHITE") == 0 )
                    Conversion[row][col] = "white";// White Line
                else if( Commands[row][col].compareTo("RED") == 0 )
                    Conversion[row][col] = "red";  // Obstacle
                else if( Commands[row][col].compareTo("GREEN") == 0 )
                    Conversion[row][col] = "green";// Beacon
                else if( Commands[row][col].compareTo("BLUE") == 0 )
                    Conversion[row][col] = "blue"; // Extra color
                else if( Commands[row][col].compareTo("YELLOW") == 0 )
                    Conversion[row][col] = "yellow";// Extra color
                else
                    Conversion[row][0] = "# " + Conversion[row][0];
            }
        }
    }
// ============================================================================    
    /**
     * Analysis between the GRPL Tool and the roboMind Script file
     * @param NONE
     * @return boolean The status of the analysis, succuss or failure
     */
    private boolean doAnalysis()
    {
        int row, col;
        String str;
        
        // Merging process:
        for(row=0 ; row<10 ; row++)
        {
            Analysis[row] = "";
            for(col=0 ; col<7 ; col++)
            {
                Analysis[row] = Analysis[row] + Conversion[row][col];
            }
        }
        
        // Analysis process:
        for(row=0 ; row<10 ; row++)
        {
            // Empty line or comment line:
            if( Analysis[row].length() < 1 || Analysis[row].matches("(.*)#(.*)") )
                continue;
            // Directions:
            else if( Analysis[row].matches("(.*)forward(.*)") && 
                    !Analysis[row].matches("(.*)\\d(.*)") )
                    {
                        str = "Problem in exporting the FORWARD command,\n does not contain NUMBER." ;
                        ErrMsg.print(0, str, -1, -1);
                        return false;
                    }
            else if( Analysis[row].matches("(.*)backward(.*)") && 
                    !Analysis[row].matches("(.*)\\d(.*)") )
                    {
                        str = "Problem in exporting the BACKWARD command,\n does not contain NUMBER." ;
                        ErrMsg.print(0, str, -1, -1);
                        return false;
                    }
            else if( Analysis[row].matches("(.*)right(.*)") && 
                     Analysis[row].matches("(.*)\\d(.*)") )
                    {
                        str = "Problem in exporting the RIGHT command,\n contains NUMBER." ;
                        ErrMsg.print(0, str, -1, -1);
                        return false;
                    }
            else if( Analysis[row].matches("(.*)left(.*)") && 
                     Analysis[row].matches("(.*)\\d(.*)") )
                    {
                        str = "Problem in exporting the LEFT command,\n contains NUMBER." ;
                        ErrMsg.print(0, str, -1, -1);
                        return false;
                    }
            // Actuators: OK
            // Sensors / If statements:
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)see(.*)") && 
                    (Analysis[row].matches("(.*)red(.*)") || Analysis[row].matches("(.*)green(.*)") ||
                     Analysis[row].matches("(.*)yellow(.*)") || Analysis[row].matches("(.*)blue(.*)")) )
                    {
                        str = "Problem in exporting the SEE command,\n"+
                              "it can't handle touchable objects.\n"+
                              "They should be colored lines: BLACK or WHITE" ;
                        ErrMsg.print(0, str, -1, -1);
                        return false;
                    }
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)touch(.*)") && 
                    (Analysis[row].matches("(.*)black(.*)") || Analysis[row].matches("(.*)white(.*)") ||
                     Analysis[row].matches("(.*)yellow(.*)") || Analysis[row].matches("(.*)blue(.*)")) )
                    {
                        str = "Problem in exporting the TOUCH command,\n"+
                              "it can't handle colored lines.\n"+
                              "They should be obstacles: RED or beacons: GREEN" ;
                        ErrMsg.print(0, str, -1, -1);
                        return false;
                    }
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)see(.*)") && 
                     Analysis[row].matches("(.*)black(.*)") && Analysis[row].matches("(.*)=/=(.*)") )
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( frontIsBlack ){ ";
                        else
                            Analysis[row] = " if( ~frontIsBlack ){ ";
                    }
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)see(.*)") && 
                     Analysis[row].matches("(.*)black(.*)"))
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( ~frontIsBlack ){ ";
                        else
                            Analysis[row] = " if( frontIsBlack ){ ";
                    }
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)see(.*)") && 
                     Analysis[row].matches("(.*)white(.*)") && Analysis[row].matches("(.*)=/=(.*)") )
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( frontIsWhite ){ ";
                        else
                            Analysis[row] = " if( ~frontIsWhite ){ ";
                    }            
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)see(.*)") && 
                     Analysis[row].matches("(.*)white(.*)") )
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( ~frontIsWhite ){ ";
                        else
                            Analysis[row] = " if( frontIsWhite ){ ";
                    }            
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)touch(.*)") && 
                     Analysis[row].matches("(.*)red(.*)") && Analysis[row].matches("(.*)=/=(.*)") )
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( frontIsObstacle ){ ";
                        else
                            Analysis[row] = " if( ~frontIsObstacle ){ ";
                    }
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)touch(.*)") && 
                     Analysis[row].matches("(.*)red(.*)"))
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( ~frontIsObstacle ){ ";
                        else
                            Analysis[row] = " if( frontIsObstacle ){ ";
                    }
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)touch(.*)") && 
                     Analysis[row].matches("(.*)green(.*)") && Analysis[row].matches("(.*)=/=(.*)") )
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( frontIsBeacon ){ ";
                        else
                            Analysis[row] = " if( ~frontIsBeacon ){ ";
                    }
            else if( Analysis[row].matches("(.*)if(.*)") && Analysis[row].matches("(.*)touch(.*)") && 
                     Analysis[row].matches("(.*)green(.*)"))
                    { 
                        if( Analysis[row].matches("(.*)~(.*)") )
                            Analysis[row] = " if( ~frontIsBeacon ){ ";
                        else
                            Analysis[row] = " if( frontIsBeacon ){ ";
                    }            
            // Loop:
            else if( Analysis[row].matches("(.*)repeat(.*)") )
                     Analysis[row] = Analysis[row] + "{";
            // Functions:
            else if( Analysis[row].matches("(.*)module1(.*)") )
                    {
                    if( Analysis[row].matches("(.*)call(.*)") )
                        Analysis[row] = " module1() ";
                    else
                        Analysis[row] = "procedure module1(){ ";
                    }
            else if( Analysis[row].matches("(.*)module2(.*)") )
                    {
                    if( Analysis[row].matches("(.*)call(.*)") )
                        Analysis[row] = " module2() ";
                    else
                        Analysis[row] = "procedure module2(){ ";
                    }
        }
        
        return true;
    }
}
// ============================================================================    