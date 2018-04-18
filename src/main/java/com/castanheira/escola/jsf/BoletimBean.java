package com.castanheira.escola.jsf;

import com.castanheira.escola.jpa.entities.Aluno;
import com.castanheira.escola.jpa.entities.Boletim;
import com.castanheira.escola.jpa.entities.Disciplina;
import com.castanheira.escola.jpa.entities.Matricula;
import com.castanheira.escola.jpa.entities.Professor;
import com.castanheira.escola.jpa.entities.Turma;
import com.castanheira.escola.jpa.session.AlunoFacade;
import com.castanheira.escola.jpa.session.MatriculaFacade;
import com.castanheira.escola.jsf.util.JsfUtil;
import com.castanheira.escola.jsf.util.PaginationHelper;
import com.castanheira.escola.jsf.controller.BoletimRN;
import com.castanheira.escola.jsf.controller.ProfessorRN;
import com.castanheira.escola.jsf.util.SessionUtil;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Named("boletimBean")
@SessionScoped
public class BoletimBean implements Serializable {

    private Boletim current;
    private DataModel items = null;
    @EJB
    private com.castanheira.escola.jpa.session.BoletimFacade ejbFacade;
    @EJB
    private com.castanheira.escola.jpa.session.TurmaFacade ejbTurmaFacade;
    @EJB
    private com.castanheira.escola.jpa.session.DisciplinaFacade ejbDisciplinaFacade;
    @EJB 
    private BoletimRN ejbBoletimRN;
    @EJB
    private ProfessorRN ejbProfessorController;
    @EJB
    private AlunoFacade ejbAlunoFacade;
    @EJB
    private MatriculaFacade ejbMatriculaFacade;    
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Turma> listaTurmas;
    private List<Disciplina> listaDisciplina;
    private List<Aluno> listaAluno;
    private Integer idTurmaFiltro;
    private Integer idDisciplinaFiltro;
    private Long idAlunoFiltro;
    private Turma turmaGerarBoletim;
    private Professor usuarioLogado;
    
    public BoletimBean() {
    }
    
    public void configuracoesTelaPesquisa() {
        usuarioLogado = getUsuarioLogado();
        carregaComboTurmas();
        carregaComboDisciplinas();
        idTurmaFiltro = null;
        idDisciplinaFiltro = null;
        items = null;
    }
    
    public void configuracoesTelaGerarBoletim() {
        usuarioLogado = getUsuarioLogado();
        carregaComboTurmas();
        turmaGerarBoletim = null;
        listaAluno = new ArrayList();
        idAlunoFiltro = null;
    }
    
    public void carregaComboTurmas(){
        if (usuarioLogado != null && usuarioLogado.getTipo().toString().equals("P")) {
            listaTurmas = ejbProfessorController.obterTurmasProfessor(usuarioLogado);
        }else {
            listaTurmas = ejbTurmaFacade.findAll();
        }
    }
    
    public void carregaComboDisciplinas(){
        if (usuarioLogado != null && usuarioLogado.getTipo().toString().equals("P")) {
            listaDisciplina = ejbProfessorController.obterDisciplinasProfessor(usuarioLogado);
        }else {
            listaDisciplina = ejbDisciplinaFacade.findAll();
        }
    }

    public Boletim getSelected() {
        if (current == null) {
            current = new Boletim();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public void getAlunosTurma(){
        listaAluno = new ArrayList();
        if (turmaGerarBoletim != null) {
            listaAluno = ejbAlunoFacade.findAlunoTurma(turmaGerarBoletim.getId());
        }
    }
    
    public void gerarBoletim(){
        if (turmaGerarBoletim != null){
            HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
            OutputStream out = null; 
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String pathToReportPackage = servletContext.getRealPath("resources/relatorio");
                JRPdfExporter PdfExporter = new JRPdfExporter ();
                List jasperPrintList = new ArrayList ();
                String jasperFile = pathToReportPackage + "\\Boletim.jasper";
                for (Aluno aluno : listaAluno) {

                    /*if (idAlunoFiltro != null && 
                            idAlunoFiltro != aluno.getId()) {
                        continue;
                    }*/
                    
                    if (idAlunoFiltro != null && 
                            (!idAlunoFiltro.equals(aluno.getId()))) {
                        continue;
                    }

                    //Matricula matriculaAluno = ejbMatriculaFacade.findMatriculaAlunoTurma(aluno.getId(), turmaGerarBoletim.getId());
                    List<Boletim> listBoletim = ejbFacade.getBoletimAluno(aluno.getId());

                    if (listBoletim.size() > 0) {
                        String statusAprovacao = ejbBoletimRN.verificarAprovacaoRelatorio(listBoletim);
                        for (Boletim boletim : listBoletim) {
                            boletim.getIdMatricula().setStatusAprovacao(statusAprovacao);
                        }
                        JasperPrint print = JasperFillManager.fillReport(jasperFile, null, new JRBeanCollectionDataSource(listBoletim));
                        jasperPrintList.add (print);
                    }
                }

                PdfExporter.setParameter (JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
                PdfExporter.setParameter (JRExporterParameter.OUTPUT_STREAM, stream);
                PdfExporter.exportReport ();
                byte[] bytes = stream.toByteArray();

                response.setContentType("application/pdf");
         
                response.setHeader("Pragma","");
                response.setHeader("Cache-Control","");
                response.setHeader("Expires",""); 

                out = response.getOutputStream();  
                out.write(bytes);
                response.setContentLength(bytes.length);
                out.flush();	
                out.close();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("GenerateBoletimSuccess"));    
            }catch(JRException e) {
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("GenerateBoletimErrorOccured"));
            }catch(Exception e){
                JsfUtil.addErrorMessage(e, e.getMessage());
            }finally {
                try {
                out.close();
                stream.close();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Bundle").getString("GenerateBoletimErrorSelectTurma"));
        }
    }
    

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(20) {

                @Override
                public int getItemsCount() {
                    return ejbBoletimRN.obterBoletinsCount(idTurmaFiltro, idDisciplinaFiltro, getUsuarioLogado());
                }

                @Override
                public DataModel createPageDataModel() {
                    List<Boletim> listaBoletim = ejbBoletimRN.obterBoletins(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},
                        idTurmaFiltro, idDisciplinaFiltro, getUsuarioLogado());
                    return new ListDataModel(listaBoletim);
                }
            };
        }
        return pagination;
    }
    
    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Boletim) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Boletim();
        selectedItemIndex = -1;
        return "digitar_notas";
    }

    public String create() {
        try {
            ejbFacade.create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("BoletimCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Boletim) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            ejbFacade.edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("BoletimUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Boletim) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            ejbFacade.remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("BoletimDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = ejbFacade.count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = ejbFacade.findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }
    
    public boolean isDisciplinaProfessor(Disciplina disciplina) {
        boolean isDisciplaProfessor = false;
        if (usuarioLogado != null && usuarioLogado.getTipo().toString().equals("P")) {
            if (listaDisciplina.contains(disciplina)) {
                isDisciplaProfessor = true;    
            }
        }else {
            isDisciplaProfessor = true;
        }
        return isDisciplaProfessor;
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    
    public void getItemsFiltro() {
        items = getPagination().createPageDataModel();
    }
    
    public String geraXML() {
        ejbFacade.GeraXMLBoletim();
        return "List";
    }
    
    public String salvarNotas(){
        List<Boletim> listBoletim = new ArrayList<Boletim>();
        for (Object item : items) {
            listBoletim.add((Boletim)item);
        }
        ejbBoletimRN.salvarNotas(listBoletim);
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("BoletimUpdated"));
        return prepareCreate();
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "digitar_notas";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "digitar_notas";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Boletim getBoletim(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    public Integer getIdTurmaFiltro() {
        return idTurmaFiltro;
    }

    public void setIdTurmaFiltro(Integer idTurmaFiltro) {
        this.idTurmaFiltro = idTurmaFiltro;
    }

    public Integer getIdDisciplinaFiltro() {
        return idDisciplinaFiltro;
    }

    public void setIdDisciplinaFiltro(Integer idDisciplinaFiltro) {
        this.idDisciplinaFiltro = idDisciplinaFiltro;
    }

    public List<Turma> getListaTurmas() {
        return listaTurmas;
    }

    public List<Disciplina> getListaDisciplina() {
        return listaDisciplina;
    }

    public List<Aluno> getListaAluno() {
        return listaAluno;
    }

    public void setListaAluno(List<Aluno> listaAluno) {
        this.listaAluno = listaAluno;
    }

    public Long getIdAlunoFiltro() {
        return idAlunoFiltro;
    }

    public void setIdAlunoFiltro(Long idAlunoFiltro) {
        this.idAlunoFiltro = idAlunoFiltro;
    }

    public Turma getTurmaGerarBoletim() {
        return turmaGerarBoletim;
    }

    public void setTurmaGerarBoletim(Turma turmaGerarBoletim) {
        this.turmaGerarBoletim = turmaGerarBoletim;
    }
    

    
    public Professor getUsuarioLogado(){
        return (Professor) SessionUtil.getParam("usuario");
    }

    @FacesConverter(forClass = Boletim.class)
    public static class BoletimBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BoletimBean controller = (BoletimBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "boletimBean");
            return controller.getBoletim(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Boletim) {
                Boletim o = (Boletim) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Boletim.class.getName());
            }
        }

    }

}
