package com.test.quotation.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

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
public class Quotation implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "quotation_seq")
    @GenericGenerator(name = "quotation_seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "beginning_of_insurance")
    private LocalDate beginningOfInsurance;

    @Min(value = 1)
    @Max(value = Integer.MAX_VALUE)
    @Column(name = "insured_amount")
    private Integer insuredAmount;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_signing_mortgage")
    private LocalDate dateOfSigningMortgage;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(mappedBy = "quotation")
    @Transient
    @JsonIgnore
    private Subscription subscription;
}
