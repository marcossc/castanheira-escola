/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.castanheira.escola.jpa.session;

import com.castanheira.escola.jpa.entities.Boletim;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.File;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mscas
 */
@Stateless
public class BoletimFacade extends AbstractFacade<Boletim> {

    @PersistenceContext(unitName = "com.castanheira_Escola_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BoletimFacade() {
        super(Boletim.class);
    }
    
    public List<Boletim> findRangeFiltro(int[] range, Integer turma, Integer disciplina) {        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Boletim> rt = cq.from(Boletim.class);
        cq.select(rt);
        ParameterExpression<Integer> pTurma = cb.parameter(Integer.class);
        ParameterExpression<Integer> pDisciplina = cb.parameter(Integer.class);
        if (turma != null && disciplina != null) {
            Predicate p1 = cb.equal(rt.get("idMatricula").get("idTurma").get("id"), pTurma);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (turma != null && disciplina == null) {
             cq.where(cb.equal(rt.get("idMatricula").get("idTurma").get("id"), pTurma));            
        }else if (disciplina != null && turma == null) {
             cq.where(cb.equal(rt.get("idDisciplina").get("id"), pDisciplina));
        }
        cq.orderBy(cb.asc(rt.get("idMatricula").get("idAluno").get("nome")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        if (turma != null && disciplina != null) {
            q.setParameter(pTurma, turma);
            q.setParameter(pDisciplina, disciplina);
        }else if (turma != null && disciplina == null) {
             q.setParameter(pTurma, turma);
        }else if (disciplina != null && turma == null) {
             q.setParameter(pDisciplina, disciplina);
        }
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);

        return q.getResultList();
    }
    
    public List<Boletim> countFiltro(Integer turma, Integer disciplina) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        Root<Boletim> rt = cq.from(Boletim.class);
        cq.select(rt);
        ParameterExpression<Integer> pTurma = cb.parameter(Integer.class);
        ParameterExpression<Integer> pDisciplina = cb.parameter(Integer.class);
        if (turma != null && disciplina != null) {
            Predicate p1 = cb.equal(rt.get("idMatricula").get("idTurma").get("id"), pTurma);
            Predicate p2 = cb.equal(rt.get("idDisciplina").get("id"), pDisciplina);
            cq.where(cb.and(p1, p2));
        }else if (turma != null && disciplina == null) {
             cq.where(cb.equal(rt.get("idMatricula").get("idTurma").get("id"), pTurma));            
        }else if (disciplina != null && turma == null) {
             cq.where(cb.equal(rt.get("idDisciplina").get("id"), pDisciplina));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        if (turma != null && disciplina != null) {
            q.setParameter(pTurma, turma);
            q.setParameter(pDisciplina, disciplina);
        }else if (turma != null && disciplina == null) {
             q.setParameter(pTurma, turma);
        }else if (disciplina != null && turma == null) {
             q.setParameter(pDisciplina, disciplina);
        }
        return q.getResultList();
    }
    
    public List<Boletim> getBoletimAluno(Long idAluno) {
        return em.createNamedQuery("Boletim.findByIdAluno").setParameter("idAluno", idAluno).getResultList();
    }

    public void GeraXMLBoletim() {
        List<Boletim> lista = findAll();
        for (Boletim i : lista) {
            if (!String.valueOf(i.getIdMatricula().getId()).equals("1")) {
                lista.remove(i);
            }
        }
        try {
            if (lista.size() > 0) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("boletim");
                doc.appendChild(rootElement);

                // staff elements
                Element aluno = doc.createElement("aluno");
                rootElement.appendChild(aluno);

                Element nome = doc.createElement("nome");
                nome.appendChild(doc.createTextNode(lista.get(0).getIdMatricula().getIdAluno().getNome()));
                aluno.appendChild(nome);

                Element turma = doc.createElement("turma");
                turma.appendChild(doc.createTextNode(lista.get(0).getIdMatricula().getIdTurma().getNome()));
                aluno.appendChild(turma);

                Element ano = doc.createElement("ano");
                ano.appendChild(doc.createTextNode(lista.get(0).getIdMatricula().getIdTurma().getIdAno().getAnoString()));
                aluno.appendChild(ano);

                Element aprovado = doc.createElement("aprovado");
                String aprovadoString;
                if(lista.get(0).getIdMatricula().getAprovado())
                    aprovadoString = "Sim";
                else 
                    aprovadoString = "Não";
                aprovado.appendChild(doc.createTextNode(aprovadoString));
                aluno.appendChild(aprovado);

                Element notas = doc.createElement("notas");
                rootElement.appendChild(notas);

                for (Boletim i : lista) {
                    Element notasDisciplina = doc.createElement("notasdisciplina");
                    notas.appendChild(notasDisciplina);

                    Element disciplina = doc.createElement("disciplina");
                    disciplina.appendChild(doc.createTextNode(i.getIdDisciplina().getNome()));
                    notasDisciplina.appendChild(disciplina);

                    Element nota1 = doc.createElement("nota1");
                    nota1.appendChild(doc.createTextNode(String.valueOf(i.getNota1())));
                    notasDisciplina.appendChild(nota1);

                    Element nota2 = doc.createElement("nota2");
                    nota2.appendChild(doc.createTextNode(String.valueOf(i.getNota2())));
                    notasDisciplina.appendChild(nota2);

                    Element nota3 = doc.createElement("nota3");
                    nota3.appendChild(doc.createTextNode(String.valueOf(i.getNota3())));
                    notasDisciplina.appendChild(nota3);

                    Element falta1 = doc.createElement("falta1");
                    falta1.appendChild(doc.createTextNode(String.valueOf(i.getFalta1())));
                    notasDisciplina.appendChild(falta1);

                    Element falta2 = doc.createElement("falta2");
                    falta2.appendChild(doc.createTextNode(String.valueOf(i.getFalta2())));
                    notasDisciplina.appendChild(falta2);

                    Element falta3 = doc.createElement("falta3");
                    falta3.appendChild(doc.createTextNode(String.valueOf(i.getFalta3())));
                    notasDisciplina.appendChild(falta3);

                    Element faltas = doc.createElement("faltas");
                    faltas.appendChild(doc.createTextNode(String.valueOf(i.getTotalFalta())));
                    notasDisciplina.appendChild(faltas);

                    Element media = doc.createElement("media");
                    media.appendChild(doc.createTextNode(String.valueOf(i.getMedia())));
                    notasDisciplina.appendChild(media);

                }

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("D:\\file.xml"));

                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);
                transformer.transform(source, result);

                System.out.println("File saved!");
            }
        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }

    }
}
