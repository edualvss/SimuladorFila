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
public class Normal implements Calculador{

    private double media;
    private double desvioPadrao;
    private Random random;
    
    public Normal(double media, double desvioPadrao) {
        this.media = media;
        this.desvioPadrao = desvioPadrao;
        this.random = new Random();
    }
        
    @Override
    public double calcular() {
        double valor = media + random.nextGaussian() * Math.pow(desvioPadrao, 2);
        if(valor < 0) {
            valor *= -1;
        }
        while(valor < 1) {
            valor *= 10;
        }
        return valor;
    }
    
}
