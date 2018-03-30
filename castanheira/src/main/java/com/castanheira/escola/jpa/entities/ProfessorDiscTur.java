/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mscas
 */
@Entity
@Table(name = "professor_disc_tur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProfessorDiscTur.findAll", query = "SELECT p FROM ProfessorDiscTur p")
    , @NamedQuery(name = "ProfessorDiscTur.findById", query = "SELECT p FROM ProfessorDiscTur p WHERE p.id = :id")})
public class ProfessorDiscTur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "PROFESSOR_DISC_TUR_ID_SEQ_GEN", sequenceName = "PROFESSOR_DISC_TUR_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFESSOR_DISC_TUR_ID_SEQ_GEN")
    private Integer id;
    @JoinColumn(name = "id_disciplina", referencedColumnName = "id")
    @ManyToOne
    private Disciplina idDisciplina;
    @JoinColumn(name = "id_professor", referencedColumnName = "id")
    @ManyToOne
    private Professor idProfessor;
    @JoinColumn(name = "id_turma", referencedColumnName = "id")
    @ManyToOne
    private Turma idTurma;

    public ProfessorDiscTur() {
    }

    public ProfessorDiscTur(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Disciplina getIdDisciplina() {
        return idDisciplina;
    }
    
    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Professor getIdProfessor() {
        return idProfessor;
    }
    
    public void setIdProfessor(Professor idProfessor) {
        this.idProfessor = idProfessor;
    }

    public Turma getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Turma idTurma) {
        this.idTurma = idTurma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfessorDiscTur)) {
            return false;
        }
        ProfessorDiscTur other = (ProfessorDiscTur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.castanheira.escola.jpa.entities.ProfessorDiscTur[ id=" + id + " ]";
    }
    
}
