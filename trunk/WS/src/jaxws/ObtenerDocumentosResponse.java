
package jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.7.5
 * Mon Sep 09 02:08:48 GMT-03:00 2013
 * Generated source version: 2.7.5
 */

@XmlRootElement(name = "obtenerDocumentosResponse", namespace = "http://default_package/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerDocumentosResponse", namespace = "http://default_package/")

public class ObtenerDocumentosResponse {

    @XmlElement(name = "return", namespace = "http://default_package/")
    private RespuestaODEDTO _return;

    public RespuestaODEDTO getReturn() {
        return this._return;
    }

    public void setReturn(RespuestaODEDTO new_return)  {
        this._return = new_return;
    }

}

