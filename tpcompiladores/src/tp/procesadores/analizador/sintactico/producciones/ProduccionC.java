package tp.procesadores.analizador.sintactico.producciones;

import java.util.ArrayList;
import java.util.List;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.manejador.errores.ManejadorErrores;

public class ProduccionC implements ProduccionI{ 

	public List<ProduccionI> producciones = new ArrayList<ProduccionI>();
	public ManejadorErrores merrores = new ManejadorErrores();
	
	@Override
	public void add(ProduccionI simbolo) {
		producciones.add(simbolo);
	}

	@Override
	public void remove(ProduccionI simbolo) {
		producciones.remove(simbolo);
	}

	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		return false;
	}

}
