package io.youcham.modules.qrtz.controller;

import io.youcham.modules.sys.shiro.ShiroUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.ValidatorUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.youcham.modules.qrtz.entity.QrtzRunHistoryEntity;
import io.youcham.modules.qrtz.service.QrtzRunHistoryService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 定时任务执行历史记录
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-01-04 15:43:04
 */
@RestController
@RequestMapping("qrtz/qrtzrunhistory")
public class QrtzRunHistoryController {
    @Autowired
    private QrtzRunHistoryService qrtzRunHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("qrtz:qrtzrunhistory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = qrtzRunHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qrtz:qrtzrunhistory:info")
    public R info(@PathVariable("id") String id){
        QrtzRunHistoryEntity qrtzRunHistory = qrtzRunHistoryService.selectById(id);

        return R.ok().put("qrtzRunHistory", qrtzRunHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("qrtz:qrtzrunhistory:save")
    public R save(@RequestBody QrtzRunHistoryEntity qrtzRunHistory){
    	ValidatorUtils.validateEntity(qrtzRunHistory,AddGroup.class);
    
   	 	qrtzRunHistory.setCreateId(ShiroUtils.getUserId());
    	qrtzRunHistory.setCreateTime(new Date());
        qrtzRunHistory.setCreateName(ShiroUtils.getUserEntity().getName());
        qrtzRunHistoryService.insert(qrtzRunHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("qrtz:qrtzrunhistory:update")
    public R update(@RequestBody QrtzRunHistoryEntity qrtzRunHistory){
        ValidatorUtils.validateEntity(qrtzRunHistory,AddGroup.class);
       
        qrtzRunHistory.setUpdateId(ShiroUtils.getUserId());
    	qrtzRunHistory.setUpdateTime(new Date());
        qrtzRunHistory.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = qrtzRunHistoryService.updateAllColumnById(qrtzRunHistory);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("qrtz:qrtzrunhistory:delete")
    public R delete(@RequestBody String[] ids){
        qrtzRunHistoryService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
