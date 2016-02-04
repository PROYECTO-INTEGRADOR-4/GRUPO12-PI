/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.entidades;

import ec.edu.espoch.sisbi.WSInfoCarrera.Persona;
import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;

/**
 *
 * @author foqc
 */
public class CUsuario extends Persona {

    private Long codigo;
    private String codigoQR;
    private String clave;
    private CRol objRol;
    private Escuela objEscuela;

    public CUsuario() {
    }
    
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public CRol getObjRol() {
        return objRol;
    }

    public void setObjRol(CRol objRol) {
        this.objRol = objRol;
    }

    public Escuela getObjEscuela() {
        return objEscuela;
    }

    public void setObjEscuela(Escuela objEscuela) {
        this.objEscuela = objEscuela;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }
}
