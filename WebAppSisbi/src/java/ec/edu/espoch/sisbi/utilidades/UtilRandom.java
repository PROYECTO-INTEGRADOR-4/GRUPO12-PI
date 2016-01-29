/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.utilidades;

import java.util.Random;

/**
 *
 * @author ©foqc
 */
public class UtilRandom {

    public static String generateKey() {
        Random random = new Random();
        return ""+random.nextInt(99999);
    }
}
