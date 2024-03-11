package mapping.dtos;

import lombok.Builder;

import java.util.Date;
@Builder
public record EmployeesDTO ( String user, String password, Date employment_start_date){
}
