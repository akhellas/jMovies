package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "FAVORITE_LIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FavoriteList.findAll", query = "SELECT f FROM FavoriteList f")
    , @NamedQuery(name = "FavoriteList.findById", query = "SELECT f FROM FavoriteList f WHERE f.id = :id")
    , @NamedQuery(name = "FavoriteList.findByName", query = "SELECT f FROM FavoriteList f WHERE f.name = :name")})
public class FavoriteList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @OneToMany(mappedBy = "favoriteListId")
    private Collection<Movie> movieCollection;

    public FavoriteList() {
    }

    public FavoriteList(Integer id) {
        this.id = id;
    }

    public FavoriteList(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Movie> getMovieCollection() {
        return movieCollection;
    }

    public void setMovieCollection(Collection<Movie> movieCollection) {
        this.movieCollection = movieCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FavoriteList)) {
            return false;
        }
        FavoriteList other = (FavoriteList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FavoriteList[ id=" + id + " ]";
    }
    
}
