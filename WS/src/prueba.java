import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "prueba", targetNamespace = "http://default_package/")
public interface prueba {

	@WebMethod(operationName = "obtenerReferencias", action = "urn:ObtenerReferencias")
	@RequestWrapper(className = "jaxws.ObtenerReferencias", localName = "obtenerReferencias", targetNamespace = "http://default_package/")
	@ResponseWrapper(className = "jaxws.ObtenerReferenciasResponse", localName = "obtenerReferenciasResponse", targetNamespace = "http://default_package/")
	public RespuestaODEDTO obtenerReferencias();

	@WebMethod(operationName = "obtenerTiposDocumentos", action = "urn:ObtenerTiposDocumentos")
	@RequestWrapper(className = "jaxws.ObtenerTiposDocumentos", localName = "obtenerTiposDocumentos", targetNamespace = "http://default_package/")
	@ResponseWrapper(className = "jaxws.ObtenerTiposDocumentosResponse", localName = "obtenerTiposDocumentosResponse", targetNamespace = "http://default_package/")
	public RespuestaODEDTO obtenerTiposDocumentos();

	@WebMethod(operationName = "obtenerDocumentos", action = "urn:ObtenerDocumentos")
	@RequestWrapper(className = "jaxws.ObtenerDocumentos", localName = "obtenerDocumentos", targetNamespace = "http://default_package/")
	@ResponseWrapper(className = "jaxws.ObtenerDocumentosResponse", localName = "obtenerDocumentosResponse", targetNamespace = "http://default_package/")
	public RespuestaODEDTO obtenerDocumentos();

	@WebMethod(operationName = "obtenerODE", action = "urn:ObtenerODE")
	@RequestWrapper(className = "jaxws.ObtenerODE", localName = "obtenerODE", targetNamespace = "http://default_package/")
	@ResponseWrapper(className = "jaxws.ObtenerODEResponse", localName = "obtenerODEResponse", targetNamespace = "http://default_package/")
	public RespuestaODEDTO obtenerODE();

	@WebMethod(operationName = "generarODE", action = "urn:GenerarODE")
	@RequestWrapper(className = "jaxws.GenerarODE", localName = "generarODE", targetNamespace = "http://default_package/")
	@ResponseWrapper(className = "jaxws.GenerarODEResponse", localName = "generarODEResponse", targetNamespace = "http://default_package/")
	public RespuestaODEDTO generarODE();

}