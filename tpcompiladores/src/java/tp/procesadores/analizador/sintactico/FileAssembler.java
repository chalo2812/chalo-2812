package tp.procesadores.analizador.sintactico;

import tp.procesadores.analizador.semantico.arbol.bloque.Asignacion;
import tp.procesadores.analizador.semantico.arbol.bloque.Mientras;
import tp.procesadores.analizador.semantico.arbol.bloque.Si;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.expresiones.NodoExpresionBooleana;
import tp.procesadores.analizador.semantico.arbol.general.Identificador;
import tp.procesadores.analizador.semantico.arbol.palres.Leer;
import tp.procesadores.analizador.semantico.arbol.palres.Mostrar;
import tp.procesadores.analizador.semantico.arbol.palres.MostrarLn;
import tp.procesadores.analizador.semantico.arbol.principal.Bloque;
import tp.procesadores.analizador.semantico.arbol.principal.Funcion;
import tp.procesadores.analizador.semantico.arbol.principal.Globales;
import tp.procesadores.analizador.semantico.arbol.principal.Procedimiento;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.FilaTabla;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.constantes.Constants;
import tp.procesadores.compilador.generadorcodigo.constantes.PreDefinedFunctions;

public class FileAssembler {

	public String resultadoConstantes = "";
	public String resultadoProcedimientos = "";
	public String resultadoFunciones = "";
	// Encabezado
	public String encabezado = Constants.encabezado
			+ Constants.jumpprocedimientoPrincipal;
	public String procedimientosDefault = PreDefinedFunctions.sb.toString();
	public int posMetodos;

	public String armarASM(ClaseNodo claseNodo) {
		Procedimiento proc;
		Funcion funcion;
		Globales variables;
		for (int i = 0; i < claseNodo.nodos.size(); i++) {
			if (claseNodo.esFuncion(claseNodo.nodos.get(i))) {
				funcion = (Funcion) claseNodo.nodos.get(i);
				resultadoFunciones = tratarFunciones(funcion, i-1);
			} else if (claseNodo.esProcedimiento(claseNodo.nodos.get(i))) {
				// Procedimientos
				proc = (Procedimiento) claseNodo.nodos.get(i);
				resultadoProcedimientos = tratarProcedimientos(proc);
				if (proc.getNombreProcedimiento() != null)
					encabezado += proc.getNombreProcedimiento()
							+ Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO;
				else
					encabezado += "principal"
							+ Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO;

			} else {
				// Variables Globales
				variables = (Globales) claseNodo.nodos.get(i);
				resultadoConstantes = tratarGlobales((Globales) variables);
				resultadoConstantes = resultadoConstantes != null ? resultadoConstantes
						+ Constants.ENTER : "";
			}

		}
		claseNodo.setContexto(encabezado + resultadoConstantes
				+ procedimientosDefault + resultadoFunciones
				+ resultadoProcedimientos + Constants.finprocedimiento);
		return encabezado + resultadoConstantes + procedimientosDefault
				+ resultadoFunciones + resultadoProcedimientos
				+ Constants.finprocedimiento;

	}

	private String tratarFunciones(Funcion funcion, int posicion) {
		int cantidadSumados=0;
		resultadoFunciones += Constants.ENTER + "; Comienzo de Funcion "
				+ funcion.nodos.get(posicion).nombreDelProceso(funcion.nodos,posicion,posMetodos) + Constants.ENTER + funcion.nodos.get(posicion).nombreDelProceso(funcion.nodos,posicion,posMetodos) +":" + Constants.ENTER ;
		for (int i = 0; i < funcion.nodos.size(); i++) {
			if (funcion.nodos.get(i).getClass().equals(TablaDeSimbolos.class)) {
				resultadoFunciones += tratarTablaDeSimbolos((TablaDeSimbolos) funcion.nodos
						.get(i));
				posMetodos++;
				cantidadSumados++;
			} else if (funcion.nodos.get(i).getClass().equals(Bloque.class)) {
				tratarBloque((Bloque) funcion.nodos.get(i));
			}

		}
		return resultadoFunciones + Constants.FIN_DE_LINEA_MAS_TAB + "RET" + Constants.ENTER+ "; Fin de Funcion "
				+ funcion.nodos.get(posicion).nombreDelProceso(funcion.nodos,posicion,posMetodos-cantidadSumados) + Constants.ENTER;
	}

	private String tratarBloque(Bloque bloque) {
		// Bloque posibles items: 1- Mostar
		// 2- MostarLN
		// 3- Si
		// 4- Identificador
		// 5- Mientras
		// 6- Leer
	   String mensaje = null;
		for (int i = 0; i < bloque.nodos.size(); i++) {
			// Palabra reservada
			if (bloque.nodos.get(i).getClass().equals(Mostrar.class)) {
			   mensaje += invocarMostrar((Mostrar) bloque.nodos
						.get(i));
				// Palabra reservada
		 	} else if (bloque.nodos.get(i).getClass().equals(MostrarLn.class)) {
		 	  mensaje += invocarMostrarLn((MostrarLn) bloque.nodos
						.get(i));
				// Nuevo Bloque
			} else if (bloque.nodos.get(i).getClass().equals(Si.class)) {
			   mensaje += invocarSi((Si) bloque.nodos.get(i));
				// Variable
			} else if (bloque.nodos.get(i).getClass()
					.equals(Identificador.class)) {
			   mensaje += invocarIdentificador((Identificador) bloque.nodos
						.get(i));
				// Nuevo Bloque
			} else if (bloque.nodos.get(i).getClass().equals(Mientras.class)) {
			   mensaje += invocarMientras((Mientras) bloque.nodos
						.get(i));
				// Palabra reservada
			} else if (bloque.nodos.get(i).getClass().equals(Leer.class)) {
			   mensaje += invocarLeer((Leer) bloque.nodos.get(i));
			//Asignacion
			}else if (bloque.nodos.get(i).getClass().equals(Asignacion.class)){
			   mensaje += invocarAsignacion((Asignacion) bloque.nodos.get(i));
			}
		}
		return mensaje + Constants.ENTER;
	}

	private String invocarAsignacion(Asignacion asignacion) {
		return Constants.ENTER + Constants.TAB + "";
	}

	private String invocarIdentificador(Identificador identificador) {
		return Constants.ENTER + Constants.TAB + "";
	}

	private String invocarLeer(Leer leer) {
		return Constants.ENTER + Constants.TAB + "";
	}

	private String invocarMientras(Mientras mientras) {
		return Constants.ENTER + Constants.TAB + "";
	}

	private String invocarSi(Si si) {
		String condicionalSi = "";
		for (int i=0;i<si.nodos.size();i++){
			ClaseNodo objeto = (ClaseNodo) si.nodos.get(i);
			if (objeto.getClass().equals(NodoExpresionBooleana.class)){

				
			}else if (objeto.getClass().equals(Bloque.class)){
				tratarBloque((Bloque)objeto);
			}
		}
		return condicionalSi + Constants.ENTER + Constants.TAB + "";
	}

	private String invocarMostrarLn(MostrarLn mostrarLn) {
		return Constants.ENTER + Constants.TAB + "";
	}

	private String invocarMostrar(Mostrar mostrar) {
		return Constants.ENTER + Constants.TAB + "";
	}

	private String tratarTablaDeSimbolos(TablaDeSimbolos listaTablas) {
		FilaTabla entrada;
		String resultadoVariablesFunciones = "";
		for (int i = 0; i < listaTablas.entradas.size(); i++) {
			entrada = (FilaTabla) listaTablas.entradas.get(i);
			if (entrada.getValor() == null)
				resultadoVariablesFunciones += Constants.TAB + entrada.getId()
						+ Constants.VARIABLES_SIN_VALOR + " ?"
						+ Constants.FIN_DE_LINEA;
			else
				resultadoVariablesFunciones += Constants.TAB + entrada.getId()
						+ Constants.VARIABLES_SIN_VALOR + ""
						+ entrada.getValor() + Constants.FIN_DE_LINEA;
		}

		return resultadoVariablesFunciones;
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
					resultado += Constants.TAB + entrada.getId()
							+ Constants.VARIABLES_SIN_VALOR + " ?"
							+ Constants.FIN_DE_LINEA;
				else
					resultado += Constants.TAB + entrada.getId()
							+ Constants.VARIABLES_SIN_VALOR + ""
							+ entrada.getValor() + Constants.FIN_DE_LINEA;
			}
		}
		return resultado;
	}
	private String tratarProcedimientos(Procedimiento procFuncPrincipal) {
		TablaDeSimbolos tablaSimbolo;
		FilaTabla variable;
		String resultado = ";Procedimiento "
				+ (procFuncPrincipal.getNombreProcedimiento() != null ? procFuncPrincipal
						.getNombreProcedimiento() : "1")
				+ Constants.FIN_DE_LINEA_MAS_LINEA_DE_ESPACIO;
		if (procFuncPrincipal.nodos.size()>0) {
			tablaSimbolo = (TablaDeSimbolos) procFuncPrincipal.nodos.get(0);
			for (int j = 0; j < tablaSimbolo.entradas.size(); j++) {
				variable = (FilaTabla) tablaSimbolo.entradas.get(j);
				resultado += Constants.TAB + variable.getId()
						+ Constants.VARIABLES_SIN_VALOR;
				if (variable.getValor() != null)
					resultado += variable.getValor() + Constants.FIN_DE_LINEA;
				else
					resultado += "?" + Constants.FIN_DE_LINEA;
			}
			Bloque bloque = (Bloque) procFuncPrincipal.nodos.get(1);
			if (procFuncPrincipal.nodos.get(1).getClass().equals(Bloque.class)) {
				tratarBloque(bloque);
			}
		}
		if (resultado != null)
			resultado += Constants.FIN_DE_LINEA
					+ procFuncPrincipal.getNombreProcedimiento() + ":";
		return resultado != null ? resultado : null;
	}
}