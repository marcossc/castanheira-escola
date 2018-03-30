package com.castanheira.escola.jsf.controller;

import com.castanheira.escola.jpa.entities.Boletim;
import com.castanheira.escola.jpa.entities.DisciplinaAno;
import com.castanheira.escola.jpa.entities.Matricula;
import com.castanheira.escola.jpa.entities.Professor;
import com.castanheira.escola.jpa.session.BoletimFacade;
import com.castanheira.escola.jpa.session.DisciplinaAnoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class BoletimRN implements Serializable {
    
    @Inject
    private BoletimFacade boletimFacade;
    
    @Inject
    private MatriculaRN matriculaRN;
    
    @Inject ProfessorRN professorRN;
    
    @Inject
    private DisciplinaAnoFacade disciplinaAnoFacade;
    
    public List<Boletim> obterBoletins(int[] range, Integer turma, Integer disciplina, Professor professor){
        List<Boletim> listBoletim = boletimFacade.findRangeFiltro(range, turma, disciplina);
        List<Boletim> listBoletimAux = new ArrayList();
        if (professor != null && professor.getTipo().toString().equals("P")) {
            List<Integer> listIdTurmaProfessor = professorRN.obterListTurmasProfessor(professor);
            for (Boletim boletim : listBoletim) {
                int idTurma = boletim.getIdMatricula().getIdTurma().getId();
                if (listIdTurmaProfessor.contains(idTurma)) {
                    listBoletimAux.add(boletim);
                }
            }
            listBoletim = listBoletimAux;
        }
        return listBoletim;
    }
    
    public Integer obterBoletinsCount(Integer turma, Integer disciplina, Professor professor){
        List<Boletim> listBoletim = boletimFacade.countFiltro(turma, disciplina);
        List<Boletim> listBoletimAux = new ArrayList();
        if (professor != null && professor.getTipo().toString().equals("P")) {
            List<Integer> listIdTurmaProfessor = professorRN.obterListTurmasProfessor(professor);
            for (Boletim boletim : listBoletim) {
                int idTurma = boletim.getIdMatricula().getIdTurma().getId();
                if (listIdTurmaProfessor.contains(idTurma)) {
                    listBoletimAux.add(boletim);
                }
            }
            listBoletim = listBoletimAux;
        }
        return listBoletim.size();
    }
      
    public void salvarNotas(List<Boletim> listBoletim){
        List<Long> listaIdAluno = new ArrayList();
        for (Boletim boletim : listBoletim) {
            //Obtem todos id aluno dos boletins lancados para processar aprovação
            Long idAluno = boletim.getIdMatricula().getIdAluno().getId();
            if (!listaIdAluno.contains(idAluno)) {
                listaIdAluno.add(idAluno);
            }
            //Atualiza a soma das notas e faltas
            int totalNota = boletim.getNota1() + boletim.getNota2() + boletim.getNota3();
            int totalFalta = boletim.getFalta1() + boletim.getFalta2() + boletim.getFalta3();
            boletim.setTotalFalta(totalFalta);
            boletim.setMedia(Short.parseShort(String.valueOf(totalNota)) );
            boletimFacade.edit(boletim);
        }
        //Percorre a lista de alunos e obtem todos os boletins do mesmo e verifica aprovacao
        // Aqui recupera todos os boletins do aluno para o caso de ter sido filtrado por alguma disciplina especifica
        for (Long idAluno : listaIdAluno) {
            verificarAprovacao(boletimFacade.getBoletimAluno(idAluno));
        }
    }
    
    public void verificarAprovacao(List<Boletim> listBoletimAluno) {
        boolean notasLancadas = true;
        boolean aprovado = true;
        for (Boletim boletim : listBoletimAluno) {
        //verifica se a media das notas são superiores a 50
            if (boletim.getNota3() != null && boletim.getFalta3() != null) {
                int totalNota = boletim.getNota1() + boletim.getNota2() + boletim.getNota3();
                int totalFalta = boletim.getFalta1() + boletim.getFalta2() + boletim.getFalta3();
                if (totalNota < 50 && totalFalta/3 < 50 ) {
                    aprovado = false;
                }
            }else {
                notasLancadas = false;
            }       
        }
        //se as notas tiverem sido lancadas atualiza a matricula
        if (notasLancadas) {
            matriculaRN.atualizarStatusMatricula(listBoletimAluno.get(0).getIdMatricula().getId(), aprovado);
        }
    }  
    
    public void criarBoletim(Matricula matricula){
        //obtem o ano da matricula
        int anoMatricula = matricula.getIdTurma().getIdAno().getAno();
        List<DisciplinaAno> listDisciplinaAno = disciplinaAnoFacade.findByAno(anoMatricula);
        for (DisciplinaAno disciplinaAno: listDisciplinaAno) {
            Boletim boletim = new Boletim();
            boletim.setIdDisciplina(disciplinaAno.getIdDisciplina());
            boletim.setIdMatricula(matricula);
            boletim.setNota1(Short.parseShort("0"));
            boletim.setNota2(Short.parseShort("0"));
            boletim.setNota3(Short.parseShort("0"));
            boletim.setFalta1(0);
            boletim.setFalta2(0);
            boletim.setFalta3(0);
            boletim.setMedia(Short.parseShort("0"));
            boletim.setTotalFalta(0);
            boletimFacade.create(boletim);
        }
    }
    
}
