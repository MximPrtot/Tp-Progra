package org.example.Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import org.example.logica.Pedido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.example.Persistencia.exceptions.IllegalOrphanException;
import org.example.Persistencia.exceptions.NonexistentEntityException;
import org.example.Persistencia.exceptions.PreexistingEntityException;
import org.example.logica.Territorio;

public class TerritorioJpaController implements Serializable {

    public TerritorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public TerritorioJpaController(){
        	emf = Persistence.createEntityManagerFactory("org.example_TP-Progra_jar_1.0-SNAPSHOTPU");
    	}
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Territorio territorio) throws PreexistingEntityException, Exception {
        if (territorio.getPedidoCollection() == null) {
            territorio.setPedidoCollection(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pedido> attachedPedidoCollection = new ArrayList<Pedido>();
            for (Pedido pedidoCollectionPedidoToAttach : territorio.getPedidoCollection()) {
                pedidoCollectionPedidoToAttach = em.getReference(pedidoCollectionPedidoToAttach.getClass(), pedidoCollectionPedidoToAttach.getPedidoID());
                attachedPedidoCollection.add(pedidoCollectionPedidoToAttach);
            }
            territorio.setPedidoCollection(attachedPedidoCollection);
            em.persist(territorio);
            for (Pedido pedidoCollectionPedido : territorio.getPedidoCollection()) {
                Territorio oldTerritoryIDOfPedidoCollectionPedido = pedidoCollectionPedido.getTerritoryID();
                pedidoCollectionPedido.setTerritoryID(territorio);
                pedidoCollectionPedido = em.merge(pedidoCollectionPedido);
                if (oldTerritoryIDOfPedidoCollectionPedido != null) {
                    oldTerritoryIDOfPedidoCollectionPedido.getPedidoCollection().remove(pedidoCollectionPedido);
                    oldTerritoryIDOfPedidoCollectionPedido = em.merge(oldTerritoryIDOfPedidoCollectionPedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTerritorio(territorio.getTerritoryID()) != null) {
                throw new PreexistingEntityException("Territorio " + territorio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Territorio territorio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Territorio persistentTerritorio = em.find(Territorio.class, territorio.getTerritoryID());
            Collection<Pedido> pedidoCollectionOld = persistentTerritorio.getPedidoCollection();
            Collection<Pedido> pedidoCollectionNew = territorio.getPedidoCollection();
            List<String> illegalOrphanMessages = null;
            for (Pedido pedidoCollectionOldPedido : pedidoCollectionOld) {
                if (!pedidoCollectionNew.contains(pedidoCollectionOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoCollectionOldPedido + " since its territoryID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Pedido> attachedPedidoCollectionNew = new ArrayList<Pedido>();
            for (Pedido pedidoCollectionNewPedidoToAttach : pedidoCollectionNew) {
                pedidoCollectionNewPedidoToAttach = em.getReference(pedidoCollectionNewPedidoToAttach.getClass(), pedidoCollectionNewPedidoToAttach.getPedidoID());
                attachedPedidoCollectionNew.add(pedidoCollectionNewPedidoToAttach);
            }
            pedidoCollectionNew = attachedPedidoCollectionNew;
            territorio.setPedidoCollection(pedidoCollectionNew);
            territorio = em.merge(territorio);
            for (Pedido pedidoCollectionNewPedido : pedidoCollectionNew) {
                if (!pedidoCollectionOld.contains(pedidoCollectionNewPedido)) {
                    Territorio oldTerritoryIDOfPedidoCollectionNewPedido = pedidoCollectionNewPedido.getTerritoryID();
                    pedidoCollectionNewPedido.setTerritoryID(territorio);
                    pedidoCollectionNewPedido = em.merge(pedidoCollectionNewPedido);
                    if (oldTerritoryIDOfPedidoCollectionNewPedido != null && !oldTerritoryIDOfPedidoCollectionNewPedido.equals(territorio)) {
                        oldTerritoryIDOfPedidoCollectionNewPedido.getPedidoCollection().remove(pedidoCollectionNewPedido);
                        oldTerritoryIDOfPedidoCollectionNewPedido = em.merge(oldTerritoryIDOfPedidoCollectionNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = territorio.getTerritoryID();
                if (findTerritorio(id) == null) {
                    throw new NonexistentEntityException("The territorio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Territorio territorio;
            try {
                territorio = em.getReference(Territorio.class, id);
                territorio.getTerritoryID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The territorio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pedido> pedidoCollectionOrphanCheck = territorio.getPedidoCollection();
            for (Pedido pedidoCollectionOrphanCheckPedido : pedidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Territorio (" + territorio + ") cannot be destroyed since the Pedido " + pedidoCollectionOrphanCheckPedido + " in its pedidoCollection field has a non-nullable territoryID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(territorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Territorio> findTerritorioEntities() {
        return findTerritorioEntities(true, -1, -1);
    }

    public List<Territorio> findTerritorioEntities(int maxResults, int firstResult) {
        return findTerritorioEntities(false, maxResults, firstResult);
    }

    private List<Territorio> findTerritorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Territorio.class));
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

    public Territorio findTerritorio(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Territorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerritorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Territorio> rt = cq.from(Territorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
