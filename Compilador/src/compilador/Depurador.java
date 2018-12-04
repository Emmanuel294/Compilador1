/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author emmanuelcobian
 */
public class Depurador {
    public void depurar(){
            //System.out.println(GlobalesInterfaz.ruta);
            File archivo = new File(GlobalesInterfaz.ruta);
            String cbh = GlobalesInterfaz.ruta.substring(0,GlobalesInterfaz.ruta.length()-4)+".cbh";
            gLOBALES.archDep = cbh;
            try {
               FileReader fileR = new FileReader(archivo);
               String line = "";
                BufferedReader br = new BufferedReader(fileR);
                //System.out.println(archivo);
                int i=0, lineas = 0;
                StringBuilder sb = new StringBuilder();
                boolean comment = false;
                String linea = "";
            while((line=br.readLine())!=null){
                lineas++;
                line=eliminarEspacios(line);
                if(i>=line.length()-1)comment = true;
                while(i<line.length()-1 && line.charAt(i) !=';'){
                  if(line.charAt(i) == '-'){
                    if(i+1<line.length()-1 && line.charAt(i+1)=='-'){
                        comment = true;
                        break;
                    }
                    else{
                       linea+=line.charAt(i)+"";
                    }
                    }
                  else{
                  linea+=line.charAt(i)+"";
                  }
                  i++;
                }
                if(!comment){
                    sb.append(linea+";\n");
                    gLOBALES.lineas.add(lineas);
                }
               // System.out.println(sb);
                linea="";
                i=0;
                comment = false;
            }
            JFileChooser select = new JFileChooser();
                //System.out.println(GlobalesInterfaz.ruta);
                File archivo1 = new File(cbh);
                   FileWriter fileR1 = new FileWriter(archivo1);
                   String sbf =sb.substring(0, sb.length()-1);
                   fileR1.write(sbf);
                   fileR1.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public static String eliminarEspacios(String linea){
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(linea);
        StringBuilder buff = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            buff.append(" ").append(tokens.nextToken());
        }
        return buff.toString().trim();
    }
}
