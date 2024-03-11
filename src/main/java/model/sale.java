package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class sale {
    private int id;
    private cliente cliente;
    private employees employees;


}
