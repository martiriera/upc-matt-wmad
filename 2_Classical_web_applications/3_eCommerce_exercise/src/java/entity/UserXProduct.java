/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author upcnet
 */
@Entity
@Table(name = "user_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT c FROM User c"),
//    @NamedQuery(name = "User.findByUserId", query = "SELECT c FROM User c WHERE c.userid = :userid"),
    @NamedQuery(name = "User.findById", query = "SELECT c FROM User c WHERE c.id = :id")})
public class UserXProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "userid")
    private Integer userid;
    
    @Basic(optional = false)
    @Column(name = "productid")
    private Integer productid;
    
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user_product")
//  private Collection<Product> productCollection;

    public UserXProduct() {
    }

    public UserXProduct(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userid;
    }

    public Integer getProductId() {
        return productid;
    }

    public void setUserId(Integer userid) {
        this.userid = userid;
    }
    
    public void setProductId(Integer productid) {
        this.productid = productid;
    }

//  @XmlTransient
//  public Collection<Product> getProductCollection() {
//    return productCollection;
//  }
//
//  public void setProductCollection(Collection<Product> productCollection) {
//    this.productCollection = productCollection;
//  }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserXProduct)) {
            return false;
        }
        UserXProduct other = (UserXProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + id + " ]";
    }

}
