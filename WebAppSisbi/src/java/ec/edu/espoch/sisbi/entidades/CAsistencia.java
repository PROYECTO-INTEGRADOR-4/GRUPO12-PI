/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.entidades;

import ec.edu.espoch.sisbi.WSInfoCarrera.Materia;
import ec.edu.espoch.sisbi.WSInfoGeneral.Periodo;
import java.util.Date;

/**
 *
 * @author foqc
 */
public class CAsistencia {

    private Boolean estado;
    private Date fechaAsistencia;
    private String paralelo;
    private CUsuario objUsuario;
    private Materia objMateria;
    private Periodo objPeriodo;

    public CAsistencia() {
        objUsuario = new CUsuario();
        objMateria = new Materia();
        objPeriodo = new Periodo();
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Date getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public CUsuario getObjUsuario() {
        return objUsuario;
    }

    public void setObjUsuario(CUsuario objUsuario) {
        this.objUsuario = objUsuario;
    }

    public Materia getObjMateria() {
        return objMateria;
    }

    public void setObjMateria(Materia objMateria) {
        this.objMateria = objMateria;
    }

    public Periodo getObjPeriodo() {
        return objPeriodo;
    }

    public void setObjPeriodo(Periodo objPeriodo) {
        this.objPeriodo = objPeriodo;
    }

    public Date fechaActual() {
        return this.fechaAsistencia = new Date();
    }
}
