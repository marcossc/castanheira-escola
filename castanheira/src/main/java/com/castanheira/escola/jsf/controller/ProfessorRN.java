/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jsf.controller;

import com.castanheira.escola.jpa.entities.Disciplina;
import com.castanheira.escola.jpa.entities.Professor;
import com.castanheira.escola.jpa.entities.ProfessorDiscTur;
import com.castanheira.escola.jpa.entities.Turma;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class ProfessorRN {
    
    public List<Integer> obterListTurmasProfessor(Professor professor){
        List<Integer> listaIdTurma = new ArrayList();
        for (ProfessorDiscTur disciplinaTurma : professor.getProfessorDiscTurCollection()) {
            listaIdTurma.add(disciplinaTurma.getIdTurma().getId());
        }
        return listaIdTurma;
    }
    
    public List<Turma> obterTurmasProfessor(Professor professor){
        List<Turma> listaTurmaProfessor = new ArrayList();
        for (ProfessorDiscTur disciplinaTurma : professor.getProfessorDiscTurCollection()) {
            listaTurmaProfessor.add(disciplinaTurma.getIdTurma());
        }
        return listaTurmaProfessor;
    }
    
    public List<Disciplina> obterDisciplinasProfessor(Professor professor){
        List<Disciplina> listaDisciplinaProfessor = new ArrayList();
        for (ProfessorDiscTur disciplinaTurma : professor.getProfessorDiscTurCollection()) {
            listaDisciplinaProfessor.add(disciplinaTurma.getIdDisciplina());
        }
        return listaDisciplinaProfessor;        
    }
}
