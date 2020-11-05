package com.br.brunokayser.myfood.cadastro.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;
    private String name;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;


}
