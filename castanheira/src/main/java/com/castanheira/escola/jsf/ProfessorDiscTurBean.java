package com.castanheira.escola.jsf;

import com.castanheira.escola.jpa.entities.Disciplina;
import com.castanheira.escola.jpa.entities.Professor;
import com.castanheira.escola.jpa.entities.ProfessorDiscTur;
import com.castanheira.escola.jpa.entities.Turma;
import com.castanheira.escola.jsf.util.JsfUtil;
import com.castanheira.escola.jsf.util.PaginationHelper;

import java.io.Serializable;
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

@Named("professorDiscTurBean")
@SessionScoped
public class ProfessorDiscTurBean implements Serializable {

    @EJB
    private com.castanheira.escola.jpa.session.ProfessorDiscTurFacade ejbFacade;
    @EJB
    private com.castanheira.escola.jpa.session.TurmaFacade ejbTurmaFacade;
    @EJB
    private com.castanheira.escola.jpa.session.DisciplinaFacade ejbDisciplinaFacade;
    @EJB
    private com.castanheira.escola.jpa.session.ProfessorFacade ejbProfessorFacade;
    private ProfessorDiscTur current;
    private DataModel items = null;   
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Turma> listaTurmas;
    private List<Disciplina> listaDisciplina;
    private List<Professor> listaProfessor;
    private Integer idTurmaFiltro;
    private Integer idDisciplinaFiltro;
    private Integer idProfessorFiltro;
    

    public ProfessorDiscTurBean() {
    }
    
    public void configuracoesTelaPesquisa(){
        listaTurmas = ejbTurmaFacade.findAll();
        listaDisciplina = ejbDisciplinaFacade.findAll();
        listaProfessor = ejbProfessorFacade.findAll();
        idTurmaFiltro = null;
        idDisciplinaFiltro = null;
        idProfessorFiltro = null;
        items = null;
    }   

    public ProfessorDiscTur getSelected() {
        if (current == null) {
            current = new ProfessorDiscTur();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return ejbFacade.countFiltro(idTurmaFiltro, idDisciplinaFiltro, idProfessorFiltro);
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(ejbFacade.findRangeFiltro(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, 
                            idTurmaFiltro, idDisciplinaFiltro, idProfessorFiltro));
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
        current = (ProfessorDiscTur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ProfessorDiscTur();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            ejbFacade.create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ProfessorDiscTurCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ProfessorDiscTur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            ejbFacade.edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ProfessorDiscTurUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ProfessorDiscTur) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("ProfessorDiscTurDeleted"));
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

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public void getItemsFiltro(){
        items = getPagination().createPageDataModel();
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
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ProfessorDiscTur getProfessorDiscTur(java.lang.Integer id) {
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

    public Integer getIdProfessorFiltro() {
        return idProfessorFiltro;
    }

    public void setIdProfessorFiltro(Integer idProfessorFiltro) {
        this.idProfessorFiltro = idProfessorFiltro;
    }

    public List<Turma> getListaTurmas() {
        return listaTurmas;
    }

    public List<Disciplina> getListaDisciplina() {
        return listaDisciplina;
    }

    public List<Professor> getListaProfessor() {
        return listaProfessor;
    }
    
    @FacesConverter(forClass = ProfessorDiscTur.class)
    public static class ProfessorDiscTurBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProfessorDiscTurBean controller = (ProfessorDiscTurBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "professorDiscTurBean");
            return controller.getProfessorDiscTur(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProfessorDiscTur) {
                ProfessorDiscTur o = (ProfessorDiscTur) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ProfessorDiscTur.class.getName());
            }
        }

    }

}
