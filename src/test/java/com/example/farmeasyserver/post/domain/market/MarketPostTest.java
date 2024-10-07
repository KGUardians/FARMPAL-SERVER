package com.example.farmeasyserver.post.domain.market;

import farmeasy.server.post.domain.PostType;
import farmeasy.server.post.domain.market.Item;
import farmeasy.server.post.domain.market.MarketPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class MarketPostTest {

    private MarketPost marketPost;
    private Item item;

    @BeforeEach
    public void setUp() {
        // Item 객체 생성
        item = Item.builder()
                .itemName("test")
                .price(1000)
                .gram(100)
                .build();

        // MarketPost 객체 생성
        marketPost = new MarketPost("test", item);
    }

    @Test
    void testMarketPostCreation(){
        // MarketPost가 정상적으로 생성되었는지 확인
        assertEquals(PostType.MARKET, marketPost.getPostType());
        assertEquals("test", marketPost.getContent());
        assertNotNull(marketPost.getItem());
        assertEquals("test", marketPost.getItem().getItemName());
    }
}
