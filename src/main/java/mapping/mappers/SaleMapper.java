package mapping.mappers;

import mapping.dtos.SaleDTO;
import model.sale;

public class SaleMapper {
    public static SaleDTO mapFromModel(sale sale){
        return new SaleDTO(sale.getCliente(),sale.getEmployees());
    }
    public static sale mapFromDTO(SaleDTO dto){
        return sale.builder()
                .cliente(dto.cliente())
                .employees(dto.employees())
                .build();
    }
}
