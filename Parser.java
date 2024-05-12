class Parser {
    private String M[][] = new String[13][16] ;//Tabla del TAS
    private String vt [] = {"{", "}", "id", "class", ";", "publico", "privado", ":", "(", ")", "integer", "real", "var", ",", "$"}; 
    private String pila[] = new String [1000];
    private int i, t;
    
    //Contruccion de la tabla TAS
    public Parser(){
        pila[0] = "$";//Se inicializa la pila
        pila[1] = "S";
        i=2;
        for(int i=0;i<13;i++){
            for(int j=0;j<16;j++){
                M[i][j]=" ";
            }
        }
                                    M[0][1] = "{"; M[0][2] = "}";  M[0][3] = "id";                            M[0][4] = "class";                                 M[0][5] = ";"; M[0][6] = "publico";            M[0][7] = "privado";           M[0][8] = ":";                            M[0][9] = "(";                                M[0][10] = ")";  M[0][11] = "integer";  M[0][12] = "real";    M[0][13] = "var";                                 M[0][14] = ",";                          M[0][15] = "$"; 
                                        
        M[1][0] = "S";                                                                                        M[1][4] = "Seguir ; } Cuerpo2 Cuerpo { id class";                  
        M[2][0] = "Seguir";                                                                                   M[2][4] = "S";                                                                                                                                                                                                                                                                                                                                                                    M[2][15] = "&";                                                                                                                                                                                                                                                               
        M[3][0] = "Cuerpo";                        M[3][2] = "&";  M[3][3] = "Elegir id";                                                                                       M[3][6] = "&";                  M[3][7] = "&";
        M[4][0] = "Elegir";                                                                                                                                                                                                                    M[4][8] = "Cuerpo ; Tipo : Agregar";     M[4][9] = "Prototipos";                                                                                                                                        M[4][14] = "Cuerpo ; Tipo : Agregar";
        M[5][0] = "Prototipos";                                                                                                                                                                                                                                                         M[5][9] = "Continuar ; Tipo : ) Linea (";                         
        M[6][0] = "Continuar";                     M[6][2] = "&";  M[6][3] = "Prototipos id";                                                                                   M[6][6] = "&";                  M[6][7] = "&";                      
        M[7][0] = "Cuerpo2";                       M[7][2] = "&";                                                                                                               M[7][6] = "Cuerpo : publico";   M[7][7] = "Cuerpo : privado";                     
        M[8][0] = "Linea";                                         M[8][3] = "Agregar2 Tipo : Agregar id";                                                                                                                                                                                                                            M[8][10] = "&";                                               M[8][13] = "Agregar2 Tipo : Agregar id Var";                                                                                                                                                                                                                                                                                  
        M[9][0] = "Agregar";                                                                                                                                                                                                                   M[9][8] = "&";                                                                                                                                                                                         M[9][14] = "Agregar id ,";                                                                                             
        M[10][0] = "Tipo";                                                                                                                                                                                                                                                                                                                             M[10][11] = "integer"; M[10][12] = "real";                      
        M[11][0] = "Agregar2";                                                                                                                                                                                                                                                                                                        M[11][10] = "&";                                                                                                M[11][14] = "Linea ,";                        
        M[12][0] = "Var";                                          M[12][3] = "&";                                                                                                                                                                                                                                                                                                                  M[12][13] = "var";    }

    public int terminal(String car) {
        for (int i = 0; i < vt.length; i++) {
            if (car.equals(vt[i])) return 1;
        }
        return 0;
    }

    public int interseccion(String XX, String ae, StringBuilder produccion) {
        int x = 0, y = 0;
        for (int i = 0; i < 16; i++) {
            if (ae.equals(M[0][i])) x = i;
        }
        for (int i = 0; i < 13; i++) {
            if (XX.equals(M[i][0])) y = i;
        }
        if (x == 0 || y == 0){
            return 0;
        }
        else if (M[y][x].equals(" ")){
            return 0;
        } 
        else {
            produccion.setLength(0);
            produccion.append(M[y][x]);
            return 1;
        }
    }

    public String retPila() {
        StringBuilder CadPila = new StringBuilder();
        for (int j = 0; j < i; j++) {
            CadPila.append(pila[j]);
        }
        return CadPila.toString();
    }

    public void empilar(String produccion) {
        int k = i;
        pila[i] = "";
        for (int j = 0; j < produccion.length(); j++) {
            if (produccion.charAt(j) != ' ') {
                pila[i] += produccion.charAt(j);
            } else {
                i++;
                pila[i] = "";
                k = i;
            }
        }
        if (k == i) i++;
    }

    public void sintactico() {
        
        Lexico lex = new Lexico();
        String XX;
        StringBuilder produccion = new StringBuilder();
        lex.lectura();
        t = lex.scanner();
        do {
            XX = pila[i - 1];
            System.out.println(retPila() + "      " + lex.retEntrada());
            if (terminal(XX) == 1) {
                if (XX.equals(lex.ae(t))) {
                    i--;
                    lex.mover();
                    t = lex.scanner();
                } else {
                    error();
                }
            } else {
                if (interseccion(XX, lex.ae(t), produccion) == 1) {
                    System.out.println("\n" + XX + "-->" + produccion + "\n");
                    i--;
                    if (!produccion.toString().equals("&")) {
                        empilar(produccion.toString());
                    }
                } else {
                    error();
                }
            }
        } while (!"$".equals(XX));
        System.out.println("\n analisis correcto");
    }

    private void error() {
        System.out.println("error de sintaxis");
        System.exit(0);
    }
}