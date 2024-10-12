package com.sfjs.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.sfjs.crud.entity.BaseEntity;
import com.sfjs.crud.repo.BaseRepository;

@DataJpaTest
public abstract class BaseRepositoryTest<R extends BaseRepository<E>, E extends BaseEntity> {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private R repository;

  protected abstract E createEntity();

  @ParameterizedTest
  @ValueSource(strings = {"entity_name_1", "entity_name_2", "entity_name_3"})
  public void whenFindByName_thenReturnEntity(String name) {
    // given
    E entity = createEntity();
    entity.setName(name);

    entityManager.persist(entity);
    entityManager.flush();

    // when
    E found = repository.findByName(name);

    // then
    assertThat(found.getName()).isEqualTo(name);
  }

  @ParameterizedTest
  @ValueSource(strings = {"entity_label_1", "entity_label_2", "entity_label_3"})
  public void whenFindByLabel_thenReturnEntity(String label) {
    // given
    E entity = createEntity();
    entity.setLabel(label);

    entityManager.persist(entity);
    entityManager.flush();

    // when
    E found = repository.findByLabel(label);

    // then
    assertThat(found.getLabel()).isEqualTo(label);
  }
}