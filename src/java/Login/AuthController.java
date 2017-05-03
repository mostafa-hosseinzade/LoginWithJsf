/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import Facade.UserFacade;
import Facade.JsfUtil;
import Model.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davood
 */
@Named(value = "authController")
@ViewScoped
public class AuthController implements Serializable {

    @EJB
    private UserFacade userFacade;

    private String username;
    private String password;

    public AuthController() {
    }

    public String login() {
        User user = userFacade.findByUsername(username);
        if (user == null || !user.checkPassword(password) || user.getStatus() == -1) {
            JsfUtil.addErrorMessage("نام کاربری یا رمز عبور اشتباه است");
            return null;
        }
        if (user.getStatus() == 1) {
            JsfUtil.addErrorMessage("حساب شما غیر فعال شده است");
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("user", user);
        JsfUtil.addSuccessMessage("شما با موفقیت وارد شدید");
        return "admin/index.xhtml?faces-redirect=true";
    }

    public String create() {
        //check mobile and password is not empty
        if (username == null
                || username.isEmpty()
                || password == null
                || password.isEmpty()) {
            JsfUtil.addErrorMessage("نام کاربری و رمز عبور نمی تواند خالی باشد");
            return null;
        }
        //check is first time to register
        User existEntity = userFacade.findByUsername(username);
        if (existEntity != null) {
            JsfUtil.addErrorMessage("این نام کاربری قبلا ثبت شده است");
            return null;
        } else {
            User cr = new User();
            cr.setUsername(username);
            cr.setPlainPassword(password);
            userFacade.edit(cr);
            JsfUtil.addErrorMessage("اطلاعات شما با موفقیت ثبت شد");
            FacesContext context = FacesContext.getCurrentInstance();
            try {
                context.getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void logout() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        context.getExternalContext().redirect("login.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
