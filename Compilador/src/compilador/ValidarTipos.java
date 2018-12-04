/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import compilador.AnalisisLex.linkedList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author emmanuelcobian
 */
public class ValidarTipos {
    public boolean validar(String valor, String tipo){
        boolean sino = false;
        gLOBALES.varAsignar = "";
        switch(tipo){
            case "e":
                String exp = "[0-9]*";
                //System.out.println(cadena);
                Pattern patron = Pattern.compile(exp);
                Matcher encaja = patron.matcher(valor);
                if(encaja.matches()){
                    gLOBALES.varAsignar = valor;
                    sino = true;
                }
                else{sino = false;}
            break;
            case "d": 
                String expd = "[0-9]*.?[0-9]*";
                //System.out.println(cadena);
                Pattern patrond = Pattern.compile(expd);
                Matcher encajad = patrond.matcher(valor);
                if(encajad.matches()){
                    gLOBALES.varAsignar = valor;
                    sino = true;
                }
                else{sino = false;}
            break;
            case "car": 
                String expcr = "\'[a-zA-Z0-9]{1}\'";
                //System.out.println(cadena);
                Pattern patroncr = Pattern.compile(expcr);
                Matcher encajacr = patroncr.matcher(valor);
                if(encajacr.matches()){
                    gLOBALES.varAsignar = valor;
                    sino = true;
                }
                else{sino = false;}
            break;
            case "cad": 
                String expc = "\"[a-zA-Z0-9_]*\"";
                //System.out.println(cadena);
                Pattern patronc = Pattern.compile(expc);
                Matcher encajac = patronc.matcher(valor);
                if(encajac.matches()){
                    gLOBALES.varAsignar = valor;
                    sino = true;
                }
                else{sino = false;}
            break;
        }
        return sino;
    }
}
