package io.youcham.modules.ins.controller;

import io.youcham.common.constant.Variable;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;
import io.youcham.modules.ins.entity.InsColumnEntity;
import io.youcham.modules.ins.service.InsColumnService;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 栏目表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-06 14:11:58
 */
@RestController
@RequestMapping("ins/inscolumn")
public class InsColumnController {
    @Autowired
    private InsColumnService insColumnService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("ins:inscolumn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = insColumnService.queryPage(params);

        return R.ok().put("page", page);
    }

//    @RequestMapping("/list2")
//    
//    public R list(@RequestParam Map<String, Object> params,HttpServletResponse response){
//    	
//        PageUtils page = insColumnService.queryPage(params);
//      
//        List<InsColumnEntity>  ninscolumn = new ArrayList<InsColumnEntity>();
//       // Page<InsColumnEntity> page
//        List<InsColumnEntity> getpage = (List<InsColumnEntity>) page.getList();
//        for (int i = 0; i < getpage.size(); i++) {
//        	 if(getpage.get(i).getColumnId() == "1000000316154733"){
//        		 ninscolumn.add(getpage.get(i));
//             }else  if(getpage.get(i).getColumnId()== "1000002030644410")){
//            	 ninscolumn.add(getpage.get(i));
//              }else  if(getpage.get(i).getColumnId()== String.parseString("1000001585344164")){
//            	  ninscolumn.add(getpage.get(i));
//              }else  if(getpage.get(i).getColumnId()== String.parseString("1000001861542884")){
//            	  ninscolumn.add(getpage.get(i));
//              }
//		}
//       
//        page.setCurrPage(1);
//        page.setPageSize(1);
//        page.setTotalCount(4);
//        page.setList(ninscolumn);
//        
//        return R.ok().put("page", page);
//    }

    /**
     * 信息
     */
    @RequestMapping("/info/{columnId}")
//    @RequiresPermissions("ins:inscolumn:info")
    public R info(@PathVariable("columnId") String columnId){
        InsColumnEntity insColumn = insColumnService.selectById(columnId);

        return R.ok().put("insColumn", insColumn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ins:inscolumn:save")
    public R save(@RequestBody InsColumnEntity insColumn){
    	ValidatorUtils.validateEntity(insColumn, AddGroup.class);
    	//手动赋值
    	SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());

    	
    	insColumn.setCreateId(user.getUserId());
    	//insInform.setPublishId(user.getUserId());
    	insColumn.setDepartId(user.getDeptId());
    	insColumn.setCreateTime(new Date());
        insColumnService.insert(insColumn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ins:inscolumn:update")
    public R update(@RequestBody InsColumnEntity insColumn){
    	ValidatorUtils.validateEntity(insColumn, UpdateGroup.class);
        
        insColumn.setUpdateTime(new Date());
        
        SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());
        insColumn.setUpdateId(user.getUserId());
        insColumnService.updateAllColumnById(insColumn);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ins:inscolumn:delete")
    public R delete(@RequestBody String[] columnIds){
        insColumnService.deleteBatchIds(Arrays.asList(columnIds));

        return R.ok();
    }

    /** 
     * @Description: 获取下拉框数据 获得所有的栏目
     * @author : xucg
     * @date : 2018年5月7日  
     * @return
     */
    @RequestMapping("/select")
    public R select(){
    	List<InsColumnEntity> list = insColumnService.queryselectList(new HashMap<String, Object>());
    	
        return R.ok().put("list", list);
    }
    
}
