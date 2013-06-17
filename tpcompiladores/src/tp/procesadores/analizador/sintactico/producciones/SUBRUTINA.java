package tp.procesadores.analizador.sintactico.producciones;

import tp.procesadores.analizador.lexico.LexicAnalyzer;
import tp.procesadores.analizador.lexico.tokens.visitor.TokensVisitor;
import tp.procesadores.analizador.sintactico.SintacticAnalyzer;
import tp.procesadores.analizador.sintactico.producciones.subrutinas.FP0;

public class SUBRUTINA extends ProduccionC {

	public SUBRUTINA() {
		FP0 fp = null;
		producciones.add(fp);
		SUBRUTINA subrutina = null;
		producciones.add(subrutina);
	}

	// SUBRUTINA -> FP SUBRUTINA | e
	@Override
	public boolean reconocer(LexicAnalyzer lexic, TokensVisitor visitor,
			SintacticAnalyzer sintactic) {
		boolean r;
//		System.out.println("SUBRUTINA");
		producciones.set(0, new FP0());
		r = producciones.get(0).reconocer(lexic, visitor, sintactic);
		if (r) 
		{
			producciones.set(1, new SUBRUTINA());
			r = producciones.get(1).reconocer(lexic, visitor, sintactic);
		}else 
		{
			r = true;
		}
		return r;
	}
}
