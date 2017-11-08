/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.Professor;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author mscas
 */
@Stateless
public class ProfessorFacade extends AbstractFacade<Professor> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfessorFacade() {
        super(Professor.class);
    }

    public Professor validaLogin(String usuario, String senha) {
        Professor retorno = null;
        
        try {
            //String teste = String.valueOf(getEntityManager().createNamedQuery(Professor.FIND_BY_EMAIL_SENHA).setParameter("usuario", usuario).setParameter("senha", senha).getSingleResult());
            retorno = (Professor) em.createNamedQuery(Professor.FIND_BY_EMAIL_SENHA).setParameter("usuario", usuario).setParameter("senha", senha).getSingleResult();
            /*FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, teste,
                            "Erro no Login!"));*/
            return retorno;
        } catch (NoResultException e) {
            return null;
        }

        //return null;
    }

}
