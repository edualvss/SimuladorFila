/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Calculador;
import model.Entidade;
import model.metodosCalculo.Deterministico;
import model.metodosCalculo.Expo;
import model.metodosCalculo.Normal;
import model.metodosCalculo.Uniforme;
import view.Tela;

/**
 *
 * @author eduardo
 */
public class Controle implements ActionListener {
    
    private Calculador calculadorTEC;
    private Calculador calculadorTS;
        
    private long relogio;
    
    private long tempoSimulado;
    private long tempoSimulacao;
    
    private int contadorEntidade;
    private int contadorAtendidos;
    
    private Tela tela;
    
    private List<Entidade> entrouNoSistema;
    private List<Entidade> saiuDoSistema;
    
    private HashMap<Integer,Integer> contadorFila;
    
    public Controle(Tela tela) {
        
        this.tela = tela;
        
        this.entrouNoSistema = new ArrayList<>();
        this.saiuDoSistema = new ArrayList<>();
        this.contadorFila = new HashMap<>();
        
        this.limparDados();
    }

    private void calcularResultado() {
        
        double numMedioEntidadesFila = 0;
        double taxaMediaOcupacaoServidor = 0;
        double tempoMedioEntidadesFila = 0;
        double tempoMedioSistema = 0;
        
        for (Entidade ent : this.entrouNoSistema) {
            tempoMedioEntidadesFila += ent.getTempoFila();
            tempoMedioSistema += ent.getTempoClienteSistema();
            taxaMediaOcupacaoServidor += ent.getTempoLivreOperador();
        }
        
        double numEntidadesSistema = this.contadorEntidade;
        
        for(Map.Entry<Integer,Integer> entry : this.contadorFila.entrySet()) {
                                    //   Qtd na fila * Número de ocorrências desta quantidade
            numMedioEntidadesFila += (entry.getKey() * entry.getValue());
        }
        
        numMedioEntidadesFila /= this.tempoSimulado;
        tempoMedioEntidadesFila /= numEntidadesSistema;
        tempoMedioSistema /= numEntidadesSistema;
        taxaMediaOcupacaoServidor /= this.tempoSimulado;
        taxaMediaOcupacaoServidor = 1 - taxaMediaOcupacaoServidor;

        this.tela.setResultado(numMedioEntidadesFila, taxaMediaOcupacaoServidor,
               tempoMedioEntidadesFila, tempoMedioSistema, this.contadorEntidade);
        
    }

    private void limparDados() {
        this.contadorEntidade = 0;
        this.contadorAtendidos = 0;
        
        this.tempoSimulacao = 0;
        this.tempoSimulado = 0;
        
        this.relogio = 0;
        this.entrouNoSistema.clear();
        this.saiuDoSistema.clear();
        
        this.contadorFila.clear();
        
    }    
    
    private void executarSimulacao() {
                
        Entidade atual = null;
        Entidade anterior = null;

        int contadorEntidadesNaFila = -1;
        
        while(this.tempoSimulado <= this.tempoSimulacao) {
            
            double tec = calculadorTEC.calcular();
            double ts = calculadorTS.calcular();

            if(atual != null && atual.getTempoChegadaSistema() == tempoSimulado) {
                if(!this.entrouNoSistema.contains(atual)) {
                    this.entrouNoSistema.add(atual);
                    this.tela.adicionarEntidadeTabela(atual);
                    contadorEntidadesNaFila++;
                    this.tela.updateAnimacao(contadorEntidadesNaFila);
                }
            }

            // Atualiza animação
//            this.tela.updateAnimacao(contadorEntidadesNaFila);

            if(contadorEntidadesNaFila > 0 ) {
                if(this.contadorFila.containsKey(contadorEntidadesNaFila)) {
                    int valorAtual = this.contadorFila.get(contadorEntidadesNaFila);
                    valorAtual++;
                    this.contadorFila.put(contadorEntidadesNaFila, valorAtual);
                } else {
                    this.contadorFila.put(contadorEntidadesNaFila, 1);
                }
            }
            
            for(int i = this.entrouNoSistema.size()-1; i >= 0 ; i--) {
                Entidade ent = this.entrouNoSistema.get(i);
                if(this.saiuDoSistema.contains(ent)) {
                    break;
                } else {
                    if( tempoSimulado == ent.getTempoFinalAtendimento()) {
                        // remover item da animação
                        contadorAtendidos++;
                        this.saiuDoSistema.add(ent);
                        contadorEntidadesNaFila--;
                        this.tela.updateAnimacao(contadorEntidadesNaFila);
                        break;
                    }                    
                }

            }

            // Criar uma entidade
            if(relogio == tempoSimulado) {
                                
                this.relogio += tec;

                anterior = atual;
                atual = new Entidade();
                atual.setNumeroEntidade(++contadorEntidade);
                if(anterior == null) {
                    // Primeira entidade
                    atual.setTempoChegadaSistema((long) tec);
                    atual.setTempoEntreChegadas((long)tec);
                    atual.setTempoClienteSistema((long)ts);
                    atual.setTempoFila(0);
                    atual.setTempoInicioAtendimento((long)tec);
                    atual.setTempoFinalAtendimento( ((long)tec)+((long)ts)-1 );
                    atual.setTempoLivreOperador((long)tec);
                    atual.setTempoServico((long)ts);
                } else {
                    // Demais entidades
                    atual.setTempoChegadaSistema(this.relogio);
                    atual.setTempoEntreChegadas((long)tec);
                    atual.setTempoServico((long)ts);
                    long tempoFinalAnterior = anterior.getTempoFinalAtendimento();
                    if( tempoFinalAnterior >= this.relogio) {
                        atual.setTempoInicioAtendimento(tempoFinalAnterior+1);
                    } else {
                        atual.setTempoInicioAtendimento(this.relogio);
                    }
                    atual.setTempoFila(atual.getTempoInicioAtendimento() - this.relogio);
                    atual.setTempoFinalAtendimento( atual.getTempoInicioAtendimento()+atual.getTempoServico() - 1 );
                    atual.setTempoClienteSistema( atual.getTempoFila() + atual.getTempoServico() );
                    atual.setTempoLivreOperador( atual.getTempoInicioAtendimento() - anterior.getTempoFinalAtendimento() -1 );
                }

            }
            
            this.tempoSimulado++;

        }
        
        this.contadorEntidade = this.tela.getQtdLinhasTabela();
        this.calcularResultado();
        
    }
    
    public void iniciarAplicacao() {
        
        this.tela.adicionarListenerBotaoExecutar(this);
        this.tela.setVisible(true);
        
   
    }
    
    public List<String> validarExpressao(String expressao) {
        
        List<String> funcao = new ArrayList<>();

        String[] split;

        try{
            split = expressao.split("\\(");
        } catch(Exception ex) {
            return null;
        }

        String exp = split[0];
        
        switch(exp) {
            case "expo":
            case "normal":
            case "triangular":
            case "uniforme":
            case "log":
                break;
            default:
                return null;
        }
        
        funcao.add(exp);
        
        try {
            split = split[1].split("\\)");
        } catch(Exception ex) {
            return null;
        }
        
        String numbers = split[0];
        
        try {
            split = numbers.split(",");
        } catch (Exception ex) {
            return null;
        }
        
        for(String n : split) {
            try {
                Double.parseDouble(n);
                funcao.add(n);
            } catch(Exception ex) {
                return null;
            }
        }
        
        return funcao;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        this.limparDados();
        this.tela.limparTabela();
//            this.tela.repaint();
        // Botao executar
        List<String> campos = this.tela.getCampos();

        // Campos
        // [0] == tipoTEC
        // [1] == valorTEC
        // [2] == tipoTS
        // [3] == valorTS
        // [4] == tempoSimulacao

        List<String> funcao;
        // Funcao
        // [0] == nomeFuncao
        // [1...] == argumentos

        double valorTEC = 0;
        if("Determinístico".equals(campos.get(0))) {
            try{
                valorTEC = Double.parseDouble( campos.get(1) );
                this.calculadorTEC = new Deterministico(valorTEC);
            } catch(Exception ex ) {
                this.tela.exibirMensagem("Entrada TEC Inválida", "Entrada Inválida");
                return;
            }
        } else {
            // Reconhecer funcoes
            funcao = this.validarExpressao(campos.get(1));
            if(funcao == null) {
                this.tela.exibirMensagem("Função TEC não reconhecida, verifique a sintaxe!", "Entrada Inválida");
                return;
            } else {
                // Metodo a utilizar
                switch(funcao.get(0)) {
                    case "expo":
                        this.calculadorTEC = new Expo(Double.parseDouble(funcao.get(1)));
                        break;
                    case "uniforme":
                        this.calculadorTEC = new Uniforme(Double.parseDouble(funcao.get(1)), Double.parseDouble(funcao.get(2)));
                        break;
                    case "normal":
                        this.calculadorTEC = new Normal(Double.parseDouble(funcao.get(1)), Double.parseDouble(funcao.get(2)));
                        break;
                    default:
                        this.tela.exibirMensagem("Função TEC não reconhecida, verifique a sintaxe!", "Entrada Inválida");
                        return;
                }

            }
        }

        double valorTS = 0;
        if("Determinístico".equals(campos.get(2))) {
            try{
                valorTS = Double.parseDouble(campos.get(3));
                this.calculadorTS = new Deterministico(valorTS);
            } catch(Exception ex) {
                this.tela.exibirMensagem("Entrada TS Inválida", "Entrada Inválida");
                return;
            }
        } else {
            // Reconhecer funções
            funcao = this.validarExpressao(campos.get(3));
            if(funcao == null) {
                this.tela.exibirMensagem("Função TS não reconhecida, verifique a sintaxe!", "Entrada Inválida");
                return;
            } else {
                // Metodo a utilizar
                switch(funcao.get(0)) {
                    case "expo":
                        this.calculadorTS = new Expo(Double.parseDouble(funcao.get(1)));
                        break;
                    case "uniforme":
                        this.calculadorTS = new Uniforme(Double.parseDouble(funcao.get(1)), Double.parseDouble(funcao.get(2)));
                        break;
                    case "normal":
                        this.calculadorTS = new Normal(Double.parseDouble(funcao.get(1)), Double.parseDouble(funcao.get(2)));
                        break;
                    default:
                        this.tela.exibirMensagem("Função TEC não reconhecida, verifique a sintaxe!", "Entrada Inválida");
                        return;

                }

            }
        }

        try {
            this.tempoSimulacao = Long.parseLong(campos.get(4));
        } catch(Exception ex) {
            this.tela.exibirMensagem("Entrada Tempo de Simulação inválido", "Entrada Inválida");
            return;
        }
        
        this.executarSimulacao();
    }

}
