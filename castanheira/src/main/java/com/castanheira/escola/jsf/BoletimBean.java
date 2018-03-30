package com.castanheira.escola.jsf;

import com.castanheira.escola.jpa.entities.Boletim;
import com.castanheira.escola.jpa.entities.Disciplina;
import com.castanheira.escola.jpa.entities.Professor;
import com.castanheira.escola.jpa.entities.Turma;
import com.castanheira.escola.jsf.util.JsfUtil;
import com.castanheira.escola.jsf.util.PaginationHelper;
import com.castanheira.escola.jsf.controller.BoletimRN;
import com.castanheira.escola.jsf.controller.ProfessorRN;
import com.castanheira.escola.jsf.util.SessionUtil;

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
    
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Turma> listaTurmas;
    private List<Disciplina> listaDisciplina;
    private Integer idTurmaFiltro;
    private Integer idDisciplinaFiltro;
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

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

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
        return "mockup_notas";
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
        return "mockup_notas";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "mockup_notas";
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
