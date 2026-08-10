package com.projects.ecomm.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Orders
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItems>items;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
