/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.controladores;

import ec.edu.espoch.sisbi.WSInfoCarrera.DictadoMateria;
import ec.edu.espoch.sisbi.WSInfoCarrera.MateriaPensum;
import ec.edu.espoch.sisbi.WSInfoCarrera.Persona;
import ec.edu.espoch.sisbi.WSInfoCarrera.TodasMatriculaEstudiantes;
import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;
import ec.edu.espoch.sisbi.WSInfoGeneral.Periodo;
import ec.edu.espoch.sisbi.entidades.CRol;
import ec.edu.espoch.sisbi.entidades.CUsuario;
import ec.edu.espoch.sisbi.modelos.MMateria;
import ec.edu.espoch.sisbi.modelos.MPeriodo;
import ec.edu.espoch.sisbi.modelos.MUsuario;
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
public class UsuarioBean implements Serializable {

    private List<CUsuario> lstUsuarios;
    private List<TodasMatriculaEstudiantes> lstTodasMatriculasEstudiantesWS;
    private List<CUsuario> usuarioWS;
    private CUsuario selObjUsuario;
    private String rolCodigo;
    private String codEscuela;
    private String codFacultad;
    private Periodo objPeriodo;
    private String buscar;

    public UsuarioBean() {
        this.lstUsuarios = new ArrayList<>();
        lstTodasMatriculasEstudiantesWS = new ArrayList<>();
        usuarioWS = new ArrayList<>();
        selObjUsuario = new CUsuario();
        rolCodigo = "EST";
        codEscuela = "EIS";
        codFacultad = "FIE";
        buscar = "";
        objPeriodo = new Periodo();
    }

    @PostConstruct
    public void reinit() {
        cargarUsuarios();
    }

    public List<CUsuario> getLstUsuarios() {
        return lstUsuarios;
    }

    public void setLstUsuarios(List<CUsuario> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }

    public CUsuario getSelObjUsuario() {
        return selObjUsuario;
    }

    public void setSelObjUsuario(CUsuario selObjUsuario) {
        this.selObjUsuario = selObjUsuario;
    }

    public String getRolCodigo() {
        return rolCodigo;
    }

    public void setRolCodigo(String rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    public String getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(String codEscuela) {
        this.codEscuela = codEscuela;
    }

    public Periodo getObjPeriodo() {
        return objPeriodo;
    }

    public void setObjPeriodo(Periodo objPeriodo) {
        this.objPeriodo = objPeriodo;
    }

    public String getRolEscuela() {
        return codEscuela;
    }

    public void setRolEscuela(String codEscuela) {
        this.codEscuela = codEscuela;
    }

    public String getCodFacultad() {
        return codFacultad;
    }

    public void setCodFacultad(String codFacultad) {
        this.codFacultad = codFacultad;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public void cargarUsuarios() {
        try {
            this.objPeriodo = MPeriodo.cargarPeriodoActual();
            this.lstUsuarios = MUsuario.cargarUsuario(rolCodigo, codEscuela);
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de usuarios. " + ex.getMessage());
        }
    }

    public void updateUsuarios() {
        try {
            if (rolCodigo.equals("EST")) {
                this.lstTodasMatriculasEstudiantesWS = MUsuario.getTodasMatriculaEstudiantes(codEscuela, objPeriodo.getCodigo()).getTodasMatriculaEstudiantes();

                for (TodasMatriculaEstudiantes matricula : lstTodasMatriculasEstudiantesWS) {
                    CUsuario objUsuario = new CUsuario();
                    objUsuario.setCedula(matricula.getCedula());
                    objUsuario.setNombres(matricula.getNombres());
                    objUsuario.setApellidos(matricula.getApellidos());
                    objUsuario.setClave(matricula.getCedula());
                    CRol rol = new CRol();
                    rol.setCodigo(rolCodigo);
                    objUsuario.setObjRol(rol);
                    Escuela escuela = new Escuela();
                    escuela.setCodigo(codEscuela);
                    objUsuario.setObjEscuela(escuela);
                    usuarioWS.add(objUsuario);
                }
            } else {
                List<MateriaPensum> materias = MMateria.cargarMateria(codEscuela);
                int c = 0, c1 = 0, k = 0, tam = materias.size() / 8;
                System.out.print("tam: " + tam);
                for (MateriaPensum materia : materias) {
                    if (k > 16) {
                        List<DictadoMateria> dictadoMateria = MMateria.getDictadosMateria(codEscuela, materia.getCodMateria()).getDictadoMateria();

                        for (DictadoMateria profesor : dictadoMateria) {
                            CUsuario objUsuario = new CUsuario();
                            objUsuario.setCedula(profesor.getDocente().getCedula());
                            objUsuario.setNombres(profesor.getDocente().getNombres());
                            objUsuario.setApellidos(profesor.getDocente().getApellidos());
                            objUsuario.setClave(profesor.getDocente().getCedula());
                            CRol rol = new CRol();
                            rol.setCodigo(rolCodigo);
                            objUsuario.setObjRol(rol);
                            Escuela escuela = new Escuela();
                            escuela.setCodigo(codEscuela);
                            objUsuario.setObjEscuela(escuela);
                            usuarioWS.add(objUsuario);
                        }

                        if (c == tam) {
                            MUsuario.insertUsers(usuarioWS);
                            usuarioWS.clear();
                            Util.addSuccessMessageAndDetail("Aviso", "Se esta procensando...");
                            Thread.sleep(60000);
                            c1 += c;
                            c = 0;
                        }
                        c++;
                        System.out.print("cont: " + c + " acum: " + c1);
                    }
                    k++;
                }

            }
            if (usuarioWS.size() > 0) {
                if (MUsuario.insertUsers(usuarioWS)) {
                    Util.addSuccessMessageAndDetail("Aviso", "Se ha actualizado correctamente.");
                } else {
                    Util.addSuccessMessageAndDetail("Aviso", "Al parecer la base de datos está actualizada.");
                }
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al obtener lista de usuarios. " + ex.getMessage());
        } finally {
            cargarUsuarios();
        }
    }

}
