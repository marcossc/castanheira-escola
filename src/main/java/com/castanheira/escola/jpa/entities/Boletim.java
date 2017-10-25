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
@Table(name = "boletim")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boletim.findAll", query = "SELECT b FROM Boletim b")
    , @NamedQuery(name = "Boletim.findById", query = "SELECT b FROM Boletim b WHERE b.id = :id")
    , @NamedQuery(name = "Boletim.findByNota1", query = "SELECT b FROM Boletim b WHERE b.nota1 = :nota1")
    , @NamedQuery(name = "Boletim.findByFalta1", query = "SELECT b FROM Boletim b WHERE b.falta1 = :falta1")
    , @NamedQuery(name = "Boletim.findByNota2", query = "SELECT b FROM Boletim b WHERE b.nota2 = :nota2")
    , @NamedQuery(name = "Boletim.findByFalta2", query = "SELECT b FROM Boletim b WHERE b.falta2 = :falta2")
    , @NamedQuery(name = "Boletim.findByNota3", query = "SELECT b FROM Boletim b WHERE b.nota3 = :nota3")
    , @NamedQuery(name = "Boletim.findByFalta3", query = "SELECT b FROM Boletim b WHERE b.falta3 = :falta3")
    , @NamedQuery(name = "Boletim.findByMedia", query = "SELECT b FROM Boletim b WHERE b.media = :media")
    , @NamedQuery(name = "Boletim.findByTotalFalta", query = "SELECT b FROM Boletim b WHERE b.totalFalta = :totalFalta")})
public class Boletim implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "BOLETIM_ID_SEQ_GEN", sequenceName = "BOLETIM_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOLETIM_ID_SEQ_GEN")
    private Long id;
    @Column(name = "nota_1")
    private Short nota1;
    @Column(name = "falta_1")
    private Integer falta1;
    @Column(name = "nota_2")
    private Short nota2;
    @Column(name = "falta_2")
    private Integer falta2;
    @Column(name = "nota_3")
    private Short nota3;
    @Column(name = "falta_3")
    private Integer falta3;
    @Column(name = "media")
    private Short media;
    @Column(name = "total_falta")
    private Integer totalFalta;
    @JoinColumn(name = "id_disciplina", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Disciplina idDisciplina;
    @JoinColumn(name = "id_matricula", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Matricula idMatricula;

    public Boletim() {
    }

    public Boletim(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getNota1() {
        return nota1;
    }

    public void setNota1(Short nota1) {
        this.nota1 = nota1;
    }

    public Integer getFalta1() {
        return falta1;
    }

    public void setFalta1(Integer falta1) {
        this.falta1 = falta1;
    }

    public Short getNota2() {
        return nota2;
    }

    public void setNota2(Short nota2) {
        this.nota2 = nota2;
    }

    public Integer getFalta2() {
        return falta2;
    }

    public void setFalta2(Integer falta2) {
        this.falta2 = falta2;
    }

    public Short getNota3() {
        return nota3;
    }

    public void setNota3(Short nota3) {
        this.nota3 = nota3;
    }

    public Integer getFalta3() {
        return falta3;
    }

    public void setFalta3(Integer falta3) {
        this.falta3 = falta3;
    }

    public Short getMedia() {
        return media;
    }

    public void setMedia(Short media) {
        this.media = media;
    }

    public Integer getTotalFalta() {
        return totalFalta;
    }

    public void setTotalFalta(Integer totalFalta) {
        this.totalFalta = totalFalta;
    }

    public Disciplina getIdDisciplina() {
        return idDisciplina;
    }
    
    public String getNomeDisciplina() {
        return idDisciplina.getNome();
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Matricula getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Matricula idMatricula) {
        this.idMatricula = idMatricula;
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
        if (!(object instanceof Boletim)) {
            return false;
        }
        Boletim other = (Boletim) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.castanheira.escola.jpa.entities.Boletim[ id=" + id + " ]";
    }
    
}
