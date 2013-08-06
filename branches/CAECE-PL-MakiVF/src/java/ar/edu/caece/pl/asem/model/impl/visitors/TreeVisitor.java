package ar.edu.caece.pl.asem.model.impl.visitors;

import java.util.List;

import ar.edu.caece.pl.alex.model.IToken;
import ar.edu.caece.pl.asem.model.AbstractVisitor;
import ar.edu.caece.pl.asem.model.IVisitor;
import ar.edu.caece.pl.asem.model.impl.Ambiente;
import ar.edu.caece.pl.asem.model.impl.SymbolTable;
import ar.edu.caece.pl.asem.model.impl.treeelements.AbstractElement;
import ar.edu.caece.pl.asem.model.impl.treeelements.Arreglo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Asignacion;
import ar.edu.caece.pl.asem.model.impl.treeelements.Constante;
import ar.edu.caece.pl.asem.model.impl.treeelements.Cuerpo;
import ar.edu.caece.pl.asem.model.impl.treeelements.Declaraciones;
import ar.edu.caece.pl.asem.model.impl.treeelements.Distinto;
import ar.edu.caece.pl.asem.model.impl.treeelements.DivisionInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.DivisionNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.Funcion;
import ar.edu.caece.pl.asem.model.impl.treeelements.Igual;
import ar.edu.caece.pl.asem.model.impl.treeelements.Impar;
import ar.edu.caece.pl.asem.model.impl.treeelements.Invocacion;
import ar.edu.caece.pl.asem.model.impl.treeelements.Mayor;
import ar.edu.caece.pl.asem.model.impl.treeelements.MayorIgual;
import ar.edu.caece.pl.asem.model.impl.treeelements.Menor;
import ar.edu.caece.pl.asem.model.impl.treeelements.MenorIgual;
import ar.edu.caece.pl.asem.model.impl.treeelements.MetodoGenerico;
import ar.edu.caece.pl.asem.model.impl.treeelements.Mientras;
import ar.edu.caece.pl.asem.model.impl.treeelements.NumeroInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.NumeroNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.Par;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametro;
import ar.edu.caece.pl.asem.model.impl.treeelements.Parametros;
import ar.edu.caece.pl.asem.model.impl.treeelements.Procedimiento;
import ar.edu.caece.pl.asem.model.impl.treeelements.ProductoInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.ProductoNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.Read;
import ar.edu.caece.pl.asem.model.impl.treeelements.RestaInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.RestaNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.ReturnType;
import ar.edu.caece.pl.asem.model.impl.treeelements.Show;
import ar.edu.caece.pl.asem.model.impl.treeelements.ShowLn;
import ar.edu.caece.pl.asem.model.impl.treeelements.Si;
import ar.edu.caece.pl.asem.model.impl.treeelements.SimboloGenerico;
import ar.edu.caece.pl.asem.model.impl.treeelements.Sino;
import ar.edu.caece.pl.asem.model.impl.treeelements.SumaInt;
import ar.edu.caece.pl.asem.model.impl.treeelements.SumaNat;
import ar.edu.caece.pl.asem.model.impl.treeelements.ToInteger;
import ar.edu.caece.pl.asem.model.impl.treeelements.ToNatural;
import ar.edu.caece.pl.asem.model.impl.treeelements.Variable;
import ar.edu.caece.pl.asem.model.tree.TreeNode;
import ar.edu.caece.pl.asin.manager.IAnalizadorSintactico;
import ar.edu.caece.pl.asin.manager.impl.AnalizadorSintactico;
import ar.edu.caece.pl.asin.model.impl.Condicion;
import ar.edu.caece.pl.asin.model.impl.Expresion;
import ar.edu.caece.pl.asin.model.impl.Factor;
import ar.edu.caece.pl.asin.model.impl.ProcsFuncs;
import ar.edu.caece.pl.asin.model.impl.Sentencia;
import ar.edu.caece.pl.asin.model.impl.Termino;

public class TreeVisitor extends AbstractVisitor {

	//Singleton
	private static IVisitor instance = new TreeVisitor();
	private TreeVisitor(){}
	public static IVisitor getInstance() { return instance; }
	
	@Override
	protected void visit(AnalizadorSintactico nt) {
		
		//if(!nt.validado()){return;}
		
		switch(nt.getNroProduccion()){
			case IAnalizadorSintactico.PROD_PRIMERA:
				for(SimboloGenerico sg : nt.getDeclaraciones()) {
					nt.getNodoDeclaraciones().addChild(new TreeNode(sg));
				}
				break;
			case IAnalizadorSintactico.PROD_SEGUNDA:
				for(TreeNode node : nt.getListaMetodos()) {
					nt.getNodoCodigo().addChild(node);
				}
				break;
			case IAnalizadorSintactico.PROD_TERCERA:
				nt.getTreeNode().addChild(nt.getNodoDeclaraciones());
				nt.getTreeNode().addChild(nt.getNodoCodigo());
				break;
		}
	}
	
	@Override
	protected void visit(ProcsFuncs nt) {			//Con estado

		//if(!nt.validado()){return;}
		
		//Componentes del procedimientos
		String                       envName = nt.getName();	//Nombre del procedimiento o funciÃ³n
		List<Parametro>           parametros = nt.getParametros();
		int                       returnType = nt.getReturnType();
		List<SimboloGenerico>  declaraciones = nt.getDeclaraciones();
		List<TreeNode> sentencias = nt.getListaSentenciasPorMetodo();
		
		MetodoGenerico method = null;
		
		switch(nt.getNroProduccion()) {
			case IAnalizadorSintactico.PROD_PRIMERA:	//Procedure
				method = new Procedimiento(envName);
				break;
			case IAnalizadorSintactico.PROD_SEGUNDA:	//Funcion
				method = new Funcion(envName);
				break;
		}
		
		method.setReturnType(returnType);
		method.setEnvName(envName);
		
		TreeNode methodNode = new TreeNode(method);
		TreeNode parametrosNode = new TreeNode(new Parametros());
		TreeNode declaracionesNode = new TreeNode(new Declaraciones());
		TreeNode returnTypeNode = new TreeNode(new ReturnType(returnType));
		TreeNode cuerpoNode = new TreeNode(new Cuerpo());
		
		methodNode.addChild(parametrosNode);
		methodNode.addChild(declaracionesNode);
		methodNode.addChild(returnTypeNode);
		methodNode.addChild(cuerpoNode);
		
		for(Parametro param : parametros) {
			parametrosNode.addChild(new TreeNode(param));
		}
		
		for(SimboloGenerico sg : declaraciones) {
			declaracionesNode.addChild(new TreeNode(sg));
		}
		
		for(TreeNode sentencia : sentencias) {
			cuerpoNode.addChild(sentencia);
		}
		sentencias.clear();		//Limpiar la lista de sentencias acumuladas por metodo, para que al reconocer al proximo la lista este limpia
		
		//El ultimo nodo del cuerpo es la expresion que devuelve la funcion
		if(nt.getNroProduccion() == IAnalizadorSintactico.PROD_SEGUNDA) {
			cuerpoNode.addChild(nt.getNodoExpresion());
		}
		
		nt.getListaMetodos().add(methodNode);
	}
	
	@Override
	protected void visit(Sentencia nt) {
		
		//if(!nt.validado()){return;}
		
		/* 
		<SENTENCIA>-> IDENTIFICADOR           := <EXP> 				    		; <SENTENCIA>	|
		              IDENTIFICADOR [ <EXP> ] := <EXP> 				    		; <SENTENCIA>	|
		              IDENTIFICADOR ( <FACTOR_1> )     				    		; <SENTENCIA>	|
		              IF 	<CONDICION> THEN <SENTENCIA> ELSE <SENTENCIA> ENDIF ; <SENTENCIA> 	|
		              IF 	<CONDICION> THEN <SENTENCIA> ENDIF 					; <SENTENCIA> 	|
		              WHILE 	<CONDICION> DO   <SENTENCIA> END-WHILE 			; <SENTENCIA> 	|
		              SHOWLN <SHOW_ELEM> 						    			; <SENTENCIA>	|
		              SHOW   <SHOW_ELEM>						    			; <SENTENCIA> 	|
		              READ   IDENTIFICADOR						    			; <SENTENCIA> 	|
		              															; <SENTENCIA> 	|
		       		  LAMBDA																	*/
		switch (nt.getNroProduccion()) {		
	
			case IAnalizadorSintactico.PROD_PRIMERA:	// IDENTIFICADOR           := <EXP>
				
				TreeNode asignacion = new TreeNode(new Asignacion());
				Ambiente ambiente = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
				
				AbstractElement variableDelAmbiente = ambiente.getVariable(nt.getIdentificador());
				
				if(variableDelAmbiente == null){
					variableDelAmbiente = ambiente.getParametro(nt.getIdentificador());	
				}
				
				TreeNode variable = new TreeNode(variableDelAmbiente);
				
				asignacion.addChild(variable);
				asignacion.addChild(nt.getNodoExpresion1());
				
				nt.setTreeNode(asignacion);
				
				break;
			case IAnalizadorSintactico.PROD_SEGUNDA:	// IDENTIFICADOR [ <EXP> ] := <EXP>
				
				TreeNode asignacion2 = new TreeNode(new Asignacion());
				Ambiente ambiente2 = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
				
				TreeNode arreglo = new TreeNode( ambiente2.getArreglo(nt.getIdentificador()) );
				arreglo.addChild(nt.getNodoExpresion1());
				asignacion2.addChild(arreglo);
				asignacion2.addChild(nt.getNodoExpresion2());
				
				nt.setTreeNode(asignacion2);
				
				break;
			
			case IAnalizadorSintactico.PROD_TERCERA:	// IDENTIFICADOR ( <FACTOR_1> )
				
				//1) Creo objeto Invocacion
				nt.getTreeNode().setData(new Invocacion());
				
				Ambiente ambiente3 = SymbolTable.getInstance().getAmbiente(nt.getIdentificador());
				
				//2) Agrego como hijo de Invocacion un Procedimiento o Funcion
				if (ambiente3.getReturnType().getType() == SymbolTable.VOID) {
					
					Procedimiento proc = new Procedimiento(nt.getIdentificador());
					proc.setReturnType(ambiente3.getReturnType().getType());
					nt.getTreeNode().addChild(new TreeNode(proc));
					
				} else {
					Funcion func = new Funcion(nt.getIdentificador());
					func.setReturnType(ambiente3.getReturnType().getType());
					nt.getTreeNode().addChild(new TreeNode(func));
				}
			
				//3) Agrego como hijo de Invocacion un Parametros
				TreeNode parametros = new TreeNode(new Parametros());
				nt.getTreeNode().addChild(parametros);
				
				//4) Agrego a Parametros las expresiones que vienen como parametros de la invocacion
				for (TreeNode parametro : nt.getParametros()) {
					parametros.addChild(parametro);
				}
				
				break;
				
			case IAnalizadorSintactico.PROD_CUARTA:		// IF 	<CONDICION> THEN <SENTENCIA> ELSE <SENTENCIA> ENDIF ;
				
				//1) Creo el IF
				TreeNode si = new TreeNode(new Si());
				
				//2) A la izquierda la Condicion con sus expresiones
				si.addChild(nt.getNodoCondicion());
				
				//3) A la derecha el cuerpo con sus sub-sentencias
				TreeNode cuerpoSi = new TreeNode(new Cuerpo());
				
				for(TreeNode sentencia : nt.getSentencias()) {					
					cuerpoSi.addChild(sentencia);
				}
				nt.getSentencias().clear();
				
				si.addChild(cuerpoSi);
				nt.setTreeNode(si);
				
				break;
				
			case IAnalizadorSintactico.PROD_QUINTA:		//ELSE
				//4) último el Sino a la derecha de todo con sus sub-sentencias
				TreeNode sino = new TreeNode(new Sino());
				
				for(TreeNode sentencia : nt.getSentencias()) {					
					sino.addChild(sentencia);
				}
				nt.getSentencias().clear();
				
				nt.getTreeNode().addChild(sino);
				
				break;
				
			case IAnalizadorSintactico.PROD_SEXTA:		//WHILE
				//1) Creo el WHILE
				TreeNode mientras = new TreeNode(new Mientras());
				
				//2) A la izquierda la Condicion con sus expresiones
				mientras.addChild(nt.getNodoCondicion());
				
				//3) A la derecha el cuerpo con sus sub-sentencias
				TreeNode cuerpoMientras = new TreeNode(new Cuerpo());
				
				for(TreeNode sentencia : nt.getSentencias()) {
					cuerpoMientras.addChild(sentencia);
				}
				nt.getSentencias().clear();
				
				mientras.addChild(cuerpoMientras);
				
				nt.setTreeNode(mientras);
				
				break;			
				
			case IAnalizadorSintactico.PROD_SEPTIMA:	//ShowLn
				//1) Creo el ShowLn
				nt.getTreeNode().setData(new ShowLn());
				
				//2) Le cuelgo los elementos
				for(TreeNode elem : nt.getElementos()) {					
					nt.getTreeNode().addChild(elem);
				}
				nt.getElementos().clear();
				break;
				
			case IAnalizadorSintactico.PROD_OCTAVA:		//Show
				//1) Creo el Show
				nt.getTreeNode().setData(new Show());
				
				//2) Le cuelgo los elementos
				for(TreeNode elem : nt.getElementos()) {					
					nt.getTreeNode().addChild(elem);
				}
				nt.getElementos().clear();
				break;
				
			case IAnalizadorSintactico.PROD_NOVENA:		//Read
				//1) Creo el Read
				nt.getTreeNode().setData(new Read());
				
				//2) Le cuelgo el Read
				Ambiente ambiente9 = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
				
				TreeNode variable9 = new TreeNode( ambiente9.getVariable(nt.getIdentificador()));
				
				nt.getTreeNode().addChild(variable9);
				break;
				
			case IAnalizadorSintactico.PROD_DECIMA:		//; (sentencia vacía)
				break;	//La sentencia vacía no genera nada
		}
	}
	
	@Override
	protected void visit(Condicion nt) {
		
		//if(!nt.validado()){return;}
		
		TreeNode tree = new TreeNode();
		
		switch (nt.getNroProduccion()) {		
		
			case IAnalizadorSintactico.PROD_PRIMERA:
				// <CONDICION> -> ODD(<EXP>)
				
				tree.setData(new Impar());
				tree.addChild(nt.getExpresion1());
				
				break;
				
			case IAnalizadorSintactico.PROD_SEGUNDA:	
				// <CONDICION> -> EVEN(<EXP>)
				
				tree.setData(new Par());
				tree.addChild(nt.getExpresion1());
				
				break;
			
			case IAnalizadorSintactico.PROD_TERCERA:
				
				
				switch (nt.getOperador()){
				case IToken.TYPE_OPER_IGUAL:
					// =
					tree.setData(new Igual());
					
					break;
				case IToken.TYPE_OPER_MAYOR_IGUAL:
					// =>
					tree.setData(new MayorIgual());
					
					break;
				case IToken.TYPE_OPER_MENOR_IGUAL:
					// <=
					tree.setData(new MenorIgual());
					
					break;
				case IToken.TYPE_OPER_MAYOR:
					// >
					tree.setData(new Mayor());
					
					break;
				case IToken.TYPE_OPER_MENOR:
					// <
					tree.setData(new Menor());
					
					break;
				case IToken.TYPE_OPER_DISTINTO:
					// <>
					tree.setData(new Distinto());
					
					break;
				}
				
				tree.addChild(nt.getExpresion1());
				tree.addChild(nt.getExpresion2());
				
				break;
		}
		nt.setTreeNode(tree);
	}
	
	@Override
	protected void visit(Expresion nt) {
		
		//if(!nt.validado()){return;}
		
		if (nt.getTreeNode().getData() != null){
			// siguientes 
			
			TreeNode treeNodeTemp = new TreeNode();
			
			// 1) Vemos el tipo de nodo segun el operador.
			switch (nt.getOperador()) {
				case IToken.TYPE_OPER_MAS_INT:
					treeNodeTemp.setData(new SumaInt());
					break;
				case IToken.TYPE_OPER_MAS_NAT:
					treeNodeTemp.setData(new SumaNat());
					break;
				case IToken.TYPE_OPER_MENOS_INT:
					treeNodeTemp.setData(new RestaInt());
					break;
				case IToken.TYPE_OPER_MENOS_NAT:
					treeNodeTemp.setData(new RestaNat());
					break;
			}
			
			// 2) Agregamos a izquierda lo que ya teniamos
			treeNodeTemp.addChild(nt.getTreeNode());
			
			// 3) Agregamos a la derecha, la produccion que estamos reconociendo.
			// <EXPRESION> -> <TERMINO>
			
			treeNodeTemp.addChild( nt.getNodoTermino());
			
			// 4) rearmo el nodo en el actual.
			nt.setTreeNode(treeNodeTemp);
			
		} else {	// 1er elemento
			// <EXPRESION> -> <TERMINO>
			nt.setTreeNode(nt.getNodoTermino());
		}
	}
	
	@Override
	protected void visit(Termino nt) {
		
		//if(!nt.validado()){return;}
		
		if (nt.getTreeNode().getData() != null){
			// siguientes 
			
			TreeNode treeNodeTemp = new TreeNode();
			
			// 1) Vemos el tipo de nodo segun el operador.
			switch (nt.getOperador()) {
				case IToken.TYPE_OPER_MULT_INT:
					
					treeNodeTemp.setData(new ProductoInt());
					break;
	
				case IToken.TYPE_OPER_MULT_NAT:
					
					treeNodeTemp.setData(new ProductoNat());
					break;
	
				case IToken.TYPE_OPER_DIV_INT:
	
					treeNodeTemp.setData(new DivisionInt());
					break;
	
				case IToken.TYPE_OPER_DIV_NAT:
	
					treeNodeTemp.setData(new DivisionNat());
					break;
			}
			
			// 2) Agregamos a izquierda lo que ya teniamos
			treeNodeTemp.addChild(nt.getTreeNode());
			
			// 3) Agregamos a la derecha, la produccion que estamos reconociendo.
			if (nt.getNroProduccion() == IAnalizadorSintactico.PROD_PRIMERA){
				// <TERMINO> -> <FACTOR>
				
				treeNodeTemp.addChild( nt.getNodoFactor());
				
			} else if (nt.getNroProduccion() == IAnalizadorSintactico.PROD_SEGUNDA){
				
				TreeNode treeNodeTemp2 = new TreeNode();
				
				if ( IToken.PALABRA_RESERVADA_TONATURAL.equals(nt.getToken()) ){

					// <TERMINO> -> TONATURAL (<EXP>) 
					treeNodeTemp2.setData(new ToNatural());
					treeNodeTemp2.addChild(nt.getNodoExpresion());
					
				} else if ( IToken.PALABRA_RESERVADA_TOINTEGER.equals(nt.getToken()) ){
					
					// <TERMINO> -> TONINTEGER (<EXP>)					
					treeNodeTemp2.setData(new ToInteger());
					treeNodeTemp2.addChild(nt.getNodoExpresion());
				}
				
				treeNodeTemp.addChild(treeNodeTemp2);
			}
			
			// 4) rearmo el nodo en el actual.
			nt.setTreeNode(treeNodeTemp);
			
		} else {
			// 1er elemento
			
			if (nt.getNroProduccion() == IAnalizadorSintactico.PROD_PRIMERA){
				// <TERMINO> -> <FACTOR>
				
				nt.setTreeNode( nt.getNodoFactor());
			}
			else if (nt.getNroProduccion() == IAnalizadorSintactico.PROD_SEGUNDA){
				
				if ( IToken.PALABRA_RESERVADA_TONATURAL.equals(nt.getToken()) ){
					// <TERMINO> -> TONATURAL (<EXP>) 
					
					nt.getTreeNode().setData(new ToNatural());
					nt.getTreeNode().addChild(nt.getNodoExpresion());
				}
				else if ( IToken.PALABRA_RESERVADA_TOINTEGER.equals(nt.getToken()) ){
					// <TERMINO> -> TONINTEGER (<EXP>)
					
					nt.getTreeNode().setData(new ToInteger());
					nt.getTreeNode().addChild(nt.getNodoExpresion());
				}

			}
		}
	}
	
	@Override
	protected void visit(Factor nt) {
		
		////if(!nt.validado()){return;}
		
		Ambiente ambiente = SymbolTable.getInstance().getAmbiente(nt.getEnvName());
		
		switch (nt.getNroProduccion()) {		
		
//		case IAnalizadorSintactico.PROD_PRIMERA:
			//Se hace dentro del codigo de Factor
//			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:	//Caso base: numero entero
			nt.getTreeNode().setData(new NumeroInt(nt.getNumValue()));
			break;
		
		case IAnalizadorSintactico.PROD_TERCERA:	//Caso base: numero natural
			nt.getTreeNode().setData(new NumeroNat(nt.getNumValue()));
			break;
			
		case IAnalizadorSintactico.PROD_CUARTA:		// <FACTOR> ->	IDENTIFICADOR ( <EXP> , .. , <EXP> )
			
			//1) Creo objeto Invocacion
			nt.getTreeNode().setData(new Invocacion());
			
			Ambiente ambiente4 = SymbolTable.getInstance().getAmbiente(nt.getIdentificador());
			
			//2) Agrego como hijo de Invocacion un Procedimiento o Funcion
			if (ambiente4.getReturnType().getType() == SymbolTable.VOID) {
				
				Procedimiento proc = new Procedimiento(nt.getIdentificador());
				proc.setReturnType(ambiente4.getReturnType().getType());
				nt.getTreeNode().addChild(new TreeNode(proc));
				
			} else {
				Funcion func = new Funcion(nt.getIdentificador());
				func.setReturnType(ambiente4.getReturnType().getType());
				nt.getTreeNode().addChild(new TreeNode(func));
			}
			
			//3) Agrego como hijo de Invocacion un Parametros
			TreeNode parametros = new TreeNode(new Parametros());
			nt.getTreeNode().addChild(parametros);
			
			//4) Agrego a Parametros las expresiones que vienen como parametros de la invocacion
			for (TreeNode parametro : nt.getParametros()) {
				parametros.addChild(parametro);
			}
			
			break;
			
		case IAnalizadorSintactico.PROD_QUINTA:		//Caso base: Arreglo 
			//(luego se le colgara la expresion que resuelve el subindice que se utiliza en este factor)
			
			String idArr = nt.getIdentificador();
			Arreglo arr = ambiente.getArreglo(idArr);
			nt.getTreeNode().setData(arr);
			break;
			
		case IAnalizadorSintactico.PROD_SEXTA:		//Caso base: identificador
			
			String id = nt.getIdentificador();
			SimboloGenerico sg = ambiente.getSimbolo(id);
			
			if(sg instanceof Constante) {
				
				if (sg.getType() == SymbolTable.INTEGER) {					
					nt.getTreeNode().setData(new NumeroInt(sg.getValue()));

				} else if (sg.getType() == SymbolTable.NATURAL) {
					nt.getTreeNode().setData(new NumeroNat(sg.getValue()));
				}
			} else if (sg instanceof Variable || sg instanceof Parametro) {
				nt.getTreeNode().setData(sg);
			}
			break;
			
		case IAnalizadorSintactico.PROD_SEPTIMA:	
			break;
	}
		
	}
}
