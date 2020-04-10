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

package io.youcham.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;

import io.youcham.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
public interface SysDeptService extends IService<SysDeptEntity> {

	List<SysDeptEntity> queryList(Map<String, Object> map);

	/**
	 * 查询子部门ID列表
	 * 
	 * @param parentId
	 *            上级部门ID
	 */
	List<String> queryDetpIdList(String parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<String> getSubDeptIdList(String deptId);

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

	/**
	 * @Description: 根据系统编码得到Organ.
	 * @author : guoqiang
	 * @date : 2018年5月22日 下午2:24:32
	 * @param sysCode
	 *            机构系统编码
	 * @param companyId
	 *            公司Id
	 * @return
	 */
	SysDeptEntity getBySC(String sysCode, String companyId);

	String insertdept(SysDeptEntity sysdept);

	SysDeptEntity selectByIdzdy(String id);

	boolean updatedept(SysDeptEntity sysdept);

	/**
	 * 角色获取部门数据权限
	 *
	 * @param hasSelf
	 * @param hasChild
	 * @return java.lang.String
	 * @author xudongfeng
	 * @date 9:44 2019/7/17
	 **/
	String getPermissionDeptIdsStr(Boolean hasSelf, Boolean hasChild);

	/**
	 *  按角色获取部门数据权限
	 *
	 * @param hasSelf
	 * @param hasChild
	 * @return java.util.List<java.lang.String>
	 * @author xudongfeng
	 * @date 9:44 2019/7/17
	 **/
	List<String> getPermissionDeptIdsArr(Boolean hasSelf, Boolean hasChild);


}
