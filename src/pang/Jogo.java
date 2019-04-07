package pang;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kisc
 */
public class Jogo extends JPanel implements Runnable {

    ArrayList<Bola> bolas;
    Boneco boneco;
    Disparo d;
    Thread t;
    Bonus bonus;
    int pontos, vidas = 3, func = 0, aux, temp = 0;
    boolean duplo = false, show = false, slow = false, invencivel = false;

    public Jogo() {
        super();
        bolas = new ArrayList<>();
    }

    void init() {
        //funçao que inicia o jogo
        //cria o boneco
        boneco = new Boneco(getWidth() / 2, getHeight() - 10, 10, this);
        //------------------------------
        //inicia o jogo com uma bola num sitio aliatorio
        Random r = new Random();
        for (int i = 0; i < 1; i++) {
            Bola b = new Bola(r.nextInt(getWidth()),
                    r.nextInt(getHeight() / 2),
                    50, this, 5);
            System.out.println(r.nextInt(2));
            b.dv = r.nextInt(2) == 0 ? -1 : 1;
            b.dh = r.nextInt(2) == 0 ? -1 : 1;
            bolas.add(b);
        }
        //----------------------------------------------
        //cria thread do jogo
        t = new Thread(this);
        t.start();
        //----------------------
        //-------------------------------------------------------
    }

    void dispara() {
        //funçao que cria o disparo e condiciona o bonus 1
        if (d == null) {
            d = new Disparo(boneco.x, boneco.y, boneco.x, boneco.y, duplo);
        } else if (!d.t.isAlive()) {
            d = new Disparo(boneco.x, boneco.y, boneco.x, boneco.y, duplo);
        }
        //------------------------------------------------------------

    }

    @Override
    public void paint(Graphics g) {
        //funçao que desenha o jogo
        //limpa os traços do arrastamento das bolas
        g.clearRect(0, 0, getWidth(), getHeight());
        //------------------------------------------
        // verifica se o boneco é nulo se nao for desenha
        if (boneco != null) {
            boneco.draw(g);
        }
        //---------------------------------------------------
        // for para o funcionamento das bolas e tudo associado a elas
        for (int i = 0; i < bolas.size(); i++) {
            Bola b = bolas.get(i);
            b.draw(g);
            // verifica se o disparo é nulo 
            if (d != null) {
                // verifica contacto entre as bolas e o disparo
                if (b.asRectangle().intersects(d.asRectangle())) {
                    d = null;
                    if (b.raio > 10) {
                        Bola b1 = new Bola(b.centro.x, b.centro.y, b.raio / 2, this, 5);
                        Bola b2 = new Bola(b.centro.x, b.centro.y, b.raio / 2, this, 5);
                        b1.dh = b.dh;
                        b1.dv = -1;
                        b2.dh = b.dh * (-1);
                        b2.dv = -1;
                        bolas.add(b1);
                        bolas.add(b2);
                    }
                    bolas.remove(i);
                    i--;
                    //verifica se o aux é superior ao array das bolas
                    if (aux > bolas.size()) {
                        func = 0;
                    }
                    //------------------------------------------------
                    //Cotação de pontos
                    pontos = pontos + bolas.size();
                    //-------------------------------
                    //Gestao de probabilidade de bonus
                    Random r = new Random();
                    int randomint = r.nextInt(100);
                    //int randomint = 85;
                    // debug numero random
                    System.out.println(randomint);
                    if (bonus == null && randomint > 23 && randomint <= 39) {
                        System.out.println("bonus1");
                        bonus = new Bonus(b.centro.x, b.centro.y, 10, 10, getHeight(), 1);
                    } else if (bonus == null && randomint > 39 && randomint <= 54) {
                        System.out.println("bonus2");
                        bonus = new Bonus(b.centro.x, b.centro.y, 0, 0, getHeight(), 2);
                    } else if (bonus == null && randomint > 54 && randomint <= 69) {
                        System.out.println("bonus3");
                        bonus = new Bonus(b.centro.x, b.centro.y, 10, 20, getHeight(), 3);
                    } else if (bonus == null && randomint > 69 && randomint <= 84) {
                        System.out.println("bonus4");
                        bonus = new Bonus(b.centro.x, b.centro.y, 10, 10, getHeight(), 4);
                    } else if (bonus == null && randomint > 84 && randomint <= 99) {
                        System.out.println("bonus5");
                        bonus = new Bonus(b.centro.x, b.centro.y, 20, 10, getHeight(), 5);
                    } else {
                        System.out.println("Pouca sorte!!");
                    }
                    //---------------------------------------------------------------
                }
                //------------------------------------------------------------------------------
            }
            //--------------------------------------------------------------------------------
            // verifica se o boneco nao é nulo se for nulo desenha o de novo
            if (boneco != null) {
                // verifica se o i nao é negativo
                if (i >= 0) {
                    //verifica o contacto entre as bolas e o boneco
                    if (bolas.get(i).asRectangle().intersects(boneco.asRectangle())) {
                        if (invencivel == false) {
                            boneco = null;
                        }
                        //decrementa as vidas
                        if (func == 0) {
                            if (invencivel == false) {
                                vidas--;
                            }
                            func = 1;
                            aux = i;
                        }
                        //--------------------
                    } else if (bolas.get(aux).centro.y + bolas.get(aux).raio < getHeight() - 40) {
                        func = 0;
                    }
                    //------------------------------------------------------------------------------
                }
                //----------------------------------------------------------------
            } else {
                boneco = new Boneco(getWidth() / 2, getHeight() - 10, 10, this);
                boneco.draw(g);
            }
            // execução dos bonus
            if (bonus != null && boneco != null) {
                if (bonus.asRectangle().intersects(boneco.asRectangle())) {
                    switch (bonus.type) {
                        case 1:
                            // disparo duplo
                            System.out.println("apanhei o bonus 1 - disparo duplo");
                            bonus = null;
                            duplo = true;
                            break;
                        case 2:
                            // disparo so desaparece quando toca uma bola
                            System.out.println("apanhei o bonus 2 - disparo nao desaparece");
                            bonus = null;
                            show = true;
                            break;
                        case 3:
                            // standard
                            System.out.println("apanhei o bonus 3 - standard");
                            bonus = null;
                            duplo = false;
                            show = false;
                            invencivel = false;
                            slow = false;
                            break;
                        case 4:
                            // deixa as bolas lentas
                            System.out.println("apanhei o bonus 4 - bolas lentas");
                            bonus = null;
                            slow = true;
                            break;
                        case 5:
                            // boneco fica ivencivel
                            System.out.println("apanhei o bonus 5 - invencivel");
                            bonus = null;
                            invencivel = true;
                            break;
                        default:
                            break;
                    }
                }
            }
            //-------------------------------------------------------------------------

            //------------------------------------------------------------------
            //desenha a string dos pontos e das vidas
            g.drawString("Vidas:" + vidas, 100, 10);
            g.drawString("Pontos:" + pontos, 0, 10);
            //-------------------------------------------
        }
        //-------------------------------------------------------------------------
        //verifica que o disparo e o bonus sao nulos para os desenhar e condiciona o bonus 2
        if (d != null) {
            d.draw(g);
            if (!d.t.isAlive() && show == false) {
                d = null;
            }
        }
        if (bonus != null) {
            bonus.draw(g);
        }
        //----------------------------------------------------------------
        //------------------------------------------------------------------------------------------------------
        //condição de bonus 4 bolas lentas
        if (slow == true) {
            temp = 100000;
            slow = false;
        }
        if (temp > 0) {
            for (int i = 0; i < bolas.size(); i++) {
                bolas.get(i).velocidade = 2;
            }
            temp = temp - 1;
        } else {
            for (int i = 0; i < bolas.size(); i++) {
                bolas.get(i).velocidade = 5;
            }
        }
        //------------------------------------------
    }

    @Override
    public void run() {
        while (vidas > 0) {
            repaint();
        }
    }

}
