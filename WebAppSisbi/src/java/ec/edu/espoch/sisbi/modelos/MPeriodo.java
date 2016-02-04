/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.WSInfoGeneral.Periodo;
import ec.edu.espoch.sisbi.accesodatos.AccesoDatos;
import ec.edu.espoch.sisbi.accesodatos.ConjuntoResultado;
import ec.edu.espoch.sisbi.accesodatos.Parametro;
import ec.edu.espoch.sisbi.utilidades.utilDates;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foqc
 */
public class MPeriodo {

    public static List<Periodo> cargarPeriodo() throws Exception {
        List<Periodo> lstPeriodos = new ArrayList<>();
        try {

            String sql = "select * from sisbi.fn_select_periodos()";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql);
            while (rs.next()) {
                Periodo objPeriodo = new Periodo();
                objPeriodo.setCodigo(rs.getString(0));
                objPeriodo.setDescripcion(rs.getString(1));
                objPeriodo.setFechaInicio(utilDates.toXMLGregorianCalendar(rs.getDate(2)));
                objPeriodo.setFechaFin(utilDates.toXMLGregorianCalendar(rs.getDate(3)));
                lstPeriodos.add(objPeriodo);
            }
        } catch (Exception e) {
            throw e;
        }
        return lstPeriodos;
    }

    public static Periodo cargarPeriodoActual() throws Exception {
        Periodo objPeriodo = new Periodo();
        try {

            String sql = "select * from sisbi.fn_select_periodo_ultimo()";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql);
            while (rs.next()) {
                objPeriodo.setCodigo(rs.getString(0));
                objPeriodo.setDescripcion(rs.getString(1));
                objPeriodo.setFechaInicio(utilDates.toXMLGregorianCalendar(rs.getDate(2)));
                objPeriodo.setFechaFin(utilDates.toXMLGregorianCalendar(rs.getDate(3)));
            }
        } catch (Exception e) {
            throw e;
        }
        return objPeriodo;
    }

    public static boolean insertPeriodo(Periodo objPeriodo) throws Exception {
        boolean respuesta = false;
        try {
            java.sql.Date sqlDateFechaInicio = new java.sql.Date(utilDates.toDate(objPeriodo.getFechaInicio()).getTime());
            java.sql.Date sqlDateFechaFin = new java.sql.Date(utilDates.toDate(objPeriodo.getFechaFin()).getTime());
            ArrayList<Parametro> lstParamPorcentaje = new ArrayList<>();
            String sql = "SELECT sisbi.fn_insert_periodo(?,?,?,?)";
            lstParamPorcentaje.add(new Parametro(1, objPeriodo.getCodigo()));
            lstParamPorcentaje.add(new Parametro(2, objPeriodo.getDescripcion()));
            lstParamPorcentaje.add(new Parametro(3, sqlDateFechaInicio));
            lstParamPorcentaje.add(new Parametro(4, sqlDateFechaFin));

            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamPorcentaje);
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

    public static Periodo getPeriodoActual() {
        ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneral service = new ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneral();
        ec.edu.espoch.sisbi.WSInfoGeneral.InfoGeneralSoap port = service.getInfoGeneralSoap();
        return port.getPeriodoActual();
    }

}
