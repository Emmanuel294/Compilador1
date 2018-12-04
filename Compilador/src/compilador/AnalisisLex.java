/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.awt.Color;
import java.io.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Emmanuel Cobian Zamora y Diego Zaizar Gutierrez.
 * @version 2.0.1
 */
/**
 *En ésta clase se declaran y guardan las palabras reservadas que tiene el lenguaje
 * para depues acceder a ellas y realizar las operaciones requeridas, así tambien 
 * declara algunas variables globales que son requeridas en los métodos.
 */
public class AnalisisLex {
    boolean declarar = false, reservada = false, error = false, asignarval = false, errorv = false;
    String tipo = "", variablea = "";
    javax.swing.JTextArea Consola, variabl;
    Hashtable<String, linkedList> variables = new Hashtable<String, linkedList>();
    public void Lexico(javax.swing.JTextArea taConsola, javax.swing.JTextArea taVar) throws FileNotFoundException, IOException{
        Consola = taConsola;
        variabl = taVar;
        String reservadas[]=new String[8];
        reservadas[0]="completo";
        reservadas[1]="doble";
        reservadas[2]="chain";
        reservadas[3]="car";
        reservadas[4]="placa";
        reservadas[5]="simon";
        reservadas[5]="acabasi";
        reservadas[5]="acabap";
        reservadas[5]="iniciap";
        analizar(reservadas);
    }
    /**
     *Analizar:
     * En éste método se recorre el archivo de texto linea por linea para
     * hacer el analicis léxico y sintactico e ir creando las instrucciones que se
     * piden.
     */
    public void analizar(String reservadas[]) throws FileNotFoundException, IOException{
        File archivo = new File(gLOBALES.archDep);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea = "", cadena = "";
        int i = 0, l = 0;
        Consola.setText("");
        variabl.setText("");
        while((linea=br.readLine())!=null){
            //System.out.println(linea);
            if(linea.contains("iniciap")){
                br.mark(1000);
            }
           while(i<linea.length()){
               if(linea.charAt(i)==' ' || linea.charAt(i)==';'){
                   reservada = false;
                   //System.out.println(cadena);
                   if(cadena.equals("=")){
                       asignarval = true;
                   }
                   else{
                    analizarCad(cadena,reservadas,l);
                   }
                   cadena = "";
               }else{
                   cadena+=linea.charAt(i);
               }
               i++;
           } 
           cadena = "";
           i=0;
           l++;
        }
        if(error==true){
            Consola.setForeground(Color.red);
                    Consola.append("COMPILACIÓN FALLIDA!!\n");
        }
        else{
            Consola.setForeground(Color.BLUE);
                    Consola.append("COMPILACIÓN COMPLETA!!\n");
        }
    }
    /**Analizar cadena:
     *éste método resibe como parametro la cadena a revisar, el arreglo con las palabras reservadas
     * y el número de linea que se está analizando, para posteriormente determinar que instrucciones 
     * son las que éstan en el archivo del código funte.
     */
    public void analizarCad(String cadena, String reservas[], int linea){
        //System.out.println(cadena);
        /**
         *  Se compara la cadena con las palabras reservadas para la declaración de variables, con el
         * fin de determinar si se va a declarar una variable nueva y de que tipo será.
         */
        for(int i=0; i<4;i++){
            if(cadena.equals(reservas[i])){
                if(declarar == true){
                    Consola.setForeground(Color.red);
                    Consola.append("ERROR LINEA "+gLOBALES.lineas.get(linea)+" LA VARIABLE NO PUEDE SER UNA PALABRA RESERVADA\n");
                    error = true;
                    errorv = true;
                }else{
                    errorv = false;
                    declarar = true;
                    reservada = true;
                    switch(reservas[i]){
                        case "completo": tipo = "e";break;
                        case "doble": tipo = "d";break;
                        case "chain": tipo = "cad";break;
                        case "car": tipo = "car";break;
                    }
                }
            }
        }
        
        if(reservada == false){
            if(declarar==true){
                String exp = "[a-zA-Z0-9]*";
                //System.out.println(cadena);
                Pattern patron = Pattern.compile(exp);
                Matcher encaja = patron.matcher(cadena);
                //System.out.println(encaja);
                if(encaja.matches() && errorv == false){
                    if(validarExistencia(cadena,linea)){
                        crearVariable(cadena);
                        variablea = cadena;}
                }else if(!encaja.matches()){
                    Consola.setForeground(Color.red);
                    Consola.append("ERROR LINEA "+gLOBALES.lineas.get(linea)+" DEBES DECLARAR UNA VARIABLE\n");
                    error = true;
                }
                declarar = false;
            }
            else{
                
                if(cadena.contains("imprim") && !error){
                   validarImpresión v = new validarImpresión();
                   gLOBALES.variMPRIM = "";
                   boolean v1 = v.valida(cadena,variables);
                   if(!v1){
                       
                       if(gLOBALES.variMPRIM.equals("")){
                           Consola.setForeground(Color.red);
                        Consola.append("ERROR LINEA "+gLOBALES.lineas.get(linea)+": VARIABLE NO ENCONTRADA\n");
                        error = true;
                       }else{
                       Consola.setForeground(Color.red);
                        Consola.append("ERROR LINEA "+gLOBALES.lineas.get(linea)+": SINTAXIS INCORRECTA EN LA IMPRESION\n");
                        error = true;
                    }
                   }
                   else{
                       Consola.setForeground(Color.green);
                        Consola.append(gLOBALES.variMPRIM+"\n");
                   }
                }
                else if(cadena.contains("placa") && !error){
                    
                }
                else if (asignarval && !error){
                    ValidarTipos t = new ValidarTipos();
                            boolean rvd = t.validar(cadena,tipo);
                            if(rvd){
                                linkedList ll = variables.get(variablea);
                                ll.head.next.next.value = cadena;
                                imprimirTabla(variablea);
                            }
                            else{
                              Consola.setForeground(Color.red);
                                Consola.append("ERROR LINEA "+gLOBALES.lineas.get(linea)+": EL TIPO DE DATO NO CORRESPONDE AL TIPO DE VARIABLE\n");
                                error = true;  
                            }
                            variablea = "";
                }
            }
        }
    }
    public boolean validarExistencia(String cadena, int linea){
        linkedList ll = variables.get(cadena);
        boolean sino = true;
        if(ll != null){
            Consola.setForeground(Color.red);
            Consola.append("ERROR LINEA "+gLOBALES.lineas.get(linea)+": LA VARIABLE "+cadena+" YA ESTÁ DECLARADA\n");
            error = true;  
            sino =  false;
        }
        return sino;
    }
    public void crearVariable(String cadena){
        linkedList ll = new linkedList();
        //System.out.println(cadena);
        Nodo n1 = new Nodo(cadena);
        Nodo n2 = new Nodo(tipo);
        Nodo n3 = new Nodo("N");
        Nodo n4 = new Nodo("N");
        ll.head = n1;
        ll.head.next = n2;
        ll.head.next.next = n3;
        ll.head.next.next.next = n4;
        variables.put(cadena, ll);
        imprimirTabla(cadena);
    }
    public void imprimirTabla(String cadena){
        Nodo h = variables.get(cadena).head;
        linkedList imprim = variables.get(cadena);
        //System.out.println(cadena+"->"+ll);
        while(imprim.head!=null){
            variabl.append("    ["+imprim.head.value+"]           ");
            imprim.head=imprim.head.next;
        }
        variabl.append("\n");
        variables.get(cadena).head = h;
    }
    class linkedList{
        Nodo head;
        int size;
    }
    public class Nodo{
         String value;
         Nodo next;
         Nodo(String d) {value = d; next = null; } 
    }
   
    
    
}
