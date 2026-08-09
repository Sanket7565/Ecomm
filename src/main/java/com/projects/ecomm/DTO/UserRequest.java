package com.projects.ecomm.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;
@Component
@Data
public class UserRequest
{

        private String fName;
        private String lName;
        private String email;
        private String password;

    }
