package com.example.farmeasyserver.entity.pestdetection;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CropPest {
    @Id
    private Long id;
    private String pest;
    @ManyToOne(fetch = FetchType.LAZY)
    private Crop crop;

    public void setCrop(Crop crop){
        this.crop = crop;
        crop.getPests().add(this);
    }
}
