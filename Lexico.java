import java.util.ArrayList;
import java.util.Scanner;

public class Lexico {
    String caracter, buffer, aux = "";
    int posicion=0;

    String[] reservadas = {"class","integer","real","publico","privado", "var"};
    String[] operador_1 = {"{","}","(",")",":",";",","};

    public static void main(String[] args) {
        Lexico iniciar = new Lexico();
        iniciar.Escaner();
    }
    
    public void Escaner(){
        Scanner entrada = new Scanner(System.in);

        ArrayList<String> tabla1 = new ArrayList<>();
        ArrayList<String> tabla2 = new ArrayList<>();
        
        int t;
        boolean salir = true;
        String aniadir;
        
        System.out.print("Ingrese la cadena: ");
        caracter = entrada.nextLine();

        entrada.close();

        caracter = caracter + "$";
        do{
            t = Scanner();
            switch(t){
                case 0:
                    System.out.println("Programa finalizado");
                    System.out.println("TABLA DE ANALISIS GENERAL:");
                    for(String imprimir : tabla1){
                        System.out.println(imprimir);
                    }
                    System.out.println("\nTABLA DE INDENTIFICADORES: ");
                    for(String imprimir : tabla2){
                        System.out.println(imprimir);
                    }
                    salir = false;
                    break;
                case 100:
                    aniadir = "100 Id ";
                    aniadir += buffer;
                    tabla1.add(aniadir);
                    tabla2.add(aniadir);
                    break;
                case 400:
                case 401:
                case 402:
                case 403:
                case 404:
                case 405:
                    aniadir = t + " Pal.Reserv. " + buffer;
                    tabla1.add(aniadir);
                    break;
                case 2000:
                case 2001:
                case 2002:
                case 2003:
                case 2004:
                case 2005:
                case 2006:
                    aniadir = t + " Operador " + buffer;
                    tabla1.add(aniadir);
                    break;
                default:
                    System.out.println("FINALIZO POR ERROR");
                    salir = false;
                    break;
            }
        }while(salir == true);
    }
    
    public int Scanner(){
        
        int i = 0, e = 0;
        char a;
        buffer = "";

        while(true){
            a = caracter.charAt(posicion);
            
            if(a=='$' && i==0){
                return 0;
            }
            else{
                if (a == '$') {
                    switch(e){
                        case 1:  
                            return Reservada(buffer);
		            }
                }
                else{
                    buffer = buffer.trim(); // Eliminar espacios en blanco alrededor del buffer
                    switch(e){
                        case 0:
                            buffer += a;
                            if(Character.isAlphabetic(a)){
                                    e = 1;  
                                    i++;
                                }
                            else{
                                if(Operador(a)>=2000){
                                    posicion++;
                                    return Operador(a);
                                }
                                else{
                                    if(a != ' '){
                                        return 1000; //bota error y acaba el programa, el posicion++ no es necesario
                                    }
                                    else{
                                        e = 0; //no se para que sirve pero el profe lo tiene en su programa
                                    }
                                }
                            }
                        break;
                        
                        case 1: 
                            if(Character.isDigit(a) || Character.isAlphabetic(a)){
                                buffer += a;
                                e=1;
                            }
                            else{
                                return Reservada(buffer);
                            }
                            i++;
                        break;
                    }
                    posicion++;
                }
            }
        }  
    }    

    public int Reservada(String buffer){     
        for(int i=0;i<reservadas.length;i++){
            if(buffer.equals(reservadas[i])){
                return 400 + i;
            }
        }
        return 100;
    }

    public int Operador(char a){
        String caracter = String.valueOf(a);

        for(int i=0;i<operador_1.length;i++){
            if(caracter.equals(operador_1[i])){
                return 2000 + i;
            }
        }
        return 1000; 
    }
}

