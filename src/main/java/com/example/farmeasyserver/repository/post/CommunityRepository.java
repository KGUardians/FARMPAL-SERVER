package com.example.farmeasyserver.repository.post;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<CommunityPost,Long> {
    @Query("SELECT cp FROM CommunityPost cp " +
            "ORDER BY cp.id DESC limit 5")
    List<CommunityPost> findTop5OrderByIdDesc();

    @Query("select cp from CommunityPost cp join fetch cp.author where cp.id = :id")
    Optional<CommunityPost> findByIdWithUser(Long id);

    @Query("select cp from CommunityPost cp " +
            "where cp.communityType = :type " +
            "and cp.cropCategory = :crop " +
            "and cp.title like concat('%',:search,'%') " +
            "or cp.content like concat('%',:search,'%') ")
    Slice<CommunityPost> findByCommunityTypeAndCropCategoryAndSearch(CommunityType type, CropCategory crop,String search, Pageable pageable);

}
