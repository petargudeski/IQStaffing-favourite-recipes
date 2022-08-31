package org.iqstaffing.assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface SearchRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> searchBy(String text, int limit, String... fields);

    List<T> searchByCategory(String text);

    List<T> searchByServingsAndInsructions(String text);

    List<T> searchByExcludedIngredient(String text);
}
