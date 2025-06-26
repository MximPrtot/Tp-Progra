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
import org.example.logica.Vendedor;

public class VendedorJpaController implements Serializable {

    public VendedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public VendedorJpaController(){
        	emf = Persistence.createEntityManagerFactory("org.example_TP-Progra_jar_1.0-SNAPSHOTPU");
    	}
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendedor vendedor) throws PreexistingEntityException, Exception {
        if (vendedor.getPedidoCollection() == null) {
            vendedor.setPedidoCollection(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Pedido> attachedPedidoCollection = new ArrayList<Pedido>();
            for (Pedido pedidoCollectionPedidoToAttach : vendedor.getPedidoCollection()) {
                pedidoCollectionPedidoToAttach = em.getReference(pedidoCollectionPedidoToAttach.getClass(), pedidoCollectionPedidoToAttach.getPedidoID());
                attachedPedidoCollection.add(pedidoCollectionPedidoToAttach);
            }
            vendedor.setPedidoCollection(attachedPedidoCollection);
            em.persist(vendedor);
            for (Pedido pedidoCollectionPedido : vendedor.getPedidoCollection()) {
                Vendedor oldVendedorIDOfPedidoCollectionPedido = pedidoCollectionPedido.getVendedorID();
                pedidoCollectionPedido.setVendedorID(vendedor);
                pedidoCollectionPedido = em.merge(pedidoCollectionPedido);
                if (oldVendedorIDOfPedidoCollectionPedido != null) {
                    oldVendedorIDOfPedidoCollectionPedido.getPedidoCollection().remove(pedidoCollectionPedido);
                    oldVendedorIDOfPedidoCollectionPedido = em.merge(oldVendedorIDOfPedidoCollectionPedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVendedor(vendedor.getSalesPersonID()) != null) {
                throw new PreexistingEntityException("Vendedor " + vendedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendedor vendedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor persistentVendedor = em.find(Vendedor.class, vendedor.getSalesPersonID());
            Collection<Pedido> pedidoCollectionOld = persistentVendedor.getPedidoCollection();
            Collection<Pedido> pedidoCollectionNew = vendedor.getPedidoCollection();
            List<String> illegalOrphanMessages = null;
            for (Pedido pedidoCollectionOldPedido : pedidoCollectionOld) {
                if (!pedidoCollectionNew.contains(pedidoCollectionOldPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pedido " + pedidoCollectionOldPedido + " since its vendedorID field is not nullable.");
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
            vendedor.setPedidoCollection(pedidoCollectionNew);
            vendedor = em.merge(vendedor);
            for (Pedido pedidoCollectionNewPedido : pedidoCollectionNew) {
                if (!pedidoCollectionOld.contains(pedidoCollectionNewPedido)) {
                    Vendedor oldVendedorIDOfPedidoCollectionNewPedido = pedidoCollectionNewPedido.getVendedorID();
                    pedidoCollectionNewPedido.setVendedorID(vendedor);
                    pedidoCollectionNewPedido = em.merge(pedidoCollectionNewPedido);
                    if (oldVendedorIDOfPedidoCollectionNewPedido != null && !oldVendedorIDOfPedidoCollectionNewPedido.equals(vendedor)) {
                        oldVendedorIDOfPedidoCollectionNewPedido.getPedidoCollection().remove(pedidoCollectionNewPedido);
                        oldVendedorIDOfPedidoCollectionNewPedido = em.merge(oldVendedorIDOfPedidoCollectionNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = vendedor.getSalesPersonID();
                if (findVendedor(id) == null) {
                    throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.");
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
            Vendedor vendedor;
            try {
                vendedor = em.getReference(Vendedor.class, id);
                vendedor.getSalesPersonID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pedido> pedidoCollectionOrphanCheck = vendedor.getPedidoCollection();
            for (Pedido pedidoCollectionOrphanCheckPedido : pedidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vendedor (" + vendedor + ") cannot be destroyed since the Pedido " + pedidoCollectionOrphanCheckPedido + " in its pedidoCollection field has a non-nullable vendedorID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vendedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendedor> findVendedorEntities() {
        return findVendedorEntities(true, -1, -1);
    }

    public List<Vendedor> findVendedorEntities(int maxResults, int firstResult) {
        return findVendedorEntities(false, maxResults, firstResult);
    }

    private List<Vendedor> findVendedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendedor.class));
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

    public Vendedor findVendedor(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendedor> rt = cq.from(Vendedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
