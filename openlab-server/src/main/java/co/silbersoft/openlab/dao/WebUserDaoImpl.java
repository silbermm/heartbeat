package co.silbersoft.openlab.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.silbersoft.openlab.models.WebUser;

@Repository
public class WebUserDaoImpl extends AbstractDao<WebUser> implements WebUserDao{
  
    @Autowired
    public WebUserDaoImpl(SessionFactory sf){
        this.setSessionFactory(sf);
    }

    @Override
    public WebUser findUserByName(String username) {               
        return (WebUser) getSession().createQuery("from WebUser where cn = :cn").setString("cn", username).uniqueResult();
    }

    @Override
    public List<WebUser> findUsersInRole(long auth) {
        Criteria crit = getSession().createCriteria(WebUser.class);
        crit.createAlias("authorities", "authAlias");
        crit.add(Restrictions.eq("authAlias.authorityId", auth));
        List<WebUser> users = crit.list();
        return users;
    }
    
    @Override
    public List<WebUser> findAllEnabled(boolean enabled){
    	return (List<WebUser>) getSession().createQuery("from WebUser where enabled = :enabled").setBoolean("enabled", enabled).list();   	    
    }

}
