package io.youcham.modules.ins.fileentity;

public class CommConstants {

    /**
     * 约定俗成："0"代表否
     */
    public static final Long version = 1l;
    /**
     * 约定俗成："0"代表否
     */
    public static final Long VERSION = 1l;

    /**
     * 约定俗成："0"代表否
     */
    public static final String NO = "0";

    /**
     * 约定俗成："1"代表是
     */
    public static final String YES = "1";


    /**
     * 约定俗成：0代表否
     */
    public static final int INT_NO = 0;

    /**
     * 约定俗成：1代表是
     */
    public static final int INT_YES = 1;

    /**
     * 删除标记：未删除
     */
    public static final int DELETEDFLAG_NO = 0;

    /**
     * 删除标记：已删除
     */
    public static final int DELETEDFLAG_YES = 1;

    /**
     * 删除标记：未删除
     */
    public static final String DELETED_FLAG_NO = "0";

    /**
     * 删除标记：已删除
     */
    public static final String DELETED_FLAG_YES = "1";

    /**
     * 结果集："success"代表成功
     */
    public static final String RESULT_SUCCESS = "success";

    /**
     * 结果集："error"代表失败
     */
    public static final String RESULT_ERROR = "error";

    //日期格式
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    //user为空
    public static final String USER_ISNULL = "user为空";


    //首页跳转页面
    public static final String LAYOUT_DAIBANSXTABS = "layout/daibanSxTabs";
    //首页页面
    public static final String MODEL_VIEW_NAME_LAYOUT_SY = "layout/sy";
    public static final String ZX_MODULE_NAME_SY = "首页";
    //MODEL
    public static final String MODEL_OBJECT_NAME_FLAG = "flag";

    /**
     * 数据标识 “TRUE”：属于数据同步
     */
    public static final String DATA_MARK_TRUE = "TRUE";

    /**
     * 组织机构类型 HH:红十字会
     */
    public static final String ORG_TYPE_HH = "HH";

    /**
     * 组织性质 0:机构
     */
    public static final String ORG_NATURE_UNIT = "0";
    /**
     * 组织性质 1:部门
     */
    public static final String ORG_NATURE_DEPT = "1";
    /**
     * 组织级别 0:省
     */
    public static final String ORG_LEVEL_PROVINCE = "0";
    /**
     * 组织级别 1:市
     */
    public static final String ORG_LEVEL_CITY = "1";
    /**
     * 组织级别 2:区县
     */
    public static final String ORG_LEVEL_COUNTY = "2";
    /**
     * 组织级别
     */
    public static final String ORG_LEVEL = "orgLevel";
    /**
     * 组织机构类型
     */
    public static final String ORG_TYPE = "orgType";
    /**
     * 组织性质
     */
    public static final String ORG_NATURE = "orgNature";

    /**
     * 组织对象：部门
     */
    public static final String ORG_OBJ_DEPT = "ORG_DEPT";
    /**
     * 组织对象：机构
     */
    public static final String ORG_OBJ_UNIT = "ORG_UNIT";
    /**
     * 组织地域
     */
    public static final String ORG_AREA="orgAreaCode";
    /**
     * 编号类型：志愿者编号
     */
    public static final String CODE_TYPE="ZYZ";

    //系统异常，请联系管理员
    public static final String STRING_ERROR_EXCEPTION = "系统异常，请联系管理员！";
    //页面信息为空异常
    public static final String PARAM_NULL_EXCEPTION = "页面传入信息为空异常！";
    //页面信息正常
    public static final String PARAM_NOT_NULL_EXCEPTION = "页面信息正常！";
    /**
     * 校验-user对象是否为空
     */
    public  static  final  String STRING_ERROR_USER ="当前用户信息为空！";
    /**
     * 为0
     */
    public static final Integer EMPTY_INT = 0;

    /**
     * 校验-用户组织机构查询不到
     */
    public  static  final  String STRING_ERROR_YHZZ ="用户组织机构查询不到!";

    /********************************附件START*************************************/
    /**
     * 附件属性:name 文件名
     */
    public static final String FILE_NAME="name";
    /**
     * 附件属性:path 存储路径
     */
    public static final String FILE_PATH="path";
    /**
     * 附件属性:type 文件类型
     */
    public static final String FILE_TYPE="type";
    /**
     * 附件属性:size 文件大小
     */
    public static final String FILE_SIZE="size";
    /**
     * 附件属性:inputStream 文件流
     */
    public static final String FILE_INPUT_STREAM="inputStream";

    /**
     * 附件:path 配置文件中附件服务器路径地址
     */
    public static final String COMMON_FILE_PATH="file.path";
    //file
    public static final String FILE = "file";
    //ID
    public static final String FILE_ID ="id";
    //文件存储方式 01:DB,02:磁盘
    public static final String FILE_STORAGE_MODE_DB ="01";
    public static final String FILE_STORAGE_MODE_CP ="02";
    /**
     * 配置文件名称
     */
    public static final String COMMON_PROPERTIES="COMMON.PROPERTIES";
    /**
     * 操作系统标识
     */
    public static final String OS_FLAG_STR = "os.name";
    public static final String OS_WIN_STR = "win";
    /**
     * win系统路径sss
     */
    public static final String OS_WIN_PATH_STR = "volunteerFile\\";
    public static final String OS_WIN_C_PATH_STR = "C:\\";
    //根目录
    public static final String ROOT_PATH_STR = "/";
    //器官和造干目录
    public static final String ROOT_FILE_PATH_STR = "/donatingFile/";
    /**
     * win系统路径 本地磁盘
     */
    public static final String OS_WIN_PATH_VALUE_STR = "本地磁盘";
    /**
     * 非win系统路径
     */
    public static final String OS_NOT_WIN_PATH_STR = "/home/portaisiteFile/";
    /********************************附件END*************************************/

    /********************************MAP字段Start*************************************/

    //id
    public static final String MAP_ID = "id";
    /********************************MAP字段End*************************************/

    /*******************************大蚂蚁配置参数**************************************/
    public static final String appId = "1500287427";
    public static final String appSecret = "25DE1CBE05F8C10";
    public static final String dataType = "json";
    public static final String restSite = "http://192.168.2.55:8000";
    public static final String ssid = "48A685E9-0646-7BE3-9889-D394F9210E03";
    public static final String uid = "1";
    public static final String uname = "系统通知";
    /************************* 不同模块查询信访案件信息列表标示 *****************************/

    /**
     * 造干流程：初筛通知单未回执：01，已回执：02
     */
    public static final String ZGJXLC_CS_TZD_WHZ_STR = "初筛通知单未回执";
    public static final String ZGJXLC_CS_TZD_YHZ_STR = "初筛通知单已回执";
    public static final String ZGJXLC_CS_TZD_WHZ = "CS_01";
    public static final String ZGJXLC_CS_TZD_YHZ = "CS_02";
    /**
     * 造干流程：再动员通知单未回执：01，已回执：02
     */
    public static final String ZGJXLC_ZDY_TZD_WHZ_STR = "再动员通知单未回执";
    public static final String ZGJXLC_ZDY_TZD_YHZ_STR = "再动员通知单已回执";
    public static final String ZGJXLC_ZDY_TZD_WHZ = "ZDY_01";
    public static final String ZGJXLC_ZDY_TZD_YHZ = "ZDY_02";
    /**
     * 造干流程：高分辨通知单未回执：01，已回执：02
     */
    public static final String ZGJXLC_GFB_TZD_WHZ_STR = "高分辨通知单未回执";
    public static final String ZGJXLC_GFB_TZD_YHZ_STR = "高分辨通知单已回执";
    public static final String ZGJXLC_GFB_TZD_WHZ = "GFB_01";
    public static final String ZGJXLC_GFB_TZD_YHZ = "GFB_02";
    /**
     * 造干流程：体检通知单未回执：01，已回执：02
     */
    public static final String ZGJXLC_TJ_TZD_WHZ_STR = "体检通知单未回执";
    public static final String ZGJXLC_TJ_TZD_YHZ_STR = "体检通知单已回执";
    public static final String ZGJXLC_TJ_TZD_WHZ = "TJ_01";
    public static final String ZGJXLC_TJ_TZD_YHZ = "TJ_02";
    /**
     * 造干流程：采集通知单未回执：01，已回执：02
     */
    public static final String ZGJXLC_CJ_TZD_WHZ_STR = "采集通知单未回执";
    public static final String ZGJXLC_CJ_TZD_YHZ_STR = "采集通知单已回执";
    public static final String ZGJXLC_CJ_TZD_WHZ = "CJ_01";
    public static final String ZGJXLC_CJ_TZD_YHZ = "CJ_02";
    /**
     * 造干流程：移植通知单未回执：01，已回执：02
     */
    public static final String ZGJXLC_YZ_TZD_WHZ_STR = "移植通知单未回执";
    public static final String ZGJXLC_YZ_TZD_YHZ_STR = "移植通知单已回执";
    public static final String ZGJXLC_YZ_TZD_WHZ = "YZ_01";
    public static final String ZGJXLC_YZ_TZD_YHZ = "YZ_02";

    /**
     * 造干流程：随访通知单未回执：01，已回执：02
     */
    public static final String ZGJXLC_SF_TZD_WHZ_STR = "随访通知单未回执";
    public static final String ZGJXLC_SF_TZD_YHZ_STR = "随访通知单已回执";
    public static final String ZGJXLC_SF_TZD_WHZ = "SF_01";
    public static final String ZGJXLC_SF_TZD_YHZ = "SF_02";

    /**
     * 造干流程：成功捐献：01，供者终止：02
     */
    public static final String ZGJXLC_SUCCESS = "01";
    public static final String ZGJXLC_STOP = "02";

    /**
     * 造干流程状态附件来源节点：初筛
     */
    public static final String ZGJXLC_FILE_SOURCE_CS = "01";
    /**
     * 造干流程状态附件来源节点：再动员
     */
    public static final String ZGJXLC_FILE_SOURCE_ZDY = "02";
    /**
     * 造干流程状态附件来源节点：高分辨
     */
    public static final String ZGJXLC_FILE_SOURCE_GFB = "03";
    /**
     * 造干流程状态附件来源节点：体检
     */
    public static final String ZGJXLC_FILE_SOURCE_TJ = "04";
    /**
     * 造干流程状态附件来源节点：采集
     */
    public static final String ZGJXLC_FILE_SOURCE_CJ = "05";
    /**
     * 造干流程状态附件来源节点：移植
     */
    public static final String ZGJXLC_FILE_SOURCE_YZ = "06";
    /**
     * 造干流程状态附件来源节点：随访
     */
    public static final String ZGJXLC_FILE_SOURCE_SF = "07";
    /**
     * 造干流程状态附件来源节点：费用登记
     */
    public static final String ZGJXLC_FILE_SOURCE_FYDJ = "08";
    /**
     * 造干流程状态附件来源节点：造血干细胞志愿者
     */
    public static final String FILE_SOURCE_ZG_ZYZ = "09";

    //返回前台flag
    public static final String FLAG = "flag";
    //返回前台flag成功0
    public static final String FLAG_SUCCESS = "0";
    //返回前台flag失败1
    public static final String FLAG_ERROR = "1";

    //返回前台messageType
    public static final String MESSAGETYPE = "messageType";
    //返回前台messageType成功1
    public static final String MESSAGETYPE_SUCCESS = "0";
    //返回前台messageType失败0
    public static final String MESSAGETYPE_ERROR = "1";
    //返回前台false
    public static final String FALSE = "false";
    //返回前台true
    public static final String TRUE = "true";
    //返回前台content
    public static final String CONTENT = "content";
    //返回前台信息msg
    public static final String MSG = "msg";
    //返回前台执行结果
    public static final String CODE = "code";

    //志愿者类型，造干用ZG，器官用QG
    public static final String VOL_INFO_FLAG_ZG = "ZG";
    public static final String VOL_INFO_FLAG_QG = "QG";
    //地域编码初始值
    public static final String VOL_REGISTER_AREACODE_INIT = "360100";
    //志愿者类别，造干用96，器官用98来标识
    public static final String VOL_SERVICE_FLAG_ZG = "96";
    public static final String VOL_SERVICE_FLAG_QG = "98";
    //志愿者出生年份默认值
    public static final String VOL_BIRTH_YEAR_INIT = "XXXX";
    //志愿者性别默认值
    public static final String VOL_REGISTER_GENDER_INIT = "X";

    //编号(18-22位)，有序6位数，初始值
    public static final String VOL_REGISTER_NUMBER_INIT = "00001";
    //编号(18-22位)，有序6位数，长度
    public static final Integer VOL_REGISTER_NUMBER_LENGTH = 5;

    //删除附件ID
    public static final String PARAM_KEY_DELETE_FILE_ID = "deleteFileIds";
    //照片
    public static final String PHOTO = "photo";
    //照片处理后的名字
    public static final String FILE_NAME_CHANGE = "fileNameChange";
    //无照片添加的常量
    public static final String NO_PHOTO = "noPhoto";
    //路径斜杠
    public static final String XG = "/";
    //照片名字
    public static final String PHOTO_NAME = "NAME";
    //照片路径
    public static final String PHOTO_URL = "URL";
}
