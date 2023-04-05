package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private int id;
    private String name;
    private String email;
    private Date registeredDate;
    private int companyId;

}
