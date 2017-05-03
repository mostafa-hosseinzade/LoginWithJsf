/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author davood
 */
@Entity
@Table(name = "User")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    
    @Size(max = 512)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 512)
    @Column(name = "SALT")
    private String salt;
    @Column(name="status")
    private Integer status;
    @ElementCollection
    private Map<String,String> roles = new Hashtable<>();
    @Transient
    private String plainPassword;
   
    
    public boolean hasRole(String role){
        return roles.containsKey(role);
    }
    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Map<String, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, String> roles) {
        this.roles = roles;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
        if (plainPassword != null && !this.plainPassword.isEmpty()) {
            salt = UUID.randomUUID().toString();
            password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(salt + "(" + plainPassword + ")" + salt);
        }
    }

    public boolean checkPassword(String plainPassword){
        String pass=org.apache.commons.codec.digest.DigestUtils.sha256Hex(salt + "(" + plainPassword + ")" + salt);
        return pass.equals(password);
    }
}
