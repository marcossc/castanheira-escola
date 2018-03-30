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
@Table(name = "disciplina_ano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisciplinaAno.findAll", query = "SELECT d FROM DisciplinaAno d")
    , @NamedQuery(name = "DisciplinaAno.findById", query = "SELECT d FROM DisciplinaAno d WHERE d.id = :id")
    , @NamedQuery(name = "DisciplinaAno.findByAno", query = "SELECT d FROM DisciplinaAno d WHERE d.idAno.id = :ano")})
public class DisciplinaAno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "DISCIPLINA_ANO_ID_SEQ_GEN", sequenceName = "DISCIPLINA_ANO_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DISCIPLINA_ANO_ID_SEQ_GEN")
    private Integer id;
    @JoinColumn(name = "id_ano", referencedColumnName = "id")
    @ManyToOne
    private Ano idAno;
    @JoinColumn(name = "id_disciplina", referencedColumnName = "id")
    @ManyToOne
    private Disciplina idDisciplina;

    public DisciplinaAno() {
    }

    public DisciplinaAno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ano getIdAno() {
        return idAno;
    }
    
    public String getIdAnoString() {
        return idAno.toString();
    }

    public void setIdAno(Ano idAno) {
        this.idAno = idAno;
    }

    public Disciplina getIdDisciplina() {
        return idDisciplina;
    }
    
    public String getIdDisciplinaString() {
        return idDisciplina.getNome();
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
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
        if (!(object instanceof DisciplinaAno)) {
            return false;
        }
        DisciplinaAno other = (DisciplinaAno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.castanheira.escola.jpa.entities.DisciplinaAno[ id=" + id + " ]";
    }
    
}
