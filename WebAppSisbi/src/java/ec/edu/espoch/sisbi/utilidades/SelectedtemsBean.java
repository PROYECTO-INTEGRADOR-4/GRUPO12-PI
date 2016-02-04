/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espoch.sisbi.utilidades;

import ec.edu.espoch.sisbi.WSInfoGeneral.Escuela;
import ec.edu.espoch.sisbi.WSInfoGeneral.Facultad;
import ec.edu.espoch.sisbi.entidades.CRol;
import ec.edu.espoch.sisbi.modelos.MEscuela;
import ec.edu.espoch.sisbi.modelos.MFacultad;
import ec.edu.espoch.sisbi.modelos.MRol;
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
public class SelectedtemsBean {

    private List<SelectItem> selectOneItemsFacultad;
    private List<SelectItem> selectOneItemsEscuela;
    private List<SelectItem> selectOneItemsRol;

    /**
     * Creates a new instance of VisionItemsBean
     */
    public SelectedtemsBean() {
    }

    public List<SelectItem> getSelectOneItemsFacultad() {
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

    public List<SelectItem> getSelectOneItemsEscuela(String codFacultad) {
        try {
            this.selectOneItemsEscuela = new ArrayList<>();
            List<Escuela> escuelas = MEscuela.cargarEscuela(codFacultad);
            for (Escuela escuela : escuelas) {
                SelectItem selectItem = new SelectItem(escuela.getCodigo(), escuela.getNombre());
                this.selectOneItemsEscuela.add(selectItem);
            }
        } catch (Exception e) {
            System.err.println("%%%%%%%%%%%%%%%%%%%% " + e.getMessage() + "%%%%%%%%%%%%%%%%%%%%");
        }
        return this.selectOneItemsEscuela;
    }

    public List<SelectItem> getSelectOneItemsRol() {
        try {
            this.selectOneItemsRol = new ArrayList<>();
            List<CRol> roles = MRol.cargarRoles();
            for (CRol rol : roles) {
                if (!rol.getCodigo().equals("ADM")) {
                    SelectItem selectItem = new SelectItem(rol.getCodigo(), rol.getNombre());
                    this.selectOneItemsRol.add(selectItem);
                }
            }
        } catch (Exception e) {

            Util.addErrorMessage(e.getMessage());
        }
        return this.selectOneItemsRol;
    }
}
