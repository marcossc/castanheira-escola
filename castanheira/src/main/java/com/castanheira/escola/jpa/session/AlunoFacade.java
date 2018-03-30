/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.Aluno;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author mscas
 */
@Stateless
public class AlunoFacade extends AbstractFacade<Aluno> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlunoFacade() {
        super(Aluno.class);
    }
    
    public List<Aluno> findRangeFiltro(int[] range, String nome) {        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Aluno> rt = cq.from(Aluno.class);
        cq.select(rt);
        ParameterExpression<String> p = cb.parameter(String.class);
        if (nome != null && !nome.isEmpty()) {
             //cq.where(getEntityManager().getCriteriaBuilder().equal(rt.get("nome"), p));
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
        javax.persistence.criteria.Root<Aluno> rt = cq.from(Aluno.class);
        cq.select(cb.count(rt));
        ParameterExpression<String> p = cb.parameter(String.class);
        if (nome != null && !nome.isEmpty()) {
           // cq.where(getEntityManager().getCriteriaBuilder().equal(rt.get("nome"), p));
           cq.where(cb.like(cb.lower(rt.<String>get("nome")), p));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        
        if (nome != null && !nome.isEmpty()) {
            q.setParameter(p, "%"+nome+"%");
        }
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
