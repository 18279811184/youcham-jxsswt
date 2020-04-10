package io.youcham.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;
import io.youcham.modules.sys.entity.SysDeclarationDateEntity;
import io.youcham.modules.sys.service.SysDeclarationDateService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/sysdeclarationdate")
public class SysDeclarationDateController {
    @Autowired
    private SysDeclarationDateService sysDeclarationDateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysdeclarationdate:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysDeclarationDateService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysdeclarationdate:info")
    public R info(@PathVariable("id") String id) {
        SysDeclarationDateEntity sysDeclarationDate = sysDeclarationDateService.selectById(id);

        return R.ok().put("sysDeclarationDate", sysDeclarationDate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysdeclarationdate:save")
    public R save(@RequestBody SysDeclarationDateEntity sysDeclarationDate) {
        ValidatorUtils.validateEntity(sysDeclarationDate, AddGroup.class);
        sysDeclarationDate.setCreateId(ShiroUtils.getUserId());
        sysDeclarationDate.setCreateTime(new Date());
        sysDeclarationDateService.insert(sysDeclarationDate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysdeclarationdate:update")
    public R update(@RequestBody SysDeclarationDateEntity sysDeclarationDate){
        ValidatorUtils.validateEntity(sysDeclarationDate, UpdateGroup.class);

        sysDeclarationDate.setCreateId(ShiroUtils.getUserId());
        sysDeclarationDate.setCreateTime(new Date());
        sysDeclarationDateService.updateById(sysDeclarationDate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysdeclarationdate:delete")
    public R delete(@RequestBody String[] ids){
        sysDeclarationDateService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/lists")
    public R lists(@RequestParam Map<String, Object> params){
        EntityWrapper<SysDeclarationDateEntity> ew = new EntityWrapper<SysDeclarationDateEntity>();
        ew.like("theDate",(String)params.get("theDate"));
        ew.like("month",(String)params.get("month"));
        ew.orderBy("MONTH");
        List<SysDeclarationDateEntity> list = sysDeclarationDateService.selectList(ew);
        return R.ok().put("list",list);
    }
}
