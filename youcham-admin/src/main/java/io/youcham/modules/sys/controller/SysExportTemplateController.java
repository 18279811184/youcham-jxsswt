package io.youcham.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.youcham.common.constant.Variable;
import io.youcham.common.utils.DocUtil;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;
import io.youcham.modules.ins.fileentity.FileConfigReader;
import io.youcham.modules.student.entity.BigjavaClassEntity;
import io.youcham.modules.student.entity.BigjavaStuEntity;
import io.youcham.modules.student.service.BigjavaStuService;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysExportTemplateEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysExportTemplateService;
import io.youcham.modules.sys.service.SysUserService;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

/**
 * 导出模板
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-10-11 09:49:51
 */
@RestController
@RequestMapping("sys/sysexporttemplate")
public class SysExportTemplateController {
	@Autowired
	private SysExportTemplateService sysExportTemplateService;
	@Autowired
	private BigjavaStuService bigjavaStuService;
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:sysexporttemplate:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = sysExportTemplateService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:sysexporttemplate:info")
	public R info(@PathVariable("id") String id) {
		SysExportTemplateEntity sysExportTemplate = sysExportTemplateService.selectById(id);

		return R.ok().put("sysExportTemplate", sysExportTemplate);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:sysexporttemplate:save")
	public R save(@RequestBody SysExportTemplateEntity sysExportTemplate) {
		ValidatorUtils.validateEntity(sysExportTemplate, AddGroup.class);
		sysExportTemplate.setCreateId(ShiroUtils.getUserId());
		sysExportTemplate.setCreateTime(new Date());
		sysExportTemplateService.insert(sysExportTemplate);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:sysexporttemplate:update")
	public R update(@RequestBody SysExportTemplateEntity sysExportTemplate) {
		ValidatorUtils.validateEntity(sysExportTemplate, UpdateGroup.class);

		sysExportTemplate.setUpdateId(ShiroUtils.getUserId());
		sysExportTemplate.setUpdateTime(new Date());
		Boolean re = sysExportTemplateService.updateAllColumnById(sysExportTemplate);// 全部更新

		return re ? R.ok() : R.oldVersion();
	}

	/**
	 * 保存
	 */
	@RequestMapping("/saveFileAndData")
	@RequiresPermissions("sys:sysexporttemplate:save")
	public R saveFileAndData(SysExportTemplateEntity sysExportTemplate, HttpServletRequest request) {
		MultipartFile multipartFile = null;
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
					MultipartHttpServletRequest.class);
			multipartFile = multipartRequest.getFile("file");

			if (null == multipartFile) {// 有更改文件
				return R.error("未上传文件");
			}
		}

		String rootPath = FileConfigReader.getUploadDocTemplate();
		R createDirRes = DocUtil.createDir(multipartFile, rootPath);
		if ((Integer) createDirRes.get("code") != 0) {
			return createDirRes;
		}

		File insertFile = (File) createDirRes.get("insertFile");

		try {
			multipartFile.transferTo(insertFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return R.error("上传文件出错!!!");
		} catch (IOException e) {
			e.printStackTrace();
			return R.error("上传文件出错");
		}
		// sysExportTemplate.setId(UUID.randomUUID().toString());
		// 获取当前用户的部门信息
		SysDeptEntity dept = (SysDeptEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue());
		sysExportTemplate.setDeptId(dept.getDeptId());
		sysExportTemplate.setCreateId(ShiroUtils.getUserId());
		sysExportTemplate.setCreateTime(new Date());
		sysExportTemplate.setFilepath(insertFile.getPath());
		sysExportTemplate.setFilename(multipartFile.getOriginalFilename());

		ValidatorUtils.validateEntity(sysExportTemplate, AddGroup.class);
		Boolean re = sysExportTemplateService.insert(sysExportTemplate);
		/*
		 * if(re && sysExportTemplate.getStatus()==StatusEnum.NORMAL.getValue()){
		 * SysExportTemplateEntity sysExportTemplate2 = new SysExportTemplateEntity();
		 * sysExportTemplate2.setStatus(0);
		 * sysExportTemplateService.update(sysExportTemplate2, new
		 * EntityWrapper<SysExportTemplateEntity>()
		 * .eq("export_module_id",sysExportTemplate.getExportModuleId())
		 * .ne("id",sysExportTemplate.getId())); }
		 */

		return re ? R.ok() : R.oldVersion();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/updateFileAndData")
	@RequiresPermissions("sys:sysexporttemplate:update")
	public R updateFileAndData(SysExportTemplateEntity sysExportTemplate, HttpServletRequest request) {
		MultipartFile multipartFile = null;
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
					MultipartHttpServletRequest.class);
			multipartFile = multipartRequest.getFile("file");

			if (null != multipartFile) {// 有更改文件
				String rootPath = FileConfigReader.getUploadDocTemplate();
				R createDirRes = DocUtil.createDir(multipartFile, rootPath);
				if ((Integer) createDirRes.get("code") != 0) {
					return createDirRes;
				}

				File insertFile = (File) createDirRes.get("insertFile");
				try {
					multipartFile.transferTo(insertFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return R.error("上传文件出错!!!");
				} catch (IOException e) {
					e.printStackTrace();
					return R.error("上传文件出错");
				}
				sysExportTemplate.setFilepath(insertFile.getPath());
				sysExportTemplate.setFilename(multipartFile.getOriginalFilename());
			}
		}

		ValidatorUtils.validateEntity(sysExportTemplate, UpdateGroup.class);

		sysExportTemplate.setUpdateId(ShiroUtils.getUserId());
		sysExportTemplate.setUpdateTime(new Date());
		Boolean re = sysExportTemplateService.updateById(sysExportTemplate);// 全部更新

		/*
		 * if(re && sysExportTemplate.getStatus()==StatusEnum.NORMAL.getValue()){
		 * SysExportTemplateEntity sysExportTemplate2 = new SysExportTemplateEntity();
		 * sysExportTemplate2.setStatus(0);
		 * sysExportTemplateService.update(sysExportTemplate2, new
		 * EntityWrapper<SysExportTemplateEntity>()
		 * .eq("export_module_id",sysExportTemplate.getExportModuleId())
		 * .ne("id",sysExportTemplate.getId())); }
		 */

		return re ? R.ok() : R.oldVersion();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:sysexporttemplate:delete")
	public R delete(@RequestBody String[] ids) {
		sysExportTemplateService.deleteBatchIds(Arrays.asList(ids));

		return R.ok();
	}


	/**
	 * 删除
	 */
	@RequestMapping("/choose")
	@RequiresPermissions("sys:sysexporttemplate:choose")
	public R choose(@RequestBody String[] ids) {

		SysUserEntity sysUserEntity  = ShiroUtils.getUserEntity();
		List<SysExportTemplateEntity> list = sysExportTemplateService.selectBatchIds(Arrays.asList(ids));


		StringBuffer buf=new StringBuffer();
		int credit = 0;
		for(SysExportTemplateEntity sysExportTemplateEntity : list){

			buf.append(sysExportTemplateEntity.getExportModuleId()+",");
			credit = Integer.valueOf(sysExportTemplateEntity.getName()) + credit;

		}


		BigjavaStuEntity bigjavaStuEntity = new BigjavaStuEntity();

		bigjavaStuEntity.setStuCourse(buf.toString());
		bigjavaStuEntity.setStuCredit(credit);



		bigjavaStuService.update(bigjavaStuEntity,new EntityWrapper<BigjavaStuEntity>()
				.eq(StringUtils.isNotBlank(sysUserEntity.getUsername()),"stu_number", sysUserEntity.getUsername()));

		return R.ok();
	}

}
