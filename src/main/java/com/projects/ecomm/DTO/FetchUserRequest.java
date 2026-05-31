package com.projects.ecomm.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchUserRequest
{
    private Long id;
    private String fName;
    private String lName;


}
