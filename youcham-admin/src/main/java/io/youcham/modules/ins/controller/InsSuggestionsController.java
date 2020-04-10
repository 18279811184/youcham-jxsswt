package io.youcham.modules.ins.controller;

import io.youcham.common.constant.Variable;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.modules.ins.entity.InsSuggestionsEntity;
import io.youcham.modules.ins.entity.SysFiletableEntity;
import io.youcham.modules.ins.fileentity.FileConfigReader;
import io.youcham.modules.ins.service.InsSuggestionsService;
import io.youcham.modules.ins.service.SysFiletableService;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



/**
 * 意见建议
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-10 10:45:30
 */
@RestController
@RequestMapping("ins/inssuggestions")
public class InsSuggestionsController {
    @Autowired
    private InsSuggestionsService insSuggestionsService;
    @Autowired
	private SysFiletableService sysFiletableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("ins:inssuggestions:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = insSuggestionsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{suggestId}")
    @RequiresPermissions("ins:inssuggestions:info")
    public R info(@PathVariable("suggestId") String suggestId){
        InsSuggestionsEntity insSuggestions = insSuggestionsService.selectById(suggestId);

        return R.ok().put("insSuggestions", insSuggestions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("ins:inssuggestions:save")
    public R save(@RequestBody InsSuggestionsEntity insSuggestions){
    	ValidatorUtils.validateEntity(insSuggestions,AddGroup.class);
    	insSuggestions.setStatu(2);
    	insSuggestions.setCreateTime(new Date());
        insSuggestionsService.insert(insSuggestions);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ins:inssuggestions:update")
    public R update(@RequestBody InsSuggestionsEntity insSuggestions){
        ValidatorUtils.validateEntity(insSuggestions);
        //存放修改人——修改时间
        SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());
        insSuggestions.setUpdateTime(new Date());
        insSuggestions.setUpdateUserid(user.getUserId());
        
        insSuggestionsService.updateById(insSuggestions);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ins:inssuggestions:delete")
    public R delete(@RequestBody String[] suggestIds){
        insSuggestionsService.deleteBatchIds(Arrays.asList(suggestIds));

        return R.ok();
    }

	/** 
	 * @Description: 保存 
	 * @author : 郭强 guoqiang@qq.com
	 * @date : 2017年12月7日 下午3:10:09 
	 * @param request
	 * @param imgFile
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/wxSugSave")
	public R wxSugSave(MultipartHttpServletRequest request,@RequestParam("file")MultipartFile filep) throws UnsupportedEncodingException{
		
		String geturl = "";
		if (!filep.isEmpty()) {
			//转换唯一文件名
			String imagehz = filep.getOriginalFilename().substring(filep.getOriginalFilename().lastIndexOf("."));
			Long timename = System.currentTimeMillis();
			StringBuffer sbuf = new StringBuffer();
			Random random = new Random();
			for(int i=0;i<4;i++){
				sbuf.append(random.nextInt(10));
			}
			random = null;
			String name = "file"+timename+imagehz;
			String fileName = sbuf.append("_").append(name).toString();
			
			// 比对文件大学
			Long size = filep.getSize();
			Long maxSize = 0L;
			String ms = FileConfigReader.getUploadFileMaxSize();
			if(ms!=null){
				maxSize = Long.parseLong(ms);
				ms=null;
			}
			if(size> maxSize){
				return R.error("上传的文件过大");
			}
			
			//创建当天文件夹
			String rootPath = FileConfigReader.getUploadFilePath();
			String path = rootPath+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			File insertFile = new File(path,fileName);
			if(!insertFile.getParentFile().exists()){
				insertFile.getParentFile().mkdirs();
			}
			
			try {
				filep.transferTo(insertFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				R.error("上传文件出错!!!");
			} catch (IOException e) {
				e.printStackTrace();
				return R.error("上传文件出错");
			}
			
			 SysFiletableEntity sysFiletable = new SysFiletableEntity();
			 sysFiletable.setFileName(fileName);
			 sysFiletable.setFileType(imagehz);
			 
			 geturl = URLEncoder.encode(insertFile.getAbsolutePath(),"utf-8");  
			 sysFiletable.setFileUrl(geturl);
			// 自动获取 创建时间
			 sysFiletable.setFileCreatedate(new Date());
			 //放文件真实名称
			 sysFiletable.setFileRealname(filep.getOriginalFilename());
			 
			 boolean re = sysFiletableService.insert(sysFiletable);
			 if(!re){
				 return R.error("保存失败！");  
			 }
		}
		InsSuggestionsEntity insSuggestions = new InsSuggestionsEntity();
    	insSuggestions.setCreateTime(new Date());
		
    	insSuggestions.setUserName(request.getParameter("userName"));
    	insSuggestions.setUserPhone(request.getParameter("userPhone"));
    	insSuggestions.setSugTitle(request.getParameter("sugTitle"));
    	insSuggestions.setSugContent(request.getParameter("sugContent"));
    	insSuggestions.setSugEnclosure(request.getContextPath()+"/fileAction/loadImg?cateogry="+geturl);
    	
    	
		ValidatorUtils.validateEntity(insSuggestions,AddGroup.class);
        insSuggestionsService.insert(insSuggestions);
			
		return R.ok();
	}
    
}
