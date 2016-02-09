/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.modelos;

import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfDictadoMateria;
import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfEstudiante;
import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfHorarioClaseParalelo;
import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfHorarioDocente;
import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfHorarioEstudiante;
import ec.edu.espoch.sisbi.WSInfoCarrera.ArrayOfMateriaParalelo;
import ec.edu.espoch.sisbi.WSInfoCarrera.HorarioClaseParalelo;
import ec.edu.espoch.sisbi.WSInfoCarrera.HorarioDocente;
import ec.edu.espoch.sisbi.WSInfoCarrera.HorarioEstudiante;
import ec.edu.espoch.sisbi.WSInfoCarrera.MateriaParalelo;
import ec.edu.espoch.sisbi.accesodatos.AccesoDatos;
import ec.edu.espoch.sisbi.accesodatos.ConjuntoResultado;
import ec.edu.espoch.sisbi.accesodatos.Parametro;
import ec.edu.espoch.sisbi.entidades.CAsistencia;
import ec.edu.espoch.sisbi.entidades.CUsuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author foqc
 */
public class MAsistencia {

    public static String verificarHorarioDocente(String materiaBuscada, String carrera, String periodo, String docente) throws Exception {
        Calendar calendario = new GregorianCalendar();
        String dia = Integer.toString(calendario.get(Calendar.DAY_OF_WEEK));
        Integer hora = calendario.get(Calendar.HOUR_OF_DAY);
        Integer minutos = calendario.get(Calendar.MINUTE);
        Integer diaDeSemana = Integer.parseInt(dia);
        String codigoHoraMateria = "";
        String codigoHora = "";
        hora = 11;
        try {
            ArrayOfHorarioDocente horario = getHorarioDocente(carrera, docente);
            List<HorarioDocente> h = horario.getHorarioDocente();
            for (HorarioDocente h1 : h) {
                switch (diaDeSemana) {
                    case 1: {
                        if (h1.getStrDomingo().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 2: {
                        if (h1.getStrLunes().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 3: {
                        if (h1.getStrMartes().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 4: {
                        if (h1.getStrMiercoles().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 5: {
                        if (h1.getStrJueves().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 6: {
                        if (h1.getStrViernes().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 7: {
                        if (h1.getStrSabado().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                }
                if (!codigoHoraMateria.isEmpty()) {
                    break;
                }
            }

            if (!codigoHoraMateria.isEmpty()) {
                if (correspondeHora(codigoHoraMateria, hora, minutos)) {
                    codigoHora = codigoHoraMateria;

                } else {
                    throw new Exception("No esta permitido registrar la asistencia fuera del horario.");
                }
            } else {
                throw new Exception("Usted no tiene la materia " + materiaBuscada + " el día de hoy.");
            }
        } catch (Exception ex) {
            throw ex;
        }
        return codigoHora;
    }

    public static List<String> verificarHorarioParalelo(String materiaBuscada, String carrera, String periodo, String estudiante, String docente) throws Exception {
        Calendar calendario = new GregorianCalendar();
        String dia = Integer.toString(calendario.get(Calendar.DAY_OF_WEEK));
        Integer hora = calendario.get(Calendar.HOUR_OF_DAY);
        Integer minutos = calendario.get(Calendar.MINUTE);
        Integer diaDeSemana = Integer.parseInt(dia);
        //
        hora = 11;
        String codigoHoraMateria = "";
        String paraleloDoc = "";
        List<String> datos = new ArrayList<>();
        try {
            ArrayOfHorarioEstudiante horario = getHorarioEstudiante(carrera, estudiante);
            List<HorarioEstudiante> h = horario.getHorarioEstudiante();
            for (HorarioEstudiante h1 : h) {
                switch (diaDeSemana) {
                    case 1: {
                        if (h1.getStrDomingo().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 2: {
                        if (h1.getStrLunes().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 3: {
                        if (h1.getStrMartes().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 4: {
                        if (h1.getStrMiercoles().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 5: {
                        if (h1.getStrJueves().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 6: {
                        if (h1.getStrViernes().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                    case 7: {
                        if (h1.getStrSabado().contains(materiaBuscada)) {
                            codigoHoraMateria = h1.getStrCodHora();
                        }
                    }
                    break;
                }
                if (!codigoHoraMateria.isEmpty()) {
                    break;
                }
            }

            if (!codigoHoraMateria.isEmpty()) {
                if (correspondeHora(codigoHoraMateria, hora, minutos)) {
                    String paraleloEst = "";
                    List<HorarioClaseParalelo> horarioDocente = getHorariosDocenteParalelo(carrera, docente, periodo).getHorarioClaseParalelo();
                    for (HorarioClaseParalelo horario1 : horarioDocente) {
                        if (horario1.getMateria().equals(materiaBuscada)) {
                            paraleloDoc = horario1.getStrCodParalelo();
                            break;
                        }
                    }
                    if (!paraleloDoc.isEmpty()) {
                        List<MateriaParalelo> horarioEstudiante = getMateriasParaleloEstudiante(carrera, estudiante, periodo).getMateriaParalelo();
                        for (MateriaParalelo materia1 : horarioEstudiante) {
                            if (materia1.getNombre().equals(materiaBuscada)) {
                                paraleloEst = materia1.getParalelo();
                                break;
                            }
                        }

                        if (paraleloDoc.equals(paraleloEst)) {
                            datos.add(paraleloDoc);
                            datos.add(codigoHoraMateria);
                            //System.out.println("Registro de asistencia correcto.");
                        } else {
                            throw new Exception("El estudiante con DNI: " + estudiante + " no pertenece al paralelo " + paraleloDoc + " pertenece al paralelo " + paraleloEst + " en la materia de " + materiaBuscada + ". ");
                        }
                    } else {
                        throw new Exception("El docente no dicta la materia " + materiaBuscada + ".");
                    }

                } else {
                    throw new Exception("No esta permitido registrar la asistencia fuera del horario.");
                }
            } else {
                throw new Exception("Usted no tiene la materia " + materiaBuscada + " el día de hoy.");
            }
        } catch (Exception ex) {
            throw ex;
        }
        return datos;
    }

    private static Boolean correspondeHora(String codigoHora, int hora, int minuto) {
        int maxMin = 20;
        Boolean correcto = false;
        switch (codigoHora) {
            case "H1": {
                if ((hora >= 7 && hora <= 9) || (hora == 9 && minuto <= maxMin)) {
                    correcto = true;
                }
            }
            break;
            case "H2": {
                if ((hora >= 9 && hora <= 11) || (hora == 11 && minuto <= maxMin)) {
                    correcto = true;
                }
            }
            break;
            case "H3": {
                if ((hora >= 11 && hora <= 13) || (hora == 13 && minuto <= maxMin)) {
                    correcto = true;
                }
            }
            break;
            case "H4": {
                if ((hora >= 13 && hora <= 15) || (hora == 15 && minuto <= maxMin)) {
                    correcto = true;
                }
            }
            break;
            case "H5": {
                if ((hora >= 14 && hora <= 16) || (hora == 16 && minuto <= maxMin)) {
                    correcto = true;
                }
            }
            break;
            case "H6": {
                if ((hora >= 16 && hora <= 18) || (hora == 18 && minuto <= maxMin)) {
                    correcto = true;
                }
            }
            break;
            case "H7": {
                if ((hora >= 18 && hora <= 20) || (hora == 20 && minuto <= maxMin)) {
                    correcto = true;
                }
            }
            break;
        }
        return correcto;
    }

    public static Boolean insertarAsistencia(CAsistencia objAsistencia) throws Exception {
        Boolean correcto = false;
        try {
            String sql = "SELECT * FROM sisbi.fn_insert_una_asistencia(?,?,?,?,?,?,?,?)";
            java.sql.Date sqlDateAsistencia = new java.sql.Date(objAsistencia.getFechaAsistencia().getTime());
            java.sql.Time sqlDateHora = new java.sql.Time(objAsistencia.getFechaAsistencia().getTime());
            String materiaCodigo = MMateria.cargarMateriaByName(objAsistencia.getObjMateria().getNombre()).getCodigo();
            long usuarioCodigo = MLogin.datosUsuario(objAsistencia.getObjUsuario()).getCodigo();
            ArrayList<Parametro> lstParam = new ArrayList<>();
            lstParam.add(new Parametro(1, objAsistencia.getEstado()));
            lstParam.add(new Parametro(2, sqlDateAsistencia));
            lstParam.add(new Parametro(3, sqlDateHora));
            lstParam.add(new Parametro(4, objAsistencia.getParalelo()));
            lstParam.add(new Parametro(5, objAsistencia.getHoraCodigo()));
            lstParam.add(new Parametro(6, usuarioCodigo));
            lstParam.add(new Parametro(7, objAsistencia.getObjPeriodo().getCodigo()));
            lstParam.add(new Parametro(8, materiaCodigo));
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParam);
            while (rs.next()) {
                correcto = rs.getBoolean(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return correcto;
    }

    public static boolean insertAsistencias(List<CAsistencia> lstAsistencias, String materiaCodigo) throws Exception {
        boolean respuesta = false;
        int count;
        String values = "";
        try {
            ArrayList<Parametro> lstParamAsistencia = new ArrayList<>();

            count = 1;
            int rows = lstAsistencias.size();
            int i = 1;
            for (CAsistencia asistencia : lstAsistencias) {
                java.sql.Date sqlDateAsistencia = new java.sql.Date(asistencia.getFechaAsistencia().getTime());
                java.sql.Time sqlDateHora = new java.sql.Time(asistencia.getFechaAsistencia().getTime());
                //String materiaCodigo = MMateria.cargarMateriaByName(asistencia.getObjMateria().getNombre()).getCodigo();
                long usuarioCodigo = MLogin.datosUsuario(asistencia.getObjUsuario()).getCodigo();

                lstParamAsistencia.add(new Parametro(count, asistencia.getEstado()));
                count++;
                lstParamAsistencia.add(new Parametro(count, sqlDateAsistencia));
                count++;
                lstParamAsistencia.add(new Parametro(count, sqlDateHora));
                count++;
                lstParamAsistencia.add(new Parametro(count, asistencia.getParalelo()));
                count++;
                lstParamAsistencia.add(new Parametro(count, asistencia.getHoraCodigo()));
                count++;
                lstParamAsistencia.add(new Parametro(count, usuarioCodigo));
                count++;
                lstParamAsistencia.add(new Parametro(count, asistencia.getObjPeriodo().getCodigo()));
                count++;
                lstParamAsistencia.add(new Parametro(count, materiaCodigo));
                count++;

                if (rows > 1 && i < rows) {
                    values = values.concat(",(?,?,?,?,?,?,?,?)");
                }
                i++;
            }
            String sql = "SELECT sisbi.fn_insert_varias_asistencias((ARRAY[(?,?,?,?,?,?,?,?)" + values + "])::sisbi.tasistencia[])";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParamAsistencia);
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

    public static List<CAsistencia> obtenerAsistencias(List<CUsuario> lstUsuarios, String paralelo, String periodoCodigo, String materiaCodigo) throws Exception {
        List<CAsistencia> lstAsistencias = new ArrayList<>();
        int count;
        String values = "";
        try {
            ArrayList<Parametro> lstParam = new ArrayList<>();

            count = 1;
            int rows = lstUsuarios.size();
            int i = 1;
            for (CUsuario usuario : lstUsuarios) {
                lstParam.add(new Parametro(count, usuario.getCodigo()));
                count++;
                lstParam.add(new Parametro(count, usuario.getCedula()));
                count++;
                lstParam.add(new Parametro(count, usuario.getNombres()));
                count++;
                lstParam.add(new Parametro(count, usuario.getApellidos()));
                count++;
                lstParam.add(new Parametro(count, usuario.getCodigoQR()));
                count++;
                lstParam.add(new Parametro(count, usuario.getClave()));
                count++;
                lstParam.add(new Parametro(count, usuario.getObjRol().getCodigo()));
                count++;
                lstParam.add(new Parametro(count, usuario.getObjEscuela().getCodigo()));
                count++;

                if (rows > 1 && i < rows) {
                    values = values.concat(",(?,?,?,?,?,?,?,?)");
                }
                i++;
            }

            lstParam.add(new Parametro(count, paralelo));
            lstParam.add(new Parametro(count + 1, periodoCodigo));
            lstParam.add(new Parametro(count + 2, materiaCodigo));
            String sql = "SELECT * from sisbi.fn_select_porcentaje_asistencias((ARRAY[(?,?,?,?,?,?,?,?)" + values + "])::sisbi.tusuario[],?,?,?)";
            ConjuntoResultado rs = AccesoDatos.ejecutaQuery(sql, lstParam);
            Boolean primero = true;
            short c = 0;
            while (rs.next()) {
                CAsistencia objAsistencia = new CAsistencia();
                CUsuario objUsuario = new CUsuario();
                objUsuario.setCodigo((long) 0);
                objUsuario.setCedula(rs.getString(0));
                objUsuario.setNombres(rs.getString(1));
                objUsuario.setApellidos(rs.getString(2));
                objAsistencia.setObjUsuario(objUsuario);
                objAsistencia.setAsistidos(rs.getShort(3));
                objAsistencia.setNoAsistidos(rs.getShort(4));
                if (primero) {
                    c = (short) (rs.getShort(3) + rs.getShort(4));
                    primero = false;
                }
                objAsistencia.setPorcentajeAsistencia(porcentajeAsistencia(c, rs.getShort(3)));
                lstAsistencias.add(objAsistencia);
            }
        } catch (Exception e) {
            throw e;
        }
        return lstAsistencias;
    }

    private static float porcentajeAsistencia(Short totalAsistencia, Short totalAsistidos) {
        return (float) redondear(((totalAsistidos * 100.0) / totalAsistencia), 1);
    }

    public static double redondear(double numero, int digitos) {
        int cifras = (int) Math.pow(10, digitos);
        return Math.rint(numero * cifras) / cifras;
    }

    private static ArrayOfHorarioEstudiante getHorarioEstudiante(java.lang.String codCarrera, java.lang.String strCedula) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getHorarioEstudiante(codCarrera, strCedula);
    }

    private static ArrayOfHorarioClaseParalelo getHorariosDocenteParalelo(java.lang.String strCodCarrera, java.lang.String strCedula, java.lang.String strCodPeriodo) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getHorariosDocenteParalelo(strCodCarrera, strCedula, strCodPeriodo);
    }

    private static ArrayOfMateriaParalelo getMateriasParaleloEstudiante(java.lang.String codCarrera, java.lang.String cedula, java.lang.String codPeriodo) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getMateriasParaleloEstudiante(codCarrera, cedula, codPeriodo);
    }

    public static ArrayOfEstudiante getAlumnosMateria(java.lang.String strCodCarrera, java.lang.String strCodNivel, java.lang.String strCodParalelo, java.lang.String strCodPeriodo, java.lang.String strCodMateria) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getAlumnosMateria(strCodCarrera, strCodNivel, strCodParalelo, strCodPeriodo, strCodMateria);
    }

    public static ArrayOfDictadoMateria getDictadosMateria(java.lang.String codCarrera, java.lang.String codMateria) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getDictadosMateria(codCarrera, codMateria);
    }

    private static ArrayOfHorarioDocente getHorarioDocente(java.lang.String codCarrera, java.lang.String strCedula) {
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera service = new ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarrera();
        ec.edu.espoch.sisbi.WSInfoCarrera.InfoCarreraSoap port = service.getInfoCarreraSoap();
        return port.getHorarioDocente(codCarrera, strCedula);
    }
}
