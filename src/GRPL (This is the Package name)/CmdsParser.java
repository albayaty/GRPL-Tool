/**
 * Graphical-based Robotics Programming Language
 * ( CmdsParser class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * LICENSE: GNU General Public License v3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */
package GRPL;

import java.awt.Component;
import java.io.*;
import javax.swing.*;
import javax.swing.border.Border;
//import java.util.Scanner;

public class CmdsParser
{
    private String[] cmds;       // The commands in the "instructions.guide" file    
    private String[][] Commands; // Array of Commands ( from the Workspace's Buttons )
    private String[][] Types;    // Array of Commands / Types
    private ImageIcon[][] Icons; // Array of Icons ( from the Workspace's Buttons )
    private ImageIcon emptyIcon;
    private InstrGuideParser parser;
    private String[] completeCmd;// The completed one command description
    private ErrMsgClassifier ErrMsg;
    private Component parent;
    private History history;
    private roboMind irobo;
    int numOfParameters;  // The return number of parameters used by the Command
    int ERRORnumOfParameters;

    /**
     * Constructor of the class
     * @param fileaddress The absolute address of the "instructions.guide" file
     * @param prt The pointer to the Controller, used with ErrMsgClassifier class
     */
    public CmdsParser(String fileaddress, Component prt)
    {
        parent = prt;
        parser = new InstrGuideParser(fileaddress, parent);
        ErrMsg = new ErrMsgClassifier(parent);
        history= new History(parent);
        cmds = parser.ParseInstrGuide();
        
        Commands = new String[10][7];
        Types = new String[10][7];
        Icons = new ImageIcon[10][7];
        //WE = new boolean[10][7];
        irobo = new roboMind(parent);
        
        emptyIcon = new ImageIcon(getClass().getResource("ICONS/Empty.jpg"));
        
        for( int i=0 ; i<10 ; i++ ){
            for( int j=0 ; j<7 ; j++ ){
                Commands[i][j]="";
                Types[i][j]="";
                Icons[i][j]=emptyIcon;
            }
        }
        numOfParameters = 0;
        ERRORnumOfParameters = 0;
    }
// ============================================================================
    /**
     * Parsing the command from the Controller / Workspace
     * @param command The command's name
     * @param type The command's type
     * @param icon The command's icon
     * @param x The x position of the command
     * @param col The y position of the command
     * @return NONE
     */
    void parseCmd(String command, String type, ImageIcon icon, int x, int col)
    {
        int y=0;
        numOfParameters = 0;
        ERRORnumOfParameters = 0;
        String foundCmd="NoneCMD";
        
        setData(command, type, icon, x, col);
        
        // Checking for the previous entered command from column 0:
        while( y != 7 )
        {
            if( !Commands[x][y].equals("") ){
                foundCmd = isCmd(Types[x][y]); 
                System.out.println("Operation ("+Commands[x][y]+") found @ ("+x+","+y+")");
                break;
            }
            y++;
        }

        // There is a previous command entered in the row as Linear Command:
        if( foundCmd.compareTo("LinearCMD") == 0 )
        {
            numOfParameters = 0;
            // Checking for the one-line commands only:
            if( isCmd(Types[x][col]).compareTo("NoneCMD") == 0 ||
                isCmd(Types[x][col]).compareTo("CompleteCMD") == 0 )
            {
                String str = String.format("The entered operation (%s) at location (%d,%d)\n"+
                                           "is not a linear command.\n"+
                                           "It will be cleared ...\n",
                                           command, x, col);                
                ErrMsg.print(0, str, -1, -1);
                setData("", "", emptyIcon, x, col);
            }

            // Checking for the next commands in the same line (recursive call):
            for(int column=col+1 ; column<7 ; column++){
                if( Types[x][column].compareTo("") != 0)
                    parseCmd(Commands[x][column], Types[x][column], Icons[x][column], x, column);
                return;
            }            
        }
        
        // There is a previous command entered in the row as Complete Command:
        else if( foundCmd.compareTo("CompleteCMD") == 0 )
        {
            int index0=1;
            // For a complete command with option and condition:
            while( completeCmd.length != index0 )
            {
                // Finding the next argument in this pre-command:
                String[] argCmd = completeCmd[index0].split(":");               

                // Skipping the empty spaces in Commands[][]/Workspace:
                if(y==6)    break;
                if(y<6)     y++;
                while( Commands[x][y].compareTo("") == 0 )
                {
                    if(y>=6){                        
                        System.out.println("Reaching the boundaries of Row: "+x);
                        return;      // Clear Nothing ...
                    }    
                    y++;                    
                }
                
                // Finding the right argument for the pre-command:
                int index1=0;
                boolean foundArg=false;
                while( argCmd.length != index1 )
                {
                    // Comparing the OPTION / CONDITION using cmdType instead of cmdName:
                    if( Types[x][y].compareTo(argCmd[index1].trim()) == 0 ){
                        System.out.println( argCmd[index1]+": arg @ ("+x+","+y+") is OK" );
                        numOfParameters = numOfParameters - index0 - 1;
                        System.out.println("111 numOfParameters: " + numOfParameters);
                        foundArg = true;  // The argument found
                        break;
                    }
                    index1++;
                }

                // Display Error if there is no arg or no more arg to process for this command
                if( !foundArg ){   // Display error msg ...
                    String str = String.format("The entered argument (%s) at location (%d,%d)\n"+
                                               "is either not found or extra for the command: %s.\n"+
                                               "It should be (%s).\n"+
                                               "The argument will be cleared ...\n",
                                               Commands[x][y], x, y, completeCmd[0], completeCmd[index0]);                    
                    ErrMsg.print(0, str, -1, -1);
                    setData("", "", emptyIcon, x, y);
                    ERRORnumOfParameters = -1;
                }
                index0++;
            }
            
            // Clearing the remaning of the line in the Workspace:
            for( int i=++y ; i<7 ; i++ )
                setData("", "", emptyIcon, x, i);
        }  
        
        // There is no previous command enteredin the row or None Command:        
        else
        {            
            String str = String.format("The entered operation (%s) at location (%d,%d)\n"+
                                       "is neither a command nor extra argument.\n"+
                                       "The whole line will be cleared ...\n",
                                       command, x, col);            
            ErrMsg.print(0, str, -1, -1);
            resetLine(x);
        }
        
        return;
    }
// ============================================================================    
    /**
     * Checking for the type of commands: COMMAND [OPTIONS] [CONDITIONS]
     * @param command The command's name
     * @return String The command's type
     */
    private String isCmd(String command)
    {
        int i=0;
        
        while( cmds[i].compareTo("???") != 0 )
        {
            // For a complete command with option and condition:
            String[] subCmd = cmds[i].split(">>");              
            
            if( command.compareTo(subCmd[0].trim()) == 0 )
            {
                System.out.println("{" + subCmd[0] + "}");
                completeCmd = subCmd;
                numOfParameters = subCmd.length-1;
                return "CompleteCMD";
            }
            
            // For one-line commands aggregations:
            String[] lineCmd = subCmd[0].split(":");            
            int j = 0;
            while( lineCmd.length > 1 && lineCmd.length > j )
            {                
                if( command.compareTo(lineCmd[j].trim()) == 0 )
                {
                    numOfParameters = 0;
                    return "LinearCMD";
                }
                j++;
            }
            //
            i++;
        }
        
        numOfParameters = 0;
        return "NoneCMD";
    }
// ============================================================================
    /**
     * Undo operation inside the Model
     * @param NONE
     * @return HistoryStack
     */
    HistoryStack undo()
    {
        HistoryStack stack = history.undo();
        //System.out.println("=====================UNDO===========================");
        for( int x=0 ; x<10 ; x++){
            for( int y=0 ; y<7 ; y++ )
            {
                Commands[ stack.getFromStack(x).getFromStructure(y).posX ]
                        [ stack.getFromStack(x).getFromStructure(y).posY ] =
                          stack.getFromStack(x).getFromStructure(y).command;  
                Types[ stack.getFromStack(x).getFromStructure(y).posX ]
                     [ stack.getFromStack(x).getFromStructure(y).posY ] =
                       stack.getFromStack(x).getFromStructure(y).type;
                Icons[ stack.getFromStack(x).getFromStructure(y).posX ]
                     [ stack.getFromStack(x).getFromStructure(y).posY ] =
                       stack.getFromStack(x).getFromStructure(y).icon;
            }
        }
        return stack;
    }
// ============================================================================
    /**
     * Redo operation inside the Model
     * @param NONE
     * @return HistoryStack
     */
    HistoryStack redo()
    {
        HistoryStack stack = history.redo();
        //System.out.println("=====================REDO===========================");
        for( int x=0 ; x<10 ; x++){
            for( int y=0 ; y<7 ; y++ )
            {
                Commands[ stack.getFromStack(x).getFromStructure(y).posX ]
                        [ stack.getFromStack(x).getFromStructure(y).posY ] =
                          stack.getFromStack(x).getFromStructure(y).command;  
                Types[ stack.getFromStack(x).getFromStructure(y).posX ]
                     [ stack.getFromStack(x).getFromStructure(y).posY ] =
                       stack.getFromStack(x).getFromStructure(y).type;
                Icons[ stack.getFromStack(x).getFromStructure(y).posX ]
                     [ stack.getFromStack(x).getFromStructure(y).posY ] =
                       stack.getFromStack(x).getFromStructure(y).icon;
            }
        }
        return stack;
    }
// ============================================================================
    /**
     * Add the current entered commands to the history stack, Model
     * @param NONE
     * @return NONE
     */
    void addToHistory(boolean WE[][], Border border[][], ImageIcon icn[][])
    {        
        Icons = icn;        
        HistoryStack stack = new HistoryStack();
        //System.out.println("=====================SAVE===========================");
        for( int x=0 ; x<10 ; x++){
            HistoryStructure structure = new HistoryStructure();
            for( int y=0 ; y<7 ; y++ )
            {
                structure.addToStructure( new HistoryCell(Commands[x][y], Types[x][y], Icons[x][y], 
                                          WE[x][y], border[x][y], x, y) );                
            }        
            stack.addToStack(structure);
        }
        history.addToHistory(stack);
    }
// ============================================================================
    /**
     * Setting data with the:
     * @param command The command's name
     * @param type The command's type
     * @param icon The command's icon
     * @param x The x position of the command
     * @param y The y position of the command
     * @return NONE
     */
    void setData(String command, String type, ImageIcon icon, int x, int y)
    {
        Commands[x][y] = command;
        Types[x][y] = type;
        Icons[x][y] = icon;
    }
// ============================================================================
    /**
     * Getting the name of the command, Model
     * @param x The x position of the Workspace
     * @param y The y position of the Workspace
     * @return String The command's name
     */
    String getCommand(int x, int y)
    {
        return Commands[x][y];
    }
// ============================================================================
    /**
     * Getting the type of the command, Model
     * @param x The x position of the Workspace
     * @param y The y position of the Workspace
     * @return String The command's type
     */
    String getType(int x, int y)
    {
        return Types[x][y];
    }
// ============================================================================
    /**
     * Getting the icon of the command, Model
     * @param x The x position of the Workspace
     * @param y The y position of the Workspace
     * @return ImageIcon The command's icon
     */
    ImageIcon getIcon(int x, int y)
    {
        return Icons[x][y];
    }
// ============================================================================    
    /**
     * Reseting a complete line in the Model
     * @param x The index of the reseted line
     * @return NONE
     */
    private void resetLine(int x)
    {
        for(int y=0 ; y<7 ; y++)
            setData("","",emptyIcon, x,y);
    }
// ============================================================================
    /**
     * Saving the Model to a file
     * @param filename The saved file name
     * @return NONE
     */
    void doSave(String filename)
    {
        try {
            PrintWriter p = new PrintWriter( new File(filename) );            
            p.println("? The GRPL Tool save file ...\n");
            String line;
            // Collecting the data to be saved from the Commands array:
            for(int x=0 ; x<10 ; x++){
                line = "";
                for(int y=0 ; y<7 ; y++)
                {
                    line = line + Commands[x][y] + " ; ";
                }
                p.println(line.toUpperCase());
            }
            p.println("\n? End of file ...");
            p.close();
            JOptionPane.showMessageDialog(parent, "The file: "+filename+
                                          "\nis successfully saved ...", "DONE ...", 
                                          JOptionPane.INFORMATION_MESSAGE);
            System.out.println("INFO: The file: "+filename+" is successfully saved ...");
        } 
        catch (FileNotFoundException e) {
            String str = "Can't write to the file:\n"+filename+"\nThe saving will be ignored ...";            
            ErrMsg.print(0,str,-1,-1);
        }
    }
// ============================================================================
    /**
     * Exporting to the roboMind Script file for the simulator:
     * @param filename The name of the exported script file
     * @param Commands The name of the commands
     * @return NONE
     */
    void doExport(String filename)
    {
        irobo.doExport(filename, Commands);
    }
}
// ============================================================================
