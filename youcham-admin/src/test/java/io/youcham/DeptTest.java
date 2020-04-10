package io.youcham;

import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.service.SysDeptService;
import io.youcham.modules.wmyj.entity.WmyjCityareaEntity;
import io.youcham.modules.wmyj.service.WmyjCityareaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeptTest {
	@Autowired
	private WmyjCityareaService wmyjCityareaService;

	@Test
	public void test() {
		WmyjCityareaEntity cityareaEntity = wmyjCityareaService.getWmyjCityareaEntityByCode("360101");
		System.out.println(cityareaEntity.getCity());
	}





}
