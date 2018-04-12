package com.castanheira.escola.jsf;

import com.castanheira.escola.jpa.entities.Aluno;
import com.castanheira.escola.jpa.entities.Boletim;
import com.castanheira.escola.jpa.entities.Matricula;
import com.castanheira.escola.jsf.controller.BoletimRN;
import com.castanheira.escola.jsf.controller.MatriculaRN;
import com.castanheira.escola.jsf.util.JsfUtil;
import com.castanheira.escola.jsf.util.PaginationHelper;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("matriculaBean")
@SessionScoped
public class MatriculaBean implements Serializable {

    private Matricula current;
    private DataModel items = null;
    @EJB
    private com.castanheira.escola.jpa.session.MatriculaFacade ejbFacade;
    @EJB
    private com.castanheira.escola.jpa.session.BoletimFacade ejbBoletimFacade;    
    @EJB
    private com.castanheira.escola.jpa.session.TurmaFacade turmaFacade;
    @EJB
    private com.castanheira.escola.jpa.session.AlunoFacade alunoFacade;
    @EJB
    private BoletimRN ejbBoletimRN;
    
    private SelectItem[] listaTurmas;
    private List<Aluno> listaAluno;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Aluno> listaAlunoSelecao;

    public MatriculaBean() {
    }

    public Matricula getSelected() {
        if (current == null) {
            current = new Matricula();
            selectedItemIndex = -1;
        }
        return current;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return ejbFacade.countFiltro();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(ejbFacade.findRangeFiltro(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List?faces-redirect=true";
    }

    public String prepareView() {
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View?faces-redirect=true";
    }

    public String prepareCreate() {
        current = new Matricula();
        selectedItemIndex = -1;
        listaTurmas = JsfUtil.getSelectItems(turmaFacade.findAll(), true);
        listaAluno = null;
        return "Create";
    }

    public String create() {
        try {
            if (listaAlunoSelecao != null && listaAlunoSelecao.size() > 0) {
                for (Aluno a : listaAlunoSelecao) {
                    Matricula matricula = new Matricula();
                    matricula.setIdTurma(current.getIdTurma());
                    matricula.setIdAluno(a);
                     if (!ejbFacade.findMatriculaAluno(matricula.getIdAluno().getId())) {
                        matricula = ejbFacade.createMatricula(matricula);
                        ejbBoletimRN.criarBoletim(matricula);
                     }
                }
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("MatriculaCreated"));
                return prepareCreate();
            }else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Bundle").getString("MatriculaVazia"));
                return null;                
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Matricula) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            ejbFacade.edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("MatriculaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/resources/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Matricula) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Bundle").getString("MatriculaDeleted"));
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
    
    public void getAlunosAno(){
        if (current.getIdTurma() != null) {
            List<Aluno> listAux = alunoFacade.findAlunoAno(current.getIdTurma().getIdAno().getId());
            listaAluno = new ArrayList();
            for (Aluno a : listAux) {
                if (!ejbFacade.findMatriculaAluno(a.getId())) {
                    listaAluno.add(a);
                }
            } 
            if (listaAluno.isEmpty()) {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Bundle").getString("MatriculaSemAlunos"));
            }
        }else {
            listaAluno = new ArrayList();
        }
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Matricula getMatricula(java.lang.Long id) {
        return ejbFacade.find(id);
    }
    
    public String getStatusAprovacao(Long idAluno){
        List<Boletim> listaBoletimAluno = ejbBoletimFacade.getBoletimAluno(idAluno);
        return ejbBoletimRN.verificarAprovacaoRelatorio(listaBoletimAluno);
    }

    public SelectItem[] getListaTurmas() {
        return listaTurmas;
    }

    public List<Aluno> getListaAluno() {
        return listaAluno;
    }

    public void setListaAluno(List<Aluno> listaAluno) {
        this.listaAluno = listaAluno;
    }


    public List<Aluno> getListaAlunoSelecao() {
        return listaAlunoSelecao;
    }

    public void setListaAlunoSelecao(List<Aluno> listaAlunoSelecao) {
        this.listaAlunoSelecao = listaAlunoSelecao;
    }
    

    @FacesConverter(forClass = Matricula.class)
    public static class MatriculaBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MatriculaBean controller = (MatriculaBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "matriculaBean");
            return controller.getMatricula(getKey(value));
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
            if (object instanceof Matricula) {
                Matricula o = (Matricula) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Matricula.class.getName());
            }
        }

    }

}
