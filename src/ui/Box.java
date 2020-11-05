/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javax.swing.JPanel;

/**
 *
 * @author aakonovalenko@mail.ru
 */
public class Box extends JPanel {
    
    public int color;
    
    public Box(int x, int y) {
        setBounds(x * Settings.SIZE, y * Settings.SIZE,
                  Settings.SIZE, Settings.SIZE);
        setBackground(Settings.COLORS[0]);
        setVisible(true);
        color = 0;
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
        if (color > 0 || color <= Settings.COLORS.length)
            setBackground(Settings.COLORS[color]);
    }
    
}
