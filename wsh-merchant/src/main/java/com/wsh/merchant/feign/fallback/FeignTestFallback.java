package com.wsh.merchant.feign.fallback;


import com.wsh.merchant.feign.FeignTestService;

public class FeignTestFallback implements FeignTestService {

    @Override
    public String search(String wd) {
        return null;
    }
}
