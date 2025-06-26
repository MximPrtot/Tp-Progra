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
@Table(name = "Territorio")
@NamedQueries({
    @NamedQuery(name = "Territorio.findAll", query = "SELECT t FROM Territorio t"),
    @NamedQuery(name = "Territorio.findByTerritoryID", query = "SELECT t FROM Territorio t WHERE t.territoryID = :territoryID"),
    @NamedQuery(name = "Territorio.findByTerritory", query = "SELECT t FROM Territorio t WHERE t.territory = :territory")})
public class Territorio implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TerritoryID")
    private BigDecimal territoryID;
    @Basic(optional = false)
    @Column(name = "Territory")
    private String territory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "territoryID")
    private Collection<Pedido> pedidoCollection;

    public Territorio() {
    }

    public Territorio(BigDecimal territoryID) {
        this.territoryID = territoryID;
    }

    public Territorio(BigDecimal territoryID, String territory) {
        this.territoryID = territoryID;
        this.territory = territory;
    }

    public BigDecimal getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(BigDecimal territoryID) {
        this.territoryID = territoryID;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
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
        hash += (territoryID != null ? territoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Territorio)) {
            return false;
        }
        Territorio other = (Territorio) object;
        if ((this.territoryID == null && other.territoryID != null) || (this.territoryID != null && !this.territoryID.equals(other.territoryID))) {
            return false;
        }
        return true;
    }

    @Override
public String toString() {
    return "Territorio{" +
           "territoryID=" + territoryID +
           ", territory=" + territory +
           ", pedidoCollectionSize=" + (pedidoCollection != null ? pedidoCollection.size() : 0) +
           '}';
}


    
    
}
