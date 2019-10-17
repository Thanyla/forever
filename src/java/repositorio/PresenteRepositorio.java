package repositorio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import modelo.Categoria;
import modelo.Presente;

/**
 *
 * @author Arnaldo Junior
 */
@Dependent
public class PresenteRepositorio {

    /**
     * O EntityManager tem seu ciclo de vida gerenciado pelo container Java EE.
     * O Contexto de Persistência é propagado pelo container para todos os
     * componentes da aplicação que utilizam a instância EntityManager dentro de
     * uma transação JTA (Java Transaction Architecture).
     */
    @PersistenceContext(unitName = "ForeverPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public void create(Presente presente) {
        Categoria categoria;
        try {
            transaction.begin();
            categoria = em.getReference(Categoria.class, presente.getCategoria().getId());
            categoria.addPresente(presente);
            em.persist(presente);
            transaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }

    public List<Presente> findAll() {
        return em.createQuery("SELECT p FROM Presente p").getResultList();
    }

    public void delete(Long id) {
        try {
            transaction.begin();
            Presente presente;
            presente = em.getReference(Presente.class, id);
            em.remove(presente);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(PresenteRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
