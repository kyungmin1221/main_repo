package com.example.delivery.service.store;

import com.example.delivery.aws.S3Uploader;
import com.example.delivery.domain.Menu;
import com.example.delivery.domain.Store;
import com.example.delivery.domain.User;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.dto.StorePageDto;
import com.example.delivery.dto.StoreProDto;
import com.example.delivery.repository.MenuRepository;
import com.example.delivery.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    // S3 이미지 디렉토리 이름
    private static final String STORE_S3_IMAGE_DIR_NAME = "store";
    private static final String MENU_S3_IMAGE_DIR_NAME = "menu";

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final S3Uploader s3Uploader;

    // 음식점 정보 등록
    @Override
    @Transactional
    public void registerStore(StoreDto.CreateRequest requestDto, User user) throws IOException {
        MultipartFile requestImage = requestDto.getImage();
        String uploadedImageUrl = s3Uploader.upload(requestImage, STORE_S3_IMAGE_DIR_NAME); // 이미지 S3 업로드

        Store store = Store.builder()
                .name(requestDto.getName())
                .workTime(requestDto.getWorkTime())
                .address(requestDto.getAddress())
                .category(requestDto.getCategory())
                .imageUrl(uploadedImageUrl)
                .user(user)
                .build();
        storeRepository.save(store);
    }

    @Override
    public StoreDto.InfoResponse getStoreInfo(User user) {
        Optional<Store> findStore = storeRepository.findByUserId(user.getId());
        if (findStore.isPresent()) {
            Store store = findStore.get();

            return StoreDto.InfoResponse.builder()
                    .name(store.getName())
                    .workTime(store.getWorkTime())
                    .category(store.getCategory())
                    .address(store.getAddress())
                    .storeScore(store.getStoreScore())
                    .totalSales(store.getTotalSales())
                    .imageUrl(store.getImageUrl())
                    .build();
        }
        return null;
    }

    @Override
    @Transactional
    public void menuRegister(StoreDto.CreateMenuRequest requestDto, User user) throws IOException {
        MultipartFile requestImage = requestDto.getImage();
        String uploadedImageUrl = s3Uploader.upload(requestImage, MENU_S3_IMAGE_DIR_NAME); // 이미지 S3 업로드

        Store store = storeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 가게는 존재하지 않습니다."));

        Menu menu = Menu.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .imageUrl(uploadedImageUrl)
                .store(store)
                .build();
        menuRepository.save(menu);
    }

    @Override
    public List<StoreDto.MenuInfoResponse> getMenuInfo(User user) {
        Store store = storeRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("음식점이 존재하지 않습니다."));
        List<Menu> findMenus = store.getMenus();

        ArrayList<StoreDto.MenuInfoResponse> responseMenus = new ArrayList<>();
        for (Menu findMenu : findMenus) {
            StoreDto.MenuInfoResponse menu = StoreDto.MenuInfoResponse.builder()
                    .id(findMenu.getId())
                    .name(findMenu.getName())
                    .price(findMenu.getPrice())
                    .description(findMenu.getDescription())
                    .imageUrl(findMenu.getImageUrl())
                    .build();
            responseMenus.add(menu);
        }

        return responseMenus;
    }

    @Override
    public List<StoreDto.MenuInfoResponse> getMenuInfoByStoreId(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("음식점이 존재하지 않습니다."));
        List<Menu> findMenus = store.getMenus();

        ArrayList<StoreDto.MenuInfoResponse> responseMenus = new ArrayList<>();
        for (Menu findMenu : findMenus) {
            StoreDto.MenuInfoResponse menu = StoreDto.MenuInfoResponse.builder()
                    .id(findMenu.getId())
                    .name(findMenu.getName())
                    .price(findMenu.getPrice())
                    .description(findMenu.getDescription())
                    .imageUrl(findMenu.getImageUrl())
                    .build();
            responseMenus.add(menu);
        }

        return responseMenus;
    }

    // 음식점 조회를 위한 리스트 반환 (“사장님” 및 “고객님”은 카테고리 기반으로 음식점을 검색하여 볼 수 있어야 합니다)
    @Override
    public StorePageDto.Info searchStoreByCategory(String category, int start, int size){
        Page<Store> storePage = storeRepository.findByCategory(category,
                PageRequest.of(start, size));

        return  StorePageDto.Info.builder()
                .stores(storePage.getContent().stream()
                        .map(this::convertToStoreDto)
                        .toList())
                .idx(storePage.getNumber() + 1)
                .totalPage(storePage.getTotalPages())
                .build();
    }



    @Override
    public StorePageDto.Info searchStoreByKeyword(String category, int start, int size){
        Pageable pageable = PageRequest.of(start, size);
        Page<Store> storePage = storeRepository.searchByCategoryWithPaging(category, pageable);

        return StorePageDto.Info.builder()
                .stores(storePage.getContent().stream()
                        .map(this::convertToStoreDto)
                        .collect(Collectors.toList()))
                .idx(storePage.getNumber() + 1)
                .totalPage(storePage.getTotalPages())
                .build();
    }

    @Override
    public Page<StoreProDto.ProDto> searchStoresByCategoryWithPaging(String category, int start, int size) {
        Pageable pageable = PageRequest.of(start, size);
        return storeRepository.testProjection(category, pageable);
    }



    // 음식점 조회를 위한 리스트 반환 (“사장님” 및 “고객님”은 키워드 기반으로 음식점을 검색하여 볼 수 있어야 합니다)
//    @Override
//    public StorePageDto.Info searchStoreByKeyword(String keyword, int start, int size) {
//
//        Page<Store> storePage = storeRepository.findByMenuNameContainingKeyword(keyword, PageRequest.of(start, size));
//
//        return  StorePageDto.Info.builder()
//                .stores(storePage.getContent().stream()
//                        .map(this::convertToStoreDto)
//                        .toList())
//                .idx(storePage.getNumber() + 1)
//                .totalPage(storePage.getTotalPages())
//                .build();
//    }




    @Override
    public StoreDto.InfoResponse getStoreInfo(Integer storeId) {
        Store store = findStoreId(storeId);
        return StoreDto.InfoResponse.builder()
                .name(store.getName())
                .workTime(store.getWorkTime())
                .category(store.getCategory())
                .address(store.getAddress())
                .storeScore(store.getStoreScore())
                .totalSales(store.getTotalSales())
                .imageUrl(store.getImageUrl())
                .build();
    }

    @Override
    public Store findStoreId(Integer storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("음식점이 존재하지 않습니다."));
    }

    public StoreDto.SearchResponse convertToStoreDto(Store store) {
        if(store == null) {
            return null;
        }

        return StoreDto.SearchResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .storeScore(store.getStoreScore())
                .workTime(store.getWorkTime())
                .imageUrl(store.getImageUrl()).build();
    }



}
