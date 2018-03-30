/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.ProfessorDiscTur;
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
public class ProfessorDiscTurFacade extends AbstractFacade<ProfessorDiscTur> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfessorDiscTurFacade() {
        super(ProfessorDiscTur.class);
    }
    
    public List<ProfessorDiscTur> findRangeFiltro(int[] range, Integer ano, Integer disciplina, Integer professor) {        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<ProfessorDiscTur> rt = cq.from(ProfessorDiscTur.class);
        cq.select(rt);
        ParameterExpression<Integer> pAno = cb.parameter(Integer.class);
        ParameterExpression<Integer> pDisciplina = cb.parameter(Integer.class);
        ParameterExpression<Integer> pProfessor = cb.parameter(Integer.class);
            
        if (ano != null && disciplina != null && professor != null) {
            Predicate p1 = cb.equal(rt.get("idTurma").get("id"), pDisciplina);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            Predicate p3 = cb.equal(rt.get("idProfessor").get("id"), pProfessor);
            cq.where(cb.and(p1, p2, p3));
        }else if (ano != null && disciplina != null && professor == null) {
            Predicate p1 = cb.equal(rt.get("idTurma").get("id"), pDisciplina);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (ano != null && disciplina == null && professor != null) {
            Predicate p1 = cb.equal(rt.get("idTurma").get("id"), pDisciplina);
            Predicate p2 = cb.equal(rt.get("idProfessor").get("id"), pProfessor);
            cq.where(cb.and(p1, p2));
        }else if (ano == null && disciplina != null && professor != null) {
            Predicate p1 = cb.equal(rt.get("idProfessor").get("id"), pProfessor);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (ano != null && disciplina == null && professor == null) {
             cq.where(cb.equal(rt.get("idTurma").get("id"), pAno));            
        }else if (disciplina != null && ano == null && professor == null) {
             cq.where(cb.equal(rt.get("idDisciplina").get("id"), pDisciplina));
        }else if (professor != null && ano == null && disciplina == null) {
             cq.where(cb.equal(rt.get("idProfessor").get("id"), pProfessor));
        }
        cq.orderBy(cb.asc(rt.get("id")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        
        if (ano != null && disciplina != null && professor != null) {
            q.setParameter(pAno, ano);
            q.setParameter(pDisciplina, disciplina);
            q.setParameter(pProfessor, professor);
        }else if (ano != null && disciplina != null && professor == null) {
            q.setParameter(pAno, ano);
            q.setParameter(pDisciplina, disciplina);
        }else if (ano != null && disciplina == null && professor != null) {
            q.setParameter(pAno, ano);
            q.setParameter(pProfessor, professor);
        }else if (ano == null && disciplina != null && professor != null) {
            q.setParameter(pDisciplina, disciplina);
            q.setParameter(pProfessor, professor);
        }else if (ano != null && disciplina == null && professor == null) {
            q.setParameter(pAno, ano);
        }else if (disciplina != null && ano == null && professor == null) {
            q.setParameter(pDisciplina, disciplina);
        }else if (professor != null && ano == null && disciplina == null) {
            q.setParameter(pProfessor, professor);
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);

        return q.getResultList();
    }
    
    public int countFiltro(Integer ano, Integer disciplina, Integer professor) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<ProfessorDiscTur> rt = cq.from(ProfessorDiscTur.class);
        cq.select(cb.count(rt));
        ParameterExpression<Integer> pAno = cb.parameter(Integer.class);
        ParameterExpression<Integer> pDisciplina = cb.parameter(Integer.class);
        ParameterExpression<Integer> pProfessor = cb.parameter(Integer.class);
            
        if (ano != null && disciplina != null && professor != null) {
            Predicate p1 = cb.equal(rt.get("idTurma").get("id"), pDisciplina);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            Predicate p3 = cb.equal(rt.get("idProfessor").get("id"), pProfessor);
            cq.where(cb.and(p1, p2, p3));
        }else if (ano != null && disciplina != null && professor == null) {
            Predicate p1 = cb.equal(rt.get("idTurma").get("id"), pDisciplina);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (ano != null && disciplina == null && professor != null) {
            Predicate p1 = cb.equal(rt.get("idTurma").get("id"), pDisciplina);
            Predicate p2 = cb.equal(rt.get("idProfessor").get("id"), pProfessor);
            cq.where(cb.and(p1, p2));
        }else if (ano == null && disciplina != null && professor != null) {
            Predicate p1 = cb.equal(rt.get("idProfessor").get("id"), pProfessor);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (ano != null && disciplina == null && professor == null) {
             cq.where(cb.equal(rt.get("idTurma").get("id"), pAno));            
        }else if (disciplina != null && ano == null && professor == null) {
             cq.where(cb.equal(rt.get("idDisciplina").get("id"), pDisciplina));
        }else if (professor != null && ano == null && disciplina == null) {
             cq.where(cb.equal(rt.get("idProfessor").get("id"), pProfessor));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        
        if (ano != null && disciplina != null && professor != null) {
            q.setParameter(pAno, ano);
            q.setParameter(pDisciplina, disciplina);
            q.setParameter(pProfessor, professor);
        }else if (ano != null && disciplina != null && professor == null) {
            q.setParameter(pAno, ano);
            q.setParameter(pDisciplina, disciplina);
        }else if (ano != null && disciplina == null && professor != null) {
            q.setParameter(pAno, ano);
            q.setParameter(pProfessor, professor);
        }else if (ano == null && disciplina != null && professor != null) {
            q.setParameter(pDisciplina, disciplina);
            q.setParameter(pProfessor, professor);
        }else if (ano != null && disciplina == null && professor == null) {
            q.setParameter(pAno, ano);
        }else if (disciplina != null && ano == null && professor == null) {
            q.setParameter(pDisciplina, disciplina);
        }else if (professor != null && ano == null && disciplina == null) {
            q.setParameter(pProfessor, professor);
        }
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
