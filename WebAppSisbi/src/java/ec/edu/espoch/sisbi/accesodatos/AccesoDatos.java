/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.accesodatos;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author foqc
 */
/**
 *
 * metodo que permite ejecutar sentencias SQLCON PARAMETROS
 */
public class AccesoDatos {
    //esto es para no hacer procedimientos para cada procedimiento almacenado
    //<editor-fold defaultstate="collapsed" desc="Procedimientos">
     /*
     metodo de que permite ejecutar una funcion con parametros
     */

    public static Boolean ejecutaComando(String comando, ArrayList<Parametro> parametros) throws Exception {
        Boolean respuesta = false;
        PreparedStatement prts = null;//prts preparestatement
        //Creo la clase que contiene las rutas de las BD
        Ruta src = new Ruta();
        LeerPropiedades rdPostgres = new LeerPropiedades(src.getFileDbPostgres());//lee el archivo .properties
        Conexion cnxPostgres = new Conexion(rdPostgres.getIpHost(), rdPostgres.getPuerto(), rdPostgres.getBasedatos(),
                rdPostgres.getUsuario(), rdPostgres.getContrasena(), rdPostgres.getGestor());//creo na instancia de conexion

        try {
            cnxPostgres.conectarBD();//me conecto a la DB
            //preparo los comandos(sentencia==> select fn_insert_tpersona())
            prts = cnxPostgres.getCnx().prepareStatement(comando);
            //mando mis parametros a la funcion
            for (Parametro param : parametros) {
                prts.setObject(param.getPosicion(), param.getValor());
            }

            int i = prts.executeUpdate();
            if (i > 0) {
                respuesta = true;
            }
            cnxPostgres.confirmar();//confirma cambiso en la BD
            prts.close();
            prts = null;//libero memoria
        } catch (SQLException exCon) {
            cnxPostgres.deshacer();//si algo sale mal deshace los cambios de la base de datos
            throw exCon;
        } finally {
            try {
                //verifico si a conexion no esta cerrada
                if (!cnxPostgres.conexionCerrada()) {
                    cnxPostgres.desconectarBD();
                }
            } catch (SQLException ex) {
                System.err.println("causa: " + ex.getCause());
                System.err.println("Mensaje: " + ex.getMessage());
                System.err.println("Localizacion: " + ex.getLocalizedMessage());
                System.err.println("Traza: " + ex.getStackTrace());
            }
        }

        return respuesta;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="funcion para ejecutar procedimientos">
    public static ConjuntoResultado ejecutaQuery(String query) throws Exception {
        ConjuntoResultado objres = new ConjuntoResultado();//objeto conjunto de resultados almacena cabecera...
        //contiene los registros traidas desde una BD
        ResultSet rs = null;
        PreparedStatement prts = null;//prts preparestatement

        try {
            //Creo la clase que contiene las rutas de las BD
            Ruta src = new Ruta();
            LeerPropiedades rdPostgres = new LeerPropiedades(src.getFileDbPostgres());//lee el archivo .properties
            Conexion cnxPostgres = new Conexion(rdPostgres.getIpHost(), rdPostgres.getPuerto(), rdPostgres.getBasedatos(),
                    rdPostgres.getUsuario(), rdPostgres.getContrasena(), rdPostgres.getGestor());//creo na instancia de conexion           
            try {
                cnxPostgres.conectarBD();//me conecto a la DB
                //precompila una sentencia SQL
                prts = cnxPostgres.getCnx().prepareStatement(query);
                //ejecuto la sentencia SQL y obtengo registros buscados
                rs = prts.executeQuery();
                cnxPostgres.confirmar();//comfirma los cambios a la BD
                //almacena todos los registros cunado ejecute la consulta y luego lleno en el metodo fill
                objres.Fill(rs);

                rs.close();
                prts = null;
            } catch (SQLException e) {
                cnxPostgres.deshacer();
                throw e;
            } finally {
                try {
                    //verifico si la conexion no esta cerrada
                    if (!cnxPostgres.conexionCerrada()) {
                        cnxPostgres.desconectarBD();
                    }
                } catch (SQLException ex) {
                    System.err.println("causa: " + ex.getCause());
                    System.err.println("Mensaje: " + ex.getMessage());
                    System.err.println("Localizacion: " + ex.getLocalizedMessage());
                    System.err.println("Traza: " + ex.getStackTrace());
                }
            }

        } catch (Exception e) {
            System.out.println("Error:*** " + e.getMessage());
        }
        return objres;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="funcion para ejecutar procedimientos con paramentros">
    public static ConjuntoResultado ejecutaQuery(String query, ArrayList<Parametro> param) throws Exception {
        ConjuntoResultado objres = new ConjuntoResultado();//objeto conjunto de resultados almacena cabecera...
        //contiene los registros traidas desde una BD
        ResultSet rs;
        PreparedStatement prts;//prts preparestatement      
        try {
            //Creo la clase que contiene las rutas de las BD
            Ruta src = new Ruta();
            LeerPropiedades rdPostgres = new LeerPropiedades(src.getFileDbPostgres());//lee el archivo .properties
            Conexion cnxPostgres = new Conexion(rdPostgres.getIpHost(), rdPostgres.getPuerto(), rdPostgres.getBasedatos(),
                    rdPostgres.getUsuario(), rdPostgres.getContrasena(), rdPostgres.getGestor());//creo na instancia de conexion
            try {
                cnxPostgres.conectarBD();//me conecto a la DB
                //precompila una sentencia SQL query="select fn_insert_tpersona(?,?,?)"
                prts = cnxPostgres.getCnx().prepareStatement(query);
                for (Parametro param1 : param) {
                    //enviando los paramentros a la sentencia precompilada
                    //select fn_insert_tpersona(juan,2,3)
                    prts.setObject(param1.getPosicion(), param1.getValor());
                }
                //ejecuto la sentencia SQL y obtengo registros buscados directo en la BD
                rs = prts.executeQuery();
                cnxPostgres.confirmar();//comfirma los cambios a la BD
                //almacena todos los registros cuando ejecute la consulta y luego lleno en el metodo fill
                objres.Fill(rs);

                rs.close();
                prts = null;
            } catch (SQLException e) {
                cnxPostgres.deshacer();
                throw e;
            } finally {
                try {
                    //verifico si la conexion no esta cerrada
                    if (!cnxPostgres.conexionCerrada()) {
                        cnxPostgres.desconectarBD();
                    }
                } catch (SQLException ex) {
                    System.err.println("causa: " + ex.getCause());
                    System.err.println("Mensaje: " + ex.getMessage());
                    System.err.println("Localizacion: " + ex.getLocalizedMessage());
                    System.err.println("Traza: " + ex.getStackTrace());
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error:*** " + e.getMessage());
        }
        return objres;
    }
    //</editor-fold>
}
