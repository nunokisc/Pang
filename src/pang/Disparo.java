package pang;

import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kisc
 */
public class Disparo implements Runnable {

    Point2D pi, pf;
    Thread t;
    boolean duplo;

    public Disparo(int x1, int y1, int x2, int y2, boolean duplo) {
        pi = new Point2D(x1, y1);
        pf = new Point2D(x2, y2);
        this.duplo = duplo;
        t = new Thread(this);
        t.start();
    }

    public void draw(Graphics g) {
        if (duplo == false) {
            g.drawLine(pi.x, pi.y, pf.x, pf.y);
        } else {
            g.drawLine(pi.x, pi.y, pf.x, pf.y);
            g.drawLine(pi.x + 5, pi.y, pf.x + 5, pf.y);
        }
    }

    public void run() {
        while (pf.y >= 0) {
            pf.y--;
            try {
                t.sleep(10);
            } catch (InterruptedException ex) {
            }
        }
    }

    Rectangle asRectangle() {
        if (duplo == false) {
            return new Rectangle(pf.x, pf.y, 1, pi.y - pf.y);
        } else{
            return new Rectangle(pf.x, pf.y, 5, pi.y - pf.y);
        }
    }
}
