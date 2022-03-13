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
        //return allDest.get(allDest.size()-1).getId()+1;
        return getMaxId(allDest)+1;
    }

    public int getNextIdPackageFromDB(){
        List<Package> allPack=getAllPackagesFromDB();
        if(allPack.size()==0){
            return 1;
        }else {
            return allPack.get(allPack.size() - 1).getId() + 1;
        }
    }
    public int getMaxId( List<Destination> list){
        int max=0;
        for(Destination d: list){
            if(d.getId()>max){
                max=d.getId();
            }
        }
        return max;
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
        entityManager.createQuery("DELETE FROM Package p WHERE p.destination= :id")
           .setParameter("id",d).executeUpdate();

        //entityManager.remove(entityManager.contains(d) ? d : entityManager.merge(d));

        entityManager.createQuery("DELETE FROM Destination d WHERE d.id= :id")
                .setParameter("id",d.getId()).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void bookPackage(User user, Package p){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

       // List<Package> packageList=user.getPackages();
       //packageList.add(p);
       // user.setPackages(packageList);
        //entityManager.merge(user);
        //entityManager.flush();
        User u1=entityManager.find(User.class,user.getId());
        Package p1=entityManager.find(Package.class,p.getId());
        u1.getPackages().add(p1);
        //entityManager.merge(u1);
        entityManager.persist(u1);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void editPackage(Package p){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        System.out.println(p.getId());
        entityManager.merge(p);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void modifyPackage(String dest, String name, double price, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, String details, int capacity, Status status, int currCap,int id){
        LocalDate startDate=LocalDate.of(startYear,startMonth,startDay);
        LocalDate endDate=LocalDate.of(endYear,endMonth,endDay);
        Destination destination=getDestinationFromName(dest);
        Package newPackage= new Package(id,name,price,startDate,endDate,details,status,capacity,currCap,destination);
        System.out.println(id);
        System.out.println(name);
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(details);

        EntityManager entityManager=entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(newPackage);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
