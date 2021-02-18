package laboratorioprogra.pkg1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class LaboratorioProgra {
//Definicion de las variables a usar

    public static Scanner entrada = new Scanner(System.in);
    public static int[][] matriz_m = null, matriz_a = null, matriz_b = null;
    public static int[][] matriz_ret = null, inversa = null, matriz_desencriptada=null;
    public static int [][] matriz_inversa = null;
    public static int[][] matriz_original = null, matriz_cifrado = null, C = null, matriz_resta;

    public static void main(String[] args) {

        byte entrada_opcion = 0;
        do {
            System.out.println("=========Bienvenido al menu=========");
            System.out.println("|Seleccione una opcion              |");
            System.out.println("|Opcion 1... Encriptar              |");
            System.out.println("|Opcion 2... Desencriptar           |");
            System.out.println("|Opcion 3... Ataque con texto claro |");
            System.out.println("|Opcion 4... Generar reportes       |");
            System.out.println("|Opcion 5... Salir                  |");
            System.out.println("=====================================");
            entrada_opcion = Byte.valueOf(entrada.nextLine());

            switch (entrada_opcion) {

                case 1:
                    encriptar();
                    break;
                case 2:
                    desencriptar();
                    break;
                case 3:
                    ataquetexto();
                    break;
                case 4:
                    generarreportes();
                    break;
                case 5:
                    salir();
                default:
                    entrada_opcion = 0;
                    break;

            }
        } while (entrada_opcion != 0);

    }

    public static void encriptar() {
        byte submenu_en = 0;

        do {
            System.out.println("========Elija una opcion========");
            System.out.println("Opcion 1... Ingresar mensaje");
            System.out.println("Opcion 2... Ingresar matriz clave A");
            System.out.println("Opcion 3... Ingresar matriz clave B");
            System.out.println("Opcion 4... Encriptar mensaje");
            System.out.println("Opcion 5... Menu Principal");
            System.out.println("=====================================");
            submenu_en = Byte.valueOf(entrada.nextLine());
            switch (submenu_en) {
                case 1:
                    matriz_m = ingresar_mensaje();
                    break;
                case 2:
                    matriz_a = ingresar_matriz();
                    break;
                case 3:
                    matriz_b = ingresar_matriz();
                    break;
                case 4:
                    Encriptar(matriz_m, matriz_a, matriz_b);
                    break;
                default:
                    submenu_en = 0;
                    break;
            }
        } while (submenu_en != 0);
    }

    public static int[][] ingresar_mensaje() {
        //Ingreso de mensaje
        System.out.println("Ingrese el mensaje");
        String entrada_men = entrada.nextLine();
        //Convertir a minusculas
        entrada_men = entrada_men.toLowerCase();
        char matriz_temp[] = entrada_men.toCharArray();
        int vector[][] = new int[3][(int) Math.ceil(matriz_temp.length / 3.0)];
        for (int i = 0; i < matriz_temp.length; i++) {
            if ((matriz_temp[i] >= 97 & matriz_temp[i] <= 122) || matriz_temp[i] == 32) {
            } else {
                System.out.println("No existe ese caracter");
                return null;
            }
            if (matriz_temp[i] == 32) {
                vector[i % 3][(int) Math.floor(i / 3.0)] = 27;
            } else if (matriz_temp[i] < 111) {
                vector[i % 3][(int) Math.floor(i / 3.0)] = matriz_temp[i] - 97;
            } else {
                vector[i % 3][(int) Math.floor(i / 3.0)] = matriz_temp[i] - 96;

            }

        }
        if (matriz_temp.length % 3 == 2) {
            vector[2][vector[0].length - 1] = 27;
        } else if (matriz_temp.length % 3 == 1) {
            vector[2][vector[0].length - 1] = vector[1][vector[0].length - 1] = 27;
        }
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector[0].length; j++) {
                System.out.print("|" + vector[i][j] + "|");
            }
            System.out.println("");
        }
        return vector;
    }

    private static int[][] ingresar_matriz() {
        System.out.println("Ingrese la direccion del archivo");
        String prueba = entrada.nextLine();
        File nuevo = new File(prueba);
        System.out.println(prueba);
        //Lectura de archivo
        try {
            FileReader archivo = new FileReader(nuevo);
            BufferedReader mi_buffer = new BufferedReader(archivo);
            String linea = mi_buffer.readLine();
            int vector[][] = new int[3][];
            for (int i = 0; linea != null & i < 3; i++) {
                String vect_temp[] = linea.split(",");
                vector[i] = new int[vect_temp.length];
                for (int j = 0; j < vect_temp.length; j++) {
                    vector[i][j] = Integer.valueOf(vect_temp[j]);
                }
                linea = mi_buffer.readLine();
            }
            for (int[] vector1 : vector) {
                for (int j = 0; j < vector[0].length; j++) {
                    System.out.print("|" + vector1[j] + "|");
                }
                System.out.println("");
            }
            return vector;
        } catch (Exception e) {
            System.out.println("Ha sucedio un problema");
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.toString());
        }
        return null;
    }

    public static void Encriptar(int matriz_m[][], int matriz_a[][], int matriz_b[][]) {
        matriz_ret = new int[matriz_a.length][matriz_m[0].length];
        for (int i = 0; i < matriz_ret.length; i++) {
            for (int j = 0; j < matriz_ret[0].length; j++) {
                int valor_resultado = 0;
                for (int z = 0; z < matriz_m.length; z++) {
                    valor_resultado += (matriz_a[i][z] * matriz_m[z][j]);
                }
                matriz_ret[i][j] = valor_resultado;
            }
        }

        for (int i = 0; i < matriz_ret.length; i++) {
            for (int j = 0; j < matriz_ret[0].length; j++) {
                matriz_ret[i][j] += matriz_b[i][j];
            }
            System.out.println();
        }
        //Igualando matriz
        for (int[] matriz_resultado : matriz_ret) {
            for (int j = 0; j < matriz_resultado.length; j++) {
                System.out.print("|" + matriz_resultado[j] + "|");
            }
            System.out.println();
        }
    }

    public static void desencriptar() {
        double determinante, x00, x01, x02, x10, x11, x12, x20, x21, x22;
        int[][] B = matriz_a;
        double[][] C = new double[3][3];
        System.out.println();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + B[i][j] + "|");
            }
            System.out.println("");
        }
        determinante = (B[0][0] * ((B[1][1] * B[2][2]) - (B[1][2] * B[2][1]))) 
        - (B[0][1] * ((B[1][0] * B[2][2]) - (B[2][0] * B[1][2]))) + (B[0][2] * ((B[1][0] * B[2][1])
       - (B[2][0] * B[1][1])));
        System.out.println();
        System.out.println("Determinante: " + determinante);
        System.out.println();
        double inversa[][] = new double[3][3];
        //Elaboracion de inversa 
        if (determinante != 0) {
            x00 = ((B[1][1] * B[2][2] - B[2][1] * B[1][2])) / determinante;
            x01 = (-(B[1][0] * B[2][2] - B[2][0] * B[1][2])) / determinante;
            x02 = ((B[1][0] * B[2][1] - B[2][0] * B[1][1])) / determinante;
            x10 = (-(B[0][1] * B[2][2] - B[2][1] * B[0][2])) / determinante;
            x11 = ((B[0][0] * B[2][2] - B[2][0] * B[0][2])) / determinante;
            x12 = (-(B[0][0] * B[2][1] - B[2][0] * B[0][1])) / determinante;
            x20 = ((B[0][1] * B[1][2] - B[1][1] * B[0][2])) / determinante;
            x21 = (-(B[0][0] * B[1][2] - B[1][0] * B[0][2])) / determinante;
            x22 = ((B[0][0] * B[1][1] - B[1][0] * B[0][1])) / determinante;

            C[0][0] = x00;
            C[0][1] = x10;
            C[0][2] = x20;
            C[1][0] = x01;
            C[1][1] = x11;
            C[1][2] = x21;
            C[2][0] = x02;
            C[2][1] = x12;
            C[2][2] = x22;

            //System.out.println("La inversa es :");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    inversa[i][j] = C[j][i];
                    // System.out.print("|" + inversa[i][j] + "|");
                }
                System.out.println();
            }

        } else {
            System.out.println("No existe matriz inversa");
        }
        
        // Se realiza la resta entre la Matriz C y la matriz B
        int matriz_resta[][] = new int[matriz_ret.length][matriz_ret[0].length];
        for (int i = 0; i < matriz_resta.length; i++) {
            for (int j = 0; j < matriz_resta[0].length; j++) {
                matriz_resta[i][j] = matriz_ret[i][j] - matriz_b[i][j];
                //System.out.print("|"+matriz_resta[i][j]+"|" );
            }
            System.out.println();
        }

        //Se lleva a cabo la desencriptacion de la matriz
        //Matriz resultante es  A^(-1)*(b-c) = matriz_desencriptada
        int matriz_desencriptada[][] = new int[C.length][matriz_resta[0].length];
        for (int i = 0; i < matriz_desencriptada.length; i++) {
            for (int j = 0; j < matriz_desencriptada[0].length; j++) {
                Double valor_resultado = 0.0;
                for (int z = 0; z < matriz_resta.length; z++) {
                    valor_resultado += ((1.0 * C[i][z]) * (1.0 * matriz_resta[z][j]));
                }
                matriz_desencriptada[i][j] = (int) Math.round(valor_resultado);
                // System.out.print("|"+matriz_desencriptada[i][j]+"|");
            }
            System.out.println();
        }
        //Conversion de la matriz_desencripatada la letras
        String alfabeto = "";
        for (int i = 0; i < matriz_desencriptada[0].length; i++) {
            for (int j = 0; j < matriz_desencriptada.length; j++) {
                //System.out.println(matriz_decodificada[j][i]);
                int mensaje_desencriptado = matriz_desencriptada[j][i];
                switch (mensaje_desencriptado) {
                    case 0:
                        alfabeto += "A";
                        break;

                    case 1:
                        alfabeto += "B";
                        break;
                    case 2:
                        alfabeto += "C";
                        break;
                    case 3:
                        alfabeto += "D";
                        break;
                    case 4:
                        alfabeto += "E";
                        break;
                    case 5:
                        alfabeto += "F";
                        break;
                    case 6:
                        alfabeto += "G";
                        break;
                    case 7:
                        alfabeto += "H";
                        break;
                    case 8:
                        alfabeto += "I";
                        break;
                    case 9:
                        alfabeto += "J";
                        break;
                    case 10:
                        alfabeto += "K";
                        break;
                    case 11:
                        alfabeto += "L";
                        break;
                    case 12:
                        alfabeto += "M";
                        break;
                    case 13:
                        alfabeto += "N";
                        break;
                    case 14:
                        alfabeto += "Ñ";
                        break;
                    case 15:
                        alfabeto += "O";
                        break;
                    case 16:
                        alfabeto += "P";
                        break;
                    case 17:
                        alfabeto += "Q";
                        break;
                    case 18:
                        alfabeto += "R";
                        break;
                    case 19:
                        alfabeto += "S";
                        break;
                    case 20:
                        alfabeto += "T";
                        break;
                    case 21:
                        alfabeto += "U";
                        break;
                    case 22:
                        alfabeto += "V";
                        break;
                    case 23:
                        alfabeto += "W";
                        break;
                    case 24:
                        alfabeto += "X";
                        break;
                    case 25:
                        alfabeto += "Y";
                        break;

                    case 26:
                        alfabeto += "Z";
                        break;

                    case 27:
                        alfabeto += " ";
                        break;
                }
            }
        }
        System.out.println("=======Menu desencriptar=======");
        System.out.println("|El mensaje desencriptado es: |");
        System.out.println("|" + alfabeto + "|");
        System.out.println("==============================");

    }

    private static void ataquetexto() {
        byte submenu_en = 0;

        do {
            System.out.println("===========Menu Ataque con texto claro===========");
            System.out.println("|Opcion 1... Ingresar Matriz mensaje original  |");
            System.out.println("|Opcion 2... Ingresar Matriz mensaje cifrado   |");
            System.out.println("|Opcion 3... Obtener clave");
            System.out.println("|Opcion 4... Menu principal                    |");
            System.out.println("=================================================");
            submenu_en = Byte.valueOf(entrada.nextLine());
            switch (submenu_en) {
                case 1:
                    matriz_original = mensaje_original();
                    break;
                case 2:
                    matriz_cifrado = mensaje_cifrado();
                    break;
                case 3:
                    clave(matriz_original, matriz_cifrado);
                    break;
                default:
                    submenu_en = 0;
                    break;
            }
        } while (submenu_en != 0);

    }

    public static void generarreportes() {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("reporte_encriptar.html");
            pw = new PrintWriter(fichero);

            pw.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head\n>"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + " <title>Document</title>\n "
                    + "</head>\n"
                    + "<body> Mensaje encriptado\n");
            System.out.println("Su reporte encriptar ha sido generado con exito");
            System.out.println("El archivo se generó en la siguiente ruta:");
            System.out.println("C:\\Users\\usuario");
            pw.println("<table class=\"default\">");
            for (int i = 0; i < matriz_m.length; i++) {
                for (int j = 0; j < matriz_m[0].length; j++) {
                    pw.println("<td>" + matriz_m[i][j] + "<td>");
                }
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("");
            pw.println("Matriz A \n ");
            pw.println("<table class=\"default\">");
            for (int i = 0; i < matriz_a.length; i++) {
                for (int j = 0; j < matriz_a[0].length; j++) {
                    pw.println("<td>" + matriz_a[i][j] + "<td>");
                }
                pw.println("</tr>");

            }
            pw.println("</table>");
            pw.println("");
            pw.println("Matriz B  \n");
            pw.println("<table class=\"default\">");
            for (int i = 0; i < matriz_b.length; i++) {
                for (int j = 0; j < matriz_b[0].length; j++) {
                    pw.println("<td>" + matriz_b[i][j] + "<td>");
                }
                pw.println("</tr>");

            }
            pw.println("</table>");
            pw.println("");
            pw.println("Matriz A*M  \n");
            pw.println("<table class=\"default\">");
            for (int i = 0; i < matriz_ret.length; i++) {
                for (int j = 0; j < matriz_ret[0].length; j++) {
                    pw.println("<td>" + matriz_ret[i][j] + "<td>");
                }
                pw.println("</tr>");

            }

      
            pw.println("    "
                    + "      \n"
                    + "</body>\n"
                    + "</html>");
            fichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fichero = new FileWriter("reporte_desencriptar.html");
            pw = new PrintWriter(fichero);

            pw.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head\n>"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + " <title>Document</title>\n "
                    + "</head>\n"
                    + "<body> \n");
            System.out.println("Su reporte desencriptar ha sido generado con exito");
            System.out.println("El archivo se generó en la siguiente ruta:");
            System.out.println("C:\\Users\\usuario");
            pw.println("<table class=\"default\">");
            
      
            pw.println("</table>");
            pw.println("");
            pw.println("Matriz A*M  \n");
            pw.println("<table class=\"default\">");
            for (int i = 0; i < matriz_ret.length; i++) {
                for (int j = 0; j < matriz_ret[0].length; j++) {
                    pw.println("<td>" + matriz_ret[i][j] + "<td>");
                }
                pw.println("</tr>");

            }

            pw.println("    "
                    + "      \n"
                    + "</body>\n"
                    + "</html>");
            fichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fichero = new FileWriter("reporte_ataque.html");
            pw = new PrintWriter(fichero);

            pw.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "<head\n>"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + " <title>Document</title>\n "
                    + "</head>\n"
                    + "<body> \n ");
            System.out.println("Su reporte Ataque texto ha sido generado con exito");
            System.out.println("El archivo se generó en la siguiente ruta:");
            System.out.println("C:\\Users\\usuario");
            pw.println("<table class=\"default\">");
           

            pw.println("</table>");
            pw.println("");
            pw.println("Matriz Mensaje Original  \n");
            pw.println("<table class=\"default\">");
            for (int i = 0; i < matriz_original.length; i++) {
                for (int j = 0; j < matriz_original[0].length; j++) {
                    pw.println("<td>" + matriz_original[i][j] + "<td>");
                }
                pw.println("</tr>");

            }
            pw.println("");
            pw.println("</table>");
            pw.println("");
            pw.println("Matriz Mensaje cifrado  \n");
            pw.println("<table class=\"default\">");
            for (int i = 0; i < matriz_cifrado.length; i++) {
                for (int j = 0; j < matriz_cifrado[0].length; j++) {
                    pw.println("<td>" + matriz_cifrado[i][j] + "<td>");
                }
                pw.println("</tr>");

            }

            pw.println("    "
                    + "      \n"
                    + "</body>\n"
                    + "</html>");
            fichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[][] mensaje_original() {
        //Ingresar mensaje original
        System.out.println("Matriz mensaje original");
        System.out.println("Ingrese la direccion del archivo");
        String original = entrada.nextLine();
        File nuevo = new File(original);
        System.out.println(original);
        try {
            FileReader archivo = new FileReader(nuevo);
            BufferedReader mi_buffer = new BufferedReader(archivo);
            String linea = mi_buffer.readLine();
            int vector[][] = new int[3][];
            for (int i = 0; linea != null & i < 3; i++) {
                String vect_temp[] = linea.split(",");
                vector[i] = new int[vect_temp.length];
                for (int j = 0; j < vect_temp.length; j++) {
                    vector[i][j] = Integer.valueOf(vect_temp[j]);
                }
                linea = mi_buffer.readLine();
            }
            for (int[] vector1 : vector) {
                for (int j = 0; j < vector[0].length; j++) {
                    System.out.print("|" + vector1[j] + "|");
                }
                System.out.println("");
            }
            return vector;
        } catch (Exception e) {
            //Posible error
            System.out.println("Ha sucedio un problema");
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.toString());
        }
        return null;
    }

    private static int[][] mensaje_cifrado() {
        //Obtener mensaje cifrado
        System.out.println("Matriz mensaje cifrado");
        System.out.println("Ingrese la direccion del archivo");
        String cifrado = entrada.nextLine();
        File nuevo = new File(cifrado);
        System.out.println(cifrado);
        try {
            FileReader archivo = new FileReader(nuevo);
            BufferedReader mi_buffer = new BufferedReader(archivo);
            String linea = mi_buffer.readLine();
            int vector[][] = new int[3][];
            for (int i = 0; linea != null & i < 3; i++) {
                String vect_temp[] = linea.split(",");
                vector[i] = new int[vect_temp.length];
                for (int j = 0; j < vect_temp.length; j++) {
                    vector[i][j] = Integer.valueOf(vect_temp[j]);
                }
                linea = mi_buffer.readLine();
            }
            for (int[] vector1 : vector) {
                for (int j = 0; j < vector[0].length; j++) {
                    System.out.print("|" + vector1[j] + "|");
                }
                System.out.println("");
            }
            return vector;
        } catch (Exception e) {
            System.out.println("Ha sucedio un problema");
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.toString());
        }
        return null;

    }

    private static void clave(int[][] matriz_original, int[][] matriz_cifrado) {
    }

    public static void salir() {
        System.out.println("Gracias por usar nuestro programa");

    }
}
