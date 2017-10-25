/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mscas
 */
@Entity
@Table(name = "aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a")
    , @NamedQuery(name = "Aluno.findById", query = "SELECT a FROM Aluno a WHERE a.id = :id")
    , @NamedQuery(name = "Aluno.findByNome", query = "SELECT a FROM Aluno a WHERE a.nome = :nome")
    , @NamedQuery(name = "Aluno.findByDtNascimento", query = "SELECT a FROM Aluno a WHERE a.dtNascimento = :dtNascimento")
    , @NamedQuery(name = "Aluno.findByLaudado", query = "SELECT a FROM Aluno a WHERE a.laudado = :laudado")
    , @NamedQuery(name = "Aluno.findBySituacao", query = "SELECT a FROM Aluno a WHERE a.situacao = :situacao")})
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "ALUNO_ID_SEQ_GEN", sequenceName = "ALUNO_ID_SEQ", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALUNO_ID_SEQ_GEN")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dtNascimento;
    @Column(name = "laudado")
    private Boolean laudado;
    @Column(name = "situacao")
    private Character situacao;
    @JoinColumn(name = "id_ano", referencedColumnName = "id")
    @ManyToOne
    private Ano idAno;
    @OneToMany(mappedBy = "idAluno")
    private Collection<Matricula> matriculaCollection;

    public Aluno() {
    }

    public Aluno(Long id) {
        this.id = id;
    }

    /*public Aluno(Long id, String nome, Date dtNascimento) {
        this.id = id;
        this.nome = nome;
        this.dtNascimento = dtNascimento;
    }*/
    
    public Aluno(String nome, Date dtNascimento) {
        this.nome = nome;
        this.dtNascimento = dtNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Boolean getLaudado() {
        return laudado;
    }

    public String getLaudadoString() {
        if(laudado)
            return "Sim";
        else
            return "Não";
    }
    
    public void setLaudado(Boolean laudado) {
        this.laudado = laudado;
    }

    public String getSituacaoString() {
        switch (situacao){
            case 'T': return "Transferido";
            case 'E': return "Evadido";
        }
        return "Ativo";
    }
    
    public Character getSituacao() {
        return situacao;
    }

    public void setSituacao(Character situacao) {
        this.situacao = situacao;
    }

    public Ano getIdAno() {
        return idAno;
    }
    
    public String getIdAnoString() {
        return idAno.getAno() + "º ano";
    }

    public void setIdAno(Ano idAno) {
        this.idAno = idAno;
    }

    @XmlTransient
    public Collection<Matricula> getMatriculaCollection() {
        return matriculaCollection;
    }

    public void setMatriculaCollection(Collection<Matricula> matriculaCollection) {
        this.matriculaCollection = matriculaCollection;
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
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
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
