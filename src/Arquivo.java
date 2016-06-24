
import ads.ADS;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lrash0
 */
public class Arquivo {
     private static int numeroDePacote = 20;
     public static void salvarArquivo(List<Double> tempoUltCh, List<Double> tempoServ,
            List<Double> tempoChRelogio, List<Double> tempoInicServRelogio,
            List<Double> tempoCltFila, List<Double> tempoFinalServRelogio,
            List<Double> tempoCltSist, List<Double> tempoLivreOp) {

        FileWriter arq = null;

        try {
            //Mude aqui o local do arquivo
            arq = new FileWriter("Coloque o camainho para salvar o arquivo");
            PrintWriter gravarArq = new PrintWriter(arq);

            List<String> colunas = new ArrayList<String>();
            //Inicializa o arraylist de acordo com as colunas solicitadas no trabalho
            inicializarColunas(colunas);

            for (int i = 0; i < colunas.size(); ++i) {

                gravarArq.printf(colunas.get(i) + ";");

            }

            gravarArq.printf("\n");

            for (int i = 0; i < numeroDePacote; ++i) {
                gravarArq.printf(i + ";" + tempoUltCh.get(i) + ";" + tempoChRelogio.get(i) + ";"
                        + tempoServ.get(i) + ";" + tempoInicServRelogio.get(i) + ";" + tempoCltFila.get(i) + ";"
                        + tempoFinalServRelogio.get(i) + ";" + tempoCltSist.get(i) + ";"
                        + tempoLivreOp.get(i) + ";" + "\n");

            }

            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(ADS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {

                if (arq != null) {

                    arq.close();
                }

            } catch (IOException ex) {

                Logger.getLogger(ADS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Colunas do arquivo.
    public static void inicializarColunas(List<String> colunas) {
        colunas.add("Número do pacote");
        colunas.add("Tempo desde a última chegada do pacote anterior (microssegundos)");
        colunas.add("Tempo de chegada no relógio");
        colunas.add("Tempo de serviço ou tempo de roteamento (microssegundos)");
        colunas.add("Tempo de início do roteamento (microssegundos)");
        colunas.add("Tempo do pacote na fila do roteador (microssegundos)");
        colunas.add("Tempo final do roteamento no relógio");
        colunas.add("Tempo do pacote no roteador (microssegundos)");
        colunas.add("Tempo livre do servidor do roteador(microssegundos)");
    }
    
}
