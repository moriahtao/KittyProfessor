package com.cs5500.server.repository;

import com.cs5500.server.model.User;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mengtao on 2/28/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void testExample() throws Exception {
        // given
        User alex = new User("alex", "1234");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        User found = repository.findByUsername(alex.getUsername());

        // then
        assertThat(found.getUsername())
                .isEqualTo(alex.getUsername());
    }


}