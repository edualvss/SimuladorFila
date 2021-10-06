/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author eduardo
 */
public class Entidade {
    
    private int numeroEntidade;
    private long tempoChegadaSistema;
    private long tempoEntreChegadas;
    private long tempoServico;
    private long tempoFila;
    private long tempoInicioAtendimento;
    private long tempoFinalAtendimento;
    private long tempoClienteSistema;
    private long tempoLivreOperador;

    public Entidade() {
    }

    public Entidade(int numeroEntidade, long tempoChegadaSistema, long tempoEntreChegadas, long tempoServico, long tempoFila, long tempoInicioAtendimento, long tempoFinalAtendimento, long tempoClienteSistema, long tempoLivreOperador) {
        this.numeroEntidade = numeroEntidade;
        this.tempoChegadaSistema = tempoChegadaSistema;
        this.tempoEntreChegadas = tempoEntreChegadas;
        this.tempoServico = tempoServico;
        this.tempoFila = tempoFila;
        this.tempoInicioAtendimento = tempoInicioAtendimento;
        this.tempoFinalAtendimento = tempoFinalAtendimento;
        this.tempoClienteSistema = tempoClienteSistema;
        this.tempoLivreOperador = tempoLivreOperador;
    }
    
    /**
     * @return the numeroEntidade
     */
    public int getNumeroEntidade() {
        return numeroEntidade;
    }

    /**
     * @param numeroEntidade the numeroEntidade to set
     */
    public void setNumeroEntidade(int numeroEntidade) {
        this.numeroEntidade = numeroEntidade;
    }

    /**
     * @return the tempoChegada
     */
    public long getTempoEntreChegadas() {
        return tempoEntreChegadas;
    }

    /**
     * @param tec the tempoChegada to set
     */
    public void setTempoEntreChegadas(long tec) {
        this.tempoEntreChegadas = tec;
    }

    /**
     * @return the tempoServico
     */
    public long getTempoServico() {
        return tempoServico;
    }

    /**
     * @param tempoServico the tempoSaida to set
     */
    public void setTempoServico(long tempoServico) {
        this.tempoServico = tempoServico;
    }

    /**
     * @return the tempoFila
     */
    public long getTempoFila() {
        return tempoFila;
    }

    /**
     * @param tempoFila the tempoFila to set
     */
    public void setTempoFila(long tempoFila) {
        this.tempoFila = tempoFila;
    }

    /**
     * @return the tempoInicioAtendimento
     */
    public long getTempoInicioAtendimento() {
        return tempoInicioAtendimento;
    }

    /**
     * @param tempoInicioAtendimento the tempoInicioAtendimento to set
     */
    public void setTempoInicioAtendimento(long tempoInicioAtendimento) {
        this.tempoInicioAtendimento = tempoInicioAtendimento;
    }

    /**
     * @return the tempoFinalAtendimento
     */
    public long getTempoFinalAtendimento() {
        return tempoFinalAtendimento;
    }

    /**
     * @param tempoFinalAtendimento the tempoFinalAtendimento to set
     */
    public void setTempoFinalAtendimento(long tempoFinalAtendimento) {
        this.tempoFinalAtendimento = tempoFinalAtendimento;
    }

    /**
     * @return the tempoClienteSistema
     */
    public long getTempoClienteSistema() {
        return tempoClienteSistema;
    }

    /**
     * @param tempoClienteSistema the tempoClienteSistema to set
     */
    public void setTempoClienteSistema(long tempoClienteSistema) {
        this.tempoClienteSistema = tempoClienteSistema;
    }

    /**
     * @return the tempoLivreOperador
     */
    public long getTempoLivreOperador() {
        return tempoLivreOperador;
    }

    /**
     * @param tempoLivreOperador the tempoLivreOperador to set
     */
    public void setTempoLivreOperador(long tempoLivreOperador) {
        this.tempoLivreOperador = tempoLivreOperador;
    }

    public long getTempoChegadaSistema() {
        return this.tempoChegadaSistema;
    }
    
    public void setTempoChegadaSistema(long tempoChegada) {
        this.tempoChegadaSistema = tempoChegada;
    }
    
    @Override
    public String toString() {
        String retorno = "";
        
        retorno += "NumEntidade: "+this.numeroEntidade+"\tTEC: "+this.tempoEntreChegadas
                +"\tTS: "+this.tempoServico+"\tTempoChegada: "+this.tempoChegadaSistema+"\tTempoFila: "+this.tempoFila
                +"\tTempoInicioAtendimento: "+this.tempoInicioAtendimento
                +"\tTempoFinalAtendimento: "+this.tempoFinalAtendimento
                +"\tTempoClienteNoSistema: "+this.tempoClienteSistema
                +"\tTempoLivreServidor: "+this.tempoLivreOperador;
        
        return retorno;
    }
    
}
