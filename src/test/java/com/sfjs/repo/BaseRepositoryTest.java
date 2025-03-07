package com.sfjs.repo;

import static org.assertj.core.api.Assertions.assertThat;

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
  @ValueSource(strings = {"entity_1", "entity_2", "entity_3"})
  public void whenFindByReference_thenReturnEntity(String reference) {
    // given
    E entity = createEntity();
    entity.setReference(reference);

    entityManager.persist(entity);
    entityManager.flush();

    // when
    E found = repository.findByReference(reference);

    // then
    assertThat(found.getReference()).isEqualTo(reference);
  }

  @ParameterizedTest
  @ValueSource(strings = {"details_1", "details_2", "details_3"})
  public void whenFindByLabel_thenReturnEntity(String details) {
    // given
    E entity = createEntity();
    entity.setDetails(details);

    entityManager.persist(entity);
    entityManager.flush();

    // when
    E found = repository.findByDetails(details);

    // then
    assertThat(found.getDetails()).isEqualTo(details);
  }
}