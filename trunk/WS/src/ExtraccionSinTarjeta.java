import javax.jws.WebService;

@WebService(targetNamespace = "http://default_package/", endpointInterface = "prueba", portName = "ExtraccionSinTarjetaPort", serviceName = "ExtraccionSinTarjetaService")
public class ExtraccionSinTarjeta implements prueba {

	public ExtraccionSinTarjetaRequest request;
	public ExtraccionSinTarjetaResponse response;

	public RespuestaODEDTO obtenerReferencias() {
		return null;

	}

	public RespuestaODEDTO obtenerTiposDocumentos() {
		return null;
	}

	public RespuestaODEDTO obtenerDocumentos() {
		return null;
	}

	public RespuestaODEDTO obtenerODE() {
		return null;
	}

	public RespuestaODEDTO generarODE() {
		return null;
	}
}
