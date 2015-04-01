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
 * ( History class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * Personal website: <http://albayaty.github.io/>
 * Source code link: <https://github.com/albayaty/GRPL-Tool.git>
 */
// ============================================================================
package GRPL;

import java.awt.Component;

public class History
{   
    private int size;
    private int UndoRedoPtr;
    private HistoryStack[] history;
    private Component parent;
    private ErrMsgClassifier ErrMsg;
    
    /**
     * Constructor of the class
     * @param prt The pointer to the Controller, used with ErrMsgClassifier class
     */
    public History(Component prt)
    {
        size = UndoRedoPtr = -1;    // Init. everything to NOTHING !!!
        history = new HistoryStack[50];
        parent = prt;
        ErrMsg = new ErrMsgClassifier(parent);
    }
// ============================================================================
    /**
     * Adding the stack of structures of cells of the Workspace to form the history
     * @param stack  The history stack, one complete Workspace
     * @return NONE
     */
    void addToHistory(HistoryStack stack)
    {        
        UndoRedoPtr++;
        if(UndoRedoPtr>49)
        {
            for(int i=0; i<49 ; i++){
                history[i] = history[i+1];
            }
            UndoRedoPtr = 49;
        }
        size = UndoRedoPtr;
        history[UndoRedoPtr] = stack;        
        System.out.println( "Add2Hist= Size: "+size +", UndoPtr: "+UndoRedoPtr );
    }
// ============================================================================
    /**
     * Undo action from the Model to the Workspace, return the most previous actions
     * @param NONE
     * @return HistoryStack One complete Workspace/History
     */
    HistoryStack undo()
    {
        if( UndoRedoPtr <= 0 )
        {
            String str = String.format("Unable to UNDO, no more previous activities ...");
            ErrMsg.print(1, str, -1, -1);
            System.out.println( "UndoHistErr= Size: "+size +", UndoPtr: "+UndoRedoPtr );
        }
        else{
            UndoRedoPtr--;
            System.out.println( "UndoHistOK= Size: "+size +", UndoPtr: "+UndoRedoPtr );
        }
        return history[UndoRedoPtr];
    }
// ============================================================================
    /**
     * Redo action from the Model to the Workspace, return the most recent actions
     * @param NONE
     * @return HistoryStack One complete Workspace/History
     */
    HistoryStack redo()
    {
        if( size <= 49 && size > UndoRedoPtr )
            UndoRedoPtr++;            
        else
        {
            String str = String.format("Unable to REDO, no more recent activities ...");
            ErrMsg.print(1, str, -1, -1);
        }
        System.out.println( "RedoHist= Size: "+size +", UndoPtr: "+UndoRedoPtr );
        return history[UndoRedoPtr];
    }

}
// ============================================================================
