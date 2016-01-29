/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.utilidades;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ©foqc
 */
@ManagedBean
@RequestScoped
public class UtilString {

    /**
     * Creates a new instance of UtilString
     */
    public UtilString() {
    }

    public static String devolverCadena(String cadena, String cadena1) {
        String sCadenaSinBlancos = "";
        String sCadenaSinBlancos1 = "";
        for (int x = 0; x < cadena.length(); x++) {
            if (cadena.charAt(x) != ' ') {
                sCadenaSinBlancos += cadena.charAt(x);
            } else {
                x = cadena.length();
            }
        }
        for (int x = 0; x < cadena1.length(); x++) {
            if (cadena1.charAt(x) != ' ') {
                sCadenaSinBlancos1 += cadena1.charAt(x);
            } else {
                x = cadena1.length();
            }
        }
        return sCadenaSinBlancos + " " + sCadenaSinBlancos1;
    }

    public static String validarCadena(String cadena) {
        cadena = cadena.replaceAll("^\\s*", "");//Elimina espacios, tabuladores y retornos delante.
        cadena = cadena.replaceAll("\\s*$", "");//Elimina espacios, tabuladores y retornos detrás.
        cadena = cadena.replaceAll("[^\\p{IsLetter} ]", "");//Elimina numeros, caracteres especiales.
        cadena = cadena.replaceAll(" +", " ");//Elimina mas de un  espacio.
        return cadena;
    }
}
