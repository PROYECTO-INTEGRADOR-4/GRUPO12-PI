/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.WSInfoGeneral.ArrayOfEscuela;
import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;
import ec.edu.espoch.sisbi.WSInfoGeneral.Facultad;
import ec.edu.espoch.sisbi.accesodatos.AccesoDatos;
import ec.edu.espoch.sisbi.accesodatos.ConjuntoResultado;
import ec.edu.espoch.sisbi.accesodatos.Parametro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foqc
 */
public class MEscuela {

    public static Facultad obtenerFacultad(String codigoEscuela) throws Exception {
        Facultad objFacultad = new Facultad();
        try {
            ArrayList<Parametro> lstParamEscuela = new ArrayList<>();
            lstParamEscuela.add(new Parametro(1, codigoEscuela));

            String sql = "select * from sisbi.fn_select_facultad_x_escuela(?)";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamEscuela);
            while (rs.next()) {
                objFacultad.setCodigo(rs.getString(0));
                objFacultad.setNombre(rs.getString(1));
            }
        } catch (Exception e) {
            throw e;
        }
        return objFacultad;
    }

    public static List<Escuela> cargarEscuela(String codigoFacultad) throws Exception {
        List<Escuela> lstEscuelas = new ArrayList<>();
        try {
            ArrayList<Parametro> lstParamEscuela = new ArrayList<>();
            lstParamEscuela.add(new Parametro(1, codigoFacultad));

            String sql = "select * from sisbi.fn_select_x_facultad_escuela(?)";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamEscuela);
            while (rs.next()) {
                Escuela objEscuela = new Escuela();
                objEscuela.setCodigo(rs.getString(0));
                objEscuela.setNombre(rs.getString(1));
                objEscuela.setCodFacultad(rs.getString(2));
                lstEscuelas.add(objEscuela);
            }
        } catch (Exception e) {
            throw e;
        }
        return lstEscuelas;
    }

    public static boolean insertEscuelas(List<Escuela> lstEscuelas) throws Exception {
        boolean respuesta = false;
        int count;
        String values = "";
        try {
            ArrayList<Parametro> lstParamEscuela = new ArrayList<>();

            count = 1;
            int rows = lstEscuelas.size();
            int i = 1;
            for (Escuela escuela : lstEscuelas) {
                lstParamEscuela.add(new Parametro(count, escuela.getCodigo()));
                count++;
                lstParamEscuela.add(new Parametro(count, escuela.getNombre()));
                count++;
                lstParamEscuela.add(new Parametro(count, escuela.getCodFacultad()));
                count++;

                if (rows > 1 && i < rows) {
                    values = values.concat(",(?,?,?)");
                }
                i++;
            }
            String sql = "SELECT sisbi.fn_insert_escuela((ARRAY[(?,?,?)" + values + "])::sisbi.tescuela[])";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamEscuela);
            while (rs.next()) {
                if (rs.getBoolean(0)) {
                    respuesta = true;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return respuesta;
    }

    public static ArrayOfEscuela getTodasEscuelasTotales() {
        ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneral service = new ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneral();
        ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneralSoap port = service.getInfoGeneralSoap();
        return port.getTodasEscuelasTotales();
    }
}
