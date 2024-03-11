package model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class cliente {
    int id;
    private String name;
    private String IDnumber;
    private Date date_birth;


}
