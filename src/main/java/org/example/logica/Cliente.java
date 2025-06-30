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
import java.awt.Font;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.swing.*;

@Entity
@Table(name = "Cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCustomerID", query = "SELECT c FROM Cliente c WHERE c.customerID = :customerID")})
public class Cliente implements Serializable, UsuarioNegocio {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CustomerID")
    private BigDecimal customerID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerID")
    private Collection<Pedido> pedidoCollection;

    public Cliente() {
    }
    
    public Cliente(BigDecimal customerID) {
        this.customerID = customerID;
    }

    public BigDecimal getCustomerID() {
        return customerID;
    }

    public void setCustomerID(BigDecimal customerID) {
        this.customerID = customerID;
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
        hash += (customerID != null ? customerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.customerID == null && other.customerID != null) || (this.customerID != null && !this.customerID.equals(other.customerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "customerID=" + customerID + '}';
}

    public void setNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  public Long getId() {
    return this.customerID != null ? this.customerID.longValue() : null;
}

// Y también implementa estos:
public String getNombre() {
    return "Cliente " + this.customerID; // O el campo real que tenga el nombre
}


@Override
    public void mostrarInterfaz() {
        JFrame frame = new JFrame("Interfaz Cliente - ID: " + customerID);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel("Bienvenido Cliente con ID: " + customerID, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));

        frame.getContentPane().add(label);

        frame.setVisible(true);
    }
    }

  
    
    

