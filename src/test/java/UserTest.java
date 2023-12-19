import dao.AbstractJpaDao;
import dao.UserJpaDao;
import entity.User;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class UserTest extends AbstractTest<User> {
    List<User> users;

    @Override
    public Callable<User> getCallableToFillEntityTable() {
        Callable<User> userCallable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                em.persist(new User("Maxim", "Chikilev", "(067)111-11-11", "Some address 1"));
                em.persist(new User("Anatoliy", "Chikilev", "(067)222-22-22", "Some address 2"));
                em.persist(new User("Anastasia", "Chikileva", "(067)333-33-33", "Some address 3"));
                return null;
            }
        };
        return userCallable;
    }

    @Override
    protected void setJpaDao() {
        jpaDao = new UserJpaDao(em);
    }

    @Test
    public void entityTest() {
        users = jpaDao.getAll();
        assertEquals(3, users.size());
        assertEquals("Maxim", users.get(0).getFirstName());
        assertEquals("Chikilev", users.get(0).getSecondName());
        assertEquals("(067)111-11-11", users.get(0).getPhoneNumber());
        assertEquals("Some address 1", users.get(0).getAddress());
        User testUser = new User("Stepan", "Ignatenko", "(050)111-11-11", "Some Address");
        jpaDao.create(testUser);
        assertEquals(4, jpaDao.getAll().size());
        testUser.setAddress("Odessa");
        jpaDao.update(testUser);
        assertEquals("Odessa", jpaDao.getEntityById(testUser.getUserId()).getAddress());
        jpaDao.delete(testUser);
        users = jpaDao.getAll();
        assertEquals(3, users.size());
    }
}
