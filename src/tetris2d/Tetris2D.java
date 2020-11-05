/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris2d;

import javax.swing.SwingUtilities;
import ui.Window;

/**
 *
 * @author aakonovalenko@mail.ru
 */
public class Tetris2D {

    public static void main(String[] args) {
        Window game = new Window();
        SwingUtilities.invokeLater(game);
        game.addFigure();
    }
    
}
