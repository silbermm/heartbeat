package edu.uc.labs.heartbeat.dao;

import edu.uc.labs.heartbeat.models.Authority;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorityDaoImpl extends AbstractDao<Authority> implements AuthorityDao {
    
    public AuthorityDaoImpl(SessionFactory sf){
        this.setSessionFactory(sf);
    }
    
    
}
