package sample.repository;

import sample.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

public class UserRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insertUserInDB(int id,String username,String password,String firstName,String lastName){
        User user=new User(id,firstName,lastName,username,password);
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public  List<User> getAllUsersFromDB(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> users=entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return users;
    }
    public boolean usernameExistsInDB(String username){
        List<User> allUsers=getAllUsersFromDB();
        for(User user: allUsers){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    public int getNextIdFromDB(){
        List<User> allUsers=getAllUsersFromDB();
        return allUsers.size()+1;
    }
    public User retrieveUserFromDB(String username){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username='" + username + "'", User.class)
                    .getSingleResult();
            return user;
        }catch(NoResultException e){
            return null;
        }finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
    public String getUserPassword(String username){
        User user=retrieveUserFromDB(username);
        if(user==null){
            return null;
        }
        return user.getPassword();
    }

}
