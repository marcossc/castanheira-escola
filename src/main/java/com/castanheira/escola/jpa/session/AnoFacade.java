/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.Ano;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mscas
 */
@Stateless
public class AnoFacade extends AbstractFacade<Ano> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnoFacade() {
        super(Ano.class);
    }
    
}
