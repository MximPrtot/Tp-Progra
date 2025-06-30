package org.example.logica;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "Producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByProductID", query = "SELECT p FROM Producto p WHERE p.productID = :productID"),
    @NamedQuery(name = "Producto.findByProduct", query = "SELECT p FROM Producto p WHERE p.product = :product"),
    @NamedQuery(name = "Producto.findByProductSubCategory", query = "SELECT p FROM Producto p WHERE p.productSubCategory = :productSubCategory"),
    @NamedQuery(name = "Producto.findByProductCategory", query = "SELECT p FROM Producto p WHERE p.productCategory = :productCategory"),
    @NamedQuery(name = "Producto.findByPrecioIndividual", query = "SELECT p FROM Producto p WHERE p.precioIndividual = :precioIndividual")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ProductID")
    private BigDecimal productID;
    @Basic(optional = false)
    @Column(name = "Product")
    private String product;
    @Basic(optional = false)
    @Column(name = "ProductSubCategory")
    private String productSubCategory;
    @Basic(optional = false)
    @Column(name = "ProductCategory")
    private String productCategory;
    @Basic(optional = false)
    @Column(name = "PrecioIndividual")
    private String precioIndividual;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productID")
    private Collection<DetallePedido> detallePedidoCollection;

    public Producto() {
    }

    public Producto(BigDecimal productID) {
        this.productID = productID;
    }

    public Producto(BigDecimal productID, String product, String productSubCategory, String productCategory, String precioIndividual) {
        this.productID = productID;
        this.product = product;
        this.productSubCategory = productSubCategory;
        this.productCategory = productCategory;
        this.precioIndividual = precioIndividual;
    }

    public BigDecimal getProductID() {
        return productID;
    }

    public void setProductID(BigDecimal productID) {
        this.productID = productID;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductSubCategory() {
        return productSubCategory;
    }

    public void setProductSubCategory(String productSubCategory) {
        this.productSubCategory = productSubCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getPrecioIndividual() {
        return precioIndividual;
    }

    public void setPrecioIndividual(String precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    public Collection<DetallePedido> getDetallePedidoCollection() {
        return detallePedidoCollection;
    }

    public void setDetallePedidoCollection(Collection<DetallePedido> detallePedidoCollection) {
        this.detallePedidoCollection = detallePedidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productID != null ? productID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.productID == null && other.productID != null) || (this.productID != null && !this.productID.equals(other.productID))) {
            return false;
        }
        return true;
    }

    @Override
public String toString() {
    return "Producto{" +
           "productID=" + productID +
           ", product=" + product +
           ", productSubCategory=" + productSubCategory +
           ", productCategory=" + productCategory +
           ", precioIndividual=" + precioIndividual +
           ", detallePedidoCollection=" + detallePedidoCollection +
           '}';
}

    public void setNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 public Long getId() {
    return this.productID != null ? this.productID.longValue() : null;
}

public String getNombre() {
    return this.product; // Usa el campo que contiene el nombre
}

public Double getPrecio() {
    try {
        return Double.parseDouble(this.precioIndividual);
    } catch (NumberFormatException e) {
        return 0.0;
    }
}

    public Object getStock() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    


    
    
}
