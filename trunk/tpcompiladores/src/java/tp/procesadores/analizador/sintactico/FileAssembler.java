package tp.procesadores.analizador.sintactico;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.principal.Globales;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.FilaTabla;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.constantes.Constants;
import tp.procesadores.compilador.generadorcodigo.constantes.PreDefinedFunctions;

public class FileAssembler {

	public String armarASM(ClaseNodo claseNodo) {
		// Variables Globales
		String encabezado = Constants.encabezado + Constants.jumpprocedimientoPrincipal;
		String procedimientosDefault = PreDefinedFunctions.sb.toString();
		Procedimiento proc = (Procedimiento) claseNodo.nodos.get(1);
		if (proc.getNombreProcedimiento()!=null)
			encabezado += proc.getNombreProcedimiento() + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO;
		else
			encabezado += "principal" + Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO;
		String resultadoConstantes = tratarGlobales((Globales) claseNodo.nodos.get(0));
		if (resultadoConstantes != null) {
			resultadoConstantes = resultadoConstantes + Constants.FIN_DE_LINEA;
		}else{
			resultadoConstantes = "";
		}
		
		// Procedimientos
		Procedimiento procesos = (Procedimiento) claseNodo.nodos.get(1);
		String resultadoProcedimientos = tratarProcedimientos(procesos);
		if (resultadoProcedimientos != null) {
			resultadoProcedimientos += Constants.FIN_DE_LINEA + procesos.getNombreProcedimiento() + ":";
		}else{
			resultadoProcedimientos += Constants.FIN_DE_LINEA + ":" ;
		}
		claseNodo.setContexto(encabezado + resultadoConstantes + procedimientosDefault
				+ resultadoProcedimientos + Constants.finprocedimiento);
		return Constants.encabezado + Constants.FIN_DE_LINEA 
				+ procedimientosDefault
				+ resultadoConstantes
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
				if (entrada.getValor() == null) 
					resultado += Constants.TAB + entrada.getId() + Constants.VARIABLES_SIN_VALOR + " 0" + Constants.FIN_DE_LINEA;
				else 
					resultado += Constants.TAB + entrada.getId() + Constants.VARIABLES_SIN_VALOR + "" + entrada.getValor() + Constants.FIN_DE_LINEA;
			}
		}
		return resultado;
	}

	private String tratarProcedimientos(Procedimiento procFuncPrincipal) {
		TablaDeSimbolos tablaSimbolo;
		FilaTabla variable;
		String resultado = ";Procedimiento " 
					+ (procFuncPrincipal.getNombreProcedimiento()!=null?procFuncPrincipal.getNombreProcedimiento():"1") 
					+ Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO;
		if (0 < procFuncPrincipal.nodos.size()) {
			tablaSimbolo = (TablaDeSimbolos) procFuncPrincipal.nodos.get(0);
			for (int j = 0; j < tablaSimbolo.entradas.size(); j++) {
				variable = (FilaTabla) tablaSimbolo.entradas.get(j);
				resultado += Constants.TAB + variable.getId() + Constants.VARIABLES_SIN_VALOR ;
				if (variable.getValor()!=null)
					resultado += variable.getValor() + Constants.FIN_DE_LINEA;
				else
					resultado += "?" + Constants.FIN_DE_LINEA;				
			}
			Metodo bloque = (Metodo) tablaSimbolo.padre.metodos.get(0);
			bloque.toString();
		}		
		return resultado!=null?resultado:null;
	}
}