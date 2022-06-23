package com.example.mythymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=3, max=30, message = "제목은 3자 이상 30자 이하이어야 합니다.")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") // User 테이블의 id값을 참조. User의 id가 PK이므로 생략가능
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +

                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
