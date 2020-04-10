package io.youcham.modules.ins.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.ins.dao.InsSuggestionsDao;
import io.youcham.modules.ins.entity.InsInformEntity;
import io.youcham.modules.ins.entity.InsSuggestionsEntity;
import io.youcham.modules.ins.service.InsSuggestionsService;


@Service("insSuggestionsService")
public class InsSuggestionsServiceImpl extends ServiceImpl<InsSuggestionsDao, InsSuggestionsEntity> implements InsSuggestionsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String sugtitle = (String)params.get("sugtitle");
    	//状态
    	String statu = (String)params.get("statu");
    	
        Page<InsSuggestionsEntity> page = this.selectPage(
        		 new Query<InsSuggestionsEntity>(params).getPage(),
        	     new EntityWrapper<InsSuggestionsEntity>()
        	    .like(StringUtils.isNotBlank(sugtitle),"sug_title", sugtitle)
        	    .eq(StringUtils.isNotBlank(statu),"statu", statu)
        );	  
        
      /*  new Query<InsInformEntity>(params).getPage(),
        new EntityWrapper<InsInformEntity>()
        .like(StringUtils.isNotBlank(informtitle),"inform_title", informtitle)
        .orderBy("inform_Order")*/

        return new PageUtils(page);
    }

}
