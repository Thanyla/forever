package controle;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Categoria;
import modelo.Presente;
import repositorio.CategoriaRepositorio;
import repositorio.PresenteRepositorio;

/**
 *
 * @author Arnaldo Junior
 */
@Named
@RequestScoped
public class PresentesControle {

    @Inject
    private Presente presente;

    @Inject
    private PresenteRepositorio presenteRepositorio;

    @Inject
    private CategoriaRepositorio categoriaRepositorio;

    private String categoria;

    private List<Presente> presentes = new ArrayList<>();

    @PostConstruct
    public void inicializar() {
        buscarTodosPresentes();
    }

    public Presente getPresente() {
        return presente;
    }

    public List<Categoria> getCategorias() {
        return categoriaRepositorio.findAll();
    }

    public String getCategoria() {
        return categoria;
    }

    public void cadastrar() {
        String msg;
        try {
            presenteRepositorio.create(presente);
            msg = "Presente " + presente.getDescricao() + " cadastrado com sucesso!";
            presente = new Presente();
        } catch (Exception e) {
            msg = "Erro ao cadastrar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public void deletar(Long id) {
        String msg;
        try {
            presenteRepositorio.delete(id);
            msg = "Presente deletado com sucesso!";
        } catch (Exception e) {
            msg = "Erro ao deletar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Presente> buscarTodosPresentes() {

        try {
            presentes = presenteRepositorio.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar presentes!");
        }
        return presentes;
    }

    public void buscarPresentesPorCategoria(ValueChangeEvent e) {
        this.categoria = e.getNewValue().toString();

        if (categoria.isEmpty()) {
            buscarTodosPresentes();
        } else {
            Categoria categoriaBuscada = categoriaRepositorio.findByName(categoria);
            presentes = categoriaBuscada.getPresentes();

            if (presentes.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nenhum presente encontrado"));
            }
        }
    }

    public List<Presente> getPresentes() {
        return presentes;
    }

}
