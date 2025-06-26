package org.example.logica;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Pedido")
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByPedidoID", query = "SELECT p FROM Pedido p WHERE p.pedidoID = :pedidoID"),
    @NamedQuery(name = "Pedido.findByFechaPedido", query = "SELECT p FROM Pedido p WHERE p.fechaPedido = :fechaPedido"),
    @NamedQuery(name = "Pedido.findByTotalImpuestos", query = "SELECT p FROM Pedido p WHERE p.totalImpuestos = :totalImpuestos"),
    @NamedQuery(name = "Pedido.findByTotalGeneral", query = "SELECT p FROM Pedido p WHERE p.totalGeneral = :totalGeneral")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PedidoID")
    private BigDecimal pedidoID;
    @Basic(optional = false)
    @Column(name = "FechaPedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;
    @Basic(optional = false)
    @Column(name = "TotalImpuestos")
    private BigDecimal totalImpuestos;
    @Basic(optional = false)
    @Column(name = "TotalGeneral")
    private BigDecimal totalGeneral;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoID")
    private Collection<DetallePedido> detallePedidoCollection;
    @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
    @ManyToOne(optional = false)
    private Cliente customerID;
    @JoinColumn(name = "StatusID", referencedColumnName = "StatusID")
    @ManyToOne(optional = false)
    private Estado statusID;
    @JoinColumn(name = "TerritoryID", referencedColumnName = "TerritoryID")
    @ManyToOne(optional = false)
    private Territorio territoryID;
    @JoinColumn(name = "VendedorID", referencedColumnName = "SalesPersonID")
    @ManyToOne(optional = false)
    private Vendedor vendedorID;

    public Pedido() {
    }

    public Pedido(BigDecimal pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Pedido(BigDecimal pedidoID, Date fechaPedido, BigDecimal totalImpuestos, BigDecimal totalGeneral) {
        this.pedidoID = pedidoID;
        this.fechaPedido = fechaPedido;
        this.totalImpuestos = totalImpuestos;
        this.totalGeneral = totalGeneral;
    }

    public BigDecimal getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(BigDecimal pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public BigDecimal getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(BigDecimal totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    public BigDecimal getTotalGeneral() {
        return totalGeneral;
    }

    public void setTotalGeneral(BigDecimal totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    public Collection<DetallePedido> getDetallePedidoCollection() {
        return detallePedidoCollection;
    }

    public void setDetallePedidoCollection(Collection<DetallePedido> detallePedidoCollection) {
        this.detallePedidoCollection = detallePedidoCollection;
    }

    public Cliente getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Cliente customerID) {
        this.customerID = customerID;
    }

    public Estado getStatusID() {
        return statusID;
    }

    public void setStatusID(Estado statusID) {
        this.statusID = statusID;
    }

    public Territorio getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(Territorio territoryID) {
        this.territoryID = territoryID;
    }

    public Vendedor getVendedorID() {
        return vendedorID;
    }

    public void setVendedorID(Vendedor vendedorID) {
        this.vendedorID = vendedorID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidoID != null ? pedidoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.pedidoID == null && other.pedidoID != null) || (this.pedidoID != null && !this.pedidoID.equals(other.pedidoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "pedidoID=" + pedidoID +
            ", fechaPedido=" + fechaPedido +
            ", totalImpuestos=" + totalImpuestos +
            ", totalGeneral=" + totalGeneral +
            ", detallePedidos=" + (detallePedidoCollection != null ? detallePedidoCollection.size() : 0) +
            ", clienteID=" + (customerID != null ? customerID.getCustomerID() : null) +
            ", estadoID=" + (statusID != null ? statusID.getStatusID() : null) +
            ", territorioID=" + (territoryID != null ? territoryID.getTerritoryID() : null) +
            ", vendedorID=" + (vendedorID != null ? vendedorID.getSalesPersonID() : null) +
            '}';
}

  public Long getId() {
    return this.pedidoID != null ? this.pedidoID.longValue() : null;
}

public Cliente getCliente() {
    return this.customerID;
}

public Producto getProducto() {
    // Necesitarás implementar esto según tu modelo
    // Si no hay relación directa, puedes obtenerlo del primer detalle
    if (this.detallePedidoCollection != null && !this.detallePedidoCollection.isEmpty()) {
        return this.detallePedidoCollection.iterator().next().getProductID();
    }
    return null;
}

 public int getCantidad() {
    if (this.detallePedidoCollection != null && !this.detallePedidoCollection.isEmpty()) {
        return this.detallePedidoCollection.stream()
            .mapToInt(d -> d.getCantidad().intValue())
            .sum();
    }
    return 0;
}

    public Object getClsiente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
    
}
    