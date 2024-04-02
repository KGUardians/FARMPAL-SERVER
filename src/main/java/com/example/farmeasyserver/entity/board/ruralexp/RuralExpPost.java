package com.example.farmeasyserver.entity.board.ruralexp;

import com.example.farmeasyserver.entity.board.Post;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RURAL_EXP")
public class RuralExpPost extends Post {
}
