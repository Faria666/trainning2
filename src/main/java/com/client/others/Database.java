package com.client.others;
import com.types.Answer;
import com.types.Request;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Database {

    /**
     * Connects to the database and proceed to the insertion of the data in the tables
     * @param answer is the answer received from the server
     * @param request is the request made to the server
     */

    public static boolean insertJDBC(final Answer answer, final Request request){
        boolean inserted = false;

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("project-JPA");

            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            em.persist(answer);
            em.persist(request);
            em.flush();
            em.getTransaction().commit();
            em.close();
            inserted = true;


        }catch (Exception e){
            e.printStackTrace();
        }
        return inserted;
    }

}
