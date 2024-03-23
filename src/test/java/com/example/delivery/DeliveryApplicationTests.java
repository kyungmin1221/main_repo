package com.example.delivery;

import com.example.delivery.constant.Role;
import com.example.delivery.domain.Menu;
import com.example.delivery.domain.QStore;
import com.example.delivery.domain.Store;
import com.example.delivery.domain.User;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.repository.MenuRepository;
import com.example.delivery.repository.StoreRepository;
import com.example.delivery.repository.UserRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import com.querydsl.core.types.dsl.Expressions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.example.delivery.domain.QStore.store;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
class DeliveryApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    EntityManager em;

    @Test
    void insertDummyDataUserAndStore() {

        int loopCount = 100_000;

        for (int i = 0; i < loopCount; i++) {

            /*
            ========================== User 더미 데이터 등록 =============================
             */
            String email = generateUniqueEmail();
            String nickname = "user" + i;
            String password = "password" + i;
            long point = 1_000_000; // 0부터 999까지의 임의의 수
            Role role = Role.CEO; // 사용자 역할로 설정

            // User 객체 생성
            User user = new User(email, nickname, password, point, role);

            // UserRepository를 통해 데이터베이스에 저장
            userRepository.save(user);

            /*
            ========================== Store 더미 데이터 등록 =============================
             */
            String name = "Store " + i;
            String workTime = "10:00 - 20:00"; // 랜덤 영업 시간을 생성하도록 수정 가능
            String category = generateRandomCategory();
            String address = "123 Main St, City, Country"; // 랜덤 주소를 생성하도록 수정 가능
            String imageUrl = "/img/logo.png"; // 적절한 이미지 URL을 넣으세요
//            float storeScore = new Random().nextFloat() * 5; // 랜덤 가게 평점을 생성하도록 수정 가능
            float storeScore = 0; // 랜덤 가게 평점을 생성하도록 수정 가능
            // double totalSales = new Random().nextInt(1000000); // 랜덤 총 매출액을 생성하도록 수정 가능
            double totalSales = 0; // 랜덤 총 매출액을 생성하도록 수정 가능

            // Store 객체 생성
            Store store = Store.builder()
                    .name(name)
                    .workTime(workTime)
                    .category(category)
                    .address(address)
                    .imageUrl(imageUrl)
                    .storeScore(storeScore)
                    .totalSales(totalSales)
                    .user(user)
                    .build();

            // StoreRepository를 통해 데이터베이스에 저장
            storeRepository.save(store);
        }


    }

    // 랜덤한 카테고리 생성하는 메서드
    private String generateRandomCategory() {
        String[] categories = {"피자", "치킨", "족발"};
        Random rand = new Random();
        return categories[rand.nextInt(categories.length)];
    }

    // 랜덤한 이메일
    private String generateUniqueEmail() {
        return UUID.randomUUID().toString() + "@example.com"; // example.com 부분은 적절한 도메인으로 변경해야 합니다.
    }


    // 패치조인 테스트 ( 연관관계 있을 시 압도적 성능) - @Query 사용
    @Test
    void fetchJoinTest() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Store> storesWithUser = storeRepository.findByCategoryWithUserFetchJoin("치킨");

        stopWatch.stop();
        System.out.println("###################################");
        System.out.println("Fetch Join Test");
        System.out.println("###################################");
        System.out.println(stopWatch.prettyPrint());
    }


    // 패치조인 테스트 ( 연관관계 있을 시 압도적 성능) - QueryDSL 사용
    @Test
    public void testFindStoresWithUsers() {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Store> storesWithUser = storeRepository.findStoresWithUsersByCategory("치킨");


        stopWatch.stop();
        System.out.println("###################################");
        System.out.println("--- 패치조인 + dsl 테스트 ---");
        System.out.println("###################################");
        System.out.println(stopWatch.prettyPrint());
    }

    // fetchJoin 테스트
    @Test
    public void testFindStoresWithUsersByCategories() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 카테고리 리스트 생성
        List<String> categories = Arrays.asList("치킨","피자");

        // 수정된 메서드 호출
        List<Store> storesWithUser = storeRepository.findStoresWithUsersByCategories(categories);

        stopWatch.stop();
        System.out.println("###################################");
        System.out.println("--- 패치조인 + dsl 테스트 ---");
        System.out.println("###################################");
        System.out.println(stopWatch.prettyPrint());
    }




    // 페이징 처리 테스트 - count 분리
    @Test
    public void testFindAllWithUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 테스트를 위한 Pageable 객체 생성 (첫 번째 페이지, 페이지당 5개의 항목)
        PageRequest pageRequest = PageRequest.of(0, 5);

        // findAllWithUsers 메서드 호출
        Page<Store> storePage = storeRepository.findAllWithUsers(pageRequest);

        stopWatch.stop();
        System.out.println("Execution time: " + stopWatch.getTotalTimeMillis() + " ms");

        // 결과 출력
        System.out.println("Total Elements: " + storePage.getTotalElements());
        System.out.println("Total Pages: " + storePage.getTotalPages());
        for (Store store : storePage.getContent()) {
            System.out.println("Store Name: " + store.getName());
            // 연관된 User 정보 출력
            if (store.getUser() != null) {
                System.out.println(" - Owner Email: " + store.getUser().getEmail());
            }
        }
    }

    @Test
    public void sortTest() {
        List<Store> list = storeRepository.sort();
        for (Store store1 : list) {
            System.out.println("store1 = " + store1);
        }
    }

    @Test
    public void tupleTest() {
        List<Tuple> results = storeRepository.tupleTest("kyungmin",1);
        for (Tuple result : results) {
            String name = result.get(store.name);
            Integer id = result.get(store.id);
            System.out.println("id = " + id);
            System.out.println("name = " + name);
        }
    }

    @Test
    public void findDtoTest() {
        List<StoreDto.InfoResponse> result  = storeRepository.findDtoTest("치킨");
        for (StoreDto.InfoResponse infoResponse : result) {
            System.out.println("infoResponse category :  " + infoResponse);
        }
    }


    // 더미데이터로 페이징 테스트
//    @Test
//    @Transactional
//    void searchPageTestWithDummyData() {
//
//        PageRequest pageRequest = PageRequest.of(0, 10); // 첫 번째 페이지, 페이지 당 10개 항목
//
//        // 테스트 실행
//        Page<StoreDto.InfoResponse> result = storeRepository.searchPage(pageRequest);
//
//        // 첫번째 페이지 데이터 출력
//        for (StoreDto.InfoResponse infoResponse : result) {
//            System.out.println("infoResponse = " + infoResponse);
//
//        }
//
//        // 검증
//        assertThat(result.getContent()).isNotEmpty();
//        assertThat(result.getTotalElements()).isEqualTo(100000); // 총 엘리먼트 개수 검증 (10만 개의 더미 데이터가 있으므로)
//        assertThat(result.getContent().size()).isEqualTo(10); // 페이지에 반환된 항목의 수 검증
//        assertThat(result.getContent()).extracting("name")
//                .contains("Store 0", "Store 1", "Store 2", "Store 3"); // 첫 번째 페이지의 스토어 이름 검증
//    }




}
