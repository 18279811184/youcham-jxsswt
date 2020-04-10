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

import io.youcham.common.constant.StatusEnum;
import io.youcham.common.utils.Constant;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;
import io.youcham.modules.sys.constant.DepartLevel;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.service.SysDeptService;
import io.youcham.modules.sys.service.impl.SysDeptServiceImpl;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 部门管理
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("sys:dept:list")
	public List<SysDeptEntity> list(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

		return deptList;
	}
	
	/**
	 * 监省室纪检组列表
	 */
	@RequestMapping("/listjs")
//	@RequiresPermissions("sys:dept:list")
	public List<SysDeptEntity> listjs(){
		Map<String, Object> nm = new HashMap<String, Object>();
		nm.put("status", StatusEnum.NORMAL.getValue()+"");
		nm.put("level", "1,2");
		List<SysDeptEntity> deptList = sysDeptService.queryList(nm);

		return deptList;
	}
	
	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
//	@RequiresPermissions("sys:dept:select")
	public R select(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", StatusEnum.NORMAL.getValue()+"");
		List<SysDeptEntity> deptList = sysDeptService.queryList(params);

		//添加一级部门
		if(ShiroUtils.isSuperUser()){
			SysDeptEntity root = new SysDeptEntity();
			root.setDeptId("0");
			root.setName("一级部门");
			root.setParentId("-1");
			root.setOpen(true);
			deptList.add(root);
		}

		return R.ok().put("deptList", deptList);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	public R info(){
		String deptId = "0";
		if(!ShiroUtils.isSuperUser()){
			List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
			String parentId = null;
			for(SysDeptEntity sysDeptEntity : deptList){
				if(parentId == null){
					parentId = sysDeptEntity.getParentId();
					continue;
				}
				
				// 
//				if(parentId > sysDeptEntity.getParentId().StringValue()){
//					parentId = sysDeptEntity.getParentId();
//				}
			}
			deptId = parentId;
		}

		return R.ok().put("deptId", deptId);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	//@RequiresPermissions("sys:dept:info")
	public R info(@PathVariable("deptId") String deptId){
		SysDeptEntity dept = sysDeptService.selectById(deptId);
		
		return R.ok().put("dept", dept);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:dept:save")
	public R save(@RequestBody SysDeptEntity dept){
		ValidatorUtils.validateEntity(dept,AddGroup.class);
		sysDeptService.insert(dept);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:dept:update")
	public R update(@RequestBody SysDeptEntity dept){
		ValidatorUtils.validateEntity(dept,UpdateGroup.class);
		sysDeptService.updateById(dept);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:dept:delete")
	public R delete(String deptId){
		//判断是否有子部门
		List<String> deptList = sysDeptService.queryDetpIdList(deptId);
		if(deptList.size() > 0){
			return R.error("请先删除子部门");
		}

		sysDeptService.deleteById(deptId);
		
		return R.ok();
	}
	
	/** 
	 * @Description: 获取部门等级
	 * @author : guoqiang
	 * @date : 2018年5月4日 下午5:03:20 
	 * @return
	 */
	@RequestMapping("/getDepartLevel")
	public R getDepartLevel(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> mapBean = null;
		for (DepartLevel _enum : DepartLevel.values()) {
			mapBean = new HashMap<String, Object>();
			mapBean.put("value", _enum.getValue());
			mapBean.put("text", _enum.getDescription());
			list.add(mapBean);
		}
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/getListByLevel")
	public List<SysDeptEntity> getListByLevel(Integer level){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", StatusEnum.NORMAL.getValue()+"");
		params.put("level", level+"");
		List<SysDeptEntity> deptList = sysDeptService.queryList(params);

		return deptList;
	}		
}
