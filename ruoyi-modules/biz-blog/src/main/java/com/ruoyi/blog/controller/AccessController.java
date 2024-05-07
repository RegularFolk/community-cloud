package com.ruoyi.blog.controller;

import com.ruoyi.blog.domain.BlogAccess;
import com.ruoyi.blog.domain.dto.AccessListDto;
import com.ruoyi.blog.domain.dto.ChangeAccessStatusDto;
import com.ruoyi.blog.domain.vo.AccessGenerateVo;
import com.ruoyi.blog.service.AccessService;
import com.ruoyi.common.core.domain.ListDto;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessController extends BaseController {

    @Resource
    private AccessService accessService;

    /**
     * 生成凭证
     */
    @GetMapping("/generate/{phoneNumber}")
    public AjaxResult generate(@PathVariable("phoneNumber") String phone) {
        AccessGenerateVo vo = accessService.generate(phone);
        return vo == null ? AjaxResult.error("一个开发者只能拥有一个凭证！") : AjaxResult.success(vo);
    }

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody AccessListDto dto) {
        List<BlogAccess> list = accessService.list(dto);
        int total = accessService.getTotal(dto);
        AjaxResult res = AjaxResult.success(list);
        res.put("total", total);
        return res;
    }

    /**
     * 改变状态
     */
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ChangeAccessStatusDto dto) {
        int flag = accessService.changeStatus(dto);
        return flag > 0 ? AjaxResult.success("操作成功！") : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }

    /**
     * 删除凭证
     */
    @PostMapping("/delete/{phone}")
    public AjaxResult delete(@PathVariable("phone") String phoneNumber) {
        int flag = accessService.delete(phoneNumber);
        return flag > 0 ? AjaxResult.success("操作成功！") : AjaxResult.error(AjaxResult.DEFAULT_ERROR_MSG);
    }


}
