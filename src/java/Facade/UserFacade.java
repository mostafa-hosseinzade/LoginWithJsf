/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import Model.User;

@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "LoginJsfPU")
    private EntityManager em;

    public UserFacade() {
        super(User.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public User findByUsername(String username) {
        try {
            System.out.println("ddddddddddd"+username);
            return (User) em.createQuery("select U From User AS U WHERE U.username=:username").setParameter("username", username).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
