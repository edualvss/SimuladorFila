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
public class Uniforme implements Calculador {

    private double minimo;
    private double maximo;
    private Random random;

    public Uniforme(double minimo, double maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.random = new Random();
    }
        
    @Override
    public double calcular() {
        long intervalo = (long)maximo - (long)minimo + 1;
        
        double fracao = (intervalo * random.nextDouble());

        double valor = (fracao + minimo);
        
        if(valor < 0) {
            valor *= -1;
        }
        while(valor < 1) {
            valor *= 10;
        }
        return valor;

    }
    
    
}
