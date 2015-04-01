/**
 * Graphical-based Robotics Programming Language
 * ( HistoryCell class: Model )
 * @author  Ali M. Al-Bayaty
 * @vesion  1.31
 * @since   3/18/2013
 * LICENSE: GNU General Public License v3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */
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
