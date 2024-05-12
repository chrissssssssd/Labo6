import java.util.Scanner;

class Lexico {
    String caracter, buffer, temporal;
    int posicion;

    String[] reservadas = {"class","integer","real","publico","privado", "var"};
    String[] operador_1 = {"{","}","(",")",":",";",","};

    public Lexico() {
        posicion = 0;
    }

    public void lectura() {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Leer cadena: ");
        caracter = entrada.nextLine();
        entrada.close();
        caracter = caracter + "$#";
        temporal = caracter;
    }

    public String retEntrada() {
        return temporal;
    }

    public void mover() {
        temporal = temporal.trim();
        String puente = "";
        for (int i = buffer.length(); i < temporal.length(); i++) {
            puente += temporal.charAt(i);
        }
        temporal = puente;
    }

    public String retBuffer() {
        return buffer;
    }

    public int buscar(String x) {
        for (int k = 0; k < reservadas.length; k++) {
            if (x.equals(reservadas[k])) return 400 + k;
        }
        return 100;
    }

    public int scanner() {
        int i = 0, e = 0;
        char a;
        buffer = "";

        while(true){
            a = caracter.charAt(posicion);
            
            if(a=='#' && i==0){
                return 0;
            }
            else{
                if (a == '#') {
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
                                    if(a == '$'){
                                        return 200;
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

    public String ae(int t) {
        switch (t) {
            case 100:
                return "id";
            case 200:
                return "$";
            case 400:
                return "class";
            case 401:
                return "integer";
            case 402:
                return "real";
            case 403:
                return "publico";
            case 404:
                return "privado";
            case 405:
                return "var";
            case 2000:
                return "{";
            case 2001:
                return "}";
            case 2002:
                return "(";
            case 2003:
                return ")";
            case 2004:
                return ":";
            case 2005:
                return ";";
            case 2006:
                return ",";
            default:
                return "";
        }
    }
}
