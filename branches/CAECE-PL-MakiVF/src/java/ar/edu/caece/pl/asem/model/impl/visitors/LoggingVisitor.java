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

public class LoggingVisitor extends AbstractVisitor { 
	
	//Singleton
	private static IVisitor instance = new LoggingVisitor();
	private LoggingVisitor(){}
	public static IVisitor getInstance() { return instance; }
	
/*    Loguea la gramatica
<PROGRAMA> -> <HEADER> <PROCS_FUNCS> EOF

<HEADER> -> CONST IDENTIFICADOR : <TIPO_C>; <HEADER> | 
            VAR <VAR>; <HEADER> |  
            LAMBDA

<TIPO_C> -> NATURAL = NUMERO_NAT					|
            NATURAL = NUMERO_NAT , IDENTIFICADOR : <TIPO_C> 	|
            INTEGER = NUMERO     , IDENTIFICADOR : <TIPO_C>	|
            INTEGER = NUMERO

<VAR> -> IDENTIFICADOR , <VAR> 			|
         IDENTIFICADOR : <TIPO>;			|
         IDENTIFICADOR [ NUMERO     ] , <VAR>	|
         IDENTIFICADOR [ NUMERO_NAT ] , <VAR>	|
         IDENTIFICADOR [ NUMERO     ] : <TIPO>; 	|
         IDENTIFICADOR [ NUMERO_NAT ] : <TIPO>;

<TIPO> -> INTEGER | NATURAL

<PROCS_FUNCS> -> 
    PROCEDURE IDENTIFICADOR(<BLOQUE_VAR_PARAM>);       <HEADER> BEGIN <SENTENCIA> END-PROC;     <PROCS_FUNCS> |
    FUNCTION  IDENTIFICADOR(<BLOQUE_VAR_PARAM>):<TIPO>;<HEADER> BEGIN <SENTENCIA> END-FUNC<EXP>;<PROCS_FUNCS> |
    LAMBDA

<BLOQUE_VAR_PARAM> -> BYREF IDENTIFICADOR : <TIPO> |
                      BYVAL IDENTIFICADOR : <TIPO> |
                            IDENTIFICADOR : <TIPO> |
                      BYREF IDENTIFICADOR : <TIPO>, <BLOQUE_VAR_PARAM> |
                      BYVAL IDENTIFICADOR : <TIPO>, <BLOQUE_VAR_PARAM> |
                            IDENTIFICADOR : <TIPO>, <BLOQUE_VAR_PARAM>

<SENTENCIA> -> IDENTIFICADOR           := <EXP> ; <SENTENCIA> |
               IDENTIFICADOR [ <EXP> ] := <EXP> ; <SENTENCIA> |
               IDENTIFICADOR ( <FACTOR_1> ) ;     <SENTENCIA> |
               IF    <CONDICION> THEN <SENTENCIA> ELSE <SENTENCIA> END-IF ;    <SENTENCIA> |
               IF    <CONDICION> THEN <SENTENCIA>                  END-IF ;    <SENTENCIA> |
               WHILE <CONDICION> DO   <SENTENCIA>                  END-WHILE ; <SENTENCIA> |
               SHOWLN <SHOW_ELEM> ; <SENTENCIA> |
               SHOW <SHOW_ELEM>   ; <SENTENCIA> |
               READ IDENTIFICADOR ; <SENTENCIA> |
               ; <SENTENCIA> |
               LAMBDA

<CONDICION> -> ODD ( <EXP> )  |
               EVEN( <EXP> )  |
               <EXP>  = <EXP> |
               <EXP> >= <EXP> |
               <EXP> <= <EXP> |
               <EXP>  < <EXP> |
               <EXP>  > <EXP> |
               <EXP> <> <EXP>

<SHOW_ELEM> -> CADENA |
               CADENA, <SHOW_ELEM> |
               <EXP> , <SHOW_ELEM> |
               <EXP>    		

<EXP> -> <TERMINO>  ++ <EXP> |
         <TERMINO>   + <EXP> |
         <TERMINO>  -- <EXP> |
         <TERMINO>   - <EXP> |
         <TERMINO>

<TERMINO> -> <FACTOR> |
             <FACTOR>  * <TERMINO> |
             <FACTOR> ** <TERMINO> |
             <FACTOR>  / <TERMINO> |
             <FACTOR> // <TERMINO> |
             TONATURAL (<EXP>)     |
             TONATURAL (<EXP>)  * <TERMINO> |
             TONATURAL (<EXP>) ** <TERMINO> |
             TONATURAL (<EXP>)  / <TERMINO> |
             TONATURAL (<EXP>) // <TERMINO> |
             TOINTEGER (<EXP>)  |
             TOINTEGER (<EXP>)  * <TERMINO> |
             TOINTEGER (<EXP>)  / <TERMINO> |
             TOINTEGER (<EXP>) ** <TERMINO> |
             TOINTEGER (<EXP>) // <TERMINO>

<FACTOR> -> ( <EXP> ) | 
            NUMERO    |
            NUMERO_NAT|
            IDENTIFICADOR ( <FACTOR_1> ) |
            IDENTIFICADOR [ <EXP> ]      |
            IDENTIFICADOR

<FACTOR_1> -> <EXP> , <FACTOR_1> |
              <EXP> |
              LAMBDA */
	
	protected void visit(AnalizadorSintactico nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer Programa:\n" +
			    "    <PROGRAMA> -> <HEADER> <PROCS_FUNCS> EOF");
			break;
		}
	}
	
	protected void visit(BloqueVarParam nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueVarParam:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<BLOQUE_VAR_PARAM> -> BYREF IDENTIFICADOR : <TIPO> <BLOQUE_VAR_PARAM_1>");
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<BLOQUE_VAR_PARAM> -> BYVAL IDENTIFICADOR : <TIPO> <BLOQUE_VAR_PARAM_1>");
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			nt.getErrorManager().debug( "<BLOQUE_VAR_PARAM> -> IDENTIFICADOR : <TIPO> <BLOQUE_VAR_PARAM_1>");
			break;
		}
	}
	protected void visit(Condicion nt) {
	
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer Condicion:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<CONDICION> -> ODD(<EXP>)");
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<CONDICION> -> EVEN(<EXP>)");
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			nt.getErrorManager().debug( "<CONDICION> -> <EXP> C1 <EXP>");
			break;
		//
		}
	}
	
	protected void visit(Expresion nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueExpresion:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<EXP> -> <TERMINO> <E1> <EXP> | <TERMINO>");
			break;
		
		/*
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<EXP> -> TONATURAL ( <TERMINO> ) <E'> | TOINTEGER ( <TERMINO> ) <E'>", nt.getTokenSiguiente() );
			break;*/
		}
	}
	
	protected void visit(Factor nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueFactor:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<FACTOR> -> ( <EXP> )");
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<FACTOR> -> NUMERO");
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			nt.getErrorManager().debug( "<FACTOR> -> NUMERO_NAT");
			break;

		case IAnalizadorSintactico.PROD_CUARTA:
			nt.getErrorManager().debug( "<FACTOR> -> IDENTIFICADOR ( <FACTOR1> )");

		case IAnalizadorSintactico.PROD_QUINTA:
			nt.getErrorManager().debug( "<FACTOR> -> IDENTIFICADOR [ <EXP> ]");

		case IAnalizadorSintactico.PROD_SEXTA:
			nt.getErrorManager().debug( "<FACTOR> -> IDENTIFICADOR ");
		
			break;
		
		}
	}
	
	protected void visit(Header nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueFactor2:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<HEADER> -> CONST IDENTIFICADOR : <TIPO_C> <HEADER>");
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<HEADER> -> VAR <VAR>; <HEADER>");
			break;			
		}
	}
	
	protected void visit(ProcsFuncs nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueProcsFuncs:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<PROCS_FUNCS> -> PROCEDURE IDENTIFICADOR ( <BLOQUE_VAR_PARAM> ) ; <HEADER> BEGIN <SENTENCIAS> END-PROC ; <PROCS_FUNCS>");
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<PROCS_FUNCS> -> FUNCTION  IDENTIFICADOR(<BLOQUE_VAR_PARAM>): <TIPO>;<HEADER> BEGIN <SENTENCIAS> END FUNC <EXP> ; <PROCS_FUNCS>");
			break;			
		}
	}
	
	protected void visit(Sentencia nt) {
	
		switch (nt.getNroProduccion()) {		
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueSentencia:");
			break;
	
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<SENTENCIA> -> IDENTIFICADOR := <EXP>           ; <SENTENCIA>");
			break;
	
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<SENTENCIA> -> IDENTIFICADOR [ <EXP> ] := <EXP> ; <SENTENCIA>");
			break;
		
		case IAnalizadorSintactico.PROD_TERCERA:
			nt.getErrorManager().debug( "<SENTENCIA> -> IDENTIFICADOR ( <FACTOR_1> )     ; <SENTENCIA>");
			break;
			
		case IAnalizadorSintactico.PROD_CUARTA:
			nt.getErrorManager().debug( "<SENTENCIA> -> IF <CONDICION> THEN <SENTENCIA> <ELSE> ENDIF ; <SENTENCIA>");
			break;
			
		case IAnalizadorSintactico.PROD_QUINTA:
			nt.getErrorManager().debug( "IF 	<CONDICION> THEN <SENTENCIA>                  ENDIF ; <SENTENCIA>");
			break;
			
		case IAnalizadorSintactico.PROD_SEXTA:
			nt.getErrorManager().debug( "<SENTENCIA> -> WHILE <CONDICION> DO <SENTENCIA> END-WHILE	; <SENTENCIA>");
			break;
			
		case IAnalizadorSintactico.PROD_SEPTIMA:
			nt.getErrorManager().debug( "<SENTENCIA> -> SHOWLN <SHOW_ELEM>	; <SENTENCIA>");
			break;			
			
		case IAnalizadorSintactico.PROD_OCTAVA:
			nt.getErrorManager().debug( "<SENTENCIA> -> SHOW   <SHOW_ELEM>	; <SENTENCIA>");
			break;
		
		case IAnalizadorSintactico.PROD_NOVENA:
			nt.getErrorManager().debug( "<SENTENCIA> -> READ   IDENTIFICADOR	; <SENTENCIA>");
			break;
			
		case IAnalizadorSintactico.PROD_DECIMA:
			nt.getErrorManager().debug( "<SENTENCIA> -> ; <SENTENCIA> ");
			break;
			
		}
	}
	
	protected void visit(ShowElem nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueShowElem:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<SHOW_ELEM> -> CADENA <SHOW_ELEM_NEXT>");
			break;
		
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<SHOW_ELEM> -> <EXP> <SHOW_ELEM_NEXT>");
			break;
		}
	}
		
	protected void visit(Termino nt) {

		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueTermino:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<TERMINO> -> <FACTOR> <T'>");
			break;
		}
	}
	
	protected void visit(Tipo nt) {
		
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueTipo:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<TIPO> -> INTEGER ");
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<TIPO> -> NATURAL");
			break;			
		}
	}
	
	protected void visit(TipoC nt) {
	
		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueTipoC:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<TIPO_C> -> NATURAL = NUMERO_NAT");
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<TIPO_C> -> NATURAL = NUMERO_NAT , IDENTIFICADOR : <TIPO_C>");
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			nt.getErrorManager().debug( "<TIPO_C> -> INTEGER = NUMERO     , IDENTIFICADOR : <TIPO_C>");
			break;
			
		case IAnalizadorSintactico.PROD_CUARTA:
			nt.getErrorManager().debug( "<TIPO_C> -> INTEGER = NUMERO");
			break;
		}	
	}
	
	protected void visit(Var nt) {

		switch (nt.getNroProduccion()) {
		
		case IAnalizadorSintactico.PROD_ENCABEZADO:
			nt.getErrorManager().debug( "Reconocer BloqueVar:");
			break;
		
		case IAnalizadorSintactico.PROD_PRIMERA:
			nt.getErrorManager().debug( "<VAR> -> IDENTIFICADOR , <VAR>");
			break;
			
		case IAnalizadorSintactico.PROD_SEGUNDA:
			nt.getErrorManager().debug( "<VAR> -> IDENTIFICADOR : <TIPO>;");
			break;
			
		case IAnalizadorSintactico.PROD_TERCERA:
			nt.getErrorManager().debug( "<VAR> -> IDENTIFICADOR [ NUMERO ]");
			break;
			
		case IAnalizadorSintactico.PROD_CUARTA:
			nt.getErrorManager().debug( "IDENTIFICADOR [ NUMERO_NAT ]");
			break;
		}	
	}
}
