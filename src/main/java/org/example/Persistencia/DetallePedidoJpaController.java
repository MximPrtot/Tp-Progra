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
import java.util.List;
import org.example.Persistencia.exceptions.NonexistentEntityException;
import org.example.Persistencia.exceptions.PreexistingEntityException;
import org.example.logica.DetallePedido;
import org.example.logica.Pedido;
import org.example.logica.Producto;

public class DetallePedidoJpaController implements Serializable {

    public DetallePedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public DetallePedidoJpaController(){
        	emf = Persistence.createEntityManagerFactory("org.example_TP-Progra_jar_1.0-SNAPSHOTPU");
    	}
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetallePedido detallePedido) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedidoID = detallePedido.getPedidoID();
            if (pedidoID != null) {
                pedidoID = em.getReference(pedidoID.getClass(), pedidoID.getPedidoID());
                detallePedido.setPedidoID(pedidoID);
            }
            Producto productID = detallePedido.getProductID();
            if (productID != null) {
                productID = em.getReference(productID.getClass(), productID.getProductID());
                detallePedido.setProductID(productID);
            }
            em.persist(detallePedido);
            if (pedidoID != null) {
                pedidoID.getDetallePedidoCollection().add(detallePedido);
                pedidoID = em.merge(pedidoID);
            }
            if (productID != null) {
                productID.getDetallePedidoCollection().add(detallePedido);
                productID = em.merge(productID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetallePedido(detallePedido.getDetalleID()) != null) {
                throw new PreexistingEntityException("DetallePedido " + detallePedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetallePedido detallePedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallePedido persistentDetallePedido = em.find(DetallePedido.class, detallePedido.getDetalleID());
            Pedido pedidoIDOld = persistentDetallePedido.getPedidoID();
            Pedido pedidoIDNew = detallePedido.getPedidoID();
            Producto productIDOld = persistentDetallePedido.getProductID();
            Producto productIDNew = detallePedido.getProductID();
            if (pedidoIDNew != null) {
                pedidoIDNew = em.getReference(pedidoIDNew.getClass(), pedidoIDNew.getPedidoID());
                detallePedido.setPedidoID(pedidoIDNew);
            }
            if (productIDNew != null) {
                productIDNew = em.getReference(productIDNew.getClass(), productIDNew.getProductID());
                detallePedido.setProductID(productIDNew);
            }
            detallePedido = em.merge(detallePedido);
            if (pedidoIDOld != null && !pedidoIDOld.equals(pedidoIDNew)) {
                pedidoIDOld.getDetallePedidoCollection().remove(detallePedido);
                pedidoIDOld = em.merge(pedidoIDOld);
            }
            if (pedidoIDNew != null && !pedidoIDNew.equals(pedidoIDOld)) {
                pedidoIDNew.getDetallePedidoCollection().add(detallePedido);
                pedidoIDNew = em.merge(pedidoIDNew);
            }
            if (productIDOld != null && !productIDOld.equals(productIDNew)) {
                productIDOld.getDetallePedidoCollection().remove(detallePedido);
                productIDOld = em.merge(productIDOld);
            }
            if (productIDNew != null && !productIDNew.equals(productIDOld)) {
                productIDNew.getDetallePedidoCollection().add(detallePedido);
                productIDNew = em.merge(productIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = detallePedido.getDetalleID();
                if (findDetallePedido(id) == null) {
                    throw new NonexistentEntityException("The detallePedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallePedido detallePedido;
            try {
                detallePedido = em.getReference(DetallePedido.class, id);
                detallePedido.getDetalleID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallePedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedidoID = detallePedido.getPedidoID();
            if (pedidoID != null) {
                pedidoID.getDetallePedidoCollection().remove(detallePedido);
                pedidoID = em.merge(pedidoID);
            }
            Producto productID = detallePedido.getProductID();
            if (productID != null) {
                productID.getDetallePedidoCollection().remove(detallePedido);
                productID = em.merge(productID);
            }
            em.remove(detallePedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetallePedido> findDetallePedidoEntities() {
        return findDetallePedidoEntities(true, -1, -1);
    }

    public List<DetallePedido> findDetallePedidoEntities(int maxResults, int firstResult) {
        return findDetallePedidoEntities(false, maxResults, firstResult);
    }

    private List<DetallePedido> findDetallePedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetallePedido.class));
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

    public DetallePedido findDetallePedido(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetallePedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallePedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetallePedido> rt = cq.from(DetallePedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
