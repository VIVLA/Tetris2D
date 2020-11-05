/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author aakonovalenko@mail.ru
 */
public class Coord {
    public final int x;
    public final int y;
    
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Coord plus (int sx, int sy) {
        return new Coord(x + sx, y + sy);
    }
    
}
