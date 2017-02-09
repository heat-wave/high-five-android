package com.highfive.highfive.model;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by heat_wave on 09.02.17.
 */

public class Bid {
    private DateTime updatedAt;
    private DateTime createdAt;
    private int offer;
    private String creatorId;
    private String orderId;
    private String bidId;
    private ArrayList<String> comments = new ArrayList<>();

    public Bid(DateTime updatedAt, DateTime createdAt, int offer, String creatorId,
               String orderId, String bidId, ArrayList<String> comments) {
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.offer = offer;
        this.creatorId = creatorId;
        this.orderId = orderId;
        this.bidId = bidId;
        this.comments = comments;
    }
}
