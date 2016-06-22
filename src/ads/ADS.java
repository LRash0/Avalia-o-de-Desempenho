/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ads;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author lrash0
 */
public class ADS {

    
    //item a
    public static double inverseTransformation(){
        //Criando valores aleatórios entre 0 e 1
        Random vRandom = new Random();
        //x = (ln(u) / lambda )* -1
        double u = vRandom.nextDouble();
        int media = 12;
        return (log(u)/media)*(-1);
        
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
            base = pow(-1 * (x-1),2);
            resultadoFinal = exp(base/2);
        }
        double u3 = vRandom.nextDouble();
        if(u3 > 0.5){
            return 10 + dvPadrao*x;
        }else{
            return 10 - dvPadrao*x;
        }
        
    }
    
    public static void main(String[] args) {
        
            
        rejection();
        
        // TODO code application logic here
    }
    
}
