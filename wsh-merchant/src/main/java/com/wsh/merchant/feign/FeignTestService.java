package com.wsh.merchant.feign;

import com.wsh.merchant.feign.fallback.FeignTestFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "baidu",url = "http://www.baidu.com",fallback = FeignTestFallback.class)
public interface FeignTestService {

    @GetMapping("/s")
    String search(@RequestParam("wd") String wd);
}
