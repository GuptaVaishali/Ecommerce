package com.ttn.project.ecommerce.entities.order;

import com.ttn.project.ecommerce.entities.registration.Customer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Amount_Paid")
    private double amountPaid;
    private Date createdDate;

    @Column(name = "Payment_Method", length = 30)
    private String paymentMethod;

    @Column(name = "Customer_Address_City", length = 30)
    private String customerAddressCity;

    @Column(name = "Customer_Address_State", length = 30)
    private String customerAddressState;

    @Column(name = "Customer_Address_Country", length = 30)
    private String customerAddressCountry;

    @Column(name = "Customer_Address_Line", length = 50)
    private String customerAddressLine;

    @Column(name = "Customer_Zip_Code")
    private int customerZipCode;

    @Column(name = "Customer_Address_Label", length = 30)
    private String CustomerAddressLabel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Customer_User_Id")
    private Customer customer;

    //mapping for order_product table
    @OneToMany(mappedBy = "order")
    List<OrderProduct> orderProducts;
}
