package sample.service;

import sample.model.Warning;
import sample.repository.TravellingAgencyRepository;

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
}
