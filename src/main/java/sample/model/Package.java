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

    @Column(nullable=false)
    private String details;

    @Column(nullable=false)
    private Status status;

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    private int currentCapacity;

    @ManyToOne
    @JoinColumn(name="destination_id", nullable=false)
    private Destination destination;

    public Package(int id, String name, double price, LocalDate startDate, LocalDate endDate, String details, Status status, int maxCapacity, int currentCapacity, Destination destination) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.details = details;
        this.status = status;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.destination = destination;
    }

    public Package() {

    }

    public String getName() {
        return name;
    }
}
