package com.example.codingweek;

import com.example.codingweek.DAO.EvalDAO;
import com.example.codingweek.database.DataBase;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class EvalDAOTest {
    @Test
    public void createNewEvalTest(){
        DataBase db = DataBase.getInstance();
        db.reset();
        db.init();
        EvalDAO evalDAO = new EvalDAO();
        UUID idReview = UUID.randomUUID();

        evalDAO.createNewEval(idReview, 5, null, "joelDTest", "test");


    }
    @Test
    public void addEvalTest(){
    }
}
