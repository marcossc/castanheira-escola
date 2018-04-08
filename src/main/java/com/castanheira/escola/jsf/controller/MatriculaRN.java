package com.castanheira.escola.jsf.controller;

import com.castanheira.escola.jpa.entities.Matricula;
import com.castanheira.escola.jpa.session.MatriculaFacade;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MatriculaRN implements Serializable {
    
    @Inject
    private MatriculaFacade matriculaFacade;
    /*@Inject
    private BoletimRN boletimRN;
    
    public void salvarMatriculaAluno(Matricula matricula){
        matricula = matriculaFacade.createMatricula(matricula);
        boletimRN.criarBoletim(matricula);
    }
    */
    public void atualizarStatusMatricula(Long id, boolean status) {
        Matricula matricula = matriculaFacade.find(id);
        matricula.setAprovado(status);
        matriculaFacade.edit(matricula);
    }

    public static MatriculaRN getInstance() {
        return new MatriculaRN();
    }
    
}
