package com.example.delivery.controller;

import com.example.delivery.service.Like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j(topic = "LikeController")
@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private LikeService likeService;


}
