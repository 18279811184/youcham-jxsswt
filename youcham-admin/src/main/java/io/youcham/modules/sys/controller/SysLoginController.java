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


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.youcham.common.annotation.SysLog;
import io.youcham.common.utils.DESUtils;
import io.youcham.common.utils.R;
import io.youcham.modules.api.UserCenterClient;
import io.youcham.modules.ins.fileentity.FileConfigReader;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysUserService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import net.sf.json.JSONObject;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 登录相关
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserCenterClient usercenter;

    protected Session session;


    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }




    /**
     * 登录
     */
    @SysLog("用户登录")
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public R login(String username, String password, String captcha) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return R.error("验证码不正确");
//        }
        //清除session
        org.apache.shiro.session.Session session = ShiroUtils.getSession();
        session.removeAttribute("token");
        session.removeAttribute("uid");

        try {
            Subject subject = ShiroUtils.getSubject();
            String userName = username;

            //判断用户名密码正确 换取token
            FileConfigReader fe = new FileConfigReader();

            String flag = fe.getProperties("systemFlag");

            R b = new R();

            //存入系统标识
            ShiroUtils.setSessionAttribute("systemFlag", flag);

            UsernamePasswordToken token = new UsernamePasswordToken(username, DESUtils.decryption(password, "12345678"));
            subject.login(token);


            //b = usercenter.login(userName, password ,flag);
            System.out.println(b.toString());
            ShiroUtils.setSessionAttribute("token", b.get("token"));
            ShiroUtils.setSessionAttribute("uid", b.get("uid"));
			/*try {
				b = usercenter.login(userName, password ,flag);
				System.out.println(b.toString());
				ShiroUtils.setSessionAttribute("token", b.get("token"));
				ShiroUtils.setSessionAttribute("uid", b.get("uid"));
			} catch (Exception e) {
				// TODO: handle exception
                System.out.println(e);				
			}*/

            // 登录通过后严重当前密码安全性 e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b（admin）
            String pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[1-9]).{6,}";
            boolean isMatch = false;
            try {
                String decPassword = DESUtils.decryption(password, "12345678");
                isMatch = Pattern.matches(pattern, decPassword);
                if(!isMatch){
                    return R.ok(2,"密码安全级别不够");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return b;

        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        } catch (Exception e) {
            //return R.error("登录失败");
            e.printStackTrace();
        }


        return R.ok();
    }


    /**
     * 免登录
     * xcg
     * 3 12
     */
    @ResponseBody
    @RequestMapping(value = "/sys/loginse", method = RequestMethod.POST)
    public R loginse(String token, String uid) {
		/*String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}*/

        try {

            Subject subject = ShiroUtils.getSubject();
            ShiroUtils.setSessionAttribute("token", token);
            ShiroUtils.setSessionAttribute("uid", uid);

            //获取用户信息
			/*SysUserEntity user = new SysUserEntity();
			R userR = usercenter.getUserInfoPass(token,uid);
			Object m =   userR.get("user");
			//String password = (String) userR.get("password");
			if(m!=null){
				JSONObject jsonObject=JSONObject.fromObject(m);
				user = (SysUserEntity) JSONObject.toBean(jsonObject, SysUserEntity.class);
				 
			}*/
            SysUserEntity user = new SysUserEntity();
//			R userR = usercenter.getUserInfouid(token, uid ,(String)ShiroUtils.getSessionAttribute("systemFlag"));
            R userR = usercenter.getUserInfouid(token, uid, "hd");
            Object m = userR.get("user");
            //String password = (String) userR.get("password");
            if (m != null) {
                JSONObject jsonObject = JSONObject.fromObject(m);
                //user = (SysUserEntity) JSONObject.toBean(jsonObject, SysUserEntity.class);
                String username = (String) jsonObject.get("username");
                user.setUsername(username);

            }
            //登录
            if (user != null) {
                UsernamePasswordToken token1 = new UsernamePasswordToken(user.getUsername(), "admin");
                subject.login(token1);
            } else {

            }


        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

        return R.ok();
    }


    /**
     * 退出
     */
    @SysLog("用户退出")
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }

    /**
     * 免密登录
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/sys/loginadmin")
    public R loginadmin(String userId) {
        try {
            SysUserEntity user = sysUserService.getUserByWxUserId(userId);
            if (user == null) {
                return R.error("账号或密码不正确");
            }

            ShiroUtils.setSessionAttribute("relationUserId", userId);

            Subject subject = ShiroUtils.getSubject();
            String username = user.getUsername();
            String password = userId;

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);

        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

        return R.ok();
    }

    /**
     * 注册
     */
    @SysLog("用户注册")
    @ResponseBody
    @RequestMapping(value = "/sys/register", method = RequestMethod.POST)
    public R register(String username, String password, String captcha) {


        try {
            Subject subject = ShiroUtils.getSubject();
            String userName = username;

            //判断用户名密码正确 换取token
            FileConfigReader fe = new FileConfigReader();

            String flag = fe.getProperties("systemFlag");

            R b = new R();

            //存入系统标识
            ShiroUtils.setSessionAttribute("systemFlag", flag);

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);


            b = usercenter.login(userName, password, flag);
            System.out.println(b.toString());
            ShiroUtils.setSessionAttribute("token", b.get("token"));
            ShiroUtils.setSessionAttribute("uid", b.get("uid"));

            return b;

        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        } catch (Exception e) {
            //return R.error("登录失败");
            e.printStackTrace();
        }

        return R.ok();
    }
}
