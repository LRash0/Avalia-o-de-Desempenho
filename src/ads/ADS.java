/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lrash0
 */
public class ADS {

    //1)  Número do pacote (ordem de chegada do pacote). No máximo 20 pacotes.
//    private static int numeroDePacote = 15;
    private static int numeroDePacote = 20;

    //item a
    public static Double inverseTransformation() {
        //Criando valores aleatórios entre 0 e 1
        Random vRandom = new Random();
        Double u = vRandom.nextDouble();
        return -12 * log(u);

    }

    //item b
    public static Double rejection() {
        Random vRandom = new Random();
        int media = 10;
        int dvPadrao = 1;
        Double u1 = vRandom.nextDouble();
        Double u2 = vRandom.nextDouble();
        Double x = -1 * (log(u1));
        Double base = pow((x - 1), 2);
        Double resultadoFinal = exp((-1 * base) / 2);
        
        while (u2 > resultadoFinal) {
            u1 = vRandom.nextDouble();
            u2 = vRandom.nextDouble();
        
            x = -1 * (log(u1));
            
            base = pow((x - 1), 2);
            
            resultadoFinal = exp(-1 * (base) / 2);
        }
        
        Double u3 = vRandom.nextDouble();
        
        if (u3 > 0.5) {
            
            return 10 + dvPadrao * x;
        } else {
            
            return 10 - dvPadrao * x;
        }

    }

    //2) Tempo desde a última chegada do pacote anterior (microssegundos)
    public static void tempoUltimaChegar(List<Double> tempoUltCh) {
        DecimalFormat df = new DecimalFormat("#,##");
        Double tmp;
        String str;
        
        for (int i = 0; i < numeroDePacote; ++i) {

            str = df.format(inverseTransformation());

            tmp = Double.parseDouble(str);

            tempoUltCh.add(tmp);
//            tempoUltCh.add(inverseTransformation());
            
        }

    }

    //3) Tempo de chegada no relógio
    public static void tempoIniServ(List<Double> tempoIniServ) {
        
        DecimalFormat df = new DecimalFormat("#,######");
        Double tmp;
        String str;
        
        for (int i = 0; i < numeroDePacote; ++i) {

            str = df.format(rejection());

            tmp = Double.parseDouble(str);

            tempoIniServ.add(tmp);
//            tempoIniServ.add(rejection());
            
        }

    }

    //4) Tempo de serviço ou tempo de roteamento (microssegundos) 
    public static void tempoChegadaRelogio(List<Double> tempoUltCh, List<Double> tempoChRelogio) {
        //Primeiro tempo de chegada
        tempoChRelogio.add(tempoUltCh.get(0));

        //Demais casos.Soma o tempo da chegada do pacote anterior com o atual
        for (int i = 1; i < numeroDePacote; i++) {

            tempoChRelogio.add(tempoChRelogio.get(i - 1) + tempoUltCh.get(i));

        }
    }

    //5) Tempo de início do roteamento (microssegundos) 
    public static void tempoIncServRl(List<Double> tempoChRelogio,
            List<Double> tempoServ, List<Double> tempoInicServRelogio, List<Double> tempoCltFila) {

        //Primeiro pacote
        tempoInicServRelogio.add(tempoChRelogio.get(0));

        //Tempo de fila do primeiro pacote.
        tempoFila(tempoChRelogio, tempoInicServRelogio, tempoCltFila, 0);

        Double tmp;

        for (int i = 1; i < numeroDePacote; ++i) {

            //Se o tempo do relogio atual for maior ou igual a soma do tempo gasto do pacote anterior,
            //adicona o tempo do relogio atual
            //Caso contrário,adicona a soma do tempo gasto do pacote anterior.
            tmp = tempoServ.get(i - 1) + tempoChRelogio.get(i - 1) + tempoCltFila.get(i - 1);

            if (tempoChRelogio.get(i) >= tmp) {

                tempoInicServRelogio.add(tempoChRelogio.get(i));

            } else {

                tempoInicServRelogio.add(tmp);
            }
            //Tempo de fila dos demais pacotes
            tempoFila(tempoChRelogio, tempoInicServRelogio, tempoCltFila, i);
        }
    }

    //6) Tempo do pacote na fila do roteador (microssegundos)
    public static void tempoFila(List<Double> tempoChRelogio,
            List<Double> tempoInicServRelogio, List<Double> tempoCltFila, int i) {
        
        tempoCltFila.add(tempoInicServRelogio.get(i) - tempoChRelogio.get(i));

    }

    //7) Tempo final do roteamento no relógio
    public static void tempoFinalServ(List<Double> tempoInicServRelogio,
            List<Double> tempoServ, List<Double> tempoFinalServRelogio) {

        for (int i = 0; i < numeroDePacote; ++i) {

            tempoFinalServRelogio.add(tempoInicServRelogio.get(i) + tempoServ.get(i));

        }
    }

    //8) Tempo do pacote no roteador (microssegundos)
    public static void tempoCltNoSistema(List<Double> tempoFinalServRelogio,
            List<Double> tempoInicServRelogio, List<Double> tempoCltFila, List<Double> tempoCltSist) {

        for (int i = 0; i < numeroDePacote; ++i) {

            tempoCltSist.add((tempoFinalServRelogio.get(i) + tempoCltFila.get(i)) - tempoInicServRelogio.get(i));
        }
    }

    //9) Tempo livre do servidor do roteador ou tempo que o servidor do roteador ficou ocupado
    public static void tempoLivre(List<Double> tempoFinalServRelogio, List<Double> tempoServ, List<Double> tempoCltFila, List<Double> tempoLivreOp) {

        tempoLivreOp.add(tempoFinalServRelogio.get(0) - tempoServ.get(0));

        for (int i = 1; i < numeroDePacote; ++i) {
            //Tempo livre do operador é igual a diferença entre o tempo que o pacote atual,a soma do tempo do pacote anterior e o tempo de serviço do pacote atual
            tempoLivreOp.add(tempoFinalServRelogio.get(i) - ( tempoFinalServRelogio.get(i - 1) + tempoServ.get(i)));
        }
    }

    public static void comecar(List<Double> tempoUltCh, List<Double> tempoServ,
            List<Double> tempoChRelogio, List<Double> tempoInicServRelogio,
            List<Double> tempoCltFila, List<Double> tempoFinalServRelogio,
            List<Double> tempoCltSist, List<Double> tempoLivreOp) {

        tempoUltimaChegar(tempoUltCh); //2)Tempo desde a última chegada do pacote anterior
//        detempoUltimaChegar(tempoUltCh);
        tempoIniServ(tempoServ); //4)Tempo de serviço ou tempo de roteamento (microssegundos) 
//        detempoIniServ(tempoServ);
        tempoChegadaRelogio(tempoUltCh, tempoChRelogio);//3)Tempo de chegada no relógio

        tempoIncServRl(tempoChRelogio, tempoServ, tempoInicServRelogio, tempoCltFila); //5) e 6

        tempoFinalServ(tempoInicServRelogio, tempoServ, tempoFinalServRelogio);//7) Tempo final do roteamento no relógio

        tempoCltNoSistema(tempoFinalServRelogio, tempoInicServRelogio, tempoCltFila, tempoCltSist);//8)Tempo do pacote no roteador (microssegundos), ou seja, fila + roteamento 

        tempoLivre(tempoFinalServRelogio, tempoServ, tempoCltFila, tempoLivreOp);//9)Tempo livre do servidor do roteador ou tempo que o servidor do roteador ficou ocupado (microssegundos) 

        /* System.out.println("Tempo ultima chegada");
        exibir(tempoUltCh);
        System.out.println("Tempo de chegada no relogio");
        exibir(tempoChRelogio);
        System.out.println("Tempo de Serviço");
        exibir(tempoServ);
        System.out.println("Tempo de Inicio de serviço no relogio");
        exibir(tempoInicServRelogio);*/
    }

    /*public static void exibir(List<Double> lista) {
        //To change body of generated methods, choose Tools | Templates.
        int soma = 0;
        for(Double t : lista){
            System.out.println(t);
            soma+=t;
        }
        System.out.println("Soma: "+soma/numeroDePacote);
    }*/
    public static void detempoUltimaChegar(List<Double> tempoUltCh) {

//        Valores teste
        tempoUltCh.add(15.0);
        tempoUltCh.add(12.0);
        tempoUltCh.add(10.0);
        tempoUltCh.add(10.0);
        tempoUltCh.add(12.0);
        tempoUltCh.add(15.0);
        tempoUltCh.add(10.0);
        tempoUltCh.add(12.0);
        tempoUltCh.add(10.0);
        tempoUltCh.add(10.0);
        tempoUltCh.add(10.0);
        tempoUltCh.add(12.0);
        tempoUltCh.add(15.0);
        tempoUltCh.add(12.0);
        tempoUltCh.add(12.0);

    }

    public static void detempoIniServ(List<Double> tempoIniServ) {
        tempoIniServ.add(11.0);
        tempoIniServ.add(10.0);
        tempoIniServ.add(9.0);
        tempoIniServ.add(10.0);
        tempoIniServ.add(9.0);
        tempoIniServ.add(10.0);
        tempoIniServ.add(11.0);
        tempoIniServ.add(9.0);
        tempoIniServ.add(11.0);
        tempoIniServ.add(10.0);
        tempoIniServ.add(11.0);
        tempoIniServ.add(9.0);
        tempoIniServ.add(10.0);
        tempoIniServ.add(9.0);
        tempoIniServ.add(11.0);

    }
}
