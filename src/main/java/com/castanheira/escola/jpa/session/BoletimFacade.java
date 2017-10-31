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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
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
