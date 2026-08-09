package com.projects.ecomm.Controller;

import com.projects.ecomm.DTO.UserResponse;
import com.projects.ecomm.DTO.UserRequest;
import com.projects.ecomm.Model.User;
import com.projects.ecomm.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")


public class UserController
{
    @Autowired
    UserService service;
    @Autowired
    User user;
    @Autowired
    UserResponse wrapper;

    @PostMapping("/addUser")
    public ResponseEntity <String> addUser(@RequestBody User user)
    {
        return service.addUser(user);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>>getAllUsers()
    {
        return service.getAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
    {
        return service.getUserById(id);
    }

    @PatchMapping("updateUser/{id}")
    public ResponseEntity <String> updateUser(@PathVariable Long id,@RequestBody UserRequest user) {return service.updateUser(id,user);}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity <String> deleteUserById(@PathVariable Long id)
    {
        return service.deleteUser(id);
    }


}
