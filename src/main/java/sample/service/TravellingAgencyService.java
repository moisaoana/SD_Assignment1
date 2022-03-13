package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Package;
import sample.model.Status;
import sample.model.User;
import sample.model.Warning;
import sample.repository.TravellingAgencyRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;

public class TravellingAgencyService {
    private TravellingAgencyRepository travellingAgencyRepository=new TravellingAgencyRepository();

    public Warning addNewDestination(String name){
        if(!name.isEmpty()) {
            if(!travellingAgencyRepository.destinationExistsInDB(name)){
               travellingAgencyRepository.insertNewDestinationInDB(name);
               return Warning.SUCCESS;
            }else{
                return Warning.DUPLICATE; //name exists
            }
        }else{
            return Warning.EMPTY_FIELDS; //empty field
        }
    }
    public List<String> getAllDestinations(){
       return  travellingAgencyRepository.getStringDestinations();
    }
    private boolean validatePackageInfo(String destination,String name, String price, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear, String details,String capacity){
        return !destination.isEmpty() && !name.isEmpty() && !price.isEmpty() && !startDay.isEmpty() && !startMonth.isEmpty() && !startYear.isEmpty() && !endDay.isEmpty() && !endMonth.isEmpty() && !endYear.isEmpty() && !details.isEmpty() && !capacity.isEmpty();
    }
    private boolean isDay(int day, int month){
        if(day>=1 && day<=31){
            if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
                return true;
            }else if(month==2){
                return day <= 29;
            }else if(month==4 || month==6 || month==9 || month==11){
                return day<=30;
            }
        }
        return false;
    }
    private boolean isMonth(int month){
        return month >= 1 && month <= 12;
    }
    private boolean isYear(int year){
        return year >= 2000 && year <= 2023;
    }
    private boolean isDate(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear){
        if(endYear<startYear){
            return false;
        }else{
            if(startYear==endYear){
                if(startMonth>endMonth){
                    return false;
                }else{
                    if(startMonth==endMonth){
                        if(startDay>endDay){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    public Warning addNewPackage(String destination,String name, String price, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear, String details,String capacity, boolean edit, Status status, int currCap, int id)
    {
        if(validatePackageInfo(destination,name,price,startDay,startMonth,startYear,endDay,endMonth,endYear,details,capacity)) {
            double packagePrice;
            try{
                packagePrice=Double.parseDouble(price);
            }catch(NumberFormatException numberFormatException){
                return Warning.INVALID_PRICE;
            }
            int startDayP,startMonthP,startYearP,endDayP,endMonthP,endYearP,capacityP;
            try{
                startDayP= Integer.parseInt(startDay);
                startMonthP=Integer.parseInt(startMonth);
                startYearP=Integer.parseInt(startYear);
                endDayP=Integer.parseInt(endDay);
                endMonthP=Integer.parseInt(endMonth);
                endYearP=Integer.parseInt(endYear);
            }catch(NumberFormatException numberFormatException){
                return Warning.INVALID_DATE;
            }
            if(!isYear(startYearP) || !isYear(endYearP)){
                return Warning.INVALID_YEAR;
            }else{
                if(!isMonth(startMonthP)|| !isMonth(endMonthP)){
                    return Warning.INVALID_MONTH;
                }else{
                    if(!isDay(startDayP,startMonthP) || !isDay(endDayP,endMonthP)){
                        return Warning.INVALID_DAY;
                    }else {
                        if (!isDate(startDayP, startMonthP, startYearP, endDayP, endMonthP, endYearP)) {
                            return Warning.INVALID_DATE;
                        } else {
                            try {
                                capacityP = Integer.parseInt(capacity);
                            } catch (NumberFormatException numberFormatException) {
                                return Warning.INVALID_CAPACITY;
                            }
                            //verify if name is unique
                            //valid data
                            if (!edit) {
                                if (travellingAgencyRepository.packageExistsInDB(name)) {
                                    return Warning.DUPLICATE;
                                } else {
                                    travellingAgencyRepository.insertPackageInDB(destination, name, packagePrice, startDayP, startMonthP, startYearP, endDayP, endMonthP, endYearP, details, capacityP);
                                    return Warning.SUCCESS;
                                }
                            } else {
                                if (capacityP < currCap) {
                                    return Warning.INVALID_CAPACITY;
                                } else {
                                    if (capacityP == currCap) {
                                        status = Status.BOOKED;
                                    } else {
                                        if (status == Status.BOOKED) {
                                            status = Status.IN_PROGRESS;
                                        }
                                    }
                                    travellingAgencyRepository.modifyPackage(destination, name, packagePrice, startDayP, startMonthP, startYearP, endDayP, endMonthP, endYearP, details, capacityP, status, currCap, id);
                                    return Warning.SUCCESS;
                                }
                            }
                        }
                    }
                }
            }
        }else{
            return Warning.EMPTY_FIELDS;
        }
    }

    public ObservableList<Package> getAllPackages() {
        List<Package> allPack=travellingAgencyRepository.getAllPackagesFromDB();
        return FXCollections.observableArrayList(allPack);
    }
    public ObservableList<Package> getAvailablePackages() {
        List<Package> allPack=travellingAgencyRepository.getAllPackagesFromDB();
        ObservableList<Package> avPackages=FXCollections.observableArrayList();
        for(Package p: allPack){
            if(p.getStatus()!= Status.BOOKED){
                avPackages.add(p);
            }
        }
        return avPackages;
    }

    public void deletePackage(int id){
        travellingAgencyRepository.deletePackageFromDB(id);
    }
    public void deleteDestination(String destination){
        travellingAgencyRepository.deleteDestinationFromDB(destination);
    }
    public boolean bookPackage(User user, Package p){
        if(p.getStatus()!=Status.BOOKED){
            if(p.getCurrentCapacity()==0){
                p.setStatus(Status.IN_PROGRESS);
            }
            p.setCurrentCapacity(p.getCurrentCapacity()+1);
            if(p.getCurrentCapacity()==p.getMaxCapacity()){
                p.setStatus(Status.BOOKED);
            }
            System.out.println(p.getId());

            boolean result=travellingAgencyRepository.bookPackage(user,p);
            if(result){
                travellingAgencyRepository.editPackage(p);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
