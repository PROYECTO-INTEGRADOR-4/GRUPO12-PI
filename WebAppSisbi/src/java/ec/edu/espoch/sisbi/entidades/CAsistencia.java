/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.entidades;

import ec.edu.espoch.sisbi.WSInfoCarrera.Materia;
import ec.edu.espoch.sisbi.WSInfoGeneral.Periodo;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author foqc
 */
public class CAsistencia {

    private Boolean estado;
    private Date fechaAsistencia;
    private Time asistenciaHora;
    private String paralelo;
    private String horaCodigo;
    private CUsuario objUsuario;
    private Materia objMateria;
    private Periodo objPeriodo;
    private Short asistidos;
    private Short noAsistidos;
    private Float porcentajeAsistencia;

    public CAsistencia() {
        objUsuario = new CUsuario();
        objMateria = new Materia();
        objPeriodo = new Periodo();
        asistidos = 0;
        noAsistidos = 0;
        porcentajeAsistencia = 0.0f;
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

    public Time horaActual() {
        return this.asistenciaHora = new Time(fechaActual().getTime());
    }

    public Time getAsistenciaHora() {
        return asistenciaHora;
    }

    public void setAsistenciaHora(Time asistenciaHora) {
        this.asistenciaHora = asistenciaHora;
    }

    public String getHoraCodigo() {
        return horaCodigo;
    }

    public void setHoraCodigo(String horaCodigo) {
        this.horaCodigo = horaCodigo;
    }

    public Short getAsistidos() {
        return asistidos;
    }

    public void setAsistidos(Short asistidos) {
        this.asistidos = asistidos;
    }

    public Short getNoAsistidos() {
        return noAsistidos;
    }

    public void setNoAsistidos(Short noAsistidos) {
        this.noAsistidos = noAsistidos;
    }

    public Float getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(Float porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

}
