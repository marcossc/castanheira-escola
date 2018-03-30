/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mscas
 */
@Entity
@Table(name = "ano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ano.findAll", query = "SELECT a FROM Ano a")
    , @NamedQuery(name = "Ano.findById", query = "SELECT a FROM Ano a WHERE a.id = :id")
    , @NamedQuery(name = "Ano.findByAno", query = "SELECT a FROM Ano a WHERE a.ano = :ano")})
public class Ano implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "ANO_ID_SEQ_GEN", sequenceName = "ANO_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANO_ID_SEQ_GEN")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ano")
    private int ano;
    @OneToMany(mappedBy = "idAno")
    private Collection<Aluno> alunoCollection;
    @OneToMany(mappedBy = "idAno")
    private Collection<DisciplinaAno> disciplinaAnoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAno")
    private Collection<Turma> turmaCollection;

    public Ano() {
    }

    public Ano(Integer id) {
        this.id = id;
    }

    public Ano(Integer id, int ano) {
        this.id = id;
        this.ano = ano;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }
    
    public String getAnoString() {
        return ano + "º ano";
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @XmlTransient
    public Collection<Aluno> getAlunoCollection() {
        return alunoCollection;
    }

    public void setAlunoCollection(Collection<Aluno> alunoCollection) {
        this.alunoCollection = alunoCollection;
    }

    @XmlTransient
    public Collection<DisciplinaAno> getDisciplinaAnoCollection() {
        return disciplinaAnoCollection;
    }

    public void setDisciplinaAnoCollection(Collection<DisciplinaAno> disciplinaAnoCollection) {
        this.disciplinaAnoCollection = disciplinaAnoCollection;
    }

    @XmlTransient
    public Collection<Turma> getTurmaCollection() {
        return turmaCollection;
    }

    public void setTurmaCollection(Collection<Turma> turmaCollection) {
        this.turmaCollection = turmaCollection;
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
        if (!(object instanceof Ano)) {
            return false;
        }
        Ano other = (Ano) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ano + "º ano";
    }
    
}
