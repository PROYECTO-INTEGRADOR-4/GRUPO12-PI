/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfDictadoMateria;
import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfMateria;
import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfMateriaPensum;
import ec.edu.espoch.sisbi.WSInfoCarrera.Materia;
import ec.edu.espoch.sisbi.WSInfoCarrera.MateriaPensum;
import ec.edu.espoch.sisbi.accesodatos.AccesoDatos;
import ec.edu.espoch.sisbi.accesodatos.ConjuntoResultado;
import ec.edu.espoch.sisbi.accesodatos.Parametro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foqc
 */
public class MMateria {

    public static List<MateriaPensum> cargarMateria(String codigoEscuela) throws Exception {
        List<MateriaPensum> lstMaterias = new ArrayList<>();
        try {
            ArrayList<Parametro> lstParamMateria = new ArrayList<>();
            lstParamMateria.add(new Parametro(1, codigoEscuela));

            String sql = "select * from sisbi.fn_select_x_escuela_materia(?)";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamMateria);
            while (rs.next()) {
                MateriaPensum objMateria = new MateriaPensum();
                objMateria.setCodMateria(rs.getString(0));
                objMateria.setMateria(rs.getString(1));
                objMateria.setNivel(rs.getString(2));
                objMateria.setHorasPracticas(rs.getInt(3));
                lstMaterias.add(objMateria);
            }
        } catch (Exception e) {
            throw e;
        }
        return lstMaterias;
    }

    public static Materia cargarMateriaByName(String nombreMateria) throws Exception {
        Materia objMateria = new Materia();
        try {
            ArrayList<Parametro> lstParamMateria = new ArrayList<>();
            lstParamMateria.add(new Parametro(1, nombreMateria));

            String sql = "select * from sisbi.fn_select_materia_by_Name(?)";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamMateria);
            while (rs.next()) {
                objMateria.setCodigo(rs.getString(0));
                objMateria.setNombre(rs.getString(1));
            }
        } catch (Exception e) {
            throw e;
        }
        return objMateria;
    }

    public static boolean insertMaterias(List<MateriaPensum> lstMaterias, String escuelaCodigo) throws Exception {
        boolean respuesta = false;
        int count;
        String values = "";
        try {
            ArrayList<Parametro> lstParamMateria = new ArrayList<>();

            count = 1;
            int rows = lstMaterias.size();
            int i = 1;
            for (MateriaPensum materia : lstMaterias) {
                lstParamMateria.add(new Parametro(count, materia.getCodMateria()));
                count++;
                lstParamMateria.add(new Parametro(count, materia.getMateria()));
                count++;
                lstParamMateria.add(new Parametro(count, materia.getNivel()));
                count++;
                lstParamMateria.add(new Parametro(count, materia.getHorasPracticas() + materia.getHorasTeoricas()));
                count++;
                lstParamMateria.add(new Parametro(count, escuelaCodigo));
                count++;

                if (rows > 1 && i < rows) {
                    values = values.concat(",(?,?,?,?,?)");
                }
                i++;
            }
            String sql = "SELECT sisbi.fn_insert_materia((ARRAY[(?,?,?,?,?)" + values + "])::sisbi.tmateria[])";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamMateria);
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

    public static ArrayOfMateriaPensum getMallaCurricularPensumVigenteSinDescripcion(java.lang.String strCodCarrera) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getMallaCurricularPensumVigenteSinDescripcion(strCodCarrera);
    }

    public static ArrayOfDictadoMateria getDictadosMateria(java.lang.String codCarrera, java.lang.String codMateria) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getDictadosMateria(codCarrera, codMateria);
    }

    public static ArrayOfMateria getMateriasDocente(java.lang.String codCarrera, java.lang.String cedula, java.lang.String codPeriodo) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getMateriasDocente(codCarrera, cedula, codPeriodo);
    }

}
