package Backend.ExpenseManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseResponse {

    private Long id;
    private String title;
    private Double amount;
}
