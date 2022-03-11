package sample.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="destination")
public class Destination {
    @Id
    private int id;

    @Column(unique= true, nullable=false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy="destination",fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Package> packages;

    public Destination(){

    }
    public Destination(int id,String name){
        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
