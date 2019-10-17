package repositorio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import modelo.Categoria;

/**
 *
 * @author Arnaldo Junior
 */
@Dependent
public class CategoriaRepositorio {

    @PersistenceContext(unitName = "ForeverPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public boolean create(Categoria categoria) {
        try {
            transaction.begin();
            em.persist(categoria);
            transaction.commit();
            return true;
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
            return false;
        }
    }

    public void delete(Long id) {
        try {
            transaction.begin();
            Categoria categoria;
            categoria = em.getReference(Categoria.class, id);
            em.remove(categoria);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(CategoriaRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Categoria> findAll() {
        return em.createQuery("SELECT c FROM Categoria c").getResultList();
    }

    public Categoria findById(Long id) {
        return em.find(Categoria.class, id);
    }

    public Categoria findByName(String nome) {
        Categoria categoria = new Categoria();

        try {
            categoria = (Categoria) em.createQuery("SELECT c FROM Categoria c WHERE c.nome = :name").setParameter("name", nome).getSingleResult();

        } catch (NoResultException nre) {
            System.out.println("Nenhuma categoria encontrada: " + nre);
        } catch (Exception ex) {
            System.out.println("Erro ao buscar categoria: " + ex);
        } 
        return categoria;
    }

}
