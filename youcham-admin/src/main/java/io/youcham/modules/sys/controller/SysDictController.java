/**
 * Copyright 2018 人人开源 http://www.youcham.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.youcham.modules.sys.controller;

import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.modules.sys.entity.SysDictEntity;
import io.youcham.modules.sys.service.SysDictService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    /*@RequiresPermissions("sys:dict:list")*/
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDictService.queryPage(params);

        return R.ok().put("page", page);
    }
    
    /**
     * 查询字典
     */
    @RequestMapping("/finddic")
    //@RequiresPermissions("sys:dict:list")
    public R list(@RequestParam String type){
        List<SysDictEntity> geilist = sysDictService.queryDetpIdList(type);

        return  R.ok().put("templateList", geilist);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") String id){
        SysDictEntity dict = sysDictService.selectById(id);

        return R.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);
        sysDictService.insert(dict);

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("orderNum", dict.getOrderNum()+"");
        List<SysDictEntity> listEx = sysDictService.queryList(params);
        if(listEx.size()>1){
        	// 修改大于等于该排序值对象排序值+1
        	sysDictService.updateOrderNum(dict);
        }
        
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        Boolean reBol = sysDictService.updateById(dict);

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("orderNum", dict.getOrderNum()+"");
        List<SysDictEntity> listEx = sysDictService.queryList(params);
        if(listEx.size()>1){
        	// 修改大于等于该排序值对象排序值+1
        	sysDictService.updateOrderNum(dict);
        }
        
        return reBol?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody String[] ids){
        sysDictService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/select")
    public R select(@RequestParam Map<String, Object> params){
    	 List<SysDictEntity> list = sysDictService.queryList(params);
    	 return R.ok().put("list", list);
    }
    
    @RequestMapping("/getMaxOrderNum")
	public R getMaxOrderNum(String parentId){
		return R.ok().put("maxOrderNum", sysDictService.getMaxOrderNum(parentId));
	}
    
}
