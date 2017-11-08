/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jsf;

import com.castanheira.escola.jpa.entities.Professor;
import com.castanheira.escola.jpa.session.ProfessorFacade;
import com.castanheira.escola.jsf.entities.Login;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.NoResultException;


/**
 *
 * @author mscas
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private Login login = new Login();
    @EJB
    private ProfessorFacade professorFacade;
    private Professor professor = new Professor();
    

    public String envia() {

        professor = professorFacade.validaLogin(login.getUsuario(), login.getSenha());
        if (professor == null) {
            professor = new Professor();
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!",
                            "Erro no Login!"));
            return null;
        } else {
            return "/index_teste";
        }

    }
    
    public LoginController() {
    }
    
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
