package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;
import tp.procesadores.compilador.generadorcodigo.Temporal;

public class ProductoNatural extends ClaseNodo {

   private static final long serialVersionUID = -7349925851495324150L;

   public ProductoNatural(ClaseNodo nodo1, ClaseNodo nodo2) {
      this.add(nodo1);
      this.add(nodo2);
   }

   @Override
   public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager) {
      GeneradorCodigoUtils gc = new GeneradorCodigoUtils();
//		Temporal temp = tempManager.getNewTemporal(codigo.getContexto());
      codigo.setLabel("aritmetico");

//		StringBuilder strbldr = new StringBuilder();
//		strbldr.append(String.format(labelManager.getTabulacion()+"%s\tdw\t?",temp.getTmp())).append("\n");
//		strbldr.append(String.format(labelManager.getTabulacion()+"PUSH\t%s",temp.getTmp())).append("\n");
//		codigo.setCodigo(codigo.getCodigo() + "\n" +  strbldr.toString() );
    
      
      Codigo codigoAux = new Codigo();
      codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
      codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
      String parametroProducto1 = codigoAux.getLabel();
      codigoAux = new Codigo();
      codigoAux = this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager);
      codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
      String parametroProducto2 = codigoAux.getLabel();

      codigo.setCodigo(codigo.getCodigo()
                       + gc.generarProductoNatural("aritmetico", parametroProducto1, parametroProducto2, labelManager.getTabulacion()));
      return codigo;
   }
}
