package trabajoFinalFP1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TrabajoFinalJhon {

    public static String[] leerDataTxT(String url){
        ArrayList<String> registros = new ArrayList<String>();


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

        for (int x=0; x< tablaTipoHorario.length; x++) {
            String registro[] = tablaTipoHorario[x].split(",");
            String id = registro[0];
            if (idHorario.equals(id)){
                horario = registro[1] + " - " + registro[2] + " -   " + registro[3];
            }
        }

        return horario;
    }

    public static String buscarPersonal(String nroDocumento){
        String registroSalida = "";

        String dataFileUrl = "src/trabajoFinalFP1/tablaPersonal.txt";
        String[] tablaPersonal = leerDataTxT(dataFileUrl);

        for (int x=0; x< tablaPersonal.length; x++) {
            String registro[] = tablaPersonal[x].split(",");
            String nro = registro[4];
            if (nroDocumento.equals(nro)){
                registroSalida = tablaPersonal[x];
            }
        }

        return registroSalida;
    }

    public static String[] buscarHorarioPersonal(String idPersonal){
        ArrayList<String> registros = new ArrayList<String>();

        String dataFileUrl = "src/trabajoFinalFP1/tablaHorarioPersonal.txt";
        String[] tabla = leerDataTxT(dataFileUrl);

        for (int x=0; x< tabla.length; x++) {
            String registro[] = tabla[x].split(",");
            String nro = registro[0];
            if (idPersonal.equals(nro)){
                registros.add(tabla[x]);
            }
        }

        String[] registrosSalida = new String[registros.size()];
        registrosSalida = registros.toArray(registrosSalida);

        return registrosSalida;
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
        String horarioPersonal[] = buscarHorarioPersonal(id);
        System.out.println("NRO -   FECHA    - INICIO -  FIN   - HORAS ");
        for (int x = 0; x < horarioPersonal.length; x++) {
            String horario[] = horarioPersonal[x].split(",");
            String tipoHorario = tipoHorario(horario[3]);
            System.out.println("  " + x + " - " + horario[2] + " - "+ tipoHorario);
        }
    }

    public static void main(String[] args) {
        System.out.println("------------  INICIO ------------");

        String horario = tipoHorario("1");
        System.out.println(horario);

        String personal = buscarPersonal("40616458");
        System.out.println(personal);

        String horarioPersonal[] = buscarHorarioPersonal("1");
        System.out.println(Arrays.toString(horarioPersonal));

        Scanner inputOperacion = new Scanner (System.in);
        System.out.println("Tipos de Operaciones - CGH EMERGENCIA");
        System.out.println("    [1] - Consultar Horario");
        System.out.println("    [2] - Ingresar Nuevo Horario");
        System.out.print("Ingrese una opción : ");
        String opcion = inputOperacion.next();

        if(opcion.equals("1")){
            System.out.println("----------------   CONSULTAR HORARIO  ----------------");
            Scanner inputDocumento = new Scanner (System.in);
            System.out.print("Ingrese el numero de documento : ");
            String nroDocumento = inputOperacion.next();
            //System.out.println(opcionDocumento);
            mostrarHorarioPersonal(nroDocumento);

        }

        System.out.println("------------   FIN  ------------");
    }
}
