/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pang;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author kisc
 */
public class Bonus implements Runnable {

    Point2D pi, pf;
    Thread t;
    double height;
    int type;

    public Bonus(int x, int y, int x1, int y1, double height, int type) {
        pi = new Point2D(x, y);
        pf = new Point2D(x1, y1);
        this.height = height;
        this.type = type;
        t = new Thread(this);
        t.start();
    }

    public void draw(Graphics g) {
        //consoante o tipo do objecto sao crados objectos com formato diferente
        switch (type) {
            case 1:
                g.drawRect(pi.x, pi.y, pf.x, pf.y);
                break;
            case 2:
                g.drawPolygon(new int[]{pi.x - 20, pi.x - 10, pi.x}, new int[]{pi.y, pi.y - 20, pi.y}, 3);
                break;
            case 3:
                g.drawRect(pi.x, pi.y, pf.x, pf.y);
                break;
            case 4:
                g.drawOval(pi.x - 10, pi.y - 10, 10 * 2, 10 * 2);
                break;
            case 5:
                g.drawRect(pi.x, pi.y, pf.x, pf.y);
                break;
        }
        //-------------------------------------------------------------------------
    }

    @Override
    public void run() {
        while (pi.y + pf.y <= height) {
            pi.y++;
            try {
                t.sleep(10);
            } catch (InterruptedException ex) {
            }
        }
    }

    Rectangle asRectangle() {
        if (type == 1 || type == 3) {
            return new Rectangle(pi.x, pi.y, pf.x, pf.y);
        } else if (type == 2) {
            return new Rectangle(pi.x, pi.y - 20, 20, 20);
        } else if (type == 4) {
            return new Rectangle(pi.x - 10, pi.y - 10, 2 * 10, 2 * 10);
        } else {
            return new Rectangle(pi.x, pi.y, pf.x, pf.y);
        }
    }
}
