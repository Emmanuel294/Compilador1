/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import compilador.AnalisisLex.linkedList;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author emmanuelcobian
 */

public class validarImpresión {
    //Hashtable<String, linkedList> variables = new Hashtable<String, linkedList>();
    public boolean valida(String cadena, Hashtable <String,linkedList> variables){
        int i = 0, tope = -1, cti = 0;
        boolean sino = false;
        String variable = "", scad = cadena.substring(6,cadena.length());
        ArrayList<String> imprim = new ArrayList<String>();
        int ctc = 0;
        if(cadena.contains("\"")){
           
            while(i<scad.length()){
                //System.out.print(scad.charAt(i));
                if(scad.charAt(i)=='"'){
                    ctc ++ ;
                    i++;
                }
                if(ctc == 1){
                    variable+=scad.charAt(i);
                }
                i++;
            }
            variable = variable.substring(1,variable.length());
            gLOBALES.variMPRIM=variable;
            if(ctc == 2){
                sino = true;
            }
            else{sino = false;}
            
            return sino;
        }else{
            while(i<scad.length()-1){
                if(scad.charAt(i)=='('){
                    imprim.add(scad.charAt(i)+"");
                    tope++;
                }
                else if(scad.charAt(i)==')'){
                    if(tope>=0){
                        if(imprim.get(tope).equals("(")){
                            tope--;
                        }
                        else{sino = false; break;}
                    }else{
                        sino = false;
                        break;
                    }
                }
                else if(scad.charAt(i)!=';' && scad.charAt(i)!='(' && scad.charAt(i)!=')'){
                    variable+=scad.charAt(i)+"";
                }
                i++;
            }
            linkedList ll;
            ll =  variables.get(variable);
            //System.out.println(cadena+"->"+ll);
            if(ll!= null){
                gLOBALES.variMPRIM = ll.head.next.next.value;
                sino = true;
            }
            else{
                sino = false;
            }
        }
        return sino;
    }
       
}
