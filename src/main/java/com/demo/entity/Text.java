package com.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Text {
    @Id
    private Integer tid;
    private String author;
    private String path;
    private String tag;
    private String picture;
}
