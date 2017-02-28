package ru.personalpro.db;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;


@Entity
@Table(name = "test.person")
public class Person {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "middle_name")
    private String middle_name;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonSerialize(using = JsonDateSerializer.class)
    @Column(name = "birth_date")
    private Date birth_date;

    @Column(name = "comment")
    private String comment;

    @Column(name = "update_date")
    private Timestamp update_date;


    public Integer getId() {
        return id;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public Date getBirth_date() throws ParseException{
        return birth_date;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getUpdate_date() {
        return update_date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUpdate_date(Timestamp update_date) {
        this.update_date = update_date;
    }

}

