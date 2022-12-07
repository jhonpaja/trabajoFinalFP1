package trabajoFinalFP1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TrabajoFinalJhon {

    public static int cantidadRegistros(String url){
        int nroLineas = 0;
        try {
            FileReader fr = new FileReader(url);
            BufferedReader bf = new BufferedReader(fr);
            nroLineas = (int) bf.lines().count();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nroLineas;
    }

    public static void grabarRegistroTxt(String url, String registro){
        File archivo = new File(url);
        FileWriter escribir;
        PrintWriter linea;
        if (archivo.exists()){
            try{
                escribir = new FileWriter(archivo, true);
                linea = new PrintWriter(escribir);
                linea.println(registro);
                linea.close();
                System.err.println("Registro agregado");
            }catch (IOException ex){
                System.err.println("Error al escribir archivo");
            }
        }
    }

    public static String[] leerDataTxT(String url){
        ArrayList<String> registros = new ArrayList<>();


        try{
            BufferedReader bf = new BufferedReader(new FileReader(url));
            String bfRead;
            while ((bfRead = bf.readLine()) != null){
                registros.add(bfRead);
            }
        }catch (Exception e){
            System.err.println("No se encontró archivo");
        }

        String[] registrosSalida = new String[registros.size()];
        registrosSalida = registros.toArray(registrosSalida);

        return registrosSalida;
    }

    public static String tipoHorario(String idHorario){
        String horario = "";
        String dataFileUrl = "src/trabajoFinalFP1/tablaHorario.txt";
        String[] tablaTipoHorario = leerDataTxT(dataFileUrl);

        for (String s : tablaTipoHorario) {
            String[] registro = s.split(",");
            String id = registro[0];
            if (idHorario.equals(id)) {
                horario = registro[1] + " - " + registro[2] + " -   " + registro[3];
            }
        }

        return horario;
    }

    public static String buscarPersonal(String nroDocumento){
        String registroSalida = "";

        String dataFileUrl = "src/trabajoFinalFP1/tablaPersonal.txt";
        String[] tablaPersonal = leerDataTxT(dataFileUrl);

        for (String s : tablaPersonal) {
            String[] registro = s.split(",");
            String nro = registro[4];
            if (nroDocumento.equals(nro)) {
                registroSalida = s;
            }
        }

        return registroSalida;
    }

    public static String[] buscarHorarioPersonal(String idPersonal){
        ArrayList<String> registros = new ArrayList<>();

        String dataFileUrl = "src/trabajoFinalFP1/tablaHorarioPersonal.txt";
        String[] tabla = leerDataTxT(dataFileUrl);

        for (String s : tabla) {
            String[] registro = s.split(",");
            String nro = registro[1];
            if (idPersonal.equals(nro)) {
                registros.add(s);
            }
        }

        String[] registrosSalida = new String[registros.size()];
        registrosSalida = registros.toArray(registrosSalida);

        return registrosSalida;
    }

    public static void mostrarPersonal(){
        String dataFileUrl = "src/trabajoFinalFP1/tablaPersonal.txt";
        String[] tabla = leerDataTxT(dataFileUrl);


        for (String s : tabla) {
            String[] registro = s.split(",");
            System.out.println("    [" + registro[0] + "] - " + registro[2] + ", " + registro[1]);
        }
    }

    public static void mostrarTipoHorario(){
        String dataFileUrl = "src/trabajoFinalFP1/tablaHorario.txt";
        String[] tabla = leerDataTxT(dataFileUrl);

        //1,700 am,700 pm,12
        for (String s : tabla) {
            String[] registro = s.split(",");
            System.out.println("    [" + registro[0] + "] - " + registro[1] + " hasta " + registro[2] + " - " + registro[3] + " horas");
        }
    }


    public static void mostrarHorarioPersonal(String nroDocumento){
        String[] registroPersonal = buscarPersonal(nroDocumento).split(",");
        String id = registroPersonal[0];
        String nombresApellidos = registroPersonal[1] + " " + registroPersonal[2];
        String cargo = registroPersonal[5];

        System.out.println("------------------------------------------------------");
        System.out.println("              CODIGO : 000"+id);
        System.out.println(" NOMBRES Y APELLIDOS : "+nombresApellidos);
        System.out.println("                 DNI : "+nroDocumento);
        System.out.println("           OCUPACIÓN : "+cargo);
        System.out.println("------------------------------------------------------");
        String[] horarioPersonal = buscarHorarioPersonal(id);
        System.out.println("NRO -   FECHA    - INICIO  -  FIN    - HORAS ");
        for (int x = 0; x < horarioPersonal.length; x++) {
            String[] horario = horarioPersonal[x].split(",");
            String tipoHorario = tipoHorario(horario[3]);
            int y = x + 1;
            System.out.println("  " + y + " - " + horario[2] + " - "+ tipoHorario);
        }
    }

    public static void menuSistema(){
        Scanner inputOperacion = new Scanner (System.in);
        System.out.println(" ******************************************************");
        System.out.println("Tipos de Operaciones - CGH EMERGENCIA");
        System.out.println("    [1] - Consultar Horario");
        System.out.println("    [2] - Ingresar Nuevo Horario");
        System.out.println("    [3] - Salir");
        System.out.print("Ingrese una opción : ");
        String opcionMenu = inputOperacion.next();

        switch (opcionMenu) {
            case "1":
                System.out.println("----------------   CONSULTAR HORARIO  ----------------");
                Scanner inputDocumento = new Scanner(System.in);
                System.out.print("  Ingrese el numero de documento : ");
                String nroDocumento = inputDocumento.next();
                mostrarHorarioPersonal(nroDocumento);
                break;
            case "2":
                System.out.println("----------------   INGRESAR HORARIO  ----------------");
                System.out.println("    Listado de Trabajadores: ");
                mostrarPersonal();
                Scanner inputId = new Scanner(System.in);
                System.out.print("Ingrese el id del trabajador para asignarle un horario :");
                String idPersonal = inputId.next();
                //System.out.println("--------------------------------");
                Scanner inputFecha = new Scanner(System.in);
                System.out.print("Ingrese la fecha del horario = YYYY-MM-DD :");
                String fecha = inputFecha.next();

                System.out.println("Listado de Tipo de Horario: ");
                mostrarTipoHorario();

                Scanner inputTipoHorario = new Scanner(System.in);
                System.out.print("Ingrese el id del tipo de horario a asignar :");
                String idTipoHorario = inputTipoHorario.next();

                String dataFileUrl = "src/trabajoFinalFP1/tablaHorarioPersonal.txt";
                int nroRegistro = cantidadRegistros(dataFileUrl) + 1;

                String registroGrabar = nroRegistro + "," + idPersonal + "," + fecha + "," + idTipoHorario;

                grabarRegistroTxt(dataFileUrl, registroGrabar);
                //9,1,2022-12-01,1

                break;
            case "3":
                System.out.println(" ***************************************************");
                System.out.println(" *********** GRACIAS POR USAR EL SISTEMA ***********");
                System.out.println(" ***************************************************");
                System.exit(1);
                break;

            default:
                System.out.println(" NO EXISTE ESA OPERACIÓN ");
                System.exit(1);
                break;
        }
    }

    public static boolean seguirMenu(){
        boolean resp = false;

        Scanner inputSeguir = new Scanner(System.in);
        System.out.println(" ******************************************************");
        System.out.print("¿Desea realizar otra operación?  [1] Si  /  [2] No  : ");
        int respuesta = Integer.parseInt(inputSeguir.next());
        //System.out.println("respuesta = "+respuesta);
        if ( respuesta == 1){
            resp = true;
        }
        return resp;
    }

    public static void main(String[] args) {
        System.out.println("------------------------  INICIO ------------------------");

        boolean seguirMenu = true;

        while(seguirMenu){
            menuSistema();
            seguirMenu = seguirMenu();
        }




        System.out.println("------------------------   FIN  ------------------------");
    }
}
