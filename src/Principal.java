
import ads.ADS;
import static ads.ADS.comecar;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lrash0
 */
public class Principal {
    public static void main(String[] args) {
        List<Double> tempoUltCh = new ArrayList<Double>();
        List<Double> tempoServ = new ArrayList<Double>();
        List<Double> tempoChRelogio = new ArrayList<Double>();
        List<Double> tempoInicServRelogio = new ArrayList<Double>();
        List<Double> tempoCltFila = new ArrayList<Double>();
        List<Double> tempoFinalServRelogio = new ArrayList<Double>();
        List<Double> tempoCltSist = new ArrayList<Double>();
        List<Double> tempoLivreOp = new ArrayList<Double>();
        ADS.comecar(tempoUltCh, tempoServ, tempoChRelogio, tempoInicServRelogio, tempoCltFila,
                tempoFinalServRelogio, tempoCltSist, tempoLivreOp);
        Arquivo.salvarArquivo(tempoUltCh, tempoServ, tempoChRelogio, tempoInicServRelogio, tempoCltFila, tempoFinalServRelogio, tempoCltSist, tempoLivreOp);
    }
    
}
