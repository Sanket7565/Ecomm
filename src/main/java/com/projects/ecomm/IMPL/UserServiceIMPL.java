package com.projects.ecomm.IMPL;

import com.projects.ecomm.DTO.UserResponse;
import com.projects.ecomm.DTO.UserRequest;
import com.projects.ecomm.Model.User;
import com.projects.ecomm.Repository.UserRepo;
import com.projects.ecomm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepo repo;

    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = repo.findAll();
        List<UserResponse> result = new ArrayList<>();

        for (User u : users) {
            UserResponse w = new UserResponse();
            w.setFName(u.getFName());
            w.setLName(u.getLName());
            w.setId(u.getId());
            w.setAddresses(u.getAddresses());
            result.add(w);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<String> addUser(User user) {
        //user.setRole("USER");
        repo.save(user);
        return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<UserResponse> getUserById(Long id) {
        Optional<User> opt = repo.findById(id);
        if (opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User u = opt.get();
        UserResponse w = new UserResponse();
        w.setFName(u.getFName());
        w.setLName(u.getLName());
        w.setId(u.getId());
        w.setAddresses(u.getAddresses());
        return new ResponseEntity<>(w, HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(Long id, UserRequest req) {
        Optional<User> opt = repo.findById(id);
        if (opt.isEmpty()) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }

        User user = opt.get();
        if (req.getFName() != null) user.setFName(req.getFName());
        if (req.getLName() != null) user.setLName(req.getLName());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getPassword() != null) user.setPassword(req.getPassword());

        repo.save(user);
        return new ResponseEntity<>("User Updated Successfully", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deleteUser(Long id) {
        Optional<User> opt = repo.findById(id);
        if (opt.isEmpty()) {
            return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }

        repo.deleteById(id);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.GONE);
    }

}




