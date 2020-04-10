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

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.youcham.modules.sys.entity.SysDeptEntity;

import java.util.List;

/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

	/**
	 * 查询子部门ID列表
	 * 
	 * @param parentId
	 *            上级部门ID
	 */
	List<String> queryDetpIdList(String parentId);

	/**
	 * 查询出部门等级并去重
	 * 
	 * @return
	 */
	List<SysDeptEntity> queryDetpLevel();

	/**
	 * 根据部门等级查询出该等级下的所有部门
	 * 
	 * @return
	 */
	List<SysDeptEntity> queryDetp(Integer value);
	
	 int insertdept(SysDeptEntity sysdept);    
	 
	 SysDeptEntity sysdept(String id);
	 boolean updatedept(SysDeptEntity sysdept);    

}
