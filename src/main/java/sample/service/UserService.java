package sample.service;

import sample.model.User;
import sample.repository.UserRepository;

import java.util.List;

public class UserService {
    UserRepository userRepository=new UserRepository();

    private boolean areFieldsNonEmpty(String username,String password, String firstName, String lastName){
        return !(username.isEmpty() | password.isEmpty()) && !firstName.isEmpty() && !lastName.isEmpty();
    }
    public int registerUser(String username,String password, String firstName, String lastName){
       if(areFieldsNonEmpty(username,password,firstName,lastName)){
           if(userRepository.usernameExistsInDB(username)){
               return 1;
           }else{
               int id= userRepository.getNextIdFromDB();
               userRepository.insertUserInDB(id,username,password,firstName,lastName);
               return 0;
           }
       }else{
           return 2;
       }
    }
    public int loginUser(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return 3;
        }else {
            String passwordFromDB = userRepository.getUserPassword(username);
            if (passwordFromDB == null) {
                return 1; //user with given username doesn't exist
            } else { //username exists
                if (password.equals(passwordFromDB)) {
                    return 0;
                } else {
                    return 2;
                }
            }
        }
    }

}
