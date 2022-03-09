package sample.service;

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
    public Warning addNewPackage(String destination,String name, String price, String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear, String details,String capacity)
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
                    }else{
                        try{
                            capacityP=Integer.parseInt(capacity);
                        }catch(NumberFormatException numberFormatException){
                            return Warning.INVALID_CAPACITY;
                        }
                        //verify if name is unique
                        //valid data
                        if(travellingAgencyRepository.packageExistsInDB(name)){
                            return Warning.DUPLICATE;
                        }else {
                            travellingAgencyRepository.insertPackageInDB(destination, name, packagePrice, startDayP, startMonthP, startYearP, endDayP, endMonthP, endYearP, details, capacityP);
                            return Warning.SUCCESS;
                        }
                    }
                }
            }
        }else{
            return Warning.EMPTY_FIELDS;
        }
    }

}
