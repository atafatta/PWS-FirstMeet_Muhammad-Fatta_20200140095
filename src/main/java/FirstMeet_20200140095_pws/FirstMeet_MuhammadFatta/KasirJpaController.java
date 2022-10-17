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
public class KasirJpaController implements Serializable {

    public KasirJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FirstMeet_20200140095_pws_FirstMeet_MuhammadFatta_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kasir kasir) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Orderr orderIdOrphanCheck = kasir.getOrderId();
        if (orderIdOrphanCheck != null) {
            Kasir oldKasirOfOrderId = orderIdOrphanCheck.getKasir();
            if (oldKasirOfOrderId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Orderr " + orderIdOrphanCheck + " already has an item of type Kasir whose orderId column cannot be null. Please make another selection for the orderId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orderr orderId = kasir.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getOrderId());
                kasir.setOrderId(orderId);
            }
            em.persist(kasir);
            if (orderId != null) {
                orderId.setKasir(kasir);
                orderId = em.merge(orderId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKasir(kasir.getIdKasir()) != null) {
                throw new PreexistingEntityException("Kasir " + kasir + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kasir kasir) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kasir persistentKasir = em.find(Kasir.class, kasir.getIdKasir());
            Orderr orderIdOld = persistentKasir.getOrderId();
            Orderr orderIdNew = kasir.getOrderId();
            List<String> illegalOrphanMessages = null;
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                Kasir oldKasirOfOrderId = orderIdNew.getKasir();
                if (oldKasirOfOrderId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Orderr " + orderIdNew + " already has an item of type Kasir whose orderId column cannot be null. Please make another selection for the orderId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getOrderId());
                kasir.setOrderId(orderIdNew);
            }
            kasir = em.merge(kasir);
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.setKasir(null);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.setKasir(kasir);
                orderIdNew = em.merge(orderIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = kasir.getIdKasir();
                if (findKasir(id) == null) {
                    throw new NonexistentEntityException("The kasir with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kasir kasir;
            try {
                kasir = em.getReference(Kasir.class, id);
                kasir.getIdKasir();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kasir with id " + id + " no longer exists.", enfe);
            }
            Orderr orderId = kasir.getOrderId();
            if (orderId != null) {
                orderId.setKasir(null);
                orderId = em.merge(orderId);
            }
            em.remove(kasir);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kasir> findKasirEntities() {
        return findKasirEntities(true, -1, -1);
    }

    public List<Kasir> findKasirEntities(int maxResults, int firstResult) {
        return findKasirEntities(false, maxResults, firstResult);
    }

    private List<Kasir> findKasirEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kasir.class));
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

    public Kasir findKasir(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kasir.class, id);
        } finally {
            em.close();
        }
    }

    public int getKasirCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kasir> rt = cq.from(Kasir.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
