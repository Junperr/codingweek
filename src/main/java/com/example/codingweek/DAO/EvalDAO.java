package com.example.codingweek.DAO;

import com.example.codingweek.data.Eval;
import com.example.codingweek.database.DataBase;

import java.util.UUID;

public class EvalDAO {
    private final DataBase db = DataBase.getInstance();
    public Eval createNewEval(int eval, UUID orderId, String writer, String reviewDescription){
        Eval newEval = new Eval(eval, orderId, writer, reviewDescription);
        addEval(newEval);
        return newEval;
    }

    public void addEval(Eval eval){
        db.exec("INSERT INTO Reviews (orderId, eval, writer, description) VALUES (?, ?, ?, ?)",
                eval.getOrderId(),
                eval.getEval(),
                eval.getWriter(),
                eval.getReviewDescription());
        UserDAO userDAO = new UserDAO();
        OrderDAO orderDAO = new OrderDAO();
        userDAO.getUserAvgEval(userDAO.getUserByUsername(orderDAO.getOrderById(eval.getOrderId()).getSeller()));
    }
}
