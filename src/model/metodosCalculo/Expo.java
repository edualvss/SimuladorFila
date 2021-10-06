/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.metodosCalculo;

import java.util.Random;
import model.Calculador;

/**
 *
 * @author eduardo
 */
public class Expo implements Calculador{

    private double media;
    private Random random;

    public Expo(double media) {
        this.media = media;
        this.random = new Random();
    }
    
    @Override
    public double calcular() {
        double valor = media * Math.pow(Math.E,((-media)*random.nextDouble()));
        if(valor < 0) {
            valor *= -1;
        }
        while(valor < 1) {
            valor *= 10;
        }
        return valor;
    }
    
}
