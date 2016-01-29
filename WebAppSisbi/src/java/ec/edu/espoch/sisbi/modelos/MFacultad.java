/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.WSInfoGeneral.ArrayOfFacultad;
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
public class MFacultad {

    public static ArrayOfFacultad getFacultadesTotales() throws Exception {
        try {
            ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneral service = new ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneral();
            ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneralSoap port = service.getInfoGeneralSoap();
            return port.getFacultadesTotales();
        } catch (Exception e) {
            throw e;
        }
    }

    public static List<Facultad> cargarFacultad() throws Exception {
        List<Facultad> lstFacultades = new ArrayList<>();
        try {
            String sql = "select * from sisbi.fn_select_facultad()";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql);
            while (rs.next()) {
                Facultad objFacultad = new Facultad();
                objFacultad.setCodigo(rs.getString(0));
                objFacultad.setNombre(rs.getString(1));
                lstFacultades.add(objFacultad);
            }
        } catch (Exception e) {
            throw e;
        }
        return lstFacultades;
    }

    public static boolean insertFacultad(List<Facultad> lstFacultades) throws Exception {
        boolean respuesta = false;
        int count;
        String values = "";
        try {
            ArrayList<Parametro> lstParamFacultad = new ArrayList<>();

            count = 1;
            int rows = lstFacultades.size();
            int i = 1;
            for (Facultad facultad : lstFacultades) {
                lstParamFacultad.add(new Parametro(count, facultad.getCodigo()));
                count++;
                lstParamFacultad.add(new Parametro(count, facultad.getNombre()));
                count++;

                if (rows > 1 && i < rows) {
                    values = values.concat(",(?,?)");
                }
                i++;
            }
            String sql = "SELECT sisbi.fn_insert_facultad((ARRAY[(?,?)" + values + "])::sisbi.tfacultad[])";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamFacultad);
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
}
