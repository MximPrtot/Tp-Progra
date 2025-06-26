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
import org.example.logica.DetallePedido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.example.Persistencia.exceptions.IllegalOrphanException;
import org.example.Persistencia.exceptions.NonexistentEntityException;
import org.example.Persistencia.exceptions.PreexistingEntityException;
import org.example.logica.Producto;

public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ProductoJpaController(){
        	emf = Persistence.createEntityManagerFactory("org.example_TP-Progra_jar_1.0-SNAPSHOTPU");
    	}
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getDetallePedidoCollection() == null) {
            producto.setDetallePedidoCollection(new ArrayList<DetallePedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<DetallePedido> attachedDetallePedidoCollection = new ArrayList<DetallePedido>();
            for (DetallePedido detallePedidoCollectionDetallePedidoToAttach : producto.getDetallePedidoCollection()) {
                detallePedidoCollectionDetallePedidoToAttach = em.getReference(detallePedidoCollectionDetallePedidoToAttach.getClass(), detallePedidoCollectionDetallePedidoToAttach.getDetalleID());
                attachedDetallePedidoCollection.add(detallePedidoCollectionDetallePedidoToAttach);
            }
            producto.setDetallePedidoCollection(attachedDetallePedidoCollection);
            em.persist(producto);
            for (DetallePedido detallePedidoCollectionDetallePedido : producto.getDetallePedidoCollection()) {
                Producto oldProductIDOfDetallePedidoCollectionDetallePedido = detallePedidoCollectionDetallePedido.getProductID();
                detallePedidoCollectionDetallePedido.setProductID(producto);
                detallePedidoCollectionDetallePedido = em.merge(detallePedidoCollectionDetallePedido);
                if (oldProductIDOfDetallePedidoCollectionDetallePedido != null) {
                    oldProductIDOfDetallePedidoCollectionDetallePedido.getDetallePedidoCollection().remove(detallePedidoCollectionDetallePedido);
                    oldProductIDOfDetallePedidoCollectionDetallePedido = em.merge(oldProductIDOfDetallePedidoCollectionDetallePedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getProductID()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getProductID());
            Collection<DetallePedido> detallePedidoCollectionOld = persistentProducto.getDetallePedidoCollection();
            Collection<DetallePedido> detallePedidoCollectionNew = producto.getDetallePedidoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetallePedido detallePedidoCollectionOldDetallePedido : detallePedidoCollectionOld) {
                if (!detallePedidoCollectionNew.contains(detallePedidoCollectionOldDetallePedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallePedido " + detallePedidoCollectionOldDetallePedido + " since its productID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<DetallePedido> attachedDetallePedidoCollectionNew = new ArrayList<DetallePedido>();
            for (DetallePedido detallePedidoCollectionNewDetallePedidoToAttach : detallePedidoCollectionNew) {
                detallePedidoCollectionNewDetallePedidoToAttach = em.getReference(detallePedidoCollectionNewDetallePedidoToAttach.getClass(), detallePedidoCollectionNewDetallePedidoToAttach.getDetalleID());
                attachedDetallePedidoCollectionNew.add(detallePedidoCollectionNewDetallePedidoToAttach);
            }
            detallePedidoCollectionNew = attachedDetallePedidoCollectionNew;
            producto.setDetallePedidoCollection(detallePedidoCollectionNew);
            producto = em.merge(producto);
            for (DetallePedido detallePedidoCollectionNewDetallePedido : detallePedidoCollectionNew) {
                if (!detallePedidoCollectionOld.contains(detallePedidoCollectionNewDetallePedido)) {
                    Producto oldProductIDOfDetallePedidoCollectionNewDetallePedido = detallePedidoCollectionNewDetallePedido.getProductID();
                    detallePedidoCollectionNewDetallePedido.setProductID(producto);
                    detallePedidoCollectionNewDetallePedido = em.merge(detallePedidoCollectionNewDetallePedido);
                    if (oldProductIDOfDetallePedidoCollectionNewDetallePedido != null && !oldProductIDOfDetallePedidoCollectionNewDetallePedido.equals(producto)) {
                        oldProductIDOfDetallePedidoCollectionNewDetallePedido.getDetallePedidoCollection().remove(detallePedidoCollectionNewDetallePedido);
                        oldProductIDOfDetallePedidoCollectionNewDetallePedido = em.merge(oldProductIDOfDetallePedidoCollectionNewDetallePedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = producto.getProductID();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getProductID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetallePedido> detallePedidoCollectionOrphanCheck = producto.getDetallePedidoCollection();
            for (DetallePedido detallePedidoCollectionOrphanCheckDetallePedido : detallePedidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the DetallePedido " + detallePedidoCollectionOrphanCheckDetallePedido + " in its detallePedidoCollection field has a non-nullable productID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
