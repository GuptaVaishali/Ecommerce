package com.ttn.project.ecommerce.entities.order;

import com.ttn.project.ecommerce.enums.FromStatus;
import com.ttn.project.ecommerce.enums.ToStatus;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_STATUS")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FROM_STATUS")
    @Enumerated(EnumType.STRING)
    private FromStatus fromStatus;

    @Column(name = "TO_STATUS")
    @Enumerated(EnumType.STRING)
    private ToStatus toStatus;

    @Column(name = "TRANSITION_NOTES_COMMENTS")
    private String transitionNotesComments;

    @OneToOne
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct;
}
