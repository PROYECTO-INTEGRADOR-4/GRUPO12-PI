package ec.edu.espoch.sisbi.controladores;

import ec.edu.espoch.sisbi.entidades.CRol;
import ec.edu.espoch.sisbi.entidades.CUsuario;
import ec.edu.espoch.sisbi.modelos.MLogin;
import ec.edu.espoch.sisbi.modelos.MUsuario;
import ec.edu.espoch.sisbi.utilidades.Util;
import ec.edu.espoch.sisbi.utilidades.UtilString;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -2152389656664659476L;
    private CUsuario objUsuario;
    private boolean logeado = false;

    public boolean estaLogeado() {
        return logeado;
    }

    public LoginBean() {
        this.objUsuario = new CUsuario();
    }

    public CUsuario getObjUsuario() {
        return objUsuario;
    }

    public void setObjUsuario(CUsuario objUsuario) {
        this.objUsuario = objUsuario;
    }

    @PostConstruct
    public void reinit() {
        CRol objTipo = new CRol();
        this.objUsuario.setObjRol(objTipo);
    }

    public void login(ActionEvent actionEvent) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            FacesMessage msg = null;
            if (MLogin.loginCorrecto(objUsuario)) {
                this.objUsuario = MLogin.datosUsuario(objUsuario);
                logeado = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", UtilString.devolverCadena(objUsuario.getNombres(), objUsuario.getApellidos()));
            } else {
                logeado = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                        "Credenciales no válidas");
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("estaLogeado", logeado);
            if (logeado) {
                redireccionarPaginas(context);
            }
        } catch (Exception e) {
            Util.addErrorMessageAndDetail("Error ", e.getMessage());
        }
    }

    public void updateSession(CUsuario usuarioSession, String codigoQR) throws Exception {
        try {
            usuarioSession.setNombres(UtilString.validarCadena(usuarioSession.getNombres()));
            usuarioSession.setApellidos(UtilString.validarCadena(usuarioSession.getApellidos()));
            if (usuarioSession.getNombres().length() > 3 && usuarioSession.getApellidos().length() > 3 && codigoQR.length() > 10) {
                usuarioSession.setCodigoQR(codigoQR);
                if (MUsuario.actualizarUsuario(usuarioSession)) {
                    HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                            .getExternalContext().getSession(false);
                    LoginBean loginBean = (LoginBean) session.getAttribute("loginBean");//actualizo session
                    loginBean.setObjUsuario(MLogin.datosUsuario(objUsuario));//en vez de buscar [MLogin.datosUsuario(objUsuario)] podria asignarle usuarioSession directamente.
                    actualizoBean();
                    Util.addSuccessMessageAndDetail("Aviso", "Sus datos se han actualizado correctamente.");
                } else {
                    Util.addErrorMessageAndDetail("Error ", "Sus datos no se han actualizado, por favor inténtelo de nuevo.");
                }
            } else {
                Util.addErrorMessageAndDetail("Error", "Verifique sus datos, hay inconsistencias!");
            }
        } catch (Exception e) {
            Util.addErrorMessageAndDetail("Error update session ", e.getMessage());
        }
        this.objUsuario = MLogin.datosUsuario(usuarioSession);//aunque se modifique o no se obtengo una vez mas los datos del usario ===> va [usuarioSession] ya que como no se va a cambiar los datos del correo siempre sera la misma
    }

    private void actualizoBean() {
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        QRBean rq = (QRBean) session.getAttribute("qRBean");
        rq.setCodigoQR("");
    }

    private void redireccionarPaginas(RequestContext context) {
        switch (this.objUsuario.getObjRol().getCodigo()) {
            case "ADM":
                context.addCallbackParam("view", "_administrador/indexAdmin.xhtml");
                break;
            case "DOC":
                context.addCallbackParam("view", "_docente/indexDoc.xhtml");
                break;
            case "EST":
                context.addCallbackParam("view", "_estudiante/indexEst.xhtml");
                break;
            default:
                context.addCallbackParam("view", "login.xhtml");
        }
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        logeado = false;
    }
}
