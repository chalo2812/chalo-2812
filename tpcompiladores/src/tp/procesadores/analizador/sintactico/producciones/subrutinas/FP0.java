package tp.procesadores.analizador.sintactico.producciones.subrutinas;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.ProduccionC;

public class FP0 extends ProduccionC {
	
	public FP0(){
		FUNCION0 funcion = new FUNCION0();
		producciones.add(funcion);
		PROCEDIMIENTO0 procedimiento = new PROCEDIMIENTO0();
		producciones.add(procedimiento);
	}
	
	
	//FP  ->   FUNCION | PROCEDIMIENTO
	@Override 
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor, SintacticAnalyzer sintactic) {
		boolean r;
//		System.out.println("FP0" + sintactic.actual.coord.getY());
		if (sintactic.siguiente.accept(visitor).equals("funcion"))
		{
			r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		}else
		{
			if (sintactic.siguiente.accept(visitor).equals("procedimiento"))
			{
				r = producciones.get(1).reconocer(lexic, visitor, sintactic);
			}else
			{
				r = false;
			}
		}
		return r;
	}
}
