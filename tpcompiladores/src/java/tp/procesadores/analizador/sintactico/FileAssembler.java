package tp.procesadores.analizador.sintactico;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Globales;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.FilaTabla;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.constantes.Constants;

public class FileAssembler {

	public String armarASM(ClaseNodo claseNodo) {
		// Variables Globales
		String encabezado = Constants.encabezado + Constants.jumpprocedimientoPrincipal;
		String resultadoConstantes = tratarGlobales((Globales) claseNodo.nodos.get(0));
		if (resultadoConstantes != null) {
			resultadoConstantes = resultadoConstantes + Constants.FIN_DE_LINEA;
		}
		// Procedimientos
		String resultadoProcedimientos = tratarProcedimientos((Procedimiento) claseNodo.nodos.get(1));
		if (resultadoProcedimientos != null) {
			resultadoProcedimientos += Constants.FIN_DE_LINEA;
		}
		claseNodo.setContexto(encabezado + resultadoConstantes
				+ resultadoProcedimientos + Constants.finprocedimiento);
		return Constants.encabezado + resultadoConstantes
				+ resultadoProcedimientos;
	}

	private String tratarGlobales(Globales constantes) {
		TablaDeSimbolos resultados;
		String resultado = "";
		FilaTabla entrada;
		for (int i = 0; i < constantes.nodos.size(); i++) {
			resultados = (TablaDeSimbolos) constantes.nodos.get(i);
			for (int j = 0; j < resultados.entradas.size(); j++) {
				entrada = (FilaTabla) resultados.entradas.get(j);
				if (entrada.getValor() == null) {
					resultado = resultado + entrada.getId()
							+ Constants.VARIABLES_SIN_VALOR + " 0"
							+ Constants.FIN_DE_LINEA;
				} else {
					resultado = resultado + entrada.getId()
							+ Constants.VARIABLES_SIN_VALOR + ""
							+ entrada.getValor() + Constants.FIN_DE_LINEA;
				}
				System.out.println(resultados.entradas.get(i).toString());
			}

		}
		return resultado;
	}

	private String tratarProcedimientos(Procedimiento procFuncPrincipal) {
		TablaDeSimbolos tablaSimbolo;
		FilaTabla variable;
		String resultado = null;
		if (0 < procFuncPrincipal.nodos.size()) {
			tablaSimbolo = (TablaDeSimbolos) procFuncPrincipal.nodos.get(0);
			for (int j = 0; j < tablaSimbolo.entradas.size(); j++) {
				variable = (FilaTabla) tablaSimbolo.entradas.get(j);
				System.out.println(variable.getId() + " " + variable.getTipo()
						+ " " + variable.getValor());
			}
			Metodo bloque = (Metodo) tablaSimbolo.padre.metodos.get(0);
			bloque.toString();
		}
		
		return resultado!=null?resultado:null;
	}

}
