package io.youcham.modules.ins.controller;

import io.youcham.common.constant.Variable;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.modules.ins.entity.InsInformEntity;
import io.youcham.modules.ins.service.InsInformService;
import io.youcham.modules.ins.service.SysFiletableService;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 新闻信息表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-06 14:33:31
 */
@RestController
@RequestMapping("ins/insinform")
public class InsInformController {
    @Autowired
    private InsInformService insInformService;
	@Autowired
	private SysFiletableService sysFiletableService;
	
    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("ins:insinform:list")
    public R list(@RequestParam Map<String, Object> params){
    	//params.put("bePublish", "1");
        PageUtils page = insInformService.queryPage(params);
        
        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @RequestMapping("/listwechat")
//    @RequiresPermissions("ins:insinform:list")
    public R listwechat(@RequestParam Map<String, Object> params){
    	params.put("bePublish", "1");
        PageUtils page = insInformService.queryPage(params);
        
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{informId}")
//    @RequiresPermissions("ins:insinform:info")
    public R info(@PathVariable("informId") String informId){
        InsInformEntity insInform = insInformService.selectById(informId);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", informId+"");
        
        String getcont =  insInform.getInformContent();
        if(getcont == "" || getcont == null){
        	
        }else{
        if (getcont.indexOf("&lt;")!=-1){
         	 String regEx = "&lt;";
 	         Pattern p = Pattern.compile(regEx);
 	         Matcher ms = p.matcher(getcont);
         	//System.out.println("1223122111"+ms.replaceAll("").trim());
 	        getcont =  ms.replaceAll("<").trim();
 	        
         }
         
         if (getcont.indexOf("&gt;")!=-1){
         	 String regEx = "&gt;";
 	         Pattern p = Pattern.compile(regEx);
 	         Matcher ms = p.matcher(getcont);
         	//System.out.println("1223122111"+ms.replaceAll("").trim());
 	        getcont =  ms.replaceAll(">").trim();
 	        
         }
         if (getcont.indexOf("&amp;nbsp;")!=-1){
         	 String regEx = "&amp;nbsp;";
 	         Pattern p = Pattern.compile(regEx);
 	         Matcher ms = p.matcher(getcont);
 	      
         	//System.out.println("1223122111"+ms.replaceAll("").trim());
 	        getcont =  ms.replaceAll("&nbsp;").trim();
 	        
         }
         
        }
         
         //
         insInform.setInformContent(getcont);
         
        return R.ok().put("insInform", insInform);
    }

    /**
     * 保存
     * @throws WeixinException 
     * @throws IOException 
     * @throws ServletException 
     */
    @RequestMapping("/save")
    @RequiresPermissions("ins:insinform:save")
    public R save(@RequestBody InsInformEntity insInform,String informContentss,HttpServletRequest request){
    	//手动赋值
    	SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());

    	
    	insInform.setCreateId(user.getUserId());
    	//insInform.setPublishId(user.getUserId());
    	insInform.setDepartId(user.getDeptId());
    	insInform.setCreateTime(new Date());
    	
    	//
    	//insInform.setInformContent(informContentss);
    	ValidatorUtils.validateEntity(insInform,AddGroup.class);
        insInformService.insert(insInform);
      

        return R.ok();
    }


    /**
     * 修改
     * @throws WeixinException 
     */
    @RequestMapping("/update")
    @RequiresPermissions("ins:insinform:update")
    public R update(@RequestBody InsInformEntity insInform,HttpServletRequest request){
        ValidatorUtils.validateEntity(insInform);
        InsInformEntity oldinform = insInformService.selectById(insInform.getInformId());
        
        insInform.setUpdateTime(new Date());
        
        SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());
        insInform.setUpdateId(user.getUserId());
        insInformService.updateById(insInform);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ins:insinform:delete")
    public R delete(@RequestBody String[] informIds){
        insInformService.deleteBatchIds(Arrays.asList(informIds));

        return R.ok();
    }
    
    @RequestMapping("/getMaxOrderNum")
    public Integer getMaxOrderNum(String columnId) {
		return insInformService.getMaxOrderNum(columnId);
	}
    


}
