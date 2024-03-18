package com.example.delivery.service.store;

import com.example.delivery.aws.S3Uploader;
import com.example.delivery.domain.Store;
import com.example.delivery.domain.User;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.exception.CustomException;
import com.example.delivery.exception.ErrorCode;
import com.example.delivery.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    // S3 이미지 디렉토리 이름
    private static final String S3_IMAGE_DIR_NAME = "store";

    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    // 음식점 정보 등록
    @Override
    @Transactional
    public void registerStore(StoreDto.CreateRequest requestDto, User user) throws IOException {
        MultipartFile requestImage = requestDto.getImage();
        String uploadedImageUrl = s3Uploader.upload(requestImage, S3_IMAGE_DIR_NAME); // 이미지 S3 업로드

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
                    .likeCount(store.getLikeCount())
                    .totalSales(store.getTotalSales())
                    .imageUrl(store.getImageUrl())
                    .build();
        }
        return null;
    }

    public Store findStoreId(Integer storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.DUPLICATE_STORE));
    }
}
