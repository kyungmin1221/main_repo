package com.example.delivery.repository.dslrepository;

import com.example.delivery.domain.QStore;
import com.example.delivery.domain.QUser;
import com.example.delivery.domain.Store;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.dto.StorePageDto;
import com.example.delivery.dto.StoreProDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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

    // where in 절 사용 - 리스트로 카테고리들 조회
    @Override
    public List<Store> findStoresWithUsersByCategories(List<String> categories) {
        return queryFactory
                .selectFrom(store)
                .join(store.user, user).fetchJoin()
                .where(store.category.in(categories))
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

    @Override
    public List<Store> sort() {
        return queryFactory
                .selectFrom(store)
                .where(store.workTime.eq("24시ㅔㅕ"))
                .orderBy(store.id.asc())
                .fetch();
    }

    @Override
    public List<Tuple> tupleTest(String name, Integer id) {
        return queryFactory
                .select(store.name, store.id)
                .from(store)
                .where(store.name.eq(name), store.id.eq(id))
                .fetch();
    }

    @Override
    public List<StoreDto.InfoResponse> findDtoTest(String category) {
        return queryFactory
                .select(Projections.bean(StoreDto.InfoResponse.class,
                               store.category))
                .from(store)
                .where(store.category.eq(category))
                .fetch();
    }



    // storeservice 에 적용할 페이징
    @Override
    public Page<Store> searchByCategoryWithPaging(String category, Pageable pageable) {

        List<Store> results = queryFactory.selectFrom(store)
                .where(store.category.eq(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 조건에 해당하는 Store 객체 전체 개수 조회 쿼리 -> 전체 페이지 수 계산할 때 활용
        long total = queryFactory
                .select(store.count())
                .from(store)
                .where(store.category.eq(category))
                .fetchOne();

        // pageable.getOffset() : 요청한 항목의 첫번째 인덱스
        // 예외 처리부분 -> 요청한 페이지수가 지금 페이지 수보다 크면 비어있는 페이지 개ㅑㄱ채ㅔ 반환
        if(total <= pageable.getOffset()) {
            return new PageImpl<>(Collections.emptyList(), pageable, total);
        }

        // 정상적인 경우 반환
        return new PageImpl<>(results, pageable, total);
    }


    /*
   Projections.constructor(StoreProDto.ProDto.class : dto 로 변환함 - 특정 카테고리만 SELECT 
   count 쿼리 : store 엔티티 조회 - 개수 조회 쿼리를 분리해 최적화를 진행함
    */
    @Override
    public Page<StoreProDto.ProDto> testProjection(String category, Pageable pageable) {
        List<StoreProDto.ProDto> result = queryFactory
                .select(Projections.bean(
                        StoreProDto.ProDto.class,
                        store.category))
                .from(store)
                .where(store.category.eq(category))
                .fetch();

        long total = queryFactory
                .select(store.count())
                .from(store)
                .where(store.category.eq(category))
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }





}
