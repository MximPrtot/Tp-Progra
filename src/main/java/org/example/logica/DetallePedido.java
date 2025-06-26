package org.example.logica;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "DetallePedido")
@NamedQueries({
    @NamedQuery(name = "DetallePedido.findAll", query = "SELECT d FROM DetallePedido d"),
    @NamedQuery(name = "DetallePedido.findByDetalleID", query = "SELECT d FROM DetallePedido d WHERE d.detalleID = :detalleID"),
    @NamedQuery(name = "DetallePedido.findByCantidad", query = "SELECT d FROM DetallePedido d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetallePedido.findByTotalEnvio", query = "SELECT d FROM DetallePedido d WHERE d.totalEnvio = :totalEnvio"),
    @NamedQuery(name = "DetallePedido.findByPrecioTotal", query = "SELECT d FROM DetallePedido d WHERE d.precioTotal = :precioTotal")})
public class DetallePedido implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DetalleID")
    private BigDecimal detalleID;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private BigDecimal cantidad;
    @Basic(optional = false)
    @Column(name = "TotalEnvio")
    private String totalEnvio;
    @Basic(optional = false)
    @Column(name = "PrecioTotal")
    private String precioTotal;
    @JoinColumn(name = "PedidoID", referencedColumnName = "PedidoID")
    @ManyToOne(optional = false)
    private Pedido pedidoID;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne(optional = false)
    private Producto productID;

    public DetallePedido() {
    }

    public DetallePedido(BigDecimal detalleID) {
        this.detalleID = detalleID;
    }

    public DetallePedido(BigDecimal detalleID, BigDecimal cantidad, String totalEnvio, String precioTotal) {
        this.detalleID = detalleID;
        this.cantidad = cantidad;
        this.totalEnvio = totalEnvio;
        this.precioTotal = precioTotal;
    }

    public BigDecimal getDetalleID() {
        return detalleID;
    }

    public void setDetalleID(BigDecimal detalleID) {
        this.detalleID = detalleID;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getTotalEnvio() {
        return totalEnvio;
    }

    public void setTotalEnvio(String totalEnvio) {
        this.totalEnvio = totalEnvio;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Pedido getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(Pedido pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Producto getProductID() {
        return productID;
    }

    public void setProductID(Producto productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleID != null ? detalleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePedido)) {
            return false;
        }
        DetallePedido other = (DetallePedido) object;
        if ((this.detalleID == null && other.detalleID != null) || (this.detalleID != null && !this.detalleID.equals(other.detalleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetallePedido{" +
            "detalleID=" + detalleID +
            ", cantidad=" + cantidad +
            ", totalEnvio=" + totalEnvio +
            ", precioTotal=" + precioTotal +
            ", pedidoID=" + (pedidoID != null ? pedidoID.getPedidoID() : null) +
            ", productID=" + (productID != null ? productID.getProductID() : null) +
            '}';
}


    
    
}
