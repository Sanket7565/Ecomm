package com.projects.ecomm.DTO;

import com.projects.ecomm.Model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse
{
    private Long id;
    private String fName;
    private String lName;
    private List<Address> addresses;
}
