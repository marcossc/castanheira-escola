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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mscas
 */
@Entity
@Table(name = "disciplina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disciplina.findAll", query = "SELECT d FROM Disciplina d")
    , @NamedQuery(name = "Disciplina.findById", query = "SELECT d FROM Disciplina d WHERE d.id = :id")
    , @NamedQuery(name = "Disciplina.findByNome", query = "SELECT d FROM Disciplina d WHERE d.nome = :nome")})
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "DISCIPLINA_ID_SEQ_GEN", sequenceName = "DISCIPLINA_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCIPLINA_ID_SEQ_GEN")
    private Integer id;
    @Size(max = 30)
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "idDisciplina")
    private Collection<ProfessorDiscTur> professorDiscTurCollection;
    @OneToMany(mappedBy = "idDisciplina")
    private Collection<DisciplinaAno> disciplinaAnoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDisciplina")
    private Collection<Boletim> boletimCollection;

    public Disciplina() {
    }

    public Disciplina(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<ProfessorDiscTur> getProfessorDiscTurCollection() {
        return professorDiscTurCollection;
    }

    public void setProfessorDiscTurCollection(Collection<ProfessorDiscTur> professorDiscTurCollection) {
        this.professorDiscTurCollection = professorDiscTurCollection;
    }

    @XmlTransient
    public Collection<DisciplinaAno> getDisciplinaAnoCollection() {
        return disciplinaAnoCollection;
    }

    public void setDisciplinaAnoCollection(Collection<DisciplinaAno> disciplinaAnoCollection) {
        this.disciplinaAnoCollection = disciplinaAnoCollection;
    }

    @XmlTransient
    public Collection<Boletim> getBoletimCollection() {
        return boletimCollection;
    }

    public void setBoletimCollection(Collection<Boletim> boletimCollection) {
        this.boletimCollection = boletimCollection;
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
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
