/**
 * Graphical-based Robotics Programming Language
 * ( ErrMsgClassifier class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * LICENSE: GNU General Public License v3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */
package GRPL;

import java.awt.Component;
import javax.swing.JOptionPane;

public class ErrMsgClassifier
{
    private Component parent;
    
    /**
     * Constructor of the class     
     * @param prt The pointer to the Controller
     */
    public ErrMsgClassifier(Component prt)
    {
        parent = prt;
    }
// ============================================================================
    /**
     * Free-expandable Error Message Printing function
     * @param code The code of the message, ERROR=0, WARNING=1, ELSE=any number
     * @param str The message itself
     * @param x The x position, future option
     * @param y The y position, future option 
     * @return NONE
     */
    public void print(int code, String str, int x, int y)
    {
        switch(code){
            // For ERROR Notifications:
            case 0:
                System.err.println("ERROR: "+str);
                JOptionPane.showMessageDialog(parent, str, "Error ...", JOptionPane.ERROR_MESSAGE);
                break;
            
            // For WARNING Notifications:
            case 1:
                System.err.println("WARNING: "+str);
                JOptionPane.showMessageDialog(parent, str, "Warning ...", JOptionPane.WARNING_MESSAGE);
                break;
            
            // For other notifications:
            default: 
                System.err.println("INFO: "+str);
                JOptionPane.showMessageDialog(parent, str, "Info ...", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }
}
// ============================================================================
