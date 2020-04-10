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

package io.youcham.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.MapUtils;

import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.sys.dao.SysDictDao;
import io.youcham.modules.sys.entity.SysDictEntity;
import io.youcham.modules.sys.service.SysDictService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("sysDictService")
public class SysDictServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String name = (String) params.get("name");
		String parentId = (String)params.get("parentId");
		//管理用的条件
		String dictType = (String)params.get("dictType");
		String id = (String)params.get("id");
		String nvalue = (String)params.get("nvalue");
		
		
		Page<SysDictEntity> page = this.selectPage(new Query<SysDictEntity>(params).getPage(),
				new EntityWrapper<SysDictEntity>()
				.like(StringUtils.isNotBlank(name), "name", name)
				.eq(StringUtils.isNotEmpty(parentId), "parent_id", parentId)
				//add by xcg
				.eq(StringUtils.isNotEmpty(dictType), "type", dictType)
				.eq(StringUtils.isNotEmpty(id), "parent_id", id)
				.like(StringUtils.isNotBlank(nvalue), "value", nvalue)
				//end
				.orderBy("type,order_num")
		);

		return new PageUtils(page);
	}
    
   /* @Override
    public List<SysDictEntity> queryDetpIdList1(Map<String, Object> params) {
    	 List<SysDictEntity> syslit = new ArrayList<>();
    	//按字典类型查询
        String type = (String)params.get("type");

<<<<<<< .mine
        Page<SysDictEntity> page = this.selectPage(
                new Query<SysDictEntity>(params).getPage(),
                new EntityWrapper<SysDictEntity>()
                    .like(StringUtils.isNotBlank(name),"name", name)
        );

        return syslit;
    }*/

	@Override
	public List<SysDictEntity> queryDetpIdList(String type) {
		 List<SysDictEntity> syslit = new ArrayList<>();
		//按字典类型查询
	    // String type = (String)params.get("type");
	     if(type == null || type.equals("")){
	    	 
	     }else{
	    	syslit = baseMapper.getdiclist(type);
	     }
	     
	     
		 
		return syslit;
	}
    
	

	@Override
	public List<SysDictEntity> listByNation() {
		return baseMapper.listByNation();
	}

	@Override
	public List<SysDictEntity> listByPost(String post) {
		return baseMapper.listByPost(post);
	}

	@Override
	public List<SysDictEntity> listByPoliticaloutlook() {
		return baseMapper.listByPoliticaloutlook();
	}

	@Override
	public List<SysDictEntity> queryList(Map<String, Object> params) {
		EntityWrapper<SysDictEntity> ew = new EntityWrapper<SysDictEntity>();
		
		if(MapUtils.isNotEmpty(params)){
			String parentId = (String)params.get("parentId");
			if(StringUtils.isNotEmpty(parentId)){
				ew.eq("parent_id", parentId);
			}

			String parentName = (String)params.get("parentName");
			if(StringUtils.isNotEmpty(parentName)){
				SysDictEntity dict = baseMapper.selectList(new EntityWrapper<SysDictEntity>().eq("value",parentName)).get(0);
				ew.eq("parent_id", dict.getId());
			}

			String type = (String)params.get("type");
			if(StringUtils.isNotEmpty(type)){
				ew.eq("type", type);
			}
			
			String ids = (String)params.get("ids");
			if(StringUtils.isNotEmpty(ids)){
				ew.in("id", ids);
			}
		}

		ew.orderBy("type,order_num");

		return this.selectList(ew);
	}

	@Override
	public SysDictEntity getByTypeAnCode(String type, String code) {
		return this.selectOne(
				new EntityWrapper<SysDictEntity>()
				.eq("type", type).eq("code", code)
			);
	}

	@Override
	public String getMaxOrderNum(String parentId) {
		return baseMapper.getMaxOrderNum(parentId);
	}

	@Override
	public int updateOrderNum(SysDictEntity dict) {
		return baseMapper.updateOrderNum(dict);
	}


}
