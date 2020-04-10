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

package io.youcham.modules.sys.dao;

import io.youcham.modules.sys.entity.SysDictEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-27
 */
public interface SysDictDao extends BaseMapper<SysDictEntity> {

	  /**
     * 查询类型查询字典列表
     * @param 
     */
	List<SysDictEntity> getdiclist(String type);
	

	/**
	 * 字典：民族
	 * 
	 * @return
	 */
	List<SysDictEntity> listByNation();

	/**
	 * 字典：民族
	 * 
	 * @return
	 */
	List<SysDictEntity> listByPost(String post);

	/**
	 * 字典：政治面貌
	 * 
	 * @return
	 */
	List<SysDictEntity> listByPoliticaloutlook();
	
	/**
	 * 获取最大排序字段
	 */
	String getMaxOrderNum(String parentId);
	/**
	 * 插队排序
	 */
	int updateOrderNum(SysDictEntity dict);
	
}
