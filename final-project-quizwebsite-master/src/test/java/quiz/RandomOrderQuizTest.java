package quiz;

import DAO.MultipleAnswerUnorderedResponseQuestionDao;
import database.DatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import question.MultipleAnswerUnorderedResponseQuestion;
import question.Question;
import quiz.QuizAttempt;
import quiz.StandardQuiz;
import user.User;

import javax.faces.component.UISelectBoolean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomOrderQuizTest  {
    private static Connection conn;
    private static MultipleAnswerUnorderedResponseQuestion q1;
    private static MultipleAnswerUnorderedResponseQuestion q2;
    private static MultipleAnswerUnorderedResponseQuestionDao qDao;
    private static List<Question> questions;

    private static User u;
    private static  String name = "quiz1";
    private static  List<QuizAttempt> history;

    private static RandomOrderQuiz quiz1;
    private static RandomOrderQuiz quiz;

    @Before
    public   void init() throws ClassNotFoundException, SQLException, IOException {
        conn= DatabaseConnection.getConnection();
        DatabaseConnection.resetTables();

        String  questionText = "which is the highest Mountain?";
        HashSet<String> legalAnswers = new HashSet<>();
        legalAnswers.add("Chomolungma");
        legalAnswers.add("Mount Everest");
        legalAnswers.add("Everest");
        int numOfRequestedAnswers = 2;


        String  questionText2 = "What is the biggest sea";
        HashSet<String> legalAnswers2 = new HashSet<>();
        legalAnswers2.add("philippine Sea");
        int numOfRequestedAnswers2 = 1;

        q1 = new MultipleAnswerUnorderedResponseQuestion(questionText, legalAnswers, numOfRequestedAnswers);
        q2 = new MultipleAnswerUnorderedResponseQuestion(questionText2, legalAnswers2, numOfRequestedAnswers2);
        qDao = new MultipleAnswerUnorderedResponseQuestionDao(conn);

        u = new User("bla", "bla", true, "bla", "blabla" );

        questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);

        history = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        QuizAttempt at1 = new QuizAttempt(u.getId(), 12, timestamp);
        QuizAttempt at2 = new QuizAttempt(u.getId(), 10, timestamp);
        history.add(at1);
        history.add(at2);

        quiz  =  new RandomOrderQuiz(questions, u, name, history);
        quiz1 = new RandomOrderQuiz(1,questions, u, name, history);
        System.out.println(questions.size());
        System.out.println(quiz.getQuestions().size());

    }

    @Test
    public void testQuiz() throws SQLException {
        assertEquals(quiz.getAuthor(), u);
        assertEquals(quiz.getName(), name);
        quiz.setId(6);
        assertEquals(quiz.getId(), 6);
        assertEquals(quiz.getHistory(),history );
        assertEquals(quiz.getQuestions().size(), questions.size());
        for(int i=0; i<questions.size(); i++){
            assertTrue(quiz.getQuestions().contains(questions.get(i)));
        }

    }
    @Test
    public void testQuiz1() throws SQLException {
        assertEquals(quiz1.getAuthor(), u);
        assertEquals(quiz1.getName(), name);
        assertEquals(quiz1.getId(), 1);
        quiz1.setId(6);
        assertEquals(quiz1.getId(), 6);
        assertEquals(quiz1.getHistory(),history );
        assertEquals(quiz1.getQuestions().size(), questions.size());
        for(int i=0; i<questions.size(); i++){
            assertTrue(quiz1.getQuestions().contains(questions.get(i)));
        }
    }

}