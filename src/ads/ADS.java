/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lrash0
 */
public class ADS {
    private static  int numeroDePacote = 20;   
    //item a
    public static double inverseTransformation(){
        //Criando valores aleatórios entre 0 e 1
        Random vRandom = new Random();
        //x = (ln(u) / lambda )* -1
        double u = vRandom.nextDouble();
        return -12 * log(u);
        
    }
    
    
    //item b
    public static double rejection(){
        Random vRandom = new Random();
        int media = 10;
        int dvPadrao = 1;
        double u1 = vRandom.nextDouble();
        double u2 = vRandom.nextDouble();
        double x = -1*(log(u1));
        double base = pow((x-1),2);
        double resultadoFinal = exp((-1*base)/2);
        while(u2 > resultadoFinal){
            u1 = vRandom.nextDouble();
            u2 = vRandom.nextDouble();
            x = -1*(log(u1));
            base = pow((x-1),2);
            resultadoFinal = exp(-1*(base)/2);
        }
        double u3 = vRandom.nextDouble();
        if(u3 > 0.5){
            return 10 + dvPadrao*x;
        }else{
            return 10 - dvPadrao*x;
        }
        
    }
    
    public static void main(String[] args) {
        
        comecar();
    }

    public static void tempoUltimaChegar(List<Double> tempoUltCh) {
        //To change body of generated methods, choose Tools | Templates.
        double tmp;
        for(int i = 0 ; i < numeroDePacote  ; ++i){
//            tmp = formatar(inverseTransformation());
            tempoUltCh.add(inverseTransformation());
        }
    }

    

    public static void tempoIniServ(List<Double> tempoIniServ) {
         //To change body of generated methods, choose Tools | Templates.
         double tmp;
         for(int i = 0 ; i < numeroDePacote ; ++i){
//             tmp = formatar(rejection());
             tempoIniServ.add(rejection());
         }
    }
    public static void tempoChegadaRelogio(List<Double> tempoUltCh,List<Double> tempoChRelogio){ 
        //Primeiro tempo de chegada
        tempoChRelogio.add(tempoUltCh.get(0));
        //Demais casos.Soma o tempo da chegada do pacote anterior com o atual
        for(int i = 1 ; i < numeroDePacote ; i++){
            tempoChRelogio.add(tempoUltCh.get(i-1)+ tempoUltCh.get(i));
        }
    }
    public static void tempoIncServRl(List<Double> tempoChRelogio, List<Double> tempoServ, List<Double> tempoInicServRelogio) {
        
        //Primeiro caso.Primeiro pacote.
        if(tempoChRelogio.get(0)<= tempoServ.get(0)){
            
            tempoInicServRelogio.add(tempoChRelogio.get(0));
            
        }else{
            
            tempoInicServRelogio.add(tempoServ.get(0) + tempoChRelogio.get(0));
        }
        
        for(int i = 1 ; i < numeroDePacote ; ++i){
            //Se o tempo do relogio atual for menor ou igual a soma do tempo gasto do pacote anterior,adicona o tempo do relogio atual
            //Caso contrário,adicona a soma do tempo gasto do pacote anterior.
            if(tempoChRelogio.get(i) <= tempoServ.get(i-1) + tempoChRelogio.get(i-1)){
                
                tempoInicServRelogio.add(tempoChRelogio.get(i));
                
            }else{
                
                double tmp = tempoServ.get(i-1) + tempoChRelogio.get(i-1);
                
                tempoInicServRelogio.add(tmp);
            }
        }
    }
    
    public static void tempoFila(List<Double> tempoChRelogio,
            List<Double> tempoInicServRelogio,List<Double> tempoCltFila){
        for(int i = 0 ; i < numeroDePacote ; ++i){
            tempoCltFila.add(tempoInicServRelogio.get(i) - tempoChRelogio.get(i));
        }
        
        
    }
    
    public static void tempoFinalServ(List<Double> tempoInicServRelogio,
            List<Double> tempoServ, List<Double> tempoFinalServRelogio) {
         for(int i = 0 ; i < numeroDePacote ; ++i){
            tempoFinalServRelogio.add(tempoInicServRelogio.get(i) + tempoServ.get(i)) ;
         }
    }
    
    public static void tempoCltNoSistema(List<Double> tempoCltFila,
            List<Double> tempoServ, List<Double> tempoCltSist) {
         for(int i = 0 ; i < numeroDePacote ; ++i){
             tempoCltSist.add(tempoCltFila.get(i) + tempoServ.get(i));
         }
    }
    
    public static void tempoLivre(List<Double> tempoFinalServRelogio,
            List<Double> tempoCltSist, List<Double> tempoLivreOp) {
        tempoLivreOp.add(tempoFinalServRelogio.get(0) - tempoCltSist.get(0));
        for(int i = 1 ; i < numeroDePacote ; ++i){
            tempoLivreOp.add(tempoFinalServRelogio.get(i) -
                    (tempoFinalServRelogio.get(i-1) + tempoCltSist.get(i)));
        }
    }
   
    public static void comecar(){
        List<Double> tempoUltCh = new ArrayList<Double>();
        List<Double> tempoServ = new ArrayList<Double>();
        List<Double> tempoChRelogio = new ArrayList<Double>();
        List<Double> tempoInicServRelogio = new ArrayList<Double>();
        List<Double> tempoCltFila = new ArrayList<Double>();
        List<Double> tempoFinalServRelogio = new ArrayList<Double>();
        List<Double> tempoCltSist = new ArrayList<Double>();
        List<Double> tempoLivreOp = new ArrayList<Double>();
        tempoUltimaChegar(tempoUltCh); //2)Tempo desde a última chegada do pacote anterior
        tempoIniServ(tempoServ); //4)Tempo de serviço ou tempo de roteamento (microssegundos) 
        tempoChegadaRelogio(tempoUltCh,tempoChRelogio);//3)Tempo de chegada no relógio
        tempoIncServRl(tempoChRelogio,tempoServ,tempoInicServRelogio); //5)Tempo de início do roteamento (microssegundos) 
        tempoFila(tempoChRelogio,tempoInicServRelogio,tempoCltFila); //6)Tempo do pacote na fila do roteador (microssegundos)
        tempoFinalServ(tempoInicServRelogio,tempoServ,tempoFinalServRelogio);//7) Tempo final do roteamento no relógio
        tempoCltNoSistema(tempoCltFila,tempoServ,tempoCltSist);//8)Tempo do pacote no roteador (microssegundos), ou seja, fila + roteamento 
        tempoLivre(tempoFinalServRelogio,tempoCltSist,tempoLivreOp);//9)Tempo livre do servidor do roteador ou tempo que o servidor do roteador ficou ocupado (microssegundos) 
        salvarArquivo(tempoUltCh,tempoServ,tempoChRelogio,tempoInicServRelogio,tempoCltFila,
                tempoFinalServRelogio,tempoCltSist,tempoLivreOp);
        
        System.out.println("Tempo ultima chegada");
        exibir(tempoUltCh);
        System.out.println("Tempo de chegada no relogio");
        exibir(tempoChRelogio);
        System.out.println("Tempo de Serviço");
        exibir(tempoServ);
        System.out.println("Tempo de Inicio de serviço no relogio");
        exibir(tempoInicServRelogio);
        
        
    }
    
    public static void exibir(List<Double> lista) {
        //To change body of generated methods, choose Tools | Templates.
        int soma = 0;
        for(Double t : lista){
            System.out.println(t);
            soma+=t;
        }
        System.out.println("Soma: "+soma/numeroDePacote);
    }
    
    public static double formatar(double tmp){
        DecimalFormat df = new DecimalFormat("#,##");
        String str;
        str = df.format(inverseTransformation());
        
        return Double.parseDouble(str);
        
    }

    private static void salvarArquivo(List<Double> tempoUltCh, List<Double> tempoServ, 
        List<Double> tempoChRelogio,List<Double> tempoInicServRelogio,
        List<Double> tempoCltFila,List<Double> tempoFinalServRelogio,
        List<Double> tempoCltSist,List<Double> tempoLivreOp) {
        
        FileWriter arq = null;
        
        try {
            //Mude aqui o local do arquivo
            arq = new FileWriter("Local do Arquivo");
            PrintWriter gravarArq = new PrintWriter(arq);
            
            List<String> colunas = new ArrayList<String>();
            //Inicializa o arraylist de acordo com as colunas solicitadas no trabalho
            inicializarColunas(colunas);
        
            for(int i = 0 ; i < colunas.size(); ++i){
            
                gravarArq.printf(colunas.get(i)+";");
            
            }   
            
            gravarArq.printf("\n");
            
            for(int i = 0 ; i < numeroDePacote ; ++i){
                gravarArq.printf(i+";"+tempoUltCh.get(i)+";"+tempoChRelogio.get(i)+";"
                        +tempoServ.get(i)+";"+tempoInicServRelogio.get(i)+";"+tempoCltFila.get(i)+";"
                        +tempoFinalServRelogio.get(i)+";"+tempoCltSist.get(i)+";"
                        +tempoLivreOp.get(i)+";"+"\n");
                
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
