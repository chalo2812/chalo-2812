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
public class AltaDocumentoDispatchAction extends DispatchAction {
	
	//private static final Logger logger = Logger.getLogger(AltaDocumentoDispatchAction.class);

	public ActionForward guardar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Guardar Documento");		
		return mapping.findForward("home");
	}
	
}
