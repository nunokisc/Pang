package pang;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kisc
 */
public class Circle {

    Point2D centro;
    int raio;

    public Circle(int x, int y, int raio) {
        this.centro = new Point2D(x, y);
        this.raio = raio;
    }

    @Override
    public String toString() {
        return "Circle{" + "centro=" + centro + ", raio=" + raio + '}';
    }

}
