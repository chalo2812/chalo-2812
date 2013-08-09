package tp.procesadores.analizador.semantico.arbol;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.InterfazNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class ArbolUtils {

   public void showArbol(ClaseNodo nodo) {
      String nivel = " ";
      System.out.println(nodo.getClass().getSimpleName());
      System.out.println(nodo.generarCodigo(new Codigo(), new TempManager(), new LabelManager()).getCodigo().replaceAll("null", ""));
      showArbolAux(nodo, nivel);
   }

   public void escribirArchivoSalida(String archivo, ClaseNodo nodo) {
      File output = new File(archivo + ".asm");
      PrintWriter fileWriter;
      try {
         fileWriter = new PrintWriter(output);
         fileWriter.write(nodo.getContexto());
         fileWriter.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void showArbolAux(ClaseNodo raiz, String nivel) {
      if (raiz.nodos != null) {
         int j = 0;
         for (InterfazNodo nodo : raiz.nodos) {
            if (nodo != null) {
               System.out.println(nivel + nodo.getClass().getSimpleName());
               if (raiz.nodos.get(j) instanceof TablaDeSimbolos) {
                  TablaDeSimbolos tabla = (TablaDeSimbolos) raiz.nodos.get(j);
                  tabla.mostrarTabla(nivel);
               }
               showArbolAux((ClaseNodo) raiz.nodos.get(j), nivel + "    ");
            }
            j++;
         }
      }
   }

   public ClaseNodo deepCopy(ClaseNodo arbol) {
      ClaseNodo obj = null;
      try {
         // Write the object out to a byte array
         FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
         ObjectOutputStream out = new ObjectOutputStream(fbos);
         out.writeObject(arbol);
         out.flush();
         out.close();

         // Retrieve an input stream from the byte array and read
         // a copy of the object back in.
         ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
         obj = (ClaseNodo) in.readObject();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException cnfe) {
         cnfe.printStackTrace();
      }
      return obj;
   }
}
