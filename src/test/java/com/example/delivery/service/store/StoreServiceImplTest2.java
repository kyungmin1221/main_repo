package com.example.delivery.service.store;

import com.example.delivery.dto.StoreDto;
import com.example.delivery.dto.StorePageDto;
import com.example.delivery.dto.StoreProDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class StoreServiceImplTest2 {
    @Autowired
    private StoreService storeService;


    // "치킨" 카테고리를 검색했을 때 속도차이 비교 테스트 코드
    /*
    검색 결과 (DSL 사용 X): 100
    StopWatch '': 0.252218666 seconds
    ----------------------------------------
    Seconds       %       Task name
    ----------------------------------------
    0.252218666   100%    searchByKeyword(DSL 사용 X)
     */
    @Test
    public void testSearchStorePerformance() {
        int start = 0;
        int size = 100;
        String keyword = "치킨";


        StopWatch stopWatch = new StopWatch();

        // 키워드 검색 테스트
        stopWatch.start("searchByKeyword(DSL 사용 X)");
        StorePageDto.Info resultByKeyword = storeService.searchStoreByCategory(keyword, start, size);
        stopWatch.stop();
        System.out.println("검색 결과 (DSL 사용 X): " + resultByKeyword.getStores().size());

        // 실행 시간 출력
        System.out.println(stopWatch.prettyPrint());
    }


    /*
    검색 결과 (DSL 사용): 100
    StopWatch '': 0.376016875 seconds
    ----------------------------------------
    Seconds       %       Task name
    ----------------------------------------
    0.376016875   100%    searchByCategory(DSL 사용)

     */
    @Test
    public void testSearchStorePerformance2() {
        int start = 0;
        int size = 100;
        String category = "치킨";

        StopWatch stopWatch = new StopWatch();

        // 카테고리 검색 테스트
        stopWatch.start("searchByCategory(DSL 사용)");
        StorePageDto.Info resultByCategory = storeService.searchStoreByKeyword(category, start, size);
        stopWatch.stop();
        System.out.println("검색 결과 (DSL 사용): " + resultByCategory.getStores().size());


        // 실행 시간 출력
        System.out.println(stopWatch.prettyPrint());
    }


    @Test
    public void testSearchStoresByCategoryWithPaging() {
        int start = 0; // 페이지 시작 인덱스 (0부터 시작)
        int size = 100; // 페이지당 항목 수
        String category = "치킨";

        StopWatch stopWatch = new StopWatch();

        // 검색 성능 테스트 시작
        stopWatch.start("페이징 테스트(Projections 사용-생성자주입)");

        // DSL(Projections- 생성자)을 사용한 카테고리 검색 및 페이징 처리
        Page<StoreProDto.ProDto> result = storeService.searchStoresByCategoryWithPaging(category, start, size);

        stopWatch.stop();

        // 결과 출력
        System.out.println("검색된 가게 개수: " + result.getContent().size());
        System.out.println("전체 페이지 수: " + result.getTotalPages());
        System.out.println("현재 페이지 인덱스: " + result.getNumber());
        System.out.println("페이지당 항목 수: " + result.getSize());

        // 성능 측정 결과 출력
        System.out.println(stopWatch.prettyPrint());
    }




}