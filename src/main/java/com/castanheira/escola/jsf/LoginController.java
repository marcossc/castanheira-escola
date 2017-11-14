/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jsf;

import com.castanheira.escola.jpa.entities.Professor;
import com.castanheira.escola.jpa.session.ProfessorFacade;
import com.castanheira.escola.jsf.entities.Login;
import com.castanheira.escola.jsf.util.FacesUtil;
import com.castanheira.escola.jsf.util.SessionUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


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
    

    public String login() {

        professor = professorFacade.validaLogin(login.getUsuario(), login.getSenha());
        if (professor == null) {
            professor = new Professor();
            FacesUtil.addErrorMessage("Usuário não encontrado!");
            return null;
        } else {
            SessionUtil.setParam("usuario", professor);
            return "/index_teste";
        }
    }
    
    public String logout() {
        SessionUtil.remove("usuario");
        return "/login";
    }
    
    public Professor getLogado(){
        return (Professor) SessionUtil.getParam("usuario");
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
