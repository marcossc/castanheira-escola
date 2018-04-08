/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.Aluno;
import com.castanheira.escola.jpa.entities.Matricula;
import com.castanheira.escola.jpa.entities.Turma;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author mscas
 */
@Stateless
public class MatriculaFacade extends AbstractFacade<Matricula> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatriculaFacade() {
        super(Matricula.class);
    }
    
    public Matricula createMatricula(Matricula entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }
    
    public Matricula findMatriculaAlunoTurma(long idAluno, int idTurma) {
        return (Matricula)em.createNamedQuery("Matricula.findByIdAlunoTurma")
                .setParameter("idAluno", idAluno)
                .setParameter("idTurma", idTurma)
                .getSingleResult();
    }
    
    public boolean findMatriculaAluno(long idAluno) {
        List<Matricula> listMatricula = em.createNamedQuery("Matricula.findByIdAluno")
                .setParameter("idAluno", idAluno)
                .getResultList();
        boolean existeMatricula = false;
        if (listMatricula != null && listMatricula.size() > 0) {
            existeMatricula = true;
        }
        return existeMatricula;
    }    
    
    public List<Matricula> findRangeFiltro(int[] range) {        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Matricula> rt = cq.from(Matricula.class);
        cq.select(rt);
        cq.orderBy(cb.asc(rt.get("idAluno").get("nome")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);       
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    public int countFiltro() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<Matricula> rt = cq.from(Matricula.class);
        cq.select(cb.count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
