/**
 * Graphical-based Robotics Programming Language
 * ( HistoryStack class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * LICENSE: GNU General Public License v3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */
package GRPL;

public class HistoryStack
{   
    private HistoryStructure[] stack;
    private int index;
    
    /**
     * The constructor of the class
     */
    public HistoryStack()
    {
        stack = new HistoryStructure[10];
        index = 0;
    }
// ============================================================================
    /**
     * Adding the structure of cells of the Workspace to form the stack
     * @param structure  The history/Workspace structure, one complete line
     * @return NONE
     */
    void addToStack(HistoryStructure structure)
    {        
        stack[index] = structure;
        index++;
    }
// ============================================================================
    /**
     * Getting the structure of cells from the stack to the Workspace
     * @param index  The index of the structure in the stack
     * @return HistoryStructure The history/Workspace structure, one complete line
     */
    HistoryStructure getFromStack(int index)
    {
        return stack[index];
    }
// ============================================================================    
    
}
// ============================================================================
