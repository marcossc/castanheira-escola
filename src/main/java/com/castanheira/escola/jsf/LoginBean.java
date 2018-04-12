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
import javax.inject.Inject;


/**
 *
 * @author mscas
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private Login loginTela = new Login();
    @EJB
    private ProfessorFacade professorFacade;
    private Professor professor = new Professor();
    
    @Inject 
    private Login usuarioLogin;
    

    public String login() {

        professor = professorFacade.validaLogin(loginTela.getUsuario(), loginTela.getSenha());
        if (professor == null) {
            professor = new Professor();
            FacesUtil.addErrorMessage("Usuário/senha inválidos!");
            return null;
        } else {
            usuarioLogin.setUsuario(professor.getUsuario());
            usuarioLogin.setLogado(true);
            usuarioLogin.setPerfil(professor.getTipoString());
            SessionUtil.setParam("usuario", professor);
            return "/index";
        }
    }
    
    public String logout() {
        limparDadosUsuario();
        SessionUtil.remove("usuario");
        return "/login";
    }
    
    public void limparDadosUsuario() {
        usuarioLogin.setUsuario(null);
        usuarioLogin.setLogado(false);
        usuarioLogin.setPerfil(null);    
    }
    
    public Professor getLogado(){
        return (Professor) SessionUtil.getParam("usuario");
    }
    
    public LoginBean() {
    }

    public Login getLoginTela() {
        return loginTela;
    }

    public void setLoginTela(Login loginTela) {
        this.loginTela = loginTela;
    }
    

}
