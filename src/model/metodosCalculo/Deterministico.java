/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.metodosCalculo;

import model.Calculador;

/**
 *
 * @author eduardo
 */
public class Deterministico implements Calculador {

    private double constante;

    public Deterministico(double constante) {
        this.constante = constante;
    }
    
    @Override
    public double calcular() {
        return constante;
    }
    
}
