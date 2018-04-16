package com.castanheira.escola.jsf;

import com.castanheira.escola.jpa.entities.Ano;
import com.castanheira.escola.jpa.entities.Disciplina;
import com.castanheira.escola.jpa.entities.DisciplinaAno;
import com.castanheira.escola.jpa.entities.Turma;
import com.castanheira.escola.jsf.util.JsfUtil;
import com.castanheira.escola.jsf.util.PaginationHelper;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("disciplinaAnoBean")
@SessionScoped
public class DisciplinaAnoBean implements Serializable {

    private DisciplinaAno current;
    private DataModel items = null;
    private List<Ano> listaAno;
    private List<Disciplina> listaDisciplina;
    private Integer idAnoFiltro;
    private Integer idDisciplinaFiltro;
  
    @EJB
    private com.castanheira.escola.jpa.session.DisciplinaAnoFacade ejbFacade;
    @EJB
    private com.castanheira.escola.jpa.session.TurmaFacade ejbTurmaFacade;
    @EJB
    private com.castanheira.escola.jpa.session.DisciplinaFacade ejbDisciplinaFacade;
    @EJB
    private com.castanheira.escola.jpa.session.AnoFacade ejbanoFacade;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DisciplinaAnoBean() {
    }

    public DisciplinaAno getSelected() {
        if (current == null) {
            current = new DisciplinaAno();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void configuracoesTelaPesquisa(){
        listaAno = ejbanoFacade.findAll();
        listaDisciplina = ejbDisciplinaFacade.findAll();
        idAnoFiltro = null;
        idDisciplinaFiltro = null;
        items = null;
    }   

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
            
                @Override
                public int getItemsCount() {
                    return ejbFacade.countFiltro(idAnoFiltro, idDisciplinaFiltro);
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(ejbFacade.findRangeFiltro(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, idAnoFiltro, idDisciplinaFiltro));
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
        current = (DisciplinaAno) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new DisciplinaAno();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            ejbFacade.create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("DisciplinaAnoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (DisciplinaAno) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            ejbFacade.edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("DisciplinaAnoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (DisciplinaAno) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("DisciplinaAnoDeleted"));
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

    public DisciplinaAno getDisciplinaAno(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public List<Ano> getListaAno() {
        return listaAno;
    }

    public void setListaAno(List<Ano> listaAno) {
        this.listaAno = listaAno;
    }

    public List<Disciplina> getListaDisciplina() {
        return listaDisciplina;
    }

    public void setListaDisciplina(List<Disciplina> listaDisciplina) {
        this.listaDisciplina = listaDisciplina;
    }

    public Integer getIdAnoFiltro() {
        return idAnoFiltro;
    }

    public void setIdAnoFiltro(Integer idAnoFiltro) {
        this.idAnoFiltro = idAnoFiltro;
    }

    public Integer getIdDisciplinaFiltro() {
        return idDisciplinaFiltro;
    }

    public void setIdDisciplinaFiltro(Integer idDisciplinaFiltro) {
        this.idDisciplinaFiltro = idDisciplinaFiltro;
    }

    @FacesConverter(forClass = DisciplinaAno.class)
    public static class DisciplinaAnoBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DisciplinaAnoBean controller = (DisciplinaAnoBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "disciplinaAnoBean");
            return controller.getDisciplinaAno(getKey(value));
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
            if (object instanceof DisciplinaAno) {
                DisciplinaAno o = (DisciplinaAno) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DisciplinaAno.class.getName());
            }
        }

    }

}
