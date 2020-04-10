package io.youcham.modules.student.service.impl;

import io.youcham.modules.student.entity.BigjavaTeaEntity;
import io.youcham.modules.student.service.BigjavaTeaService;
import io.youcham.modules.sys.entity.SysExportModuleEntity;
import io.youcham.modules.sys.entity.SysExportTemplateEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysExportModuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.student.dao.BigjavaStuDao;
import io.youcham.modules.student.entity.BigjavaStuEntity;
import io.youcham.modules.student.service.BigjavaStuService;


@Service("bigjavaStuService")
public class BigjavaStuServiceImpl extends ServiceImpl<BigjavaStuDao, BigjavaStuEntity> implements BigjavaStuService {

    @Autowired
    private BigjavaTeaService bigjavaTeaService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String stuName= (String) params.get("stuName");
        String stuCredit = (String) params.get("stuCredit");
        Page<BigjavaStuEntity> page = this.selectPage(
                new Query<BigjavaStuEntity>(params).getPage(),
                new EntityWrapper<BigjavaStuEntity>().like(StringUtils.isNoneEmpty(stuName),"stu_name",stuName)
                        .like(StringUtils.isNoneEmpty(stuCredit),"stu_credit",stuCredit)
                        .eq(StringUtils.isNotEmpty((String) params.get("allids")), "stu_teaId",
                (String) params.get("allids"))
        );

        for (BigjavaStuEntity bigjavaStuEntity : page.getRecords()) {

            BigjavaTeaEntity bigjavaTeaEntity = bigjavaTeaService.selectById(bigjavaStuEntity.getStuTeaid());

            if(bigjavaTeaEntity != null) {
                bigjavaStuEntity.setStuTeaid(bigjavaTeaEntity.getTeaName());

            }
        }

        return new PageUtils(page);
    }

}
