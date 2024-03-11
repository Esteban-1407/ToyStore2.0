package mapping.dtos;

import lombok.Builder;

import java.util.Date;
@Builder
public record ClienteDTO ( String name, String iDnumber, Date date_birth){
}
