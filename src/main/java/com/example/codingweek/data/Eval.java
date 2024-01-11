package com.example.codingweek.data;

import java.util.UUID;

public class Eval {
    private final UUID idReview;
    private int eval;
    private UUID orderId;
    private String writer;
    private String reviewDescription;
    public UUID getIdReview(){
        return this.idReview;
    }
    public int getEval() {
        return eval;
    }
    public UUID getOrderId() {
        return orderId;
    }
    public String getWriter() {
        return writer;
    }
    public String getReviewDescription() {
        return reviewDescription;
    }

    public Eval(UUID idReview, int eval, UUID orderId, String writer, String reviewDescription){
        this.idReview = idReview;
        this.eval = eval;
        this.orderId = orderId;
        this.writer = writer;
        this.reviewDescription = reviewDescription;
    }

    public Eval(int eval, UUID orderId, String writer, String reviewDescription){
        this.idReview = UUID.randomUUID();
        this.eval = eval;
        this.orderId = orderId;
        this.writer = writer;
        this.reviewDescription = reviewDescription;
    }
}
