package lk.ijse.gdse67.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Customer {
    private String id;
    private String name;
    private String city;
    private String telephone;
}
