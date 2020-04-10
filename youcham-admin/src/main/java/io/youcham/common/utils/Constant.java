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

package io.youcham.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2016-11-15
 */
public class Constant {
	/** 超级管理员ID */
	public static final String SUPER_ADMIN1 = "1";
	public static final String SUPER_ADMIN2 = "2";
	public static final String SUPER_ADMIN3 = "3";
    /** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";
	/** 车进柜货 */
	public static final String[] CAR_CONTA_LIST = new String[] { "JXDN",
			"JX_MT", "GGLW_NC_USER", "VESSEL_I_E_FLAG", "CONSIGNOR_CODE",
			"CONTA_EMPTY_FLAG", "CONTAINER_CONTA_SIZE", "BOX_PILE",
			"CONTA_OWNER", "CONTA_AGENT", "VESSEL_DSCODE", "BILL_LOADING_PORT",
			"BILL_LOADING_IF_TRADE_FLAG", "BILL_DC_FLAG",
			"BILL_LOADING_WRAP_TYPE", "CAR_PURPOSE", "COAL_CONTA", "P_E_FLAG",
			"FEE_FLAG", "REPAIR_DAMAGE" };
	/** 车进散货 */
	public static final String[] CAR_CARGO_LIST = new String[] { "JXDN",
			"JX_MT", "GGLW_NC_USER", "ARRIVALS_PURPOSE", "VESSEL_I_E_FLAG",
			"BILL_LOADING_IF_TRADE_FLAG", "CONTAINER_CHANGE_MARK", "IF_FALL",
			"COAL_CONTA", "P_E_FLAG", "FEE_FLAG", "REPAIR_DAMAGE" };
	/** 装箱 **/
	public static final String[] BOX_UP_LIST = new String[] { "JXDN", "JX_MT",
			"GGLW_NC_USER", "CONTAINER_CONTA_SIZE", "WORK_QCDIFFICULT",
			"WORK_DIFFICULT", "BILL_DC_FLAG", "CONSIGNOR_CODE",
			"BILL_LOADING_IF_TRADE_FLAG", "VESSEL_DSCODE", "VESSEL_SITE_CODE",
			"BOX_PILE", "CONTA_EMPTY_FLAG", "CONTA_OWNER", "CONTA_AGENT",
			"FEE_FLAG" };
	/** 拆箱 **/
	public static final String[] STRIPPING_LIST = new String[] { "JXDN",
			"JX_MT", "GGLW_NC_USER", "BOX_PILE", "CONTAINER_CONTA_SIZE",
			"WORK_QCDIFFICULT", "WORK_DIFFICULT", "CONSIGNOR_CODE",
			"STRIP_WORKTYPE", "REPAIR_DAMAGE", "P_E_FLAG", "FEE_FLAG" };
	/** 船舶配载 **/
	public static final String[] MATCH_LOAD_LIST = new String[] { "JX_MT",
			"BILL_LOADING_PORT", "FEE_FLAG" };
	/** 提箱 */
	public static final String[] CARRY_CONTA_LIST = new String[] { "JXDN",
			"JX_MT", "GGLW_NC_USER", "CONTA_OWNER", "CONTA_AGENT",
			"CONSIGNOR_CODE", "VESSEL_I_E_FLAG", "VESSEL_DSCODE",
			"BILL_DC_FLAG", "REPAIR_DAMAGE", "P_E_FLAG", "BOX_PILE",
			"CONTAINER_CONTA_SIZE", "BILL_LOADING_PORT", "FEE_FLAG" };
	/** 装卸船计划 */
	public static final String[] VESSEL_LIST = new String[] { "JHMT", "JX_MT",
			"VESSEL_I_E_FLAG", "GGLW_NC_USER", "VESSEL_DSCODE",
			"VESSEL_TRAF_MODE", "VESSEL_P_E_FLAG", "VESSEL_ARRIVE_FLAG",
			"FEE_FLAG", "VESSEL_CXDSCODE" };
	/** 装卸船计划--提单信息 */
	public static final String[] BILL_LOADING_LIST = new String[] {
			"BILL_LOADING_WRAP_TYPE", "GGLW_NC_USER", "JX_MT",
			"BILL_TRADE_RULE", "CONSIGNOR_CODE", "BILL_LOADING_IF_TRADE_FLAG",
			"BILL_LOADING_PORT", "FEE_FLAG", "JHMT" };
	/** 装卸船计划--货物信息 */
	public static final String[] GOODS_LIST = new String[] { "GOODS_TYPE",
			"GOODS_MZ", "GOODS_GB", "GOODS_UNITMONEY", "GOODS_ZL" };
	/** 装卸船计划--集装箱信息 */
	public static final String[] CONTA_LIST = new String[] {
			"CONTA_EMPTY_FLAG", "GGLW_NC_USER", "CONTAINER_CONTA_SIZE",
			"JX_MT", "BOX_PILE", "CONSIGNOR_CODE", "CONTAINER_CHANGE_MARK",
			"CONTA_TRADE_RULE", "CONTAINER_LIMIT_FLAG", "BILL_DC_FLAG",
			"REPAIR_DAMAGE", "P_E_FLAG", "CONTA_OWNER", "CONTA_AGENT",
			"COAL_CONTA", "FEE_FLAG", "JHMT" ,"CONTA_OWNERCX" };
	/** 装卸船计划--散货信息 */
	public static final String[] CARGO_LIST = new String[] { "JX_MT",
			"CARGO_PACKAGE_CODE", "GGLW_NC_USER", "COAL_CONTA", "FEE_FLAG" };
	/** 堆场作业计划 */
	public static final String[] STACK_PLAN_LIST = new String[] { "JXDN",
			"JX_MT", "GGLW_NC_USER", "CONTA_OWNER", "CONTA_AGENT", "BOX_PILE",
			"CONTAINER_CONTA_SIZE", "FEE_FLAG" };

	/** 熏蒸箱信息维护 **/
	public static final String[] FUMIGATE_BOX_LIST = new String[] { "JX_MT",
			"GGLW_NC_USER", "FEE_FLAG", "BOX_PILE", "CONTAINER_CONTA_SIZE",
			"CONTA_OWNER", "CONTA_AGENT" };
	/** 移箱信息维护 **/
	public static final String[] SHIFT_BOX_LIST = new String[] { "JX_MT",
			"GGLW_NC_USER", "FEE_FLAG", "BOX_PILE", "CONTAINER_CONTA_SIZE",
			"CONTA_OWNER", "CONTA_AGENT" };
	/** 制冷箱信息维护 **/
	public static final String[] RERRIGERATE_BOX_LIST = new String[] { "JX_MT",
			"GGLW_NC_USER", "FEE_FLAG", "BOX_PILE", "CONTAINER_CONTA_SIZE",
			"CONTA_OWNER", "CONTA_AGENT" };
	/** 换箱信息维护 **/
	public static final String[] CHANGE_BOX_LIST = new String[] { "JX_MT",
			"GGLW_NC_USER", "FEE_FLAG", "BOX_PILE", "CONTAINER_CONTA_SIZE",
			"CONTA_OWNER", "CONTA_AGENT" };
	/** 海关查验箱信息维护 **/
	public static final String[] CHECK_BOX_LIST = new String[] { "JX_MT",
			"GGLW_NC_USER", "FEE_FLAG", "BOX_PILE", "CONTAINER_CONTA_SIZE",
			"CONTA_OWNER", "CONTA_AGENT" };
	/** excel导入时用来验证箱属、箱代、箱型,尺码 ，目的港的 */
	public static final String[] EXCEL_LIST = new String[] { "CONTA_OWNER",
			"CONTA_AGENT", "BOX_PILE", "BILL_LOADING_PORT",
			"CONTAINER_CONTA_SIZE", "GOODS_ZL", "GOODS_UNITMONEY", "PORT_NAME",
			"CONTA_OWNERCX", "GGLW_GOODSCAGO", "XZQ_LOADING_PORT",
			"COUNTRY_CATEGORY", "G_WRAP_TYPE" };
	
	/** 删除用户标志*/
	public static final String DELETE_YES = "1";
	/** 未删除用户标志*/
	public static final String DELETE_NO = "0";
	/**发送mq消息状态
	 * 未发送：0
	 * 已发送：1
	 * */
	/** 未发送:0*/
	public static final String SEND_MQ_NO="0";
	/** 已发送:1*/
	public static final String SEND_MQ_YES="1";
	/**申报确认状态
	 * 未确认:0
	 * 已确认:1
	 * */
	/** 未确认:0*/
	public static final String VERIFY_NO="0";
	/** 已确认:1*/
	public static final String VERIFY_YES="1";
	
	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
