/**
 * Copyright 2018 人人开源 http://www.youcham.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.youcham.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月24日 下午11:05:27
 */
@Controller
public class SysPageController {
	
	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "modules/" + module + "/" + url;
	}
	
	@RequestMapping("modules/{module}/{module2}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("module2") String module2, @PathVariable("url") String url){
		return "modules/" + module + "/" + module2 + "/" + url;
	}

	@RequestMapping("index.html")
	public String index(){
		return "index";
	}

	@RequestMapping(value = {"/", "index1.html"})
	public String index1(){
		return "index1";
	}

	@RequestMapping(value = {"/", "index3.html"})
	public String index3(){
		return "index1";
	}

	@RequestMapping("login.html")
	public String login(){
		return "login";
	}
	
	@RequestMapping("login2.html")
	public String login2(){
		return "login2";
	}

	@RequestMapping("register.html")
	public String register(){
		return "register";
	}
	
	@RequestMapping("main.html")
	public String main(){
		return "main";
	}

	@RequestMapping("404.html")
	public String notFound(){
		return "404";
	}

	@RequestMapping("500.html")
	public String error(){
		return "500";
	}
	
	@RequestMapping("not_permission.html")
	public String notPermission(){
		return "not_permission";
	}
	
	@RequestMapping("logintzz.html")
	public String logintzz(){
		return "logintzz";
	}
	
}
