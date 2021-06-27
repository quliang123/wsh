package com.wsh.web.controller.miniapp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wsh.common.annotation.Log;
import com.wsh.common.core.controller.BaseController;
import com.wsh.common.core.domain.AjaxResult;
import com.wsh.common.core.page.TableDataInfo;
import com.wsh.common.enums.BusinessType;
import com.wsh.common.utils.poi.ExcelUtil;
import com.wsh.system.domain.Message;
import com.wsh.system.service.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 消息Controller
 * 
 * @author wsh
 * @date 2021-03-31
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/miniapp/message" )
@Api(tags = "消息模块")
public class MessageController extends BaseController {

    private final IMessageService iMessageService;

    /**
     * 查询消息列表
     */
    @ApiOperation("消息列表")
    @GetMapping("/list")
    public TableDataInfo list(Message message) {
        startPage();
        message.setUserId(getUserId());
        List<Message> list = iMessageService.queryList(message);
        return getDataTable(list);
    }

    /**
     * 导出消息列表
     */
    @Log(title = "消息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(Message message) {
        List<Message> list = iMessageService.queryList(message);
        ExcelUtil<Message> util = new ExcelUtil<Message>(Message.class);
        return util.exportExcel(list, "message" );
    }

    /**
     * 获取消息详细信息
     */
    @ApiOperation("消息详情")
    @GetMapping(value = "/{messageId}" )
    public AjaxResult getInfo(@PathVariable("messageId" ) Long messageId) {
        return AjaxResult.success(iMessageService.getById(messageId));
    }

    /**
     * 新增消息
     */
    @Log(title = "消息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Message message) {
        return toAjax(iMessageService.save(message) ? 1 : 0);
    }

    /**
     * 修改消息
     */
    @Log(title = "消息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Message message) {
        return toAjax(iMessageService.updateById(message) ? 1 : 0);
    }

    /**
     * 删除消息
     */
    @Log(title = "消息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{messageIds}" )
    public AjaxResult remove(@PathVariable Long[] messageIds) {
        return toAjax(iMessageService.removeByIds(Arrays.asList(messageIds)) ? 1 : 0);
    }


    /**
     * 我的消息数量
     * @param
     * @return
     */
    @GetMapping(value = "/count")
    @ApiOperation("我的消息数量统计")
    public AjaxResult count(){
        int count = iMessageService.count(new QueryWrapper<Message>().eq("user_id", getUserId()).eq("status", "0"));
        return AjaxResult.success(count);
    }

}
