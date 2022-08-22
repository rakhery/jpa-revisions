package fr.m2i.dao;

import fr.m2i.exception.AccountDaoException;
import fr.m2i.model.Account;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private EntityManagerFactory emf;

    public AccountDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(Account account) throws AccountDaoException {
        EntityManager em=this.emf.createEntityManager();
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            em.persist(account);
            tx.commit();

        }catch (Exception e) {
            System.out.println("Une erreur est survenu pendant le sauvegarde");
            System.out.println("Exception message : " + e.getMessage());
            throw new AccountDaoException("AccountDaoException should be thrown",e);
        }

    }

    @Override
    public Account findById(Long id) {
        EntityManager em=this.emf.createEntityManager();
        Account founded = em.find(Account.class, id);
        if (founded == null) {
            System.out.println("Attention le compte avec l'id: " + id + " n'existe pas !");
        }
        return founded;
    }

    @Override
    public Account findByEmail(String email) {
        EntityManager em=this.emf.createEntityManager();
        if (email == null) {
            System.out.println("L\' email du compte  n'est pas valide !");
            return null;
        }
        try{
            Query query = em.createQuery("select a from Account a where a.email = ?1");
            query.setParameter(1, email);
            return (Account) query.getSingleResult();
        }
        finally {
            if(em!=null){
                em.close();
            }
        }

    }

    @Override
    public List<Account> findAll() {
        EntityManager em=emf.createEntityManager();
        try{
            Query query = em.createQuery("select a from Account a ");
            return query.getResultList();
        }
        finally {
            if(em!=null){
                em.close();
            }
        }
    }
    /**
     *
     if you want to update a managed entity, you should not invoke any method, JPA will detect any state changes automatically and persist the changes at flush time
     if you want to update a detached entity, you should use merge (and return the merged instance).

     */


    @Override
    public void update(Account account) throws  AccountDaoException{
        Account accountToUpdate=findById(account.getId());
        if (accountToUpdate == null) {
            System.out.println("Le compte avec l'id:" + account.getId() + " n'existe pas");
            return;
        }
        accountToUpdate.copy(account);
        EntityManager em=emf.createEntityManager();
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            em.merge(accountToUpdate);
            tx.commit();

        }catch (Exception e) {
            System.out.println("Une erreur est survenu pendant update");
            System.out.println("Exception message : " + e.getMessage());
            throw new AccountDaoException("AccountDaoException should be thrown",e);

        }


    }

    @Override
    public void remove(Account account) {
        if (account == null || account.getId() == null ) {
            System.out.println("Le compte n'est pas valide !");
            return;
        }
        Account accountToDelete = findById(account.getId());
        EntityManager  em=emf.createEntityManager();
        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            /*em.createNativeQuery("delete  from Account where id=:id ")
                    .setParameter("id",account.getId())
                    .executeUpdate();*/
            em.remove(em.contains(accountToDelete)? accountToDelete:em.merge(accountToDelete));
            tx.commit();
        }catch (Exception e) {
            System.out.println("Une erreur est survenu pendant suppression");
            System.out.println("Exception message : " + e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}


