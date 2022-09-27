package com.example.util;

import android.content.Intent;

import com.example.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class Constant {
    public static final String SHARED_PREFERENCES_NAME = "Smart_city01";
    public static final String USER_INFO_NAME = "userInfo";
    //根请求路径
    public static String BASE_API = "http://" + NetWork.IP + ":" + NetWork.PORT;
    public static Integer pageSize = 10;
    public static Integer pageSize_6 = 6;
    public static Integer CLEAR_INTENT_STACK = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK;
    //版本
    public static Integer VERSION = 1;
    //数据库名字
    public static String DB_NAME = "Smart_City";
    //支付方式
    public static String[] PAY_TYPE = {"支付宝", "微信", "电子支付"};
    //智慧缴费分类
    public static String[] EXPENSES_PAY_TYPE = {"话费", "水费", "电费", "燃气费"};
    //天气图标
    public static Map<String, Integer> EXPENSES_WEATHER_MAPS = new HashMap<>();
    //准驾车型
    public static List<String> TRAFFIC_LICENSE_TYPE = new ArrayList<>(Arrays.asList("B1", "C3", "A3", "C2", "E", "A2", "C1", "D", "A1", "B2", "C4"));
    //驾驶证有效期
    public static List<String> TRAFFIC_LICENSE_VERIFY = new ArrayList<>(Arrays.asList("6年", "10年", "长期"));
    //号牌类型
    public static List<String> TRAFFIC_PLATE_TYPE = new ArrayList<>(Arrays.asList("小型汽车", "大型型能源汽车", "大型汽车", "小型新能源车", "领馆汽车", "警用汽车"));
    //影片类型
    public static List<String> FILM_CATEGORY = new ArrayList<>(Arrays.asList("故事", "爱情", "动作", "喜剧", "恐怖", "惊悚", "战争", "科幻"));
    //播放类型
    public static List<String> FILM_PLAY_TYPE = new ArrayList<>(Arrays.asList("2D", "3D", "IMAX", "4DX"));

    static {
        EXPENSES_WEATHER_MAPS.put("晴", R.mipmap.weather_fine_day);
        EXPENSES_WEATHER_MAPS.put("多云", R.mipmap.weather_cloudy);
        EXPENSES_WEATHER_MAPS.put("阴", R.mipmap.weather_overcast);
        EXPENSES_WEATHER_MAPS.put("雨", R.mipmap.weather_rain);
    }

    //网络常量类
    public static class NetWork {
        public static String IP = "124.93.196.45";
        public static String PORT = "10001";
        //登陆
        public static final String LOGIN = "/prod-api/api/login";
        //注册
        public static final String REGISTER = "/prod-api/api/register";
        //退出登陆
        public static final String LOGOUT = "/logout";
        //获取用户信息
        public static final String GETINFO = "/prod-api/api/common/user/getInfo";
        //修改用户信息
        public static final String UPDATE_USER = "/prod-api/api/common/user";
        //重置密码
        public static final String RESET_PWD = "/prod-api/api/common/user/resetPwd";
        //获取轮播图
        public static final String ROTATION_IMAGE = "/prod-api/api/rotation/list";
        //查询所有服务
        public static final String SERVICE_LIST = "/prod-api/api/service/list";
        //新增意见反馈
        public static final String ADD_FEEDBACK = "/prod-api/api/common/feedback";
        //获取新闻分类
        public static final String NEWS_CATEGORY_LIST = "/prod-api/press/category/list";
        //获取新闻列表
        public static final String GET_PRESS_LIST = "/prod-api/press/press/list";
        //获取新闻详情
        public static final String GET_PRESS_DETAILS = "/prod-api/press/press";
        //评论点赞
        public static final String PRESS_COMMENT_LIKE = "/prod-api/press/pressComment/like";
        //发表新闻评论
        public static final String SEND_PRESS_COMMENT = "/prod-api/press/pressComment";
        //获取评论列表
        public static final String PRESS_LIST = "/prod-api/press/comments/list";
        //获取新闻详细信息
        public static final String PRESS_DETAILS = "/prod-api/press/comments";
        //新闻点赞
        public static final String PRESS_LIKE = "/prod-api/press/press/like";
        //获取当前地理位置
        public static final String CURRENT_GPS_LOCATION = "/prod-api/api/common/gps/location";
        //全部订单
        public static final String ALL_ORDER = "/prod-api/api/allorder/list";

        //城市地铁轮播图
        public static final String METRO_ROTATION = "/prod-api/api/metro/rotation/list";
        //城市地铁总路线图
        public static final String METRO_CITY = "/prod-api/api/metro/city";
        //根据站点名和路线编号查询站点信息   或  查询地铁站详情
        public static final String METRO_LINE = "/prod-api/api/metro/line";
        //查询城市所有路线
        public static final String METRO_LINE_LIST = "/prod-api/api/metro/line/list";
        //首页地铁站点查询
        public static final String METRO_LIST = "/prod-api/api/metro/list";
        //查询站点信息列表
        public static final String METRO_STEP_LIST = "/prod-api/api/metro/step/list";
        //获取用户乘车卡信息  或 领取乘车卡   或   注销乘车卡
        public static final String METRO_CARD = "/prod-api/api/metro/card";
        //获取地铁公告列表
        public static final String METRO_NOTICE_LIST = "/prod-api/api/metro/notice/list";
        //获取地铁公告详情
        public static final String METRO_NOTICE = "/prod-api/api/metro/notice";
        //获取地铁声明
        public static final String METRO_NOTICE_STATEMENT = "/prod-api/api/metro/statement";
        //获取失物招领列表
        public static final String GET_LAST_FOUND_LIST = "/prod-api/api/metro/found/list";

        //活动轮播
        public static final String ACTIVITY_BANNER = "/prod-api/api/activity/rotation/list";
        //活动分类列表
        public static final String ACTIVITY_CATEGORY = "/prod-api/api/activity/category/list";
        //活动列表
        public static final String ACTIVITY_LIST = "/prod-api/api/activity/activity/list";
        //活动详情
        public static final String ACTIVITY_DETAILS = "/prod-api/api/activity/activity";
        //活动报名
        public static final String SIGNUP_ACTIVITY = "/prod-api/api/activity/signup";
        //判断用户是否报名活动
        public static final String CHECK_SIGNUP_ACTIVITY = "/prod-api/api/activity/signup/check";
        //添加活动评论
        public static final String ACTIVITY_COMMENT = "/prod-api/api/activity/comment";
        //获取活动评论列表
        public static final String ACTIVITY_COMMENT_LIST = "/prod-api/api/activity/comment/list";
        //获取评论评论数
        public static final String ACTIVITY_COMMENT_NUMBER = "/prod-api/api/activity/comment/number";

        //获取医院详情轮播图
        public static final String HOSPITAL_BANNER = "/prod-api/api/hospital/banner/list";
        //获取医院信息列表
        public static final String HOSPITAL_LIST = "/prod-api/api/hospital/hospital/list";
        //获取医院详细信息
        public static final String HOSPITAL_DETAILS = "/prod-api/api/hospital/hospital";
        //查询专家诊和科室分类
        public static final String HOSPITAL_CATEGORY = "/prod-api/api/hospital/category/list";
        //新增就诊卡  或  修改就诊卡
        public static final String HOSPITAL_PATIENT = "/prod-api/api/hospital/patient";
        //查询用户所有的就诊卡
        public static final String HOSPITAL_PATIENT_LIST = "/prod-api/api/hospital/patient/list";
        //生成预约单
        public static final String HOSPITAL = "/prod-api/api/hospital";
        //查询预约详情
        public static final String HOSPITAL_RESERVATION = "/prod-api/api/hospital/reservation";
        //查询预约列表
        public static final String HOSPITAL_RESERVATION_LIST = "/prod-api/api/hospital/reservation/list";

        //提交纠错信息
        public static final String PARK_CORRECT = "/prod-api/api/park/correct";
        //查询停车场列表
        public static final String PARK_LOT_LIST = "/prod-api/api/park/lot/list";
        //查询停车记录列表
        public static final String PARK_RECORD_LIST = "/prod-api/api/park/lot/record/list";
        //查询停车场详情
        public static final String PARK_LOT_DETAIL = "/prod-api/api/park/lot";
        //查询充值记录列表
        public static final String PARK_RECHARGE_LIST = "/prod-api/api/park/recharge/list";
        //充值
        public static final String PARK_RECHARGE_PAY = "/prod-api/api/park/recharge/pay";
        //查询换购商品列表
        public static final String PARK_PRODUCT_LIST = "/prod-api/api/park/product/list";
        //查询广告轮播
        public static final String PARK_ROTATION_LIST = "/prod-api/api/park/rotation/list";
        //积分兑换
        public static final String PARK_CONSUME = "/prod-api/api/park/score/consume";
        //查询积分等级列表
        public static final String PARK_SCORE_LEVEL = "/prod-api/api/park/score/level/list";
        //查询积分记录列表
        public static final String PARK_SCORE_LIST = "/prod-api/api/park/score/list";
        //添加我的车辆信息 | 修改我的车辆信息 | 删除我的车辆信息
        public static final String PARK_CAR = "/prod-api/api/park/car";
        //删除车辆里程信息 | 添加车辆里程信息 | 修改车辆里程信息 | 删除车辆里程信息
        public static final String PARK_CAR_CONSUMPTION = "/prod-api/api/park/car/consumption";
        //查询我的车辆信息
        public static final String PARK_CAR_MY = "/prod-api/api/park/car/my";

        //查询站点信息
        public static final String BUS_STOP_LIST = "/prod-api/api/bus/stop/list";
        //查询线路列表 | 查询线路详情
        public static final String BUS_LINE_LIST = "/prod-api/api/bus/line/list";
        //新增巴士订单
        public static final String BUS_ORDER = "/prod-api/api/bus/order";
        //查询订单列表
        public static final String BUS_ORDER_LIST = "/prod-api/api/bus/order/list";
        //支付巴士订单
        public static final String BUS_PAY = "/prod-api/api/bus/pay";

        //生活缴费轮播
        public static final String LIVING_ROTATION = "/prod-api/api/living/rotation/list";
        //天气预报接口
        public static final String LIVING_WEATHER = "/prod-api/api/living/weather";
        //查询缴费分类
        public static final String LIVING_CATEGORY_LIST = "/prod-api/api/living/category/list";
        //缴费接口
        public static final String LIVING_RECHARGE = "/prod-api/api/living/recharge";
        //查询当前用户缴费记录
        public static final String LIVING_RECHARGE_LIST = "/prod-api/api/living/recharge/record/list";
        //根据缴费编号查询缴费账单
        public static final String LIVING_BILL_LIST = "/prod-api/api/living/bill";
        //根据身份证查询缴费编号
        public static final String LIVING_ACCOUNT_LIST = "/prod-api/api/living/account/list";
        //查询手机话费余额信息
        public static final String LIVING_PHONE = "/prod-api/api/living/phone";
        //手机话费充值
        public static final String LIVING_PHONE_RECHARGE = "/prod-api/api/living/phone/recharge";
        //充值当前用户话费充值记录
        public static final String LIVING_RECORD_LIST = "/prod-api/api/living/phone/record/list";
        //查询话费充值优惠策略
        public static final String LIVING_RULE_LIST = "/prod-api/api/living/phone/rule/list";

        //房源列表
        public static final String HOUSE_LIST = "/prod-api/api/house/housing/list";
        //房源详情
        public static final String HOUSE_LIST_DETAIL = "/prod-api/api/house/housing";

        //查询公司列表
        public static final String JOB_COMPANY_LIST = "/prod-api/api/job/company/list";
        //获取公司详情
        public static final String JOB_COMPANY_DETAIL = "/prod-api/api/job/company";
        //投递简历
        public static final String JOB_DELIVER = "/prod-api/api/job/deliver";
        //投递简历历史列表
        public static final String JOB_DELIVER_LIST = "/prod-api/api/job/deliver/list";
        //查询招聘列表
        public static final String JOB_POST_LIST = "/prod-api/api/job/post/list";
        //查询招聘信息详情
        public static final String JOB_POST_DETAIL = "/prod-api/api/job/post";
        //新增当前简历 | 修改当前用户简历
        public static final String ADD_RESUME = "/prod-api/api/job/resume";
        //查询用户简历详情
        public static final String QUERY_RESUME_DETAIL = "/prod-api/api/job/resume/queryResumeByUserId";
        //查询职位列表
        public static final String JOB_PROFESSION_LIST = "/prod-api/api/job/profession/list";

        //智慧交管轮播
        public static final String TRAFFIC_ROTATION_LIST = "/prod-api/api/traffic/rotation/list";
        //绑定车辆 | 修改绑定车辆信息 | 解除绑定车辆信息
        public static final String TRAFFIC_CAR = "/prod-api/api/traffic/car";
        //检车须知
        public static final String TRAFFIC_CHECK_CAT_GRT = "/prod-api/api/traffic/checkCar/grt";
        //查询所有检车地点
        public static final String TRAFFIC_CHECK_CAR_PLACE_LIST = "/prod-api/api/traffic/checkCar/place/list";
        //查询检车详情
        public static final String TRAFFIC_CHECK_CAR_PLACE = "/prod-api/api/traffic/checkCar/plate";
        //预约检车
        public static final String APPLY_TRAFFIC_CHECK_CAR = "/prod-api/api/traffic/checkCar/apply";
        //查询预约检车的订单
        public static final String TRAFFIC_APPLY_LIST = "/prod-api/api/traffic/checkCar/apply/list";
        //获取绑定车辆列表
        public static final String TRAFFIC_CAR_LIST = "/prod-api/api/traffic/car/list";
        //绑定驾驶证信息 | 获取驾驶证详细信息 | 解除绑定驾驶证信息
        public static final String TRAFFIC_LICENSE = "/prod-api/api/traffic/license";
        //获取当前登录人绑定的驾驶证信息
        public static final String TRAFFIC_USER_LICENSE = "/prod-api/api/traffic/license/user";

        //电影轮播图
        public static final String MOVIE_ROTATION = "/prod-api/api/movie/rotation/list";
        //看过
        public static final String MOVIE_FILM_FAVORITE = "/prod-api/api/movie/film/favorite";
        //想看
        public static final String MOVIE_FILM_LIKE = "/prod-api/api/movie/film/like";
        //查询影片信息列表
        public static final String MOVIE_FILM_LIST = "/prod-api/api/movie/film/list";
        //查询电影预告信息列表
        public static final String MOVIE_FILM_PREVIEW_LIST = "/prod-api/api/movie/film/preview/list";
        //获取电影预告信息详情
        public static final String MOVIE_FILM_PREVIEW = "/prod-api/api/movie/film/preview";
        //获取电影详细信息
        public static final String MOVIE_FILM_DETAIL = "/prod-api/api/movie/film/detail";
        //查询影院信息
        public static final String MOVIE_THEATRE = "/prod-api/api/movie/theatre/list";
        //查询场次座位信息
        public static final String MOVIE_THEATRE_LIST4TIME = "/prod-api/api/movie/theatre/list4times";
        //查询影厅信息列表
        public static final String MOVIE_THEATRE_ROOM_LIST = "/prod-api/api/movie/theatre/room/list";
        //查询座位信息列表
        public static final String MOVIE_THEATRE_SEAT_LIST = "/prod-api/api/movie/theatre/seat/list";
        //获取座位信息详细信息
        public static final String MOVIE_THEATRE_SEAT = "/prod-api/api/movie/theatre/seat";
        //查询影片场次列表
        public static final String MOVIE_THEATRE_TIMES_LIST = "/prod-api/api/movie/theatre/times/list";
        //获取影院详细信息
        public static final String MOVIE_THEATRE_DETAIL = "/prod-api/api/movie/theatre";
        //提交订单
        public static final String MOVIE_TICKET = "/prod-api/api/movie/ticket";
        //查询影票信息列表
        public static final String MOVIE_TICKET_LIST = "/prod-api/api/movie/ticket/list";
        //查询订单信息列表
        public static final String MOVIE_TICKET_ORDER_LIST = "/prod-api/api/movie/ticket/order/list";
        //订单支付
        public static final String MOVIE_TICKET_PAY_ORDER = "/prod-api/api/movie/ticket/order/pay";
        //获取订单详细信息 | 删除订单信息
        public static final String MOVIE_TICKER_ORDER = "/prod-api/api/movie/ticket/order";

        //外卖轮播图
        public static final String TAKE_OUT_ROTATION = "/prod-api/api/takeout/rotation/list";
        //外卖分类
        public static final String TAKE_OUT_THEME_LIST = "/prod-api/api/takeout/theme/list";
        //查询店家列表
        public static final String TAKE_OUT_SELLER_LIST = "/prod-api/api/takeout/seller/list";
        //查询附件商家
        public static final String TAKE_OUT_SELLER_NEAR = "/prod-api/api/takeout/seller/near";
        //获取店家详情
        public static final String TAKE_OUT_SELLER = "/prod-api/api/takeout/seller";
        //菜品查询店家信息
        public static final String TAKE_OUT_SEARCH = "/prod-api/api/takeout/search";
        //根据查询菜品列表
        public static final String TAKE_OUT_PRODUCT_LIST = "/prod-api/api/takeout/product/list";
        //查询店家菜品类别
        public static final String TAKE_OUT_CATEGORY_LIST = "/prod-api/api/takeout/category/list";
        //添加店家收藏 | 取消店家收藏
        public static final String TAKE_OUT_COLLECT = "/prod-api/api/takeout/collect";
        //判断当前用户是否收藏店家
        public static final String TAKE_OUT_COLLECT_CHECK = "/prod-api/api/takeout/collect/check";
        //获取当前用户收藏列表
        public static final String TAKE_OUT_COLLECT_LIST = "/prod-api/api/takeout/collect/list";
        //创建订单
        public static final String TAKE_OUT_ORDER_CREATE = "/prod-api/api/takeout/order/create";
        //支付订单
        public static final String TAKE_OUT_PAY = "/prod-api/api/takeout/pay";
        //查询当前用户订单列表
        public static final String TAKE_OUT_ORDER_LIST = "/prod-api/api/takeout/order/list";
        //订单退款
        public static final String TAKE_OUT_ORDER_REFOUND = "/prod-api/api/takeout/order/refound";
        //查询订单详情
        public static final String TAKE_OUT_ORDER = "/prod-api/api/takeout/order";
        //添加店家评论
        public static final String TAKE_OUT_COMMENT = "/prod-api/api/takeout/comment";
        //查询店家评论列表
        public static final String TAKE_OUT_COMMENT_LIST = "/prod-api/api/takeout/comment/list";
        //添加收货地址 | 修改收货地址 | 获取收货地址详细信息 | 删除收货地址
        public static final String TAKE_OUT_ADDRESS = "/prod-api/api/takeout/address";
        //获取当前用户收货地址列表
        public static final String TAKE_OUT_ADDRESS_LIST = "/prod-api/api/takeout/address/list";
    }

    //数据库表名类
    public static class TableName {
        //停车场列表
        public static final String PARKING_LOT = "parkingLot";
        //服务列表
        public static final String SERVICE_LIST = "serviceList";
        //公交线路列表
        public static final String BUS_LIST = "busList";
    }
}
