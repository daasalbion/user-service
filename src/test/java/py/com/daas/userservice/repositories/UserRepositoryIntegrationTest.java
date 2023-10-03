package py.com.daas.userservice.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import py.com.daas.userservice.factories.UserTestFactory;
import py.com.daas.userservice.models.User;

@DataJpaTest
class UserRepositoryIntegrationTest {
    private final TestEntityManager entityManager;
    private final UserRepository userRepository;
    private final UserTestFactory userTestFactory;

    @Autowired
    UserRepositoryIntegrationTest(TestEntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.userTestFactory = new UserTestFactory();
    }

    @Test
    void existsByEmailEqualsAndIsActiveIsTrueReturnsTrue() {
        User user = userTestFactory.getUser();
        user.setId(null);
        entityManager.persist(user);
        entityManager.flush();
        var result = userRepository.existsByEmailEqualsAndIsActiveIsTrue(user.getEmail());

        assertThat(result).isTrue();
    }

    @Test
    void existsByEmailEqualsAndIsActiveNoActiveUser() {
        User user = userTestFactory.getUser();
        user.setId(null);
        user.setIsActive(false);
        entityManager.persist(user);
        entityManager.flush();
        var result = userRepository.existsByEmailEqualsAndIsActiveIsTrue(user.getEmail());

        assertThat(result).isFalse();
    }

    @Test
    void existsByEmailEqualsAndIsActiveNoSuchUser() {
        var result = userRepository.existsByEmailEqualsAndIsActiveIsTrue("i.do.not.exist@gmail.com");

        assertThat(result).isFalse();
    }
}