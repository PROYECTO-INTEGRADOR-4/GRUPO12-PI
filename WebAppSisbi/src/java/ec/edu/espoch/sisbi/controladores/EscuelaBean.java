/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.controladores;

import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;
import ec.edu.espoch.sisbi.modelos.MEscuela;
import ec.edu.espoch.sisbi.utilidades.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author foqc
 */
@ManagedBean
@ViewScoped
public class EscuelaBean implements Serializable {

    private List<Escuela> lstEscuelas;
    private String codFacultad;
    private List<Escuela> lstEscuelasWS;
    private Escuela selObjEscuela;

    public EscuelaBean() {
        lstEscuelas = new ArrayList<>();
        codFacultad = "FIE";
        lstEscuelasWS = new ArrayList<>();
        selObjEscuela = new Escuela();
    }

    @PostConstruct
    public void reinit() {
        cargarEscuelas();
    }

    public List<Escuela> getLstEscuelas() {
        return lstEscuelas;
    }

    public void setLstEscuelas(List<Escuela> lstEscuelas) {
        this.lstEscuelas = lstEscuelas;
    }

    public Escuela getSelObjEscuela() {
        return selObjEscuela;
    }

    public void setSelObjEscuela(Escuela selObjEscuela) {
        this.selObjEscuela = selObjEscuela;
    }

    public String getCodFacultad() {
        return codFacultad;
    }

    public void setCodFacultad(String codFacultad) {
        this.codFacultad = codFacultad;
    }

    public void cargarEscuelas() {
        try {
            this.lstEscuelas = MEscuela.cargarEscuela(codFacultad);
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de ecuelas. " + ex.getMessage());
        }
    }

    public void updateEscuela() {
        try {
            this.lstEscuelasWS = MEscuela.getTodasEscuelasTotales().getEscuela();
            if (MEscuela.insertEscuelas(lstEscuelasWS)) {
                Util.addSuccessMessageAndDetail("Aviso", "Se ha actualizado correctamente.");
            } else {
                Util.addSuccessMessageAndDetail("Aviso", "Al parecer la base de datos está actualizada.");
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al obtener lista de esculeas. " + ex.getMessage());
        } finally {
            cargarEscuelas();
        }
    }
}
