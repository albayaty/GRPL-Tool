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
 * ( HistoryStructure class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * Personal website: <http://albayaty.github.io/>
 * Source code link: <https://github.com/albayaty/GRPL-Tool.git>
 */
// ============================================================================
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