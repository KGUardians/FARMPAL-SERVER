package com.example.farmeasyserver.entity.pestdetection;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Crop {

    @Id
    @Column(name = "crop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "crop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CropPest> pests;
}
