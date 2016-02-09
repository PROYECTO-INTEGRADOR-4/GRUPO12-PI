/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.controladores;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import ec.edu.espoch.sisbi.WSInfoCarrera.DictadoMateria;
import ec.edu.espoch.sisbi.WSInfoCarrera.Estudiante;
import ec.edu.espoch.sisbi.WSInfoCarrera.Materia;
import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;
import ec.edu.espoch.sisbi.WSInfoGeneral.Periodo;
import ec.edu.espoch.sisbi.entidades.CAsistencia;
import ec.edu.espoch.sisbi.entidades.CRol;
import ec.edu.espoch.sisbi.entidades.CUsuario;
import ec.edu.espoch.sisbi.modelos.MAsistencia;
import ec.edu.espoch.sisbi.modelos.MEscuela;
import ec.edu.espoch.sisbi.modelos.MMateria;
import ec.edu.espoch.sisbi.modelos.MUsuario;
import ec.edu.espoch.sisbi.utilidades.Util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import org.primefaces.context.DefaultRequestContext;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author foqc
 */
@ManagedBean
@ViewScoped
public class AsistenciaBean implements Serializable {

    private List<Materia> lstMateriasDocente;
    @ManagedProperty(value = "#{loginBean.objUsuario}")
    private CUsuario user;
    @ManagedProperty(value = "#{periodoBean.objPeriodoActual}")
    private Periodo periodo;
    private String codigoFacultad;
    private String codigoEscuela;
    private CAsistencia objAsistencia;
    private List<CAsistencia> lstAsistencias;
    private String filename;
    private String materia;
    private List<Estudiante> lstEstudiante;
    //[codigoParalelo,materiaCodigo]solo estaran disponible cuando se llame a la funcion obtenerUsuariosMateria()
    private String codigoParalelo = "";
    private String materiaCodigo = "";

    public AsistenciaBean() {
        lstMateriasDocente = new ArrayList<>();
        lstEstudiante = new ArrayList<>();
        lstAsistencias = new ArrayList<>();
        objAsistencia = new CAsistencia();
        materia = "";
    }

    @PostConstruct
    public void reinit() {
        codigoEscuela = user.getObjEscuela().getCodigo();
        cargarMateriaDocente();
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getCodigoEscuela() {
        return codigoEscuela;
    }

    public void setCodigoEscuela(String codigoEscuela) {
        this.codigoEscuela = codigoEscuela;
    }

    public List<Materia> getLstMateriasDocente() {
        return lstMateriasDocente;
    }

    public void setLstMateriasDocente(List<Materia> lstMateriasDocente) {
        this.lstMateriasDocente = lstMateriasDocente;
    }

    public String getCodigoFacultad() {
        return codigoFacultad;
    }

    public void setCodigoFacultad(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
    }

    public CAsistencia getObjAsistencia() {
        return objAsistencia;
    }

    public void setObjAsistencia(CAsistencia objAsistencia) {
        this.objAsistencia = objAsistencia;
    }

    public CUsuario getUser() {
        return user;
    }

    public void setUser(CUsuario user) {
        this.user = user;
    }

    private String getRandomImageName() {
        int i = (int) (Math.random() * 10000000);

        return String.valueOf(i);
    }

    public String getFilename() {
        return filename;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public List<Estudiante> getLstEstudiante() {
        return lstEstudiante;
    }

    public void setLstEstudiante(List<Estudiante> lstEstudiante) {
        this.lstEstudiante = lstEstudiante;
    }

    public List<CAsistencia> getLstAsistencias() {
        return lstAsistencias;
    }

    public void setLstAsistencias(List<CAsistencia> lstAsistencias) {
        this.lstAsistencias = lstAsistencias;
    }

    public void cargarMateriaDocente() {
        try {
            materia = "";
            codigoFacultad = MEscuela.obtenerFacultad(codigoEscuela).getCodigo();
            this.lstMateriasDocente = MMateria.getMateriasDocente(codigoEscuela, user.getCedula(), periodo.getCodigo()).getMateria();
            if (lstMateriasDocente.isEmpty()) {
                Util.addSuccessMessageAndDetail("Aviso", "No tiene materias en la escuela: " + codigoEscuela);
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de materias del docente. " + ex.getMessage());
        }
    }

    public void cargarMateriaDocenteBoton(String codigoEscuela) {
        try {
            codigoFacultad = MEscuela.obtenerFacultad(codigoEscuela).getCodigo();
            this.lstMateriasDocente = MMateria.getMateriasDocente(codigoEscuela, user.getCedula(), periodo.getCodigo()).getMateria();
            if (lstMateriasDocente.isEmpty()) {
                Util.addSuccessMessageAndDetail("Aviso", "No tiene materias en la escuela: " + codigoEscuela);
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Error al cargar lista de materias del docente. " + ex.getMessage());
        }
    }

    public void actualizarMateriasView() throws Exception {
        this.lstMateriasDocente.clear();
        materia = "";
        codigoEscuela = MEscuela.cargarEscuela(codigoFacultad).get(0).getCodigo();
        cargarMateriaDocente();
    }

    public void oncapture(CaptureEvent captureEvent) {
        filename = getRandomImageName();
        byte[] data = captureEvent.getData();

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources"
                + File.separator + "images" + File.separator + filename + ".jpeg";

        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (IOException e) {
            throw new FacesException("Error in writing captured image.", e);
        }
        try {
            String qr = leerCodigoQR(newFileName);
            if (MUsuario.existeQR(qr)) {
                String id = MUsuario.obtenerId(MUsuario.descifrarQR(qr));
                if (!id.isEmpty()) {
                    List<String> paralelo = MAsistencia.verificarHorarioParalelo(materia, codigoEscuela, periodo.getCodigo(), id, user.getCedula());
                    if (!paralelo.isEmpty()) {
                        objAsistencia.setEstado(true);
                        objAsistencia.setFechaAsistencia(objAsistencia.fechaActual());
                        objAsistencia.setAsistenciaHora(objAsistencia.horaActual());
                        objAsistencia.setParalelo(paralelo.get(0));
                        objAsistencia.setHoraCodigo(paralelo.get(1));
                        objAsistencia.getObjUsuario().setCedula(id);
                        objAsistencia.setObjPeriodo(periodo);
                        objAsistencia.getObjMateria().setNombre(materia);
                        if (MAsistencia.insertarAsistencia(objAsistencia)) {
                            Util.addSuccessMessageAndDetail("Aviso", "Asistencia registrada correctamente, " + id);
                        } else {
                            Util.addErrorMessageAndDetail("Error", "No se ha realizado el registro de asistencia!");
                        }
                    }
                } else {
                    Util.addErrorMessageAndDetail("Error", "Usuario desconocido!");
                }
            } else {
                Util.addErrorMessageAndDetail("Error", "Código QR no válido!");
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Problemas al registrar asistencia, " + ex.getMessage());
        }
    }

    public String leerCodigoQR(String fileName) {
        String codigoQR = "";
        Reader lector = new MultiFormatReader();
        File ubicacionImagen = new File(fileName);
        BufferedImage imagen;
        if (ubicacionImagen.exists()) {
            try {
                imagen = ImageIO.read(ubicacionImagen);
                LuminanceSource fuente = new BufferedImageLuminanceSource(imagen);
                BinaryBitmap mapaBits = new BinaryBitmap(new HybridBinarizer(fuente));
                Result resultado = lector.decode(mapaBits);
                codigoQR = resultado.getText();
                // Util.addSuccessMessageAndDetail("Aviso", "El valor del Qr es: " + resultado.getText());
            } catch (IOException ex) {
                Util.addErrorMessageAndDetail("Error", "Error al obtener imagen, " + ex.getMessage());
            } catch (NotFoundException ex) {
                Util.addErrorMessageAndDetail("Error", "Imagen no se puede leer, intente con otra imagen, " + ex.getMessage());
            } catch (ChecksumException ex) {
                Util.addErrorMessageAndDetail("Error", "Error en check imagen, " + ex.getMessage());
            } catch (FormatException ex) {
                Util.addErrorMessageAndDetail("Error", "Fromato imagen no acpetado, " + ex.getMessage());
            }
        } else {
            Util.addErrorMessageAndDetail("Error", "Archivo no encontrado. ");
        }
        return codigoQR;
    }

    public void materiaEscojida() {
        Util.addSuccessMessageAndDetail("Aviso", "la materia escojida es: " + materia);
    }

    public void obtenerUsuariosMateria() {
        try {
            String codigoNivel = "";
            materiaCodigo = MMateria.cargarMateriaByName(materia).getCodigo();
            List<DictadoMateria> materias = MAsistencia.getDictadosMateria(codigoEscuela, materiaCodigo).getDictadoMateria();
            for (DictadoMateria materia1 : materias) {
                if (materia1.getDocente().getCedula().equals(user.getCedula())) {
                    codigoNivel = materia1.getCodNivel();
                    codigoParalelo = materia1.getParalelo();
                    break;
                }
            }
            lstEstudiante = MAsistencia.getAlumnosMateria(codigoEscuela, codigoNivel, codigoParalelo, periodo.getCodigo(), materiaCodigo).getEstudiante();
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Problemas al obtener usuarios de la materia, " + ex.getMessage());
        }
    }

    public void obtenerPocentajeAsistencia() {
        try {
            String codigoNivel = "";
            materiaCodigo = MMateria.cargarMateriaByName(materia).getCodigo();
            List<DictadoMateria> materias = MAsistencia.getDictadosMateria(codigoEscuela, materiaCodigo).getDictadoMateria();
            for (DictadoMateria materia1 : materias) {
                if (materia1.getDocente().getCedula().equals(user.getCedula())) {
                    codigoNivel = materia1.getCodNivel();
                    codigoParalelo = materia1.getParalelo();
                    break;
                }
            }

            lstEstudiante = MAsistencia.getAlumnosMateria(codigoEscuela, codigoNivel, codigoParalelo, periodo.getCodigo(), materiaCodigo).getEstudiante();
            if (!lstEstudiante.isEmpty()) {
                List<CUsuario> lstUsuario = new ArrayList<>();
                for (Estudiante estudiante : lstEstudiante) {
                    CUsuario objUsuario = new CUsuario();
                    objUsuario.setCodigo((long) 0);
                    objUsuario.setCedula(estudiante.getCedula());
                    objUsuario.setNombres(estudiante.getNombres());
                    objUsuario.setApellidos(estudiante.getApellidos());
                    objUsuario.setCodigoQR("qr");
                    objUsuario.setClave("clave");
                    CRol objRol = new CRol();
                    objRol.setCodigo("rol");
                    objUsuario.setObjRol(objRol);
                    Escuela objEscuela = new Escuela();
                    objEscuela.setCodigo("escuela");
                    objUsuario.setObjEscuela(objEscuela);
                    lstUsuario.add(objUsuario);
                }
                lstAsistencias = MAsistencia.obtenerAsistencias(lstUsuario, codigoParalelo, periodo.getCodigo(), materiaCodigo);
            } else {
                Util.addErrorMessageAndDetail("Error", "No se pudo obtener lista de estudiantes del servicio web.");
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Problemas al obtener registro de asistencias," + ex.getMessage());
        }
    }

    public void registrarTodasAsistencias() {
        try {
            List<CAsistencia> lstAsistencia = new ArrayList<>();
            String horaCodigo = MAsistencia.verificarHorarioDocente(materia, codigoEscuela, periodo.getCodigo(), user.getCedula());
            if (!horaCodigo.isEmpty() && !lstEstudiante.isEmpty()) {
                for (Estudiante estudiante : lstEstudiante) {
                    CAsistencia asistencia = new CAsistencia();
                    asistencia.setEstado(false);
                    asistencia.setFechaAsistencia(asistencia.fechaActual());
                    asistencia.setAsistenciaHora(asistencia.horaActual());
                    asistencia.setParalelo(codigoParalelo);
                    asistencia.setHoraCodigo(horaCodigo);
                    asistencia.getObjUsuario().setCedula(estudiante.getCedula());
                    asistencia.setObjPeriodo(periodo);
                    asistencia.getObjMateria().setNombre(materia);
                    lstAsistencia.add(asistencia);
                }
                if (MAsistencia.insertAsistencias(lstAsistencia, materiaCodigo)) {
                    DefaultRequestContext.getCurrentInstance().execute("PF('asistenciasViewDialog').hide()");
                    lstEstudiante.clear();
                    Util.addSuccessMessageAndDetail("Aviso", "Se ha registrado las asistencias de sus alumnos correctamente.");
                } else {
                    Util.addErrorMessageAndDetail("Error", "Al parecer ya se ha realizado el registro de las asistencias de sus alumnos en esta materia.");
                }
            } else {
                Util.addErrorMessageAndDetail("Error", "Problemas al obtener el horario del docente.");
            }
        } catch (Exception ex) {
            Util.addErrorMessageAndDetail("Error", "Problemas al registrar asistencias, " + ex.getMessage());
        }
    }

}
