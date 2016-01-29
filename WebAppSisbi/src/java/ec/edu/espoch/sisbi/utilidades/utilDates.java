/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.utilidades;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author ©foqc
 */
@ManagedBean
@RequestScoped
public class utilDates {

    /**
     * Creates a new instance of utilDates
     */
    public utilDates() {
    }

    public Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }

    public Boolean compareDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarAreservar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendarAreservar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime().before(calendarAreservar.getTime());
    }

    public Boolean afterCompareDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarAreservar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendarAreservar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime().after(calendarAreservar.getTime());
    }

    public Boolean compareMinutesFecha(Date fecha, int minutes) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarAreservar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendarAreservar.add(Calendar.MINUTE, minutes);
        return calendar.getTime().after(calendarAreservar.getTime());
    }

    public Boolean reservaDosHoras(Date fecha1, Date fecha2) {
        Boolean respuesta = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha1);
        calendar.add(Calendar.HOUR, 2);
        fecha1 = calendar.getTime();
        if (fecha1.getTime() <= fecha2.getTime()) {
            respuesta = true;
        }
        return (respuesta);

    }

    public Boolean verificarFechas(Date fecha1, Date fecha2, Date fecha3, Date fecha4) {
        Boolean respuesta = true;
        if ((fecha1.getTime() >= fecha3.getTime() & fecha2.getTime() <= fecha4.getTime()) || (fecha1.getTime() >= fecha3.getTime() & fecha1.getTime() < fecha4.getTime()) || (fecha2.getTime() > fecha3.getTime() & fecha2.getTime() <= fecha4.getTime()) || (fecha1.getTime() <= fecha3.getTime() & fecha2.getTime() >= fecha4.getTime())) {
            respuesta = false;
        }
        return (respuesta);
    }

    /*
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
    public static Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    public Date toDate1(XMLGregorianCalendar calendar) {
        
        
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /*
     * Converts java.util.Date to javax.xml.datatype.XMLGregorianCalendar
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(String.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xmlCalendar;
    }
}
