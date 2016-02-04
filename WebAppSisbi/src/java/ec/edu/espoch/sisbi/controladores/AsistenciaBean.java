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
import ec.edu.espoch.sisbi.WSInfoCarrera.Materia;
import ec.edu.espoch.sisbi.WSInfoGeneral.Periodo;
import ec.edu.espoch.sisbi.entidades.CAsistencia;
import ec.edu.espoch.sisbi.entidades.CUsuario;
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
    private String filename;
    private String materia;

    public AsistenciaBean() {
        lstMateriasDocente = new ArrayList<>();
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
                
                Util.addSuccessMessageAndDetail("Aviso", "mensaje entendido");
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

}
