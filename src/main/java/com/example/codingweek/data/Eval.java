package com.example.codingweek.data;

import java.util.UUID;

public class Eval {
    private int eval;
    private UUID orderId;
    private String writer;
    private String reviewDescription;

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



    public Eval(int eval, UUID orderId, String writer, String reviewDescription){
        this.eval = eval;
        this.orderId = orderId;
        this.writer = writer;
        this.reviewDescription = reviewDescription;
    }
}
