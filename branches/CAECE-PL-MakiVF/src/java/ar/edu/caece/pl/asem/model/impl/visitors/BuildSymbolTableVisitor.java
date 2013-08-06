package ar.edu.caece.pl.asem.model.impl.visitors;

import java.util.List;

import ar.edu.caece.pl.asem.model.AbstractVisitor;
import ar.edu.caece.pl.asem.model.IVisitor;
import ar.edu.caece.pl.asem.model.impl.Ambiente;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametro;
import ar.edu.caece.pl.asem.model.impl.treeelements.ReturnType;
import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.impl.ProcsFuncs;

public class BuildSymbolTableVisitor extends AbstractVisitor {

	SymbolTable symbolTable = SymbolTable.getInstance();
	
	//Singleton
	private static IVisitor instance = new BuildSymbolTableVisitor();
	private BuildSymbolTableVisitor(){}
	public static IVisitor getInstance() { return instance; }

	@Override
	protected void visit(AnalizadorSintactico nt) {
		
		switch(nt.getNroProduccion()) {
		
			case IAnalizadorSintactico.PROD_ENCABEZADO:	//Crear ambiente global
				this.symbolTable.addAmbiente(SymbolTable.GLOBAL, new Ambiente());
				break;
				
			case IAnalizadorSintactico.PROD_PRIMERA:	//Crear declaraciones globales
				for(SimboloGenerico sg : nt.getDeclaraciones()) {
					Ambiente env = this.symbolTable.getAmbiente(SymbolTable.GLOBAL);
					if(env.contains(sg)){
						nt.getErrorManager().error("ASEM-ANALIZADORSINTACTICO-1-BUILDSYMBOLTABLE_CHECK_001","ASEM-Simbolo Global duplicado: "+sg.getName());
					} else {
						env.add(sg);
					}
				}
				break;
			case IAnalizadorSintactico.PROD_TERCERA:	//Validar la existencia de Main
				if(!this.symbolTable.containsAmbiente("main") && !this.symbolTable.containsAmbiente("principal")) {
					nt.getErrorManager().error("ASEM-ANALIZADORSINTACTICO-3-BUILDSYMBOLTABLE_CHECK_001","ASEM-Falta el procedimiento main o principal");
				}
				break;
		}
	}
	
	@Override
	protected void visit(ProcsFuncs nt) {
		
		String envName = nt.getName();	//Nombre del procedimiento o función
		List<Parametro> parametros = nt.getParametros();
		int returnType = nt.getReturnType();
		List<SimboloGenerico> declaraciones = nt.getDeclaraciones();
		
		if(this.symbolTable.containsAmbiente(envName)) { 
			nt.getErrorManager().error("ASEM-PROCSFUNCS-"+nt.getNroProduccion()+"-BUILDSYMBOLTABLE_CHECK_001","ASEM-Procedimiento o Función duplicado: " + envName);
			nt.setValidated(false);
			return;
		}
		
		//Nombre
		this.symbolTable.addAmbiente(envName, new Ambiente()); //Crear el ambiente al comenzar la funcion o procedimiento
		Ambiente env = this.symbolTable.getAmbiente(envName);
		
		
		int memoryInStack = (parametros.size() + 1) * 2;
		
		//Parametros
		for (Parametro p: parametros){
			
			if(!env.contains(p)){
				p.setMemoryInStack(memoryInStack);
				env.add(p);
				memoryInStack = memoryInStack - 2;
			} else {
				nt.getErrorManager().error("ASEM-PROCSFUNCS-"+nt.getNroProduccion()+"-BUILDSYMBOLTABLE_CHECK_002","ASEM-Parametro duplicado: "+ p.getName() +" en ambiente:" + envName);
				nt.setValidated(false);
			}
		}
		
		//ReturnType
		env.setReturnType(new ReturnType(returnType));	//VOID, INTEGER o NATURAL
		
		//Declaraciones
		for (SimboloGenerico sg : declaraciones) {
			if(!env.contains(sg)){
				env.add(sg);
			} else {
				nt.getErrorManager().error("ASEM-PROCSFUNCS-"+nt.getNroProduccion()+"-BUILDSYMBOLTABLE_CHECK_003","ASEM-Simbolo duplicado: "+ sg.getName() +" en ambiente:" + envName);
			}
		}
	}
}
