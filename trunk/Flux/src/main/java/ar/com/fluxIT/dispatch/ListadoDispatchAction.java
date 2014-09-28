package ar.com.fluxIT.dispatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * 
 * @author gsola
 * 
 */
public class ListadoDispatchAction extends DispatchAction {
	
	public ActionForward home(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Cargar al iniciar");
		return mapping.findForward("volver");
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Volver al Home");
		return mapping.findForward("home");
	}
	
}
