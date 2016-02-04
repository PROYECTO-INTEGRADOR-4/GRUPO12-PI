/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfTodasMatriculaEstudiantes;
import ec.edu.espoch.sisbi.WSInfoCarrera.Persona;
import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;
import ec.edu.espoch.sisbi.accesodatos.AccesoDatos;
import ec.edu.espoch.sisbi.accesodatos.ConjuntoResultado;
import ec.edu.espoch.sisbi.accesodatos.Parametro;
import ec.edu.espoch.sisbi.entidades.CRol;
import ec.edu.espoch.sisbi.entidades.CUsuario;
import es.edu.espoh.sisbi.cipher.CAes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foqc
 */
public class MUsuario {

    public static List<CUsuario> cargarUsuario(String codRol, String codEscuela) throws Exception {
        List<CUsuario> lstUsuarios = new ArrayList<>();
        try {
            ArrayList<Parametro> lstParamUsuario = new ArrayList<>();
            lstParamUsuario.add(new Parametro(1, codRol));
            lstParamUsuario.add(new Parametro(2, codEscuela));

            String sql = "select * from sisbi.fn_select_usuariosEstudiantes(?,?)";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamUsuario);
            while (rs.next()) {
                CUsuario objUsuario = new CUsuario();
                objUsuario.setCodigo(rs.getLong(0));
                objUsuario.setCedula(rs.getString(1));
                objUsuario.setNombres(rs.getString(2));
                objUsuario.setApellidos(rs.getString(3));

                CRol myRol = new CRol();
                myRol.setCodigo(rs.getString(4));
                objUsuario.setObjRol(myRol);

                Escuela objEscuela = new Escuela();
                objEscuela.setCodigo(rs.getString(5));
                objUsuario.setObjEscuela(objEscuela);

                lstUsuarios.add(objUsuario);
            }
        } catch (Exception e) {
            throw e;
        }
        return lstUsuarios;
    }

    public static boolean insertUsers(List<CUsuario> lstUsuarios) throws Exception {
        boolean respuesta = false;
        int count;
        String values = "";
        try {
            ArrayList<Parametro> lstParamUsuario = new ArrayList<>();

            count = 1;
            int rows = lstUsuarios.size();
            int i = 1;
            for (CUsuario usuario : lstUsuarios) {
                lstParamUsuario.add(new Parametro(count, 1));
                count++;
                lstParamUsuario.add(new Parametro(count, usuario.getCedula()));
                count++;
                lstParamUsuario.add(new Parametro(count, usuario.getNombres()));
                count++;
                lstParamUsuario.add(new Parametro(count, usuario.getApellidos()));
                count++;
                lstParamUsuario.add(new Parametro(count, null));
                count++;
                lstParamUsuario.add(new Parametro(count, usuario.getClave()));
                count++;
                lstParamUsuario.add(new Parametro(count, usuario.getObjRol().getCodigo()));
                count++;
                lstParamUsuario.add(new Parametro(count, usuario.getObjEscuela().getCodigo()));
                count++;

                if (rows > 1 && i < rows) {
                    values = values.concat(",(?,?,?,?,?,?,?,?)");
                }
                i++;
            }
            String sql = "SELECT sisbi.fn_insert_usuario((ARRAY[(?,?,?,?,?,?,?,?)" + values + "])::sisbi.tusuario[])";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamUsuario);
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

    public static boolean actualizarUsuario(CUsuario usuario) throws Exception {
        boolean respuesta = false;
        try {
            ArrayList<Parametro> lstParamUsusario = new ArrayList<>();
            String sql = "SELECT sisbi.fn_update_tusuario(?,?,?,?,?,?,?,?)";
            lstParamUsusario.add(new Parametro(1, usuario.getCodigo()));
            lstParamUsusario.add(new Parametro(2, usuario.getCedula()));
            lstParamUsusario.add(new Parametro(3, usuario.getNombres()));
            lstParamUsusario.add(new Parametro(4, usuario.getApellidos()));
            lstParamUsusario.add(new Parametro(5, usuario.getCodigoQR()));
            lstParamUsusario.add(new Parametro(6, usuario.getClave()));
            lstParamUsusario.add(new Parametro(7, usuario.getObjRol().getCodigo()));
            lstParamUsusario.add(new Parametro(8, usuario.getObjEscuela().getCodigo()));

            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamUsusario);
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

    public static boolean existeQR(String codigoQR) throws Exception {
        boolean respuesta = false;
        try {
            ArrayList<Parametro> lstParamUsusarioQR = new ArrayList<>();
            String sql = "SELECT sisbi.fn_existe_QR(?)";
            lstParamUsusarioQR.add(new Parametro(1, codigoQR));

            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamUsusarioQR);
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

    public static String descifrarQR(String codigoQR) throws Exception {
        String codigoDecifrado = "";
        if (codigoQR.length() > 0) {
            try {
                codigoDecifrado = CAes.decrypt(codigoQR);
            } catch (Exception e) {
                throw e;
            }
        }
        return codigoDecifrado;
    }

    public static String obtenerId(String texto) {
        String nuevo = "";
        int i = 0;
        while (i <= texto.length()) {
            if (texto.charAt(i) == '_') {
                for (int j = 1; j <= 11; j++) {
                    nuevo += texto.charAt(i + j);
                }
                break;
            }
            i++;
        }
        return nuevo;
    }

    public static ArrayOfTodasMatriculaEstudiantes getTodasMatriculaEstudiantes(java.lang.String strCodCarrera, java.lang.String strCodPeriodo) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getTodasMatriculaEstudiantes(strCodCarrera, strCodPeriodo);
    }
}
