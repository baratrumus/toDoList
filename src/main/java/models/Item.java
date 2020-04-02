package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.*;

/**
 * @author Ivannikov Ilya (voldores@mail.ru)
 * @version $id
 * @since 0.1
 */

@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("descr")
    @Column(name = "descr")
    @NotNull
    private String descr;

    @JsonProperty("created")
    @Column(name = "created")
    @Basic
    //@Temporal(TemporalType.TIMESTAMP)
    private Timestamp created;

    @JsonProperty("done")
    @Column(name = "done")
    @NotNull
    private Boolean done;


    public Item(int id, String descr, Timestamp created, Boolean done) {
        this.id = id;
        this.descr = descr;
        this.created = created;
        this.done = done;
    }


    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}