/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import java.util.List;

/**
 *
 * @author foqc
 */
public class Usuario {
        public static List<Usuario> cargarUsuario(String codigo) throws Exception {
        List<Escuela> lstEscuelas = new ArrayList<>();
        try {
            ArrayList<Parametro> lstParamEscuela = new ArrayList<>();
            lstParamEscuela.add(new Parametro(1, codigo));
            
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
}
