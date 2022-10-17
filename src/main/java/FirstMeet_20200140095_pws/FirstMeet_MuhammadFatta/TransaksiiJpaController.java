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

/**
 *
 * @author STRIX
 */
public class TransaksiiJpaController implements Serializable {

    public TransaksiiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaksii transaksii) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetailTransaksi detailTransaksi = transaksii.getDetailTransaksi();
            if (detailTransaksi != null) {
                detailTransaksi = em.getReference(detailTransaksi.getClass(), detailTransaksi.getIdDetail());
                transaksii.setDetailTransaksi(detailTransaksi);
            }
            em.persist(transaksii);
            if (detailTransaksi != null) {
                Transaksii oldIdTransaksiOfDetailTransaksi = detailTransaksi.getIdTransaksi();
                if (oldIdTransaksiOfDetailTransaksi != null) {
                    oldIdTransaksiOfDetailTransaksi.setDetailTransaksi(null);
                    oldIdTransaksiOfDetailTransaksi = em.merge(oldIdTransaksiOfDetailTransaksi);
                }
                detailTransaksi.setIdTransaksi(transaksii);
                detailTransaksi = em.merge(detailTransaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaksii(transaksii.getIdTransaksi()) != null) {
                throw new PreexistingEntityException("Transaksii " + transaksii + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaksii transaksii) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksii persistentTransaksii = em.find(Transaksii.class, transaksii.getIdTransaksi());
            DetailTransaksi detailTransaksiOld = persistentTransaksii.getDetailTransaksi();
            DetailTransaksi detailTransaksiNew = transaksii.getDetailTransaksi();
            List<String> illegalOrphanMessages = null;
            if (detailTransaksiOld != null && !detailTransaksiOld.equals(detailTransaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DetailTransaksi " + detailTransaksiOld + " since its idTransaksi field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (detailTransaksiNew != null) {
                detailTransaksiNew = em.getReference(detailTransaksiNew.getClass(), detailTransaksiNew.getIdDetail());
                transaksii.setDetailTransaksi(detailTransaksiNew);
            }
            transaksii = em.merge(transaksii);
            if (detailTransaksiNew != null && !detailTransaksiNew.equals(detailTransaksiOld)) {
                Transaksii oldIdTransaksiOfDetailTransaksi = detailTransaksiNew.getIdTransaksi();
                if (oldIdTransaksiOfDetailTransaksi != null) {
                    oldIdTransaksiOfDetailTransaksi.setDetailTransaksi(null);
                    oldIdTransaksiOfDetailTransaksi = em.merge(oldIdTransaksiOfDetailTransaksi);
                }
                detailTransaksiNew.setIdTransaksi(transaksii);
                detailTransaksiNew = em.merge(detailTransaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = transaksii.getIdTransaksi();
                if (findTransaksii(id) == null) {
                    throw new NonexistentEntityException("The transaksii with id " + id + " no longer exists.");
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
            Transaksii transaksii;
            try {
                transaksii = em.getReference(Transaksii.class, id);
                transaksii.getIdTransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaksii with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            DetailTransaksi detailTransaksiOrphanCheck = transaksii.getDetailTransaksi();
            if (detailTransaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaksii (" + transaksii + ") cannot be destroyed since the DetailTransaksi " + detailTransaksiOrphanCheck + " in its detailTransaksi field has a non-nullable idTransaksi field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(transaksii);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaksii> findTransaksiiEntities() {
        return findTransaksiiEntities(true, -1, -1);
    }

    public List<Transaksii> findTransaksiiEntities(int maxResults, int firstResult) {
        return findTransaksiiEntities(false, maxResults, firstResult);
    }

    private List<Transaksii> findTransaksiiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaksii.class));
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

    public Transaksii findTransaksii(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaksii.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaksiiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaksii> rt = cq.from(Transaksii.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
