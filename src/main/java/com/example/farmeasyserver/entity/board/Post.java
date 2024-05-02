package com.example.farmeasyserver.entity.board;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@MappedSuperclass
@Data
public abstract class Post {

    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;
    private int postLike;


    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

}
