package br.com.brunokayser.myfood.cadastro.dto;

import com.br.brunokayser.myfood.cadastro.domain.Restaurant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_menu")
public class MenuDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    private String name;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private RestaurantDto restaurant;

}
