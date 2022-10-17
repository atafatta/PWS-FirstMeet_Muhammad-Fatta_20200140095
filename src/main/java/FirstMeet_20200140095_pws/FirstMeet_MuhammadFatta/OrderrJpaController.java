/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta;

import FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.exceptions.IllegalOrphanException;
import FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.exceptions.NonexistentEntityException;
import FirstMeet_20200140095_pws.FirstMeet_MuhammadFatta.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author STRIX
 */
public class OrderrJpaController implements Serializable {

    public OrderrJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FirstMeet_20200140095_pws_FirstMeet_MuhammadFatta_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderr orderr) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kasir kasir = orderr.getKasir();
            if (kasir != null) {
                kasir = em.getReference(kasir.getClass(), kasir.getIdKasir());
                orderr.setKasir(kasir);
            }
            em.persist(orderr);
            if (kasir != null) {
                Orderr oldOrderIdOfKasir = kasir.getOrderId();
                if (oldOrderIdOfKasir != null) {
                    oldOrderIdOfKasir.setKasir(null);
                    oldOrderIdOfKasir = em.merge(oldOrderIdOfKasir);
                }
                kasir.setOrderId(orderr);
                kasir = em.merge(kasir);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrderr(orderr.getOrderId()) != null) {
                throw new PreexistingEntityException("Orderr " + orderr + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderr orderr) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderr persistentOrderr = em.find(Orderr.class, orderr.getOrderId());
            Kasir kasirOld = persistentOrderr.getKasir();
            Kasir kasirNew = orderr.getKasir();
            List<String> illegalOrphanMessages = null;
            if (kasirOld != null && !kasirOld.equals(kasirNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Kasir " + kasirOld + " since its orderId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (kasirNew != null) {
                kasirNew = em.getReference(kasirNew.getClass(), kasirNew.getIdKasir());
                orderr.setKasir(kasirNew);
            }
            orderr = em.merge(orderr);
            if (kasirNew != null && !kasirNew.equals(kasirOld)) {
                Orderr oldOrderIdOfKasir = kasirNew.getOrderId();
                if (oldOrderIdOfKasir != null) {
                    oldOrderIdOfKasir.setKasir(null);
                    oldOrderIdOfKasir = em.merge(oldOrderIdOfKasir);
                }
                kasirNew.setOrderId(orderr);
                kasirNew = em.merge(kasirNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = orderr.getOrderId();
                if (findOrderr(id) == null) {
                    throw new NonexistentEntityException("The orderr with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderr orderr;
            try {
                orderr = em.getReference(Orderr.class, id);
                orderr.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderr with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Kasir kasirOrphanCheck = orderr.getKasir();
            if (kasirOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orderr (" + orderr + ") cannot be destroyed since the Kasir " + kasirOrphanCheck + " in its kasir field has a non-nullable orderId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(orderr);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orderr> findOrderrEntities() {
        return findOrderrEntities(true, -1, -1);
    }

    public List<Orderr> findOrderrEntities(int maxResults, int firstResult) {
        return findOrderrEntities(false, maxResults, firstResult);
    }

    private List<Orderr> findOrderrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderr.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Orderr findOrderr(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderr.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderr> rt = cq.from(Orderr.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
