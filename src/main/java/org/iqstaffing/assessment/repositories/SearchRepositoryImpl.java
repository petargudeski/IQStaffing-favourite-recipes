package org.iqstaffing.assessment.repositories;

import org.hibernate.search.engine.search.common.ValueConvert;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.iqstaffing.assessment.exceptions.CategoryNotFoundException;
import org.iqstaffing.assessment.models.enums.Category;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Transactional
public class SearchRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements SearchRepository<T, ID> {

    private final EntityManager entityManager;

    public SearchRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    public SearchRepositoryImpl(
            JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> searchBy(String text, int limit, String... fields) {

        SearchResult<T> result = getSearchResult(text, limit, fields);

        return result.hits();
    }

    private SearchResult<T> getSearchResult(String text, int limit, String[] fields) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<T> result =
                searchSession
                        .search(getDomainClass())
                        .where(f -> f.match().fields(fields).matching(text, ValueConvert.NO))
                        .fetch(limit);
        return result;
    }

    @Override
    public List<T> searchByCategory(String text) {
        SearchSession searchSession = Search.session(entityManager);

        Category category = Arrays.stream(Category.values()).filter(c -> text.toLowerCase().contains(c.getName())).findAny().orElseThrow(() -> new CategoryNotFoundException("Category not existing"));
        List<T> recipes =
                searchSession
                        .search(getDomainClass())
                        .where(f -> f.bool()
                                .must(f.match().field("category").matching(category)))
                        .fetchAllHits();
        return recipes;
    }

    @Override
    public List<T> searchByServingsAndInsructions(String text) {
        SearchSession searchSession = Search.session(entityManager);
        try {
            List<T> recipes =
                    searchSession
                            .search(getDomainClass())
                            .where(f -> f.bool()
                                    .must(f.match().field("numberOfServings").matching(Integer.valueOf(text.replaceAll("[^0-9]", "")).intValue()))
                                    .must(f.match().field("recipeIngredients.ingredient.name").matching(text.toLowerCase())))
                            .fetchAllHits();

            return recipes;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number of servings is missing in the request.");
        }
    }

    public List<T> searchByExcludedIngredient(String text) {
        SearchSession searchSession = Search.session(entityManager);

        List<T> recipes =
                searchSession
                        .search(getDomainClass())
                        .where(f -> f.bool()
                                .mustNot(f.match().field("recipeIngredients.ingredient.name").matching(text))
                                .must(f.match().field("instruction.instruction").matching(text)))
                        .fetchAllHits();
        return recipes;
    }
}
