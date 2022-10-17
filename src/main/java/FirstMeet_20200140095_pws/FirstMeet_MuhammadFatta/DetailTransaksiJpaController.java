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
public class DetailTransaksiJpaController implements Serializable {

    public DetailTransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetailTransaksi detailTransaksi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Transaksii idTransaksiOrphanCheck = detailTransaksi.getIdTransaksi();
        if (idTransaksiOrphanCheck != null) {
            DetailTransaksi oldDetailTransaksiOfIdTransaksi = idTransaksiOrphanCheck.getDetailTransaksi();
            if (oldDetailTransaksiOfIdTransaksi != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Transaksii " + idTransaksiOrphanCheck + " already has an item of type DetailTransaksi whose idTransaksi column cannot be null. Please make another selection for the idTransaksi field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksii idTransaksi = detailTransaksi.getIdTransaksi();
            if (idTransaksi != null) {
                idTransaksi = em.getReference(idTransaksi.getClass(), idTransaksi.getIdTransaksi());
                detailTransaksi.setIdTransaksi(idTransaksi);
            }
            em.persist(detailTransaksi);
            if (idTransaksi != null) {
                idTransaksi.setDetailTransaksi(detailTransaksi);
                idTransaksi = em.merge(idTransaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetailTransaksi(detailTransaksi.getIdDetail()) != null) {
                throw new PreexistingEntityException("DetailTransaksi " + detailTransaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetailTransaksi detailTransaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetailTransaksi persistentDetailTransaksi = em.find(DetailTransaksi.class, detailTransaksi.getIdDetail());
            Transaksii idTransaksiOld = persistentDetailTransaksi.getIdTransaksi();
            Transaksii idTransaksiNew = detailTransaksi.getIdTransaksi();
            List<String> illegalOrphanMessages = null;
            if (idTransaksiNew != null && !idTransaksiNew.equals(idTransaksiOld)) {
                DetailTransaksi oldDetailTransaksiOfIdTransaksi = idTransaksiNew.getDetailTransaksi();
                if (oldDetailTransaksiOfIdTransaksi != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Transaksii " + idTransaksiNew + " already has an item of type DetailTransaksi whose idTransaksi column cannot be null. Please make another selection for the idTransaksi field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTransaksiNew != null) {
                idTransaksiNew = em.getReference(idTransaksiNew.getClass(), idTransaksiNew.getIdTransaksi());
                detailTransaksi.setIdTransaksi(idTransaksiNew);
            }
            detailTransaksi = em.merge(detailTransaksi);
            if (idTransaksiOld != null && !idTransaksiOld.equals(idTransaksiNew)) {
                idTransaksiOld.setDetailTransaksi(null);
                idTransaksiOld = em.merge(idTransaksiOld);
            }
            if (idTransaksiNew != null && !idTransaksiNew.equals(idTransaksiOld)) {
                idTransaksiNew.setDetailTransaksi(detailTransaksi);
                idTransaksiNew = em.merge(idTransaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = detailTransaksi.getIdDetail();
                if (findDetailTransaksi(id) == null) {
                    throw new NonexistentEntityException("The detailTransaksi with id " + id + " no longer exists.");
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
            DetailTransaksi detailTransaksi;
            try {
                detailTransaksi = em.getReference(DetailTransaksi.class, id);
                detailTransaksi.getIdDetail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detailTransaksi with id " + id + " no longer exists.", enfe);
            }
            Transaksii idTransaksi = detailTransaksi.getIdTransaksi();
            if (idTransaksi != null) {
                idTransaksi.setDetailTransaksi(null);
                idTransaksi = em.merge(idTransaksi);
            }
            em.remove(detailTransaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetailTransaksi> findDetailTransaksiEntities() {
        return findDetailTransaksiEntities(true, -1, -1);
    }

    public List<DetailTransaksi> findDetailTransaksiEntities(int maxResults, int firstResult) {
        return findDetailTransaksiEntities(false, maxResults, firstResult);
    }

    private List<DetailTransaksi> findDetailTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetailTransaksi.class));
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

    public DetailTransaksi findDetailTransaksi(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetailTransaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetailTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetailTransaksi> rt = cq.from(DetailTransaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
