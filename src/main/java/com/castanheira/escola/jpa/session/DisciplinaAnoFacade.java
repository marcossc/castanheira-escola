/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.DisciplinaAno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author mscas
 */
@Stateless
public class DisciplinaAnoFacade extends AbstractFacade<DisciplinaAno> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisciplinaAnoFacade() {
        super(DisciplinaAno.class);
    }
    
    public List<DisciplinaAno> findRangeFiltro(int[] range, Integer ano, Integer disciplina) {        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<DisciplinaAno> rt = cq.from(DisciplinaAno.class);
        cq.select(rt);
        ParameterExpression<Integer> pAno = cb.parameter(Integer.class);
        ParameterExpression<Integer> pDisciplina = cb.parameter(Integer.class);
        if (ano != null && disciplina != null) {
            Predicate p1 = cb.equal(rt.get("idAno").get("id"), pAno);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (ano != null && disciplina == null) {
             cq.where(cb.equal(rt.get("idAno").get("id"), pAno));            
        }else if (disciplina != null && ano == null) {
             cq.where(cb.equal(rt.get("idDisciplina").get("id"), pDisciplina));
        }
        cq.orderBy(cb.asc(rt.get("idAno").get("id")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        if (ano != null && disciplina != null) {
            q.setParameter(pAno, ano);
            q.setParameter(pDisciplina, disciplina);
        }else if (ano != null && disciplina == null) {
             q.setParameter(pAno, ano);
        }else if (disciplina != null && ano == null) {
             q.setParameter(pDisciplina, disciplina);
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);

        return q.getResultList();
    }
    
    public int countFiltro(Integer ano, Integer disciplina) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<DisciplinaAno> rt = cq.from(DisciplinaAno.class);
        cq.select(cb.count(rt));
        ParameterExpression<Integer> pAno = cb.parameter(Integer.class);
        ParameterExpression<Integer> pDisciplina = cb.parameter(Integer.class);
        if (ano != null && disciplina != null) {
            Predicate p1 = cb.equal(rt.get("idAno").get("id"), pAno);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (ano != null && disciplina == null) {
             cq.where(cb.equal(rt.get("idAno").get("id"), pAno));            
        }else if (disciplina != null && ano == null) {
             cq.where(cb.equal(rt.get("idDisciplina").get("id"), pDisciplina));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        
        if (ano != null && disciplina != null) {
            q.setParameter(pAno, ano);
            q.setParameter(pDisciplina, disciplina);
        }else if (ano != null && disciplina == null) {
             q.setParameter(pAno, ano);
        }else if (disciplina != null && ano == null) {
             q.setParameter(pDisciplina, disciplina);
        }
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public List<DisciplinaAno> findByAno(int ano) {
        return em.createNamedQuery("DisciplinaAno.findByAno").setParameter("ano", ano).getResultList();
    }
    
}
