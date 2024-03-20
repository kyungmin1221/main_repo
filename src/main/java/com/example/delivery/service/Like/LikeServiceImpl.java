//package com.example.delivery.service.Like;
//
//
//import com.example.delivery.domain.Like;
//import com.example.delivery.domain.Store;
//import com.example.delivery.domain.User;
//import com.example.delivery.dto.LikeDto;
//import com.example.delivery.repository.LikeRepository;
//import com.example.delivery.repository.StoreRepository;
//import com.example.delivery.service.store.StoreService;
//import com.example.delivery.service.store.StoreServiceImpl;
//import com.example.delivery.service.user.UserService;
//import com.example.delivery.service.user.UserServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class LikeServiceImpl implements LikeService {
//
//    private final UserService userService;
//    private final StoreService storeService;
//    private final LikeRepository likeRepository;
//    private final StoreRepository storeRepository;
//
//    @Transactional
//    public LikeDto.LikeResponseDto toggleLike(Long userId, Integer storeId) {
//        User user = userService.findUserId(userId);
//        Store store = storeService.findStoreId(storeId);
//
//        Optional<Like> like = likeRepository.findByUserIdAndStoreId(userId, storeId);
//        if(like.isPresent()) {
//            return removeLike(like.get(), store);
//        } else {
//            return addLike(user,store);
//        }
//
//    }
//
//    private LikeDto.LikeResponseDto addLike(User user, Store store) {
//        Like like = Like.builder()
//                .user(user)
//                .store(store)
//                .build();
//        likeRepository.save(like);
//
//        Store newStore = Store.builder()
//                .likeCount(store.getLikeCount() + 1)
//                .build();
//        storeRepository.save(newStore);
//
//        return new LikeDto.LikeResponseDto(false);
//    }
//
//    private LikeDto.LikeResponseDto removeLike(Like like, Store store) {
//        likeRepository.delete(like);
//
//        Store newStore = Store.builder()
//                .likeCount(store.getLikeCount() - 1)
//                .build();
//        storeRepository.save(newStore);
//        return new LikeDto.LikeResponseDto(false);
//    }
//}
