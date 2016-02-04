/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.controladores;

import ec.edu.espoch.sisbi.WSInfoCarrera.Materia;
import ec.edu.espoch.sisbi.WSInfoCarrera.MateriaPensum;
import ec.edu.espoch.sisbi.modelos.MMateria;
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
public class MateriaBean implements Serializable {

    private List<MateriaPensum> lstMaterias;
    private String codEscuela;
    private List<MateriaPensum> lstMateriasWS;
    private MateriaPensum selObjMateria;
    private String codFacultad;

    public MateriaBean() {
        lstMaterias = new ArrayList<>();
        lstMateriasWS = new ArrayList<>();
        codFacultad = "FIE";
        codEscuela = "EIS";
        selObjMateria = new MateriaPensum();
    }

    @PostConstruct
    public void reinit() {
        cargarMaterias();
    }

    public List<MateriaPensum> getLstMaterias() {
        return lstMaterias;
    }

    public void setLstMaterias(List<MateriaPensum> lstMaterias) {
        this.lstMaterias = lstMaterias;
    }

    public String getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(String codEscuela) {
        this.codEscuela = codEscuela;
    }

    public MateriaPensum getSelObjMateria() {
        return selObjMateria;
    }

    public void setSelObjMateria(MateriaPensum selObjMateria) {
        this.selObjMateria = selObjMateria;
    }

    public String getCodFacultad() {
        return codFacultad;
    }

    public void setCodFacultad(String codFacultad) {
        this.codFacultad = codFacultad;
    }

    public void cargarMaterias() {
        try {
            this.lstMaterias = MMateria.cargarMateria(codEscuela);
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de materias. " + ex.getMessage());
        }
    }

    public void updateMaterias() {
        try {
            this.lstMateriasWS = MMateria.getMallaCurricularPensumVigenteSinDescripcion(codEscuela).getMateriaPensum();
            if (MMateria.insertMaterias(lstMateriasWS, codEscuela)) {
                Util.addSuccessMessageAndDetail("Aviso", "Se ha actualizado correctamente.");
            } else {
                Util.addSuccessMessageAndDetail("Aviso", "Al parecer la base de datos está actualizada.");
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al obtener lista de materias. " + ex.getMessage());
        } finally {
            cargarMaterias();
        }
    }

}
