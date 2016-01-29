/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.utilidades;

import ec.edu.espoch.sisbi.WSInfoGeneral.Facultad;
import ec.edu.espoch.sisbi.modelos.MFacultad;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author ©foqc
 */
@ManagedBean
@RequestScoped
public class FacultadtemsBean {

    private List<SelectItem> selectOneItemsFacultad;

    /**
     * Creates a new instance of VisionItemsBean
     */
    public FacultadtemsBean() {
    }
    
    public List<SelectItem> getSelectOneItemsVision() {
        try {
            this.selectOneItemsFacultad = new ArrayList<>();
            List<Facultad> facultades = MFacultad.cargarFacultad();
            for (Facultad facultad : facultades) {
                SelectItem selectItem = new SelectItem(facultad.getCodigo(), facultad.getNombre());
                this.selectOneItemsFacultad.add(selectItem);
            }
        } catch (Exception e) {
            System.err.println("%%%%%%%%%%%%%%%%%%%% " + e.getMessage() + "%%%%%%%%%%%%%%%%%%%%");
        }
        return this.selectOneItemsFacultad;
    }
}
