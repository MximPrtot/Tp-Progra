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
import org.example.logica.Cliente;
import org.example.logica.Estado;
import org.example.logica.Territorio;
import org.example.logica.Vendedor;
import org.example.logica.DetallePedido;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.example.Persistencia.exceptions.IllegalOrphanException;
import org.example.Persistencia.exceptions.NonexistentEntityException;
import org.example.Persistencia.exceptions.PreexistingEntityException;
import org.example.logica.Pedido;

public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PedidoJpaController(){
        	emf = Persistence.createEntityManagerFactory("org.example_TP-Progra_jar_1.0-SNAPSHOTPU");
    	}
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) throws PreexistingEntityException, Exception {
        if (pedido.getDetallePedidoCollection() == null) {
            pedido.setDetallePedidoCollection(new ArrayList<DetallePedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente customerID = pedido.getCustomerID();
            if (customerID != null) {
                customerID = em.getReference(customerID.getClass(), customerID.getCustomerID());
                pedido.setCustomerID(customerID);
            }
            Estado statusID = pedido.getStatusID();
            if (statusID != null) {
                statusID = em.getReference(statusID.getClass(), statusID.getStatusID());
                pedido.setStatusID(statusID);
            }
            Territorio territoryID = pedido.getTerritoryID();
            if (territoryID != null) {
                territoryID = em.getReference(territoryID.getClass(), territoryID.getTerritoryID());
                pedido.setTerritoryID(territoryID);
            }
            Vendedor vendedorID = pedido.getVendedorID();
            if (vendedorID != null) {
                vendedorID = em.getReference(vendedorID.getClass(), vendedorID.getSalesPersonID());
                pedido.setVendedorID(vendedorID);
            }
            Collection<DetallePedido> attachedDetallePedidoCollection = new ArrayList<DetallePedido>();
            for (DetallePedido detallePedidoCollectionDetallePedidoToAttach : pedido.getDetallePedidoCollection()) {
                detallePedidoCollectionDetallePedidoToAttach = em.getReference(detallePedidoCollectionDetallePedidoToAttach.getClass(), detallePedidoCollectionDetallePedidoToAttach.getDetalleID());
                attachedDetallePedidoCollection.add(detallePedidoCollectionDetallePedidoToAttach);
            }
            pedido.setDetallePedidoCollection(attachedDetallePedidoCollection);
            em.persist(pedido);
            if (customerID != null) {
                customerID.getPedidoCollection().add(pedido);
                customerID = em.merge(customerID);
            }
            if (statusID != null) {
                statusID.getPedidoCollection().add(pedido);
                statusID = em.merge(statusID);
            }
            if (territoryID != null) {
                territoryID.getPedidoCollection().add(pedido);
                territoryID = em.merge(territoryID);
            }
            if (vendedorID != null) {
                vendedorID.getPedidoCollection().add(pedido);
                vendedorID = em.merge(vendedorID);
            }
            for (DetallePedido detallePedidoCollectionDetallePedido : pedido.getDetallePedidoCollection()) {
                Pedido oldPedidoIDOfDetallePedidoCollectionDetallePedido = detallePedidoCollectionDetallePedido.getPedidoID();
                detallePedidoCollectionDetallePedido.setPedidoID(pedido);
                detallePedidoCollectionDetallePedido = em.merge(detallePedidoCollectionDetallePedido);
                if (oldPedidoIDOfDetallePedidoCollectionDetallePedido != null) {
                    oldPedidoIDOfDetallePedidoCollectionDetallePedido.getDetallePedidoCollection().remove(detallePedidoCollectionDetallePedido);
                    oldPedidoIDOfDetallePedidoCollectionDetallePedido = em.merge(oldPedidoIDOfDetallePedidoCollectionDetallePedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPedido(pedido.getPedidoID()) != null) {
                throw new PreexistingEntityException("Pedido " + pedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getPedidoID());
            Cliente customerIDOld = persistentPedido.getCustomerID();
            Cliente customerIDNew = pedido.getCustomerID();
            Estado statusIDOld = persistentPedido.getStatusID();
            Estado statusIDNew = pedido.getStatusID();
            Territorio territoryIDOld = persistentPedido.getTerritoryID();
            Territorio territoryIDNew = pedido.getTerritoryID();
            Vendedor vendedorIDOld = persistentPedido.getVendedorID();
            Vendedor vendedorIDNew = pedido.getVendedorID();
            Collection<DetallePedido> detallePedidoCollectionOld = persistentPedido.getDetallePedidoCollection();
            Collection<DetallePedido> detallePedidoCollectionNew = pedido.getDetallePedidoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetallePedido detallePedidoCollectionOldDetallePedido : detallePedidoCollectionOld) {
                if (!detallePedidoCollectionNew.contains(detallePedidoCollectionOldDetallePedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallePedido " + detallePedidoCollectionOldDetallePedido + " since its pedidoID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customerIDNew != null) {
                customerIDNew = em.getReference(customerIDNew.getClass(), customerIDNew.getCustomerID());
                pedido.setCustomerID(customerIDNew);
            }
            if (statusIDNew != null) {
                statusIDNew = em.getReference(statusIDNew.getClass(), statusIDNew.getStatusID());
                pedido.setStatusID(statusIDNew);
            }
            if (territoryIDNew != null) {
                territoryIDNew = em.getReference(territoryIDNew.getClass(), territoryIDNew.getTerritoryID());
                pedido.setTerritoryID(territoryIDNew);
            }
            if (vendedorIDNew != null) {
                vendedorIDNew = em.getReference(vendedorIDNew.getClass(), vendedorIDNew.getSalesPersonID());
                pedido.setVendedorID(vendedorIDNew);
            }
            Collection<DetallePedido> attachedDetallePedidoCollectionNew = new ArrayList<DetallePedido>();
            for (DetallePedido detallePedidoCollectionNewDetallePedidoToAttach : detallePedidoCollectionNew) {
                detallePedidoCollectionNewDetallePedidoToAttach = em.getReference(detallePedidoCollectionNewDetallePedidoToAttach.getClass(), detallePedidoCollectionNewDetallePedidoToAttach.getDetalleID());
                attachedDetallePedidoCollectionNew.add(detallePedidoCollectionNewDetallePedidoToAttach);
            }
            detallePedidoCollectionNew = attachedDetallePedidoCollectionNew;
            pedido.setDetallePedidoCollection(detallePedidoCollectionNew);
            pedido = em.merge(pedido);
            if (customerIDOld != null && !customerIDOld.equals(customerIDNew)) {
                customerIDOld.getPedidoCollection().remove(pedido);
                customerIDOld = em.merge(customerIDOld);
            }
            if (customerIDNew != null && !customerIDNew.equals(customerIDOld)) {
                customerIDNew.getPedidoCollection().add(pedido);
                customerIDNew = em.merge(customerIDNew);
            }
            if (statusIDOld != null && !statusIDOld.equals(statusIDNew)) {
                statusIDOld.getPedidoCollection().remove(pedido);
                statusIDOld = em.merge(statusIDOld);
            }
            if (statusIDNew != null && !statusIDNew.equals(statusIDOld)) {
                statusIDNew.getPedidoCollection().add(pedido);
                statusIDNew = em.merge(statusIDNew);
            }
            if (territoryIDOld != null && !territoryIDOld.equals(territoryIDNew)) {
                territoryIDOld.getPedidoCollection().remove(pedido);
                territoryIDOld = em.merge(territoryIDOld);
            }
            if (territoryIDNew != null && !territoryIDNew.equals(territoryIDOld)) {
                territoryIDNew.getPedidoCollection().add(pedido);
                territoryIDNew = em.merge(territoryIDNew);
            }
            if (vendedorIDOld != null && !vendedorIDOld.equals(vendedorIDNew)) {
                vendedorIDOld.getPedidoCollection().remove(pedido);
                vendedorIDOld = em.merge(vendedorIDOld);
            }
            if (vendedorIDNew != null && !vendedorIDNew.equals(vendedorIDOld)) {
                vendedorIDNew.getPedidoCollection().add(pedido);
                vendedorIDNew = em.merge(vendedorIDNew);
            }
            for (DetallePedido detallePedidoCollectionNewDetallePedido : detallePedidoCollectionNew) {
                if (!detallePedidoCollectionOld.contains(detallePedidoCollectionNewDetallePedido)) {
                    Pedido oldPedidoIDOfDetallePedidoCollectionNewDetallePedido = detallePedidoCollectionNewDetallePedido.getPedidoID();
                    detallePedidoCollectionNewDetallePedido.setPedidoID(pedido);
                    detallePedidoCollectionNewDetallePedido = em.merge(detallePedidoCollectionNewDetallePedido);
                    if (oldPedidoIDOfDetallePedidoCollectionNewDetallePedido != null && !oldPedidoIDOfDetallePedidoCollectionNewDetallePedido.equals(pedido)) {
                        oldPedidoIDOfDetallePedidoCollectionNewDetallePedido.getDetallePedidoCollection().remove(detallePedidoCollectionNewDetallePedido);
                        oldPedidoIDOfDetallePedidoCollectionNewDetallePedido = em.merge(oldPedidoIDOfDetallePedidoCollectionNewDetallePedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = pedido.getPedidoID();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getPedidoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetallePedido> detallePedidoCollectionOrphanCheck = pedido.getDetallePedidoCollection();
            for (DetallePedido detallePedidoCollectionOrphanCheckDetallePedido : detallePedidoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the DetallePedido " + detallePedidoCollectionOrphanCheckDetallePedido + " in its detallePedidoCollection field has a non-nullable pedidoID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente customerID = pedido.getCustomerID();
            if (customerID != null) {
                customerID.getPedidoCollection().remove(pedido);
                customerID = em.merge(customerID);
            }
            Estado statusID = pedido.getStatusID();
            if (statusID != null) {
                statusID.getPedidoCollection().remove(pedido);
                statusID = em.merge(statusID);
            }
            Territorio territoryID = pedido.getTerritoryID();
            if (territoryID != null) {
                territoryID.getPedidoCollection().remove(pedido);
                territoryID = em.merge(territoryID);
            }
            Vendedor vendedorID = pedido.getVendedorID();
            if (vendedorID != null) {
                vendedorID.getPedidoCollection().remove(pedido);
                vendedorID = em.merge(vendedorID);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
