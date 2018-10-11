package com.client.aux;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Database {

    /**
     * Connects to the database and proceed to the insertion of the data in the tables
     * @param answer is the answer received from the server
     * @param request is the request made to the server
     */

    public static void insertJDBC(final Answer answer, final Request request){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jerseytesting-JPA");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(answer);
        em.persist(request);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    public Database() {
    }
}
