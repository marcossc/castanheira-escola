/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.Turma;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mscas
 */
@Stateless
public class TurmaFacade extends AbstractFacade<Turma> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TurmaFacade() {
        super(Turma.class);
    }
    
    public List<Turma> findByAno(int ano) {
        return em.createNamedQuery("Turma.findByAno").setParameter("ano", ano).getResultList();
    }
    
}
