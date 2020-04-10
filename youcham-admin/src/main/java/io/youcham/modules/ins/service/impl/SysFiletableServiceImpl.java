package io.youcham.modules.ins.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.ins.dao.SysFiletableDao;
import io.youcham.modules.ins.entity.SysFiletableEntity;
import io.youcham.modules.ins.service.SysFiletableService;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sysFiletableService")
public class SysFiletableServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<SysFiletableDao, SysFiletableEntity> implements SysFiletableService {

	@Autowired
	private SysUserService userService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String  getallid = (String)params.get("getallid");

    	String[] listid = getallid.split(",");


		if (listid.length == 0) {
			getallid = "null";
		}
    	//文件备注

		/* 以下代码是为了mybatis更好的效验*/
		if (StringUtils.isBlank(getallid)) {
			getallid = "null";
		}

    	String fileRemark = (String)params.get("fileRemark");
        Page<SysFiletableEntity> page = this.selectPage(
                new Query<SysFiletableEntity>(params).getPage(),
                new EntityWrapper<SysFiletableEntity>()
                .in(StringUtils.isNotEmpty(getallid),"id", getallid)
                .like(StringUtils.isNotBlank(fileRemark),"file_remark", fileRemark)
        );

        for (SysFiletableEntity filetable : page.getRecords()) {
			SysUserEntity user = userService.selectById(filetable.getFileCreatid());
			filetable.setCreateUser(user);
		}
        
        return new PageUtils(page);
    }
    
	


	/**
	 * 更新备注方法
	 * 
	 * @param 
	 *           
	 * @return：
	 */
	@Override
	public String updatemark(String mark,List<String> ids) {
		// TODO Auto-generated method stub
		Map<String, Object> condition = new HashMap<>();
		condition.put("mark", mark);
		condition.put("ids", ids);
		String res= "";
		int i = baseMapper.updatemark(condition);
		if(i>0){
			res = "success";
		}else{
			res = "fail";
		}
		return res;
	}

	/** 
	 * @Description: 获取文件列表 
	 * @author : guoqiang
	 * @date : 2018年6月30日 下午3:01:56 
	 * @param params
	 * @return
	 */
	@Override
	public List<SysFiletableEntity> queryList(Map<String, Object> params) {
		EntityWrapper<SysFiletableEntity> ew = new EntityWrapper<SysFiletableEntity>();
		String getallid = (String)params.get("getallid");
		if(StringUtils.isNotEmpty(getallid)){
			ew.in(StringUtils.isNotBlank(getallid),"id",getallid);
		}else{
			ew.and("1=2");
		}
		
        List<SysFiletableEntity> list = this.selectList(ew);
        
        return list;
	}



		

}
