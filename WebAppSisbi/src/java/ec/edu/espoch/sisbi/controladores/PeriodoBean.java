/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.controladores;

import ec.edu.espoch.sisbi.WSInfoGeneral.Periodo;
import ec.edu.espoch.sisbi.modelos.MPeriodo;
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
public class PeriodoBean implements Serializable {

    private List<Periodo> lstPeriodos;
    private Periodo objPeriodo;
    private Periodo objPeriodoActual;
    private Periodo selObjPeriodo;

    public PeriodoBean() {
        lstPeriodos = new ArrayList<>();
        objPeriodo = new Periodo();
        objPeriodoActual = new Periodo();
        selObjPeriodo = new Periodo();
    }

    @PostConstruct
    public void reinit() {
        cargarPeriodos();
    }

    public List<Periodo> getLstPeriodos() {
        return lstPeriodos;
    }

    public void setLstPeriodos(List<Periodo> lstPeriodos) {
        this.lstPeriodos = lstPeriodos;
    }

    public Periodo getObjPeriodo() {
        return objPeriodo;
    }

    public void setObjPeriodo(Periodo objPeriodo) {
        this.objPeriodo = objPeriodo;
    }

    public Periodo getSelObjPeriodo() {
        return selObjPeriodo;
    }

    public void setSelObjPeriodo(Periodo selObjPeriodo) {
        this.selObjPeriodo = selObjPeriodo;
    }

    public Periodo getObjPeriodoActual() {
        return objPeriodoActual;
    }

    public void setObjPeriodoActual(Periodo objPeriodoActual) {
        this.objPeriodoActual = objPeriodoActual;
    }

    public void cargarPeriodos() {
        try {
            this.lstPeriodos = MPeriodo.cargarPeriodo();
            obtenerPeriodoActual();
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de períodos. " + ex.getMessage());
        }
    }

    public void obtenerPeriodoActual() {
        try {
            this.objPeriodoActual = MPeriodo.cargarPeriodoActual();
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de períodos. " + ex.getMessage());
        }
    }

    public void updatePeriodos() {
        try {
            this.objPeriodo = MPeriodo.getPeriodoActual();
            if (MPeriodo.insertPeriodo(objPeriodo)) {
                Util.addSuccessMessageAndDetail("Aviso", "Se ha actualizado correctamente.");
            } else {
                Util.addSuccessMessageAndDetail("Aviso", "Al parecer la base de datos está actualizada.");
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al obtener lista de períodos. " + ex.getMessage());
        } finally {
            cargarPeriodos();
        }
    }
}
