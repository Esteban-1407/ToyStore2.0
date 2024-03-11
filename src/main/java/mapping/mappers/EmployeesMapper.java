package mapping.mappers;

import mapping.dtos.EmployeesDTO;
import model.employees;

import java.sql.Date;

public class EmployeesMapper {
    public static EmployeesDTO mapFromModel(employees employees){
        return new EmployeesDTO(employees.getUser(),employees.getPassword(),employees.getEmployment_start_date());

    }
    public static employees mapFromDTO(EmployeesDTO dto){
        return employees.builder()
                .user(dto.user())
                .password(dto.password())
                .employment_start_date((Date) dto.employment_start_date())
                .build();
    }

}
