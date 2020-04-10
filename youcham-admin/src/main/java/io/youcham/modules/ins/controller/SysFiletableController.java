package io.youcham.modules.ins.controller;

import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.modules.ins.entity.SysFiletableEntity;
import io.youcham.modules.ins.service.SysFiletableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 文件表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-18 09:56:24
 */
@RestController
@RequestMapping("ins/sysfiletable")
public class SysFiletableController {
    @Autowired
    private SysFiletableService sysFiletableService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("ins:sysfiletable:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysFiletableService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("ins:sysfiletable:info")
    public R info(@PathVariable("id") Long id){
        SysFiletableEntity sysFiletable = sysFiletableService.selectById(id);

        return R.ok().put("sysFiletable", sysFiletable);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("ins:sysfiletable:save")
    public R save(@RequestBody SysFiletableEntity sysFiletable){
        sysFiletableService.insert(sysFiletable);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("ins:sysfiletable:update")
    public R update(@RequestBody SysFiletableEntity sysFiletable){
        ValidatorUtils.validateEntity(sysFiletable);
        sysFiletableService.updateAllColumnById(sysFiletable);//全部更新
        
        return R.ok();
    }
    
    /**
     * 批量更新备注修改
     */
   // @RequestMapping("/updatemark", method = RequestMethod.post)
    @RequestMapping(value = "/updatemark")
    //@RequiresPermissions("ins:sysfiletable:update")
    
    public  String updatemark(@RequestParam String mark,@RequestParam String ids){
       // ValidatorUtils.validateEntity(sysFiletable);
    	//String s = reques
    	//String mark = request.getParameter("mark");
    	//String ids = request.getParameter("ids");
    	List<String> a = new  ArrayList<>();
    	  String[] as = ids.split(",");
    	  for (int i = 0; i < as.length; i++) {
    	   System.out.println(as[i]);
    	   a.add(as[i]);
    	  }
 	
    	// a  = ids.split(","); 
    	String res ="";
    	if(a.size()>0){
    		 res =  sysFiletableService.updatemark(mark, a);//全部更新
    	}else{
    		res = "fail";
    	}
        
        
        return res;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("ins:sysfiletable:delete")
    public R delete(@RequestBody String[] ids){
        sysFiletableService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
