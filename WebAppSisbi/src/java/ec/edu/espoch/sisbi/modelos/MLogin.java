/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;
import ec.edu.espoch.sisbi.accesodatos.AccesoDatos;
import ec.edu.espoch.sisbi.accesodatos.ConjuntoResultado;
import ec.edu.espoch.sisbi.accesodatos.Parametro;
import ec.edu.espoch.sisbi.entidades.CRol;
import ec.edu.espoch.sisbi.entidades.CUsuario;
import java.util.ArrayList;

/**
 *
 * @author Tupac
 */
public class MLogin {

    public static Boolean loginCorrecto(CUsuario objUsuario) throws Exception {
        Boolean correcto = false;
        try {
            String sql = "SELECT * FROM sisbi.fn_logincorrecto_tusuario(?,?)";
            ArrayList<Parametro> lstParam = new ArrayList<>();
            lstParam.add(new Parametro(1, objUsuario.getCedula()));
            lstParam.add(new Parametro(2, objUsuario.getClave()));
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParam);
            while (rs.next()) {
                correcto = rs.getBoolean(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return correcto;
    }

    public static CUsuario datosUsuario(CUsuario objUsuario) throws Exception {

        try {
            String sql = "SELECT * FROM sisbi.fn_select_user_tusuario(?)";
            ArrayList<Parametro> lstParam = new ArrayList<>();
            lstParam.add(new Parametro(1, objUsuario.getCedula()));
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParam);
            while (rs.next()) {
                objUsuario.setCodigo(rs.getLong(0));
                objUsuario.setCedula(rs.getString(1));
                objUsuario.setNombres(rs.getString(2));
                objUsuario.setApellidos(rs.getString(3));
                if (rs.getObject(4) == null) {
                    objUsuario.setCodigoQR("");
                } else {
                    objUsuario.setCodigoQR(rs.getString(4));
                }
                CRol objRol = new CRol();
                objRol.setCodigo(rs.getString(5));
                objUsuario.setObjRol(objRol);
                Escuela objEscuela = new Escuela();
                objEscuela.setCodigo(rs.getString(6));
                objUsuario.setObjEscuela(objEscuela);
            }
        } catch (Exception e) {
            throw e;
        }
        return objUsuario;
    }

}
