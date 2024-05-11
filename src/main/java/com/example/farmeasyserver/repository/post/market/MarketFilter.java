package com.example.farmeasyserver.repository.post.market;

import com.example.farmeasyserver.entity.board.CropCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketFilter {
    private CropCategory crop;
}
