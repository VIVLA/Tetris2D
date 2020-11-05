/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import model.Coord;
import model.Figures;
import ui.Settings;

/**
 *
 * @author aakonovalenko@mail.ru
 */
public class FlyFigure {
    
    private Coord coord;
    private Figures figure;
    private Mapable map;
    private boolean landed;
    private int ticks;
    
    public FlyFigure(Mapable map) {
        this.map = map;
        coord = new Coord(Settings.WIDTH / 3, -1);
        figure = Figures.addRandom();
        landed = false;
        ticks = 2;
    }
    
    public Coord getCoord() {
        return coord;
    }
    
    public Figures getFigure() {
        return figure;
    }
    
    public boolean isLanded() {
        return landed;
    }
    
    public boolean canPlaceFigure() {
        return canMoveFigure(figure, 0, 0);
    }
    
    private boolean canMoveFigure(Figures figure, int sx, int sy) {
        if (coord.x + sx + figure.top.x < 0) return false;
        if (coord.x + sx + figure.bot.x >= Settings.WIDTH) return false;
        if (coord.y + sy + figure.bot.y >= Settings.HEIGHT) return false;
        for (Coord dot : figure.dots) {
            if (map.getBoxColor(coord.x + sx + dot.x, coord.y + sy + dot.y) > 0)
                return false;
        }
        return true;
    }
    
    public void moveFigure(int sx, int sy) {
        if (canMoveFigure(figure, sx, sy))
            coord = coord.plus(sx, sy);
        else if (sy == 1) {
            if (ticks > 0)
                ticks--;
            else 
                landed = true;
        }
    }
    
    public void turnFigure() {
        Figures rotated = figure.turnRight();
        if (canMoveFigure(rotated, 0, 0)) {
            figure = rotated;
        } else 
        if (canMoveFigure(rotated, 1, 0)) {
            figure = rotated;
            moveFigure(1, 0);
        } else 
        if (canMoveFigure(rotated, -1, 0)) {
            figure = rotated;
            moveFigure(-1, 0);
        } else 
        if (canMoveFigure(rotated, 0, -1)) {
            figure = rotated;
            moveFigure(0, -1);
        } 
    }
    
}
