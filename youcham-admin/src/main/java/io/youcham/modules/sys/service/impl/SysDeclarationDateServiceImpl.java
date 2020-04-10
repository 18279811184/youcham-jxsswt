package io.youcham.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.sys.dao.SysDeclarationDateDao;
import io.youcham.modules.sys.entity.SysDeclarationDateEntity;
import io.youcham.modules.sys.service.SysDeclarationDateService;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Map;

@Service("SysDeclarationDateService")

public class SysDeclarationDateServiceImpl extends ServiceImpl<SysDeclarationDateDao, SysDeclarationDateEntity> implements SysDeclarationDateService {

    @Override
    public PageUtils queryPage(Map<String,Object> params){
        Page<SysDeclarationDateEntity> page = this.selectPage(
                new Query<SysDeclarationDateEntity>(params).getPage(),
                new EntityWrapper<SysDeclarationDateEntity>()
                        .like("theDate",(String)params.get("theDate"))
                        .like("month",(String)params.get("month"))
                        .orderBy("MONTH")
                        //.like("caoZuo",(String)params.get("caoZuo"))
        );

        //List<SysDeclarationDateEntity> list = page.getRecords();
        //System.out.println("                 "+list.get(1).getCaoZuo()+"                  ");
        /*for (int i = 0;i < params.size();i++)
        {
            list.get(i).setCaoZuo("修改");
        }*/
        /*for (SysDeclarationDateEntity n:list)
        {
            //n.setMonth(n.getMonth()+"月份");
        }*/
        return new PageUtils(page);
    }
}