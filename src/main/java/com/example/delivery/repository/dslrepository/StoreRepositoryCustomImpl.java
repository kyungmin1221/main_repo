package com.example.delivery.repository.dslrepository;

import com.example.delivery.domain.QStore;
import com.example.delivery.domain.QUser;
import com.example.delivery.domain.Store;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.delivery.domain.QStore.store;
import static com.example.delivery.domain.QUser.user;

@Repository
public class StoreRepositoryCustomImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public StoreRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Store> findStoresWithUsersByCategory(String category) {
        return queryFactory
                .selectFrom(store)
                .join(store.user, user).fetchJoin()
                .where(store.category.eq(category))
                .fetch();  // 리스트 조회 -> fetchOne() : 1건 조회

    }

    // in 절 사용
    @Override
    public List<Store> findStoresWithUsersByCategories(List<String> categories) {
        return queryFactory
                .selectFrom(store)
                .join(store.user, user).fetchJoin()
                .where(store.category.in(categories))
                .fetch();
    }
    // join(store.user, user).

    @Override
    public List<Store> pagingTest() {
        return queryFactory
                .selectFrom(store)
                .orderBy(store.id.asc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    // 페이징 처리 - count 성능개선
    @Override
    public Page<Store> findAllWithUsers(Pageable pageable) {
        QStore qStore = QStore.store;
        QUser qUser = QUser.user;

        // 리스트 조회 쿼리 실행
        List<Store> stores = queryFactory
                .selectFrom(qStore)
                .leftJoin(qStore.user, qUser).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count 쿼리 실행
        long count = queryFactory
                .select(qStore.count())
                .from(qStore)
                .fetchOne();

        return new PageImpl<>(stores, pageable, count);
    }


}
