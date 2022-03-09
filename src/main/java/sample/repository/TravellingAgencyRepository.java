package sample.repository;

import sample.model.Destination;
import sample.model.User;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TravellingAgencyRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");
    public void insertNewDestinationInDB(String name){
        int id=getNextIdFromDB();
        Destination destination=new Destination(id,name);
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(destination);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public List<Destination> getAllDestinationsFromDB(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Destination> dest=entityManager.createQuery("SELECT d FROM Destination d", Destination.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return dest;
    }
    public boolean destinationExistsInDB(String name){
        List<Destination> allDest=getAllDestinationsFromDB();
        for(Destination dest: allDest){
            if(dest.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public int getNextIdFromDB(){
        List<Destination> allDest=getAllDestinationsFromDB();
        return allDest.size()+1;
    }
}
