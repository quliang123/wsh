package com.wsh.merchant.controller;

import com.wsh.common.core.controller.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/merchant")
@RestController
public class LoginController extends BaseController {

}
