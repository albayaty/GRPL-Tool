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
 * ( HistoryCell class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * Personal website: <http://albayaty.github.io/>
 * Source code link: <https://github.com/albayaty/GRPL-Tool.git>
 */
// ============================================================================
package GRPL;

import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class HistoryCell
{
    String command;
    String type;
    ImageIcon icon;
    boolean WE;
    int posX;
    int posY;
    Border border;
    /**
     * The constructor of the class
     * @param actn The command's name
     * @param typ The command's type
     * @param icn The command's icon
     * @param en The Workspace enable
     * @param x The x position of the command, Model
     * @param y The y position of the command, Model
     */
    public HistoryCell(String actn, String typ, ImageIcon icn, boolean en, Border brdr, int x, int y)
    {
        command = actn;
        type = typ;
        icon = icn;
        WE = en;
        border = brdr;
        posX = x;
        posY = y;
    }

}
// ============================================================================
