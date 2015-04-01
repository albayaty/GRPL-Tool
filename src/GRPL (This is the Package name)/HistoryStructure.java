/**
 * Graphical-based Robotics Programming Language
 * ( HistoryStructure class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * LICENSE: GNU General Public License v3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */
package GRPL;

public class HistoryStructure
{
    private HistoryCell[] structure;
    private int index;
    
    /**
     * The constructor of the class
     */
    public HistoryStructure()
    {
        structure = new HistoryCell[7];
        index = 0;
    }    
// ============================================================================
    /**
     * Adding the cells of the Workspace to form the structure
     * @param cell  The history/Workspace cell
     * @return NONE
     */
    void addToStructure(HistoryCell cell)
    {
        structure[index] = cell;
        index++;
    }
// ============================================================================
    /**
     * Getting the cells from the structure to the Workspace
     * @param index The index of the cell in the structure
     * @return HistoryCell The history/Workspace cell
     */
    HistoryCell getFromStructure(int index)
    {
        return structure[index];
    }
}
// ============================================================================