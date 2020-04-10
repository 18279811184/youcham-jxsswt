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


import io.youcham.common.annotation.SysLog;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.modules.ftp.FtpOperation;
import io.youcham.modules.sys.entity.SysConfigEntity;
import io.youcham.modules.sys.service.SysConfigService;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysConfigService.queryPage(params);

		return R.ok().put("page", page);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") String id){
		SysConfigEntity config = sysConfigService.selectById(id);
		
		return R.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.save(config);
		
		return R.ok();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody String[] ids){
		sysConfigService.deleteBatch(ids);
		
		return R.ok();
	}
	
	/**
	 * Ftp存储文件
	 */
	@RequestMapping("/importfile")
	public R importFile(HttpServletRequest request) {
		FtpOperation ftpOperation = new FtpOperation();
		// 上传
		// 从前台传过来的文件输入流
		MultipartFile multipartFile = null;
		InputStream inputStream = null;

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
					MultipartHttpServletRequest.class);
			multipartFile = multipartRequest.getFile("file");
			if (null == multipartFile) {// 有更改文件
				return R.error("未上传文件");
			}
		}
		
		// 生成唯一文件名  避免重复覆盖  该路径存到数据库中 下载时使用
		String newFileName = FtpOperation.getDateDirAndFileName(multipartFile.getOriginalFilename());
		try {
		   Boolean res =  ftpOperation.uploadToFtp(multipartFile.getInputStream(),newFileName,false);
		   System.out.println("上传FTP结果res="+res+",filename="+newFileName);
		   if(res){
		       //上传成功

		   }else {
			   return R.error("上传失败");
		   }

		} catch (Exception e) {
		    e.printStackTrace();
		    return R.error("上传失败");
		}

		return R.ok();
		
	}
	

}
