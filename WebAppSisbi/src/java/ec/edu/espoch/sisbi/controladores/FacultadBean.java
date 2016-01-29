/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.controladores;

import ec.edu.espoch.sisbi.WSInfoGeneral.Facultad;
import ec.edu.espoch.sisbi.modelos.MFacultad;
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
public class FacultadBean implements Serializable {

    private List<Facultad> lstFacultades;
    private List<Facultad> lstFacultadesWS;
    private Facultad selObjFacultad;

    public FacultadBean() {
        lstFacultades = new ArrayList<>();
        lstFacultadesWS = new ArrayList<>();
        selObjFacultad = new Facultad();
    }

    @PostConstruct
    public void reinit() {
        cargarFacultades();
    }

    public List<Facultad> getLstFacultades() {
        return lstFacultades;
    }

    public void setLstFacultades(List<Facultad> lstFacultades) {
        this.lstFacultades = lstFacultades;
    }

    public Facultad getSelObjFacultad() {
        return selObjFacultad;
    }

    public void setSelObjFacultad(Facultad selObjFacultad) {
        this.selObjFacultad = selObjFacultad;
    }

    private void cargarFacultades() {
        try {
            this.lstFacultades = MFacultad.cargarFacultad();
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de facultades. " + ex.getMessage());
        }
    }

    public void updateFacultad() {
        try {
            this.lstFacultadesWS = MFacultad.getFacultadesTotales().getFacultad();
            if (MFacultad.insertFacultad(lstFacultadesWS)) {
                Util.addSuccessMessageAndDetail("Aviso", "Se ha actualizado correctamente.");
            } else {
                Util.addSuccessMessageAndDetail("Aviso", "Al parecer la base de datos está actualizada.");
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al obtener lista de facultades. " + ex.getMessage());
        } finally {
            cargarFacultades();
        }
    }

}
