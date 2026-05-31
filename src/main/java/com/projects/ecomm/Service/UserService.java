package com.projects.ecomm.Service;

import com.projects.ecomm.DTO.FetchUserRequest;
import com.projects.ecomm.DTO.UserUpdateRequest;
import com.projects.ecomm.Model.User;
import com.projects.ecomm.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    @Autowired
    User user;
    @Autowired
    private FetchUserRequest fetchUserRequest;

    public ResponseEntity<List<FetchUserRequest>> getAllUsers() {
        Optional<List<User>> UserFromDB = Optional.of(repo.findAll()); // Fetch the user from the database using the provided ID

        List<User> user = UserFromDB.get(); // Check if the fetched user's ID matches the provided ID and create a list containing the user if it does, otherwise set it to null
        ArrayList<FetchUserRequest> fetchUserRequest = new ArrayList<>(); //  Create an ArrayList to hold UserWrapper objects

        for (User u : user) {
            FetchUserRequest uWrapper = new FetchUserRequest(); // Create a new instance of UserWrapper for each user
            uWrapper.setFName(u.getFName());
            uWrapper.setLName(u.getLName());
            uWrapper.setId(u.getId());
            fetchUserRequest.add(uWrapper);
        }

        return new ResponseEntity<>(fetchUserRequest, HttpStatus.OK);
    }


    public ResponseEntity<String> addUser(User user) {
        user.setRole("USER");
        repo.save(user);
        return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<FetchUserRequest> getUserById(Long id) {

        User user1 = repo.findById(id).get();

        if (user1 != null) {
            Optional<User> UserFromDB = repo.findById(id); // Fetch the user from the database using the provided ID
            List<User> user = UserFromDB.get().getId().equals(id) ? List.of(UserFromDB.get()) : null; // Check if the fetched user's ID matches the provided ID and create a list containing the user if it does, otherwise set it to null
            ArrayList<FetchUserRequest> fetchUserRequest = new ArrayList<>(); //  Create an ArrayList to hold UserWrapper objects

            FetchUserRequest uWrapper = new FetchUserRequest(); // Create a new instance of UserWrapper

            for (User u : user) {
                uWrapper.setFName(u.getFName());
                uWrapper.setLName(u.getLName());
                uWrapper.setId(u.getId());
            }

            fetchUserRequest.add(uWrapper);
            return new ResponseEntity<>(uWrapper, HttpStatus.FOUND);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> updateUser(Long id, UserUpdateRequest user1) {
        User user = repo.findById(id).get();

        if (user != null) {
            Optional<User> userFromDB = repo.findById(id); // Fetch the user from the database using the provided ID

          if(user1.getFName()!=null) { user.setFName(user1.getFName());}
          if(user1.getLName()!=null) { user.setLName(user1.getLName());}
          if(user1.getEmail()!=null) { user.setEmail(user1.getEmail());}
          if(user1.getPassword()!=null) { user.setPassword(user1.getPassword());}

            repo.save(user);
            return new ResponseEntity<>("User Updated Successfully", HttpStatus.ACCEPTED);

        } else {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteUser(Long id) {
        User user = repo.findById(id).get();

        try {

            repo.deleteById(id);
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.GONE);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);


    }
    }
}




