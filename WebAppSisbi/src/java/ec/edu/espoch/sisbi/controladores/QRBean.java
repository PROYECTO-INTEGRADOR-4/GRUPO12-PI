/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.controladores;

import ec.edu.espoch.sisbi.QR.QR;
import ec.edu.espoch.sisbi.utilidades.Util;
import ec.edu.espoch.sisbi.utilidades.UtilRandom;
import es.edu.espoh.sisbi.cipher.CAes;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.ByteArrayContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author foqc
 */
@ManagedBean
@SessionScoped
public class QRBean implements Serializable {

    private StreamedContent imageQR;
    private String codigoQR;
    private Boolean generoNuevo;

    /**
     * Creates a new instance of QRBean
     */
    public QRBean() {
        imageQR = new ByteArrayContent();
    }

    @PostConstruct
    public void init() {
        generoNuevo = false;
        codigoQR = "";
    }

    public StreamedContent getImageQR() {
        return imageQR;
    }

    public void setImageQR(StreamedContent imageQR) {
        this.imageQR = imageQR;
    }

    public Boolean getGeneroNuevo() {
        return generoNuevo;
    }

    public void setGeneroNuevo(Boolean generoNuevo) {
        this.generoNuevo = generoNuevo;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public void generarNuevaImagenQR(String cedula) {
        if (cedula.length() > 0) {
            try {
                codigoQR = CAes.encrypt(UtilRandom.generateKey() + "/_" + cedula + "_/" + UtilRandom.generateKey());
                QR qr = new QR();
                imageQR = qr.generarQR(codigoQR);
                generoNuevo = true;
            } catch (Exception ex) {
                Util.addErrorMessageAndDetail("Error", "Error al intentar generar imagen QR. " + ex.getMessage());
            }
        }
    }

    public StreamedContent cargarImagenQR(String codigoQR) {
        StreamedContent img = null;
        if (codigoQR.length() > 0) {
            try {
                QR qr = new QR();
                img = qr.generarQR(codigoQR);
            } catch (Exception ex) {
                Util.addErrorMessageAndDetail("Error", "Error al intentar cargar imagen QR. " + ex.getMessage());
            }
        }
        return img;
    }

    public void inicializar() {
        codigoQR = "";
        generoNuevo=false;
    }
}
