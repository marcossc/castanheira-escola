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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "matricula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matricula.findAll", query = "SELECT m FROM Matricula m")
    , @NamedQuery(name = "Matricula.findById", query = "SELECT m FROM Matricula m WHERE m.id = :id")
    , @NamedQuery(name = "Matricula.findByAprovado", query = "SELECT m FROM Matricula m WHERE m.aprovado = :aprovado")})
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "MATRICULA_ID_SEQ_GEN", sequenceName = "MATRICULA_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MATRICULA_ID_SEQ_GEN")
    private Long id;
    @Column(name = "aprovado")
    private Boolean aprovado = false;
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    @ManyToOne
    private Aluno idAluno;
    @JoinColumn(name = "id_turma", referencedColumnName = "id")
    @ManyToOne
    private Turma idTurma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMatricula")
    private Collection<Boletim> boletimCollection;

    public Matricula() {
    }

    public Matricula(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAprovado() {
        return aprovado;
    }
    
    public String getAprovadoString() {
        if (aprovado)
            return "Sim";
        else
            return "Não";
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Aluno getIdAluno() {
        return idAluno;
    }
    
    public String getIdAlunoString() {
        return idAluno.getNome();
    }

    public void setIdAluno(Aluno idAluno) {
        this.idAluno = idAluno;
    }

    public Turma getIdTurma() {
        return idTurma;
    }
    
    public String getIdTurmaString() {
        return idTurma.getNome();
    }

    public void setIdTurma(Turma idTurma) {
        this.idTurma = idTurma;
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
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.castanheira.escola.jpa.entities.Matricula[ id=" + id + " ]";
    }
    
}
