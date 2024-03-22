package com.example.delivery;

import com.example.delivery.constant.Role;
import com.example.delivery.domain.Menu;
import com.example.delivery.domain.QStore;
import com.example.delivery.domain.Store;
import com.example.delivery.domain.User;
import com.example.delivery.repository.MenuRepository;
import com.example.delivery.repository.StoreRepository;
import com.example.delivery.repository.UserRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
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
    private MenuRepository menuRepository;


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

//    @Test
//    void queryTest() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        List<Store> findStore = storeRepository.findByCategory("치킨");
//
//        stopWatch.stop();
//        System.out.println("###################################");
//        System.out.println(stopWatch.prettyPrint());
//        System.out.println("###################################");
//    }

//    @Test
//    void queryWithoutFetchJoinTest() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        List<Store> stores = storeRepository.findByCategory("치킨");
//        for (Store store : stores) {
//            System.out.println("가게 이름: " + store.getName());
//
//            User owner = userRepository.findById(store.getUser().getId()).orElse(null);
//            if (owner != null) {
//                System.out.println("이메일 : " + owner.getEmail());
//            }
//        }
//
//        stopWatch.stop();
//        System.out.println("without Fetch Join Test");
//        System.out.println(stopWatch.prettyPrint());
//    }


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

    // 페이징 처리
    @Test
    public void testPaging() {
        List<Store> stores = storeRepository.pagingTest();

        Assert.notEmpty(stores, "스토어 목록은 비어있지 않아야 합니다.");
        Assert.isTrue(stores.size() <= 10, "크기는 10 이하여야 함");

        // 선택적: 반환된 스토어 목록의 내용을 출력하여 검증ㅌ
        stores.forEach(store -> System.out.println(store.getName()));
    }


    // 페이징
//    @Test
//    public void testFindAllWithUsers() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start(); // 타이머 시작
//
//        // 페이징 요청 생성
//        PageRequest pageRequest = PageRequest.of(0, 5);
//        // 커스텀 리포지토리 메서드 호출
//        Page<Store> storePage = storeRepository.findAllWithUsers(pageRequest);
//
//        stopWatch.stop(); // 타이머 정지
//        System.out.println("시간: " + stopWatch.getTotalTimeMillis() + "ms");
//
//        // 조회 결과 출력
//        for (Store store : storePage.getContent()) {
//            System.out.println("Store Name: " + store.getName());
//            // 연관된 User 정보 출력
//            if (store.getUser() != null) {
//                System.out.println("Email: " + store.getUser().getEmail());
//            }
//            System.out.println();
//        }
//    }


    // 페이징 테스트 2
//    @Test
//    public void testFindAllWithUsers() {
//        StopWatch stopWatch = new StopWatch("Store and User Fetch Test");
//        stopWatch.start("Fetch Stores with Users by Categories");
//
//        // 카테고리 리스트 생성
//        List<String> categories = Arrays.asList("치킨", "피자");
//
//        // 수정된 메서드 호출
//        List<Store> storesWithUser = storeRepository.findStoresWithUsersByCategories(categories);
//
//        stopWatch.stop();
//        System.out.println("###################################");
//        System.out.println("--- 패치조인 + dsl 테스트 ---");
//        System.out.println("###################################");
//        System.out.println(stopWatch.prettyPrint());
//
//        // 조회 결과를 출력하는 부분도 추가할 수 있습니다.
//        storesWithUser.forEach(store -> {
//            System.out.println("Store Name: " + store.getName());
//            // 연관된 User 정보 출력, 이 부분은 해당하는 관계와 필드에 따라 달라질 수 있습니다.
//            if (store.getUser() != null) {
//                System.out.println("Owner: " + store.getUser().getEmail());
//            }
//            System.out.println();
//        });
//    }

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


}
