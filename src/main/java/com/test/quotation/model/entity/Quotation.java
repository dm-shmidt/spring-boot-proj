package com.test.quotation.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "quotation",
        uniqueConstraints = {
                @UniqueConstraint(name = "customer_id_fk", columnNames = "customer_id")
        }
)
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "quotation_seq")
    @GenericGenerator(name = "quotation_seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "beginning_of_insurance")
    private Date beginningOfInsurance;

    @Min(value = 1)
    @Max(value = Integer.MAX_VALUE)
    @Column(name = "insured_amount")
    private Integer insuredAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_signing_mortgage")
    private Date dateOfSigningMortgage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(mappedBy = "quotation")
    @Transient
    private Subscription subscription;
}
