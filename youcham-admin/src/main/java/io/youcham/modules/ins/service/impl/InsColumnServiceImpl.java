package io.youcham.modules.ins.service.impl;

import io.youcham.common.constant.StatusEnum;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.ins.dao.InsColumnDao;
import io.youcham.modules.ins.entity.InsColumnEntity;
import io.youcham.modules.ins.service.InsColumnService;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("insColumnService")
public class InsColumnServiceImpl extends ServiceImpl<InsColumnDao, InsColumnEntity> implements InsColumnService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String columnname = (String)params.get("columnname");
    	
        Page<InsColumnEntity> page = this.selectPage(
                new Query<InsColumnEntity>(params).getPage(),           
                new EntityWrapper<InsColumnEntity>()
                .like(StringUtils.isNotBlank(columnname),"column_name", columnname)
                .orderBy("column_order")
                
        );

        return new PageUtils(page);
    }
    
	@Override
	public List<InsColumnEntity> queryselectList(Map<String, Object> params){
		List<InsColumnEntity> deptList =
			this.selectList(new EntityWrapper<InsColumnEntity>()
			.eq("column_status", StatusEnum.NORMAL.getValue())
			.orderBy("column_order"));

		return deptList;
	}

}
