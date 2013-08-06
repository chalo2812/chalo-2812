package ar.edu.caece.pl.asem.model.impl.visitors;

import ar.edu.caece.pl.asem.model.AbstractVisitor;
import ar.edu.caece.pl.asem.model.IVisitor;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.impl.BloqueVarParam;
import ar.edu.caece.pl.asin.model.impl.Condicion;
import ar.edu.caece.pl.asin.model.impl.Expresion;
import ar.edu.caece.pl.asin.model.impl.Factor;
import ar.edu.caece.pl.asin.model.impl.Header;
import ar.edu.caece.pl.asin.model.impl.ProcsFuncs;
import ar.edu.caece.pl.asin.model.impl.Sentencia;
import ar.edu.caece.pl.asin.model.impl.ShowElem;
import ar.edu.caece.pl.asin.model.impl.Termino;
import ar.edu.caece.pl.asin.model.impl.Tipo;
import ar.edu.caece.pl.asin.model.impl.TipoC;
import ar.edu.caece.pl.asin.model.impl.Var;

public class TemplateVisitor extends AbstractVisitor {

	//Singleton
	private static IVisitor instance = new TemplateVisitor();
	private TemplateVisitor(){}
	public static IVisitor getInstance() { return instance; }
	
	@Override
	protected void visit(AnalizadorSintactico nt) {
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		}
	}
	
	@Override
	protected void visit(BloqueVarParam nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			// TODO Auto-generated method stub
			break;
		}
	}
	
//	@Override
//	protected void visit(C1 nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//		}
//	}
	
	@Override
	protected void visit(Condicion nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			// TODO Auto-generated method stub
			break;
		}
	}
	
//	@Override
//	protected void visit(E1 nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//		}
//	}
	
//	@Override
//	protected void visit(Else nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//		}
//	}
	
	@Override
	protected void visit(Expresion nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;
		}
	}
	
	@Override
	protected void visit(Factor nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			// TODO Auto-generated method stub
			break;
		}
		
	}
//	@Override
//	protected void visit(Factor1 nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//			
//		case IAnalizadorSintactico.PROD_SEGUNDA:
//			// TODO Auto-generated method stub
//			break;			
//		}
//	}
//	
//	@Override
//	protected void visit(Factor2 nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//		}
//	}
	
	@Override
	protected void visit(Header nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;			
		}
	}
	
	@Override
	protected void visit(ProcsFuncs nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;			
		}
	}
	
	@Override
	protected void visit(Sentencia nt) {
		
		switch (nt.getNroProduccion()) {		
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
	
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
	
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_TERCERA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_CUARTA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_QUINTA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_SEXTA:
			// TODO Auto-generated method stub
			break;			
			
		case IAnalizadorSintactico.PROD_SEPTIMA:
			// TODO Auto-generated method stub
			break;
		}
	}
	
//	@Override
//	protected void visit(Sentencia1 nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_SEGUNDA:
//			// TODO Auto-generated method stub
//			break;
//			
//		case IAnalizadorSintactico.PROD_TERCERA:
//			// TODO Auto-generated method stub
//			break;			
//		}
//	}
	
	@Override
	protected void visit(ShowElem nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;
		}
	}
	
//	@Override
//	protected void visit(ShowElementNext nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//		}
//	}
//	
//	@Override
//	protected void visit(T1 nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//		}
//	}
	
	@Override
	protected void visit(Termino nt) {

		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
		}
	}
	
//	@Override
//	protected void visit(Termino1 nt) {
//		
//		switch (nt.getNroProduccion()) {
//		
//		case IAnalizadorSintactico.PROD_ENCABEZADO:
//			// TODO Auto-generated method stub
//			break;
//		
//		case IAnalizadorSintactico.PROD_PRIMERA:
//			// TODO Auto-generated method stub
//			break;
//			
//		case IAnalizadorSintactico.PROD_SEGUNDA:
//			// TODO Auto-generated method stub
//			break;			
//		}
//	}
	
	@Override
	protected void visit(Tipo nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;			
		}
	}
	
	@Override
	protected void visit(TipoC nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			// TODO Auto-generated method stub
			break;			
		}
	}
	
	@Override
	protected void visit(Var nt) {

		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			// TODO Auto-generated method stub
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			// TODO Auto-generated method stub
			break;		
		}	
	}
}
