package tp.procesadores.compilador.generadorcodigo;

import tp.procesadores.compilador.generadorcodigo.constantes.Constants;

/**
 * Clase que genera el c&oacute;digo segun las funciones, procedimientos o condiciones
 */
public class GeneradorCodigoUtils {

   public static int numeroVariables = 0;

   /**
    * 
    * @return c&oacute;digo para la definicion de un array de Enteros.
    */
   public String generarArrayEntero(String variables) {
      
      numeroVariables++;
      String mensaje = "array"+numeroVariables+" DB "; 
      return mensaje;
   }

   public String generarArrayNatural() {
      numeroVariables++;
      return "";
   }

   public String generarAsignacion() {
      return "";
   }

   public String generarConstantesEntera() {
      numeroVariables++;
      return "";
   }

   public String generarConstantesNatural() {
      numeroVariables++;
      return "";
   }

   public String generarDistintoEnteros() {
      return "";
   }

   public String generarDistintoNaturales() {
      return "";
   }

   public String generarDivEntera() {
      return "";
   }

   public String generarDivNatural() {
      return "";
   }

   public String generarExpresionBooleana(String entero1, String entero2, Object tabulacion) {
      return null;
   }

   public String generarFuncionAnd() {
      return "";
   }

   public String generarFuncionEsImpar() {
      return "";
   }

   public String generarFuncionEsPar() {
      return "";
   }

   public String generarFuncionLeer() {
      int posicion = numeroVariables + 1;
      numeroVariables++;
      return "org 100h" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO_MAS_TAB + "jmp start" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO + "msg"
             + posicion + ":      db      'MENSAJE A MOSTRAR', 24h" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO + "start:"
             + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO_MAS_TAB + "mov dx, msg" + Constants.FIN_DE_LINEA_MAS_TAB + "mov     ah, 09h"
             + Constants.FIN_DE_LINEA_MAS_TAB + "int     21h" + Constants.FIN_DE_LINEA_MAS_TAB + "mov     ah, 0" + Constants.FIN_DE_LINEA_MAS_TAB
             + "int     16h" + Constants.FIN_DE_LINEA + "ret";
   }

   public String generarFuncionMostrar(String mensaje) {
      return "org 100h" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO + "jmp start" + Constants.FIN_DE_LINEA + "msg:      db      '" + mensaje
             + "', 24h  " + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO + "start: " + Constants.FIN_DE_LINEA_MAS_TAB + "mov dx, msg"
             + Constants.FIN_DE_LINEA_MAS_TAB + "mov     ah, 09h" + "int     21h" + "mov     ah, 0" + "int     16h" + "ret";
   }

   public String generarFuncionMostrarLn(String mensaje) {
      int posicion = numeroVariables;
      numeroVariables++;
      return "org 100h" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO + "jmp start" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO + "msg" + posicion
             + ":      db      '" + mensaje + "', 0Dh,0Ah, 24h" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO + "start:"
             + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO_MAS_TAB + "mov    dx, msg" + posicion + Constants.FIN_DE_LINEA_MAS_TAB + "mov    ah, 09h"
             + Constants.FIN_DE_LINEA_MAS_TAB + "int    21h" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO_MAS_TAB + "mov    ah, 0"
             + Constants.FIN_DE_LINEA_MAS_TAB + "int    16h" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO_MAS_TAB + "ret";
   }

   public String generarFuncionOr() {
      return "";
   }

   public String generarIgualdadEnteros() {
      return "";
   }

   public String generarIgualdadNaturales() {
      return "";
   }

   public String generarLeer() {
      return "mov ah, 08h" + Constants.FIN_DE_LINEA + "int 21h" + Constants.FIN_DE_LINEA;
   }

   public String generarLlamadoFP() {
      return "";
   }

   public String generarMenorEnteros() {
      return "";
   }

   public String generarMenorNaturales() {
      return "";
   }

   public String generarMientras() {
      return "";
   }

   public String generarNodoSumaEnteros() {
      return "";
   }

   public String generarNodoSumaNatural() {
      return "";
   }

   public String generarProductoEntero() {
      return "";
   }

   public String generarProductoNatural() {
      return "";
   }

   public String generarProductoNatural(String string, String parametroProducto1, String parametroProducto2, Object tabulacion) {
      return null;
   }

   public String generarRestaEntera() {
      return "";
   }

   public String generarRestaNatural() {

      return "";
   }

   public String generarSi() {
      return "";
   }

   public String generarSino() {
      return "";
   }

   /**
    * 
    * @return
    */
   public String generarVariableEntero() {
      numeroVariables++;
      return "";
   }

   public String generarVariableNatural() {
      numeroVariables++;
      return "";
   }

}
