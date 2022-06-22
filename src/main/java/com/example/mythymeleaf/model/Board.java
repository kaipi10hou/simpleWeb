package com.example.mythymeleaf.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=3, max=30, message = "제목은 3자 이상 30자 이하이어야 합니당.")
    private String title;
    private String content;

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +

                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
