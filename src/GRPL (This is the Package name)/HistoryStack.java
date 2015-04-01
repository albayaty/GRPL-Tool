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
 * ( HistoryStack class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * Personal website: <http://albayaty.github.io/>
 * Source code link: <https://github.com/albayaty/GRPL-Tool.git>
 */
// ============================================================================
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
