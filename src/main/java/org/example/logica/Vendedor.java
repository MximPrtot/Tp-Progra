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
@Table(name = "Vendedor")
@NamedQueries({
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v"),
    @NamedQuery(name = "Vendedor.findBySalesPersonID", query = "SELECT v FROM Vendedor v WHERE v.salesPersonID = :salesPersonID")})
public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "SalesPersonID")
    private BigDecimal salesPersonID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedorID")
    private Collection<Pedido> pedidoCollection;

    public Vendedor() {
    }

    public Vendedor(BigDecimal salesPersonID) {
        this.salesPersonID = salesPersonID;
    }

    public BigDecimal getSalesPersonID() {
        return salesPersonID;
    }

    
    public void setSalesPersonID(BigDecimal salesPersonID) {
        this.salesPersonID = salesPersonID;
    }

    public Collection<Pedido> getPedidoCollection() {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection) {
        this.pedidoCollection = pedidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salesPersonID != null ? salesPersonID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.salesPersonID == null && other.salesPersonID != null) || (this.salesPersonID != null && !this.salesPersonID.equals(other.salesPersonID))) {
            return false;
        }
        return true;
    }

    @Override
public String toString() {
    return "Vendedor{" +
           "salesPersonID=" + salesPersonID + '}';
}



    
    
}
