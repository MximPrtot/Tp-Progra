package org.example.Persistencia;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.Persistencia.exceptions.IllegalOrphanException;
import org.example.Persistencia.exceptions.NonexistentEntityException;
import org.example.logica.Cliente;
import org.example.logica.DetallePedido;
import org.example.logica.Estado;
import org.example.logica.Pedido;
import org.example.logica.Producto;
import org.example.logica.Territorio;
import org.example.logica.Vendedor;

public class ControladoraPersistencia {
     ClienteJpaController clientej = new ClienteJpaController();
     DetallePedidoJpaController detallePj = new DetallePedidoJpaController();
     EstadoJpaController estadoj = new EstadoJpaController();
     PedidoJpaController pedidoj = new PedidoJpaController();
     ProductoJpaController productoj = new ProductoJpaController();
     TerritorioJpaController territorioj = new TerritorioJpaController();
     VendedorJpaController vendedorj = new VendedorJpaController();
     
     //________________CLIENTE______________________________________
    public void crearCliente(Cliente cliente) {
         try {
             clientej.create(cliente);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
public void eliminarCliente(int id) {
    try {
        clientej.destroy(new BigDecimal(id));  // Convertir el int a BigDecimal
    } catch (IllegalOrphanException | NonexistentEntityException ex) {
        Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
  public void editarCliente(Cliente cliente) {
    try {
        clientej.edit(cliente); // Esto debería persistir los cambios en la base de datos
    } catch (Exception ex) {
        Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        throw new RuntimeException("Error al editar cliente", ex);
    }
}
public Cliente traerCliente(int id) {
    return clientej.findCliente(new BigDecimal(id));  // Convertir el int a BigDecimal
}

    public List<Cliente> traerClientes() {
        return clientej.findClienteEntities();
    }
    
    //________________DETALLEPEDIDO______________________________________

    public void crearDetallePedido(DetallePedido detallepedido) {
         try {
             detallePj.create(detallepedido);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void eliminarDetallePedido(int id) {
         try {
             detallePj.destroy(BigDecimal.ONE);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void editarDetallePedido(DetallePedido detallepedido) {
         try {
             detallePj.edit(detallepedido);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public DetallePedido traerDetallePedido(int id) {
        return detallePj.findDetallePedido(BigDecimal.ONE);
    }

    public List<DetallePedido> traerDetallePedidos() {
        return detallePj.findDetallePedidoEntities();
    }
    
     //________________ESTADO______________________________________

    public List<Estado> traerEstados() {
        return estadoj.findEstadoEntities();
    }

    public Estado traerEstado(int id) {
        return estadoj.findEstado(BigDecimal.ONE);
    }

    public void crearEstado(Estado estado) {
         try {
             estadoj.create(estado);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void eliminarEstado(int id) {
         try {
             estadoj.destroy(BigDecimal.ONE);
         } catch (IllegalOrphanException | NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void editarEstado(Estado estado) {
         try {
             estadoj.edit(estado);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    //________________PEDIDO______________________________________
    public void crearPedido(Pedido pedido) {
         try {
             pedidoj.create(pedido);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void eliminarPedido(int id) {
         try {
             pedidoj.destroy(BigDecimal.ONE);
         } catch (IllegalOrphanException | NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void editarPedido(Pedido pedido) {
         try {
             pedidoj.edit(pedido);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public Pedido traerPedido(int id) {
        return pedidoj.findPedido(BigDecimal.ONE);
    }

    public List<Pedido> traerPedidos() {
        return pedidoj.findPedidoEntities();
    }
    
    //________________PRODUCTO______________________________________

    public void crearProducto(Producto producto) {
         try {
             productoj.create(producto);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void eliminarProducto(int id) {
         try {
             productoj.destroy(BigDecimal.ONE);
         } catch (IllegalOrphanException | NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void editarProducto(Producto producto) {
         try {
             productoj.edit(producto);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public Producto traerProducto(int id) {
        return productoj.findProducto(BigDecimal.ONE);
    }

    public List<Producto> traerProductos() {
        return productoj.findProductoEntities();
    }
    //________________TERRITORIO______________________________________

    public void crearTerritorio(Territorio territorio) {
         try {
             territorioj.create(territorio);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void eliminarTerritorio(int id) {
         try {
             territorioj.destroy(BigDecimal.ONE);
         } catch (IllegalOrphanException | NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void editarTerritorio(Territorio territorio) {
         try {
             territorioj.edit(territorio);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public Territorio traerTerritorio(int id) {
        return territorioj.findTerritorio(BigDecimal.ONE);
    }

    public List<Territorio> traerTerritorios() {
        return territorioj.findTerritorioEntities();
    }
    
    //________________VENDEDOR______________________________________

    public void crearVendedor(Vendedor vendedor) {
         try {
             vendedorj.create(vendedor);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void eliminarVendedor(int id) {
         try {
             vendedorj.destroy(BigDecimal.ONE);
         } catch (IllegalOrphanException | NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public void editarVendedor(Vendedor vendedor) {
         try {
             vendedorj.edit(vendedor);
         } catch (NonexistentEntityException ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    public Vendedor traerVendedor(int id) {
        return vendedorj.findVendedor(BigDecimal.ONE);
    }

    public List<Vendedor> traerVendedores() {
        return vendedorj.findVendedorEntities();
    }
    

}
