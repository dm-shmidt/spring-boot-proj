package com.test.quotation.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "quotation")
@Entity
@Table(
        name = "subscription",
        uniqueConstraints = {
                @UniqueConstraint(name = "quotation_id_fk", columnNames = "quotation_id")
        })
public class Subscription implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "subscription_seq")
    @GenericGenerator(name = "subscription_seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "valid_until")
    private LocalDate validUntil;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "quotation_id", referencedColumnName = "id")
    private Quotation quotation;
}
