/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.Professor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

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
    
    public List<Professor> findRangeFiltro(int[] range, String nome) {        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Professor> rt = cq.from(Professor.class);
        cq.select(rt);
        ParameterExpression<String> p = cb.parameter(String.class);
        if (nome != null && !nome.isEmpty()) {
             cq.where(cb.like(cb.lower(rt.<String>get("nome")), p));
        }
        cq.orderBy(cb.asc(rt.get("nome")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        if (nome != null && !nome.isEmpty()) {
            q.setParameter(p, "%"+nome+"%");
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);

        return q.getResultList();
    }
    public int countFiltro(String nome) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Professor> rt = cq.from(Professor.class);
        cq.select(cb.count(rt));
        ParameterExpression<String> p = cb.parameter(String.class);
        if (nome != null && !nome.isEmpty()) {
           cq.where(cb.like(cb.lower(rt.<String>get("nome")), p));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        
        if (nome != null && !nome.isEmpty()) {
            q.setParameter(p, "%"+nome+"%");
        }
        return ((Long) q.getSingleResult()).intValue();
    }

}
