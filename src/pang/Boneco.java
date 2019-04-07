package pang;

import java.awt.Color;
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
public class Boneco extends Circle {

    Jogo pai;
    int x, y;

    public Boneco(int x, int y, int raio, Jogo pai) {
        super(x, y, raio);
        this.x = x;
        this.y = y;
        this.pai = pai;
    }

    void draw(Graphics g) {
        g.setColor(Color.black);
        g.drawOval(centro.x - raio,
                centro.y - raio,
                raio * 2, raio * 2);
    }

    Rectangle asRectangle() {
        return new Rectangle(centro.x - raio, centro.y - raio, 2 * raio, 2 * raio);
    }
}
