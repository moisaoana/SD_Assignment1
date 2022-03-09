package sample.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="package")
public class Package {
    @Id
    private int id;

    @Column(unique= true, nullable=false)
    private String name;

    @Column(nullable=false)
    private double price;

    @Column(nullable=false)
    private LocalDate startDate;

    @Column(nullable=false)
    private LocalDate endDate;

    @Column
    private String details;

    @Column(nullable=false)
    private Status status;

    @ManyToOne
    @JoinColumn(name="destination_id", nullable=false)
    private Destination destination;



}
