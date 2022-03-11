package sample.repository;

import javafx.collections.ObservableList;
import sample.model.Destination;
import sample.model.Package;
import sample.model.Status;
import sample.model.User;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TravellingAgencyRepository {
    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ro.tutorial.lab.SD");

    public void insertNewDestinationInDB(String name){
        int id= getNextIdDestinationFromDB();
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
    public Destination getDestinationFromName(String name){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Destination> dest=entityManager.createQuery("SELECT d FROM Destination d WHERE d.name=?1", Destination.class)
                .setParameter(1,name)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return dest.get(0);
    }
    public List<Package> getAllPackagesFromDB(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Package> pack=entityManager.createQuery("SELECT p FROM Package p", Package.class)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return pack;
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
    public int getNextIdDestinationFromDB(){
        List<Destination> allDest=getAllDestinationsFromDB();
        return allDest.size()+1;
    }

    public int getNextIdPackageFromDB(){
        List<Package> allPack=getAllPackagesFromDB();
        return allPack.get(allPack.size()-1).getId()+1;
    }

    public List<String> getStringDestinations(){
        List<Destination> allDest=getAllDestinationsFromDB();
        return allDest.stream().map(Destination::getName).collect(Collectors.toList());
    }
    public void insertPackageInDB(String dest, String name, double price, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, String details, int capacity){
        LocalDate startDate=LocalDate.of(startYear,startMonth,startDay);
        LocalDate endDate=LocalDate.of(endYear,endMonth,endDay);
        int id=getNextIdPackageFromDB();
        Destination destination=getDestinationFromName(dest);
        Package newPackage= new Package(id,name,price,startDate,endDate,details, Status.NOT_BOOKED,capacity,0,destination);

        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        destination.getPackages().add(newPackage);
        entityManager.persist(newPackage);
        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public boolean packageExistsInDB(String name){
        List<Package> allPack=getAllPackagesFromDB();
        for(Package pack: allPack){
            if(pack.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public void deletePackageFromDB(int id){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Package p WHERE p.id= :id")
                .setParameter("id",id).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void deleteDestinationFromDB(String destination){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Destination d=getDestinationFromName(destination);
        entityManager.remove(entityManager.contains(d) ? d : entityManager.merge(d));;
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void bookPackage(User user, Package p){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        user.getPackages().add(p);
        entityManager.merge(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void editPackage(Package p){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(p);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
