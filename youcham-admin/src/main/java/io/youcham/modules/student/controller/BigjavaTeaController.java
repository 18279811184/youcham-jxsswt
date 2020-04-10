package io.youcham.modules.student.controller;

import io.youcham.modules.sys.entity.SysExportModuleEntity;
import io.youcham.modules.sys.shiro.ShiroUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.ValidatorUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.youcham.modules.student.entity.BigjavaTeaEntity;
import io.youcham.modules.student.service.BigjavaTeaService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-09 12:15:31
 */
@RestController
@RequestMapping("student/bigjavatea")
public class BigjavaTeaController {
    @Autowired
    private BigjavaTeaService bigjavaTeaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("student:bigjavatea:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bigjavaTeaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("student:bigjavatea:info")
    public R info(@PathVariable("id") String id){
        BigjavaTeaEntity bigjavaTea = bigjavaTeaService.selectById(id);

        return R.ok().put("bigjavaTea", bigjavaTea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("student:bigjavatea:save")
    public R save(@RequestBody BigjavaTeaEntity bigjavaTea){
    	ValidatorUtils.validateEntity(bigjavaTea,AddGroup.class);
    

        bigjavaTeaService.insert(bigjavaTea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("student:bigjavatea:update")
    public R update(@RequestBody BigjavaTeaEntity bigjavaTea){
        ValidatorUtils.validateEntity(bigjavaTea,AddGroup.class);
       

        Boolean re = bigjavaTeaService.updateAllColumnById(bigjavaTea);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("student:bigjavatea:delete")
    public R delete(@RequestBody String[] ids){
        bigjavaTeaService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * @Description: 获取下拉框
     * @author : guoqiang
     * @date : 2018年10月11日 下午2:50:26
     * @return
     */
    @RequestMapping("/select")
    public List<BigjavaTeaEntity> select(){
        List<BigjavaTeaEntity> modules = bigjavaTeaService.selectList(null);

        return modules;
    }
}
