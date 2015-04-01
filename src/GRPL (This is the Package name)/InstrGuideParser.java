// ============================================================================
/**
 * Copyright ©  2014  Ali M. Al-Bayaty
 * 
 * GRPL Tool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * GRPL Tool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
// ============================================================================
/**
 * Graphical-based Robotics Programming Language
 * ( InstrGuideParser class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * Personal website: <http://albayaty.github.io/>
 * Source code link: <https://github.com/albayaty/GRPL-Tool.git>
 */
// ============================================================================
package GRPL;

import java.awt.Component;
import java.io.*;

public class InstrGuideParser
{    
    private FileReader fr;
    private String[] cmds;
    private ErrMsgClassifier ErrMsg;
    private Component parent;
    
    /**
     * Constructor of the class
     * @param fileaddress The absolute address of the "instructions.guide" file
     * @param prt The pointer to the Controller, used with ErrMsgClassifier class
     */
    public InstrGuideParser(String fileaddress, Component prt){
        try {
            parent = prt;
            ErrMsg = new ErrMsgClassifier(parent);
            fr = new FileReader( new File(fileaddress) );
        } 
        catch (FileNotFoundException e) {
            String str = "The file \"instructions.guide\" is not found in the current\nworking directory, the program will be closed ...";
            ErrMsg.print(0,str,-1,-1);
            System.exit(1);
        }
        cmds = new String[50];
    }
// ============================================================================    
    /**
     * Loading and checking the "instructions.guide" file
     * @param NONE
     * @return String[] The actual formatted command in this file
     */
    String[] ParseInstrGuide()
    {
        try {
            int index=0;
            
            char[] linesChar = new char[3072];            
            fr.read(linesChar);

            String[] lines = new String(linesChar).split("\n");
            
            System.out.println("\n<<< The \"instructions.guide\" file >>>");  
            System.out.println("========================================");
            for(int i=0 ; i<lines.length ; i++)
            {
                lines[i] = lines[i].trim();
                
                // Skipping the comment and empty lines:
                if( lines[i].length() < 1 || lines[i].charAt(0) == '?' )
                    continue;
                
                cmds[index] = lines[i].toUpperCase();
                System.out.println(cmds[index]);
                index++;
            }
            
            cmds[index] = "???";//StopParsing";
            System.out.println("========================================\n");
        }
        catch (IOException ex) {
            String str = "Can't read from the file: \"instructions.guide\",\nthe program will be closed ...";
            ErrMsg.print(0,str,-1,-1);
            System.exit(1);
        }
                       
        try {
            fr.close();
        } 
        catch (IOException ex) {
            String str = "Can't close the file: \"instructions.guide\"";            
            ErrMsg.print(1,str,-1,-1);
        }
        return cmds;
    }    
}
// ============================================================================
