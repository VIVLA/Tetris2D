/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import engine.FlyFigure;
import engine.Mapable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import model.Coord;

/**
 *
 * @author aakonovalenko@mail.ru
 */
public class Window extends JFrame implements Runnable, Mapable {
    
    private final Box [][] boxes;
    private FlyFigure fly;
    
    public Window() {
        boxes = new Box[Settings.WIDTH][Settings.HEIGHT];
        initFrame();
        initBoxes();
        addKeyListener(new KeyPressAdapter());
        TimerAdapter time = new TimerAdapter();
        Timer timer = new Timer(300, time);
        timer.start();
    }
    
    @Override
    public void run() {
        repaint();
    }
    
    private void initFrame() {
        pack();
        setSize(Settings.WIDTH * Settings.SIZE + 15, 
                Settings.HEIGHT * Settings.SIZE + 40);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Tetris The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initBoxes() {
        for (int x = 0; x < Settings.WIDTH; x++) {
            for (int y = 0; y < Settings.HEIGHT; y++) {
                boxes[x][y] = new Box(x, y);
                add(boxes[x][y]);
            }
        }
    }
    
    public void addFigure() {
        fly = new FlyFigure(this);
        if (fly.canPlaceFigure()) {
            showFigure();        
        } else {
            dispose();
            setVisible(false);
        }
    }
    
    private void showFigure() {
        showFigure(1);
    }
    
    private void hideFigure() {
        showFigure(0);
    }
    
    private void showFigure(int color) {
        for (Coord dot : fly.getFigure().dots)
            setBoxColor(dot.x + fly.getCoord().x, dot.y + fly.getCoord().y, color);
    }
    
    private void setBoxColor(int x, int y, int color) {
        if (x < 0 || x >= Settings.WIDTH) return;
        if (y < 0 || y >= Settings.HEIGHT) return;
        boxes[x][y].setColor(color);
    }
    
    @Override
    public int getBoxColor(int x, int y) {
        if (x < 0 || x >= Settings.WIDTH) return -1;
        if (y < 0 || y >= Settings.HEIGHT) return -1;
        return boxes[x][y].getColor();
    }
    
    private void moveFly(int sx, int sy) {
        hideFigure();
        fly.moveFigure(sx, sy);
        showFigure();
    }
    
    private void turnFly() {
        hideFigure();
        fly.turnFigure();
        showFigure();
    }
    
    private void stripLine() {
        for (int y = Settings.HEIGHT - 1; y >= 0; y--) {
            while(isFullLine(y)) {
                dropLine(y);    
            }
        }
    }
    
    private void dropLine(int y) {
        for (int my = y - 1; my >= 0; my--) {
            for (int x = 0; x < Settings.WIDTH; x++) {
                setBoxColor(x, my + 1, getBoxColor(x, my));
            }
        }
        for (int x = 0; x < Settings.WIDTH; x++) {
            setBoxColor(x, 0, 0);
        }
    }
    
    private boolean isFullLine(int y) {
        for (int x = 0; x < Settings.WIDTH; x++) {
            if (getBoxColor(x, y) != 2) return false;
        }
        return true;
    }
    
    private class KeyPressAdapter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
           hideFigure();
           switch(e.getKeyCode()) {
               case KeyEvent.VK_LEFT : moveFly(-1,  0); break;
               case KeyEvent.VK_RIGHT: moveFly(+1,  0); break;
               case KeyEvent.VK_UP   : turnFly(); break;
               case KeyEvent.VK_SPACE: moveFly( 0, -1); break;
               case KeyEvent.VK_DOWN : moveFly( 0, +1); break;
           }
           showFigure();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
    
    private class TimerAdapter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            moveFly(0, +1);
            if (fly.isLanded()) {
                showFigure(2);
                stripLine();
                addFigure();
            }
        }
        
    }
    
}
