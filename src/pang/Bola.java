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
public class Bola extends Circle implements Runnable {

    Thread t;
    Jogo pai;
    int dv, dh;
    int raio;
    int velocidade;

    public Bola(int x, int y, int raio, Jogo pai, int velocidade) {
        super(x, y, raio);
        t = new Thread(this);
        this.raio = raio;
        this.pai = pai;
        dv = 1;
        dh = -1;
        this.velocidade = velocidade;
        t.start();
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

    @Override
    public void run() {
        while (true) {
            try {
                //define os limites da janela
                if (dh == 1 && dv == 1) {
                    centro.x += velocidade;
                    centro.y += velocidade;
                }
                if (dh == 1 && dv == -1) {
                    centro.x += velocidade;
                    centro.y -= velocidade;
                }
                if (dh == -1 && dv == -1) {
                    centro.x -= velocidade;
                    centro.y -= velocidade;
                }
                if (dh == -1 && dv == 1) {
                    centro.x -= velocidade;
                    centro.y += velocidade;
                }

                if (centro.y + raio >= pai.getHeight()) {
                    dv = -1;
                }
                if (centro.x + raio >= pai.getWidth()) {
                    dh = -1;
                }
                if (centro.y <= 0 + raio) {
                    dv = 1;
                }
                if (centro.x <= 0 + raio) {
                    dh = 1;
                }
                t.sleep(50);
            } catch (InterruptedException ex) {
            }
            //-------------------------------------------------------
        }
    }

}
