package com.sfjs.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.sfjs.data.BaseObject;
import com.sfjs.jpa.repo.BaseRepository;

@DataJpaTest
public abstract class BaseRepositoryTest<R extends BaseRepository<E>, E extends BaseObject> {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private R repository;

  protected abstract E createEntity();

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "3"})
  public void whenFindById_thenReturnEntity(String id) {
    // given
    E entity = createEntity();

    entity = entityManager.persist(entity);
    entityManager.flush();

    // when
    Optional<E> found = repository.findById(entity.getId());

    // then
    assertThat(found.isPresent());
    assertThat(found.get().getId().toString()).isEqualTo(id);
  }

}