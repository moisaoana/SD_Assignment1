package sample.service;

import sample.model.User;
import sample.model.Warning;
import sample.repository.UserRepository;

import java.util.List;

public class UserService {
    UserRepository userRepository=new UserRepository();

    private boolean areFieldsNonEmpty(String username,String password, String firstName, String lastName){
        return !(username.isEmpty() | password.isEmpty()) && !firstName.isEmpty() && !lastName.isEmpty();
    }
    public Warning registerUser(String username, String password, String firstName, String lastName){
       if(areFieldsNonEmpty(username,password,firstName,lastName)){
           if(userRepository.usernameExistsInDB(username)){
               return Warning.DUPLICATE; //username already exists
           }else{
               int id= userRepository.getNextIdFromDB();
               userRepository.insertUserInDB(id,username,password,firstName,lastName);
               return Warning.SUCCESS;
           }
       }else{
           return Warning.EMPTY_FIELDS; //some fields are empty
       }
    }
    public Warning loginUser(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return Warning.EMPTY_FIELDS; //some fields are empty
        }else {
            String passwordFromDB = userRepository.getUserPassword(username);
            if (passwordFromDB == null) {
                return Warning.NOT_FOUND; //user with given username doesn't exist
            } else { //username exists
                if (password.equals(passwordFromDB)) {
                    return Warning.SUCCESS;
                } else {
                    return Warning.WRONG_PASS; //passwords don't match
                }
            }
        }
    }

}
