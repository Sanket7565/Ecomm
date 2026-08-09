package com.projects.ecomm.Service;

import com.projects.ecomm.DTO.UserResponse;
import com.projects.ecomm.DTO.UserRequest;
import com.projects.ecomm.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService 
{
    ResponseEntity<String> addUser(User user);

    ResponseEntity<List<UserResponse>> getAllUsers();

    ResponseEntity<UserResponse> getUserById(Long id);

    ResponseEntity<String> updateUser(Long id, UserRequest user);

    ResponseEntity<String> deleteUser(Long id);
}
