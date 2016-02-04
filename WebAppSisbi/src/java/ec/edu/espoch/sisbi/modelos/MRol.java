/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.accesodatos.AccesoDatos;
import ec.edu.espoch.sisbi.accesodatos.ConjuntoResultado;
import ec.edu.espoch.sisbi.accesodatos.Parametro;
import ec.edu.espoch.sisbi.entidades.CRol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foqc
 */
public class MRol {

    public static List<CRol> cargarRoles() throws Exception {
        List<CRol> lstRols = new ArrayList<>();
        try {
            String sql = "select * from sisbi.fn_select_trol()";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql);
            while (rs.next()) {
                CRol tipoUsuario = new CRol();
                tipoUsuario.setCodigo(rs.getString(0));
                tipoUsuario.setNombre(rs.getString(1));
                tipoUsuario.setDescripcion(rs.getString(2));

                lstRols.add(tipoUsuario);
            }
            rs = null;
        } catch (Exception e) {
            lstRols.clear();
            throw e;
        }
        return lstRols;
    }

    public static CRol cargarRolPorCodigo(int codigo) throws Exception {
        CRol objRol = new CRol();
        try {
            ArrayList<Parametro> lstParamRol = new ArrayList<>();
            String sql = "select * from sisbi.fn_selectxcodigo_trol(?)";
            lstParamRol.add(new Parametro(1, codigo));

            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamRol);
            while (rs.next()) {
                objRol.setCodigo(rs.getString(0));
                objRol.setNombre(rs.getString(1));
                objRol.setDescripcion(rs.getString(2));
            }
            rs = null;
        } catch (Exception e) {
            throw e;
        }
        return objRol;
    }
}
