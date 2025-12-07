package indi.repo.springboot.toexcel.mt2;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 根响应对象
 */
@Data
public class ProductResponse {
    private String msg;

    private Integer code;

    private ProductData data;


/**
 * 主数据对象
 */
@Data
class ProductData {
    @ExcelProperty("商品标签ID")
    private String product_tag_id;

    @ExcelProperty("POI域类型")
    private Integer poi_domain_type;

    @ExcelProperty("当前页")
    private Integer current_page;

    @ExcelProperty("是否有下一页")
    private Boolean has_next_page;

    @ExcelProperty("商品数量")
    private Integer product_count;

    @ExcelProperty("商品SPU列表")
    private List<ProductSpu> product_spu_list;
}

/**
 * 商品SPU对象
 */
@Data
class ProductSpu {
    @ExcelProperty("标准分类")
    private List<StandardCategory> standardCategorys;

    @ExcelProperty("UGC首分类")
    private Boolean ugcFirstCategory;

    @ExcelProperty("店内推荐搜索标签")
    private List<Object> search_in_shop_recommend_tags;

    @ExcelProperty("商品ID")
    private Long id;

    @ExcelProperty("CSPU ID")
    private Integer cspu_id;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("最低价")
    private Double min_price;

    @ExcelProperty("会员价")
    private Double member_price;

    @ExcelProperty("点赞数")
    private Integer praise_count;

    @ExcelProperty("点赞数量")
    private Integer praise_num;

    @ExcelProperty("点赞内容")
    private String praise_content;

    @ExcelProperty("点踩数")
    private Integer tread_num;

    @ExcelProperty("新点赞数")
    private Integer praise_num_new;

    @ExcelProperty("单位")
    private String unit;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("图片")
    private String picture;

    @ExcelProperty("月销量")
    private Integer month_saled;

    @ExcelProperty("月销量内容")
    private String month_saled_content;

    @ExcelProperty("想买人数")
    private Integer want_to_Buy;

    @ExcelProperty("想买内容")
    private String want_to_buy_content;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("CSPU售罄")
    private Boolean cspu_sold_out;

    @ExcelProperty("CSPU类型")
    private Integer cspu_type;

    @ExcelProperty("状态描述")
    private String status_description;

    @ExcelProperty("状态提醒列表")
    private List<Object> status_remind_list;

    @ExcelProperty("属性")
    private List<Object> attrs;

    @ExcelProperty("SKU标签")
    private String sku_label;

    @ExcelProperty("SKU列表")
    private List<Sku> skus;

    @ExcelProperty("SKU复制信息")
    private List<Object> skus_copy_info;

    @ExcelProperty("标签")
    private String tag;

    @ExcelProperty("排序")
    private Integer sequence;

    @ExcelProperty("促销信息")
    private String promotion_info;

    @ExcelProperty("推荐促销信息")
    private Object recommend_promotion_info;

    @ExcelProperty("促销")
    private Promotion promotion;

    @ExcelProperty("活动标签")
    private String activity_tag;

    @ExcelProperty("分享提示")
    private ShareTip share_tip;

    @ExcelProperty("活动类型")
    private Integer activity_type;

    @ExcelProperty("活动策略")
    private ActivityPolicy activity_policy;

    @ExcelProperty("朋友点赞内容")
    private String friends_praise_content;

    @ExcelProperty("朋友昵称点赞内容")
    private String friends_nickname_praise_content;

    @ExcelProperty("商品标签图片")
    private String product_label_picture;

    @ExcelProperty("商品标签图片列表")
    private List<Object> product_label_picture_list;

    @ExcelProperty("商品图片标签列表")
    private List<Object> product_picture_label_list;

    @ExcelProperty("日志字段")
    private LogField log_field;

    @ExcelProperty("标签文本")
    private String label_text;

    @ExcelProperty("活动标签ID")
    private String activity_tag_id;

    @ExcelProperty("是否免费获取")
    private Boolean is_freeget;

    @ExcelProperty("免费获取URL")
    private String freeget_url;

    @ExcelProperty("活动UUID")
    private String activityUuid;

    @ExcelProperty("活动文本")
    private String activity_act_text;

    @ExcelProperty("排名数字标签")
    private String rank_num_label;

    @ExcelProperty("分享活动UUID")
    private String shareActivityUuid;

    @ExcelProperty("库存标签")
    private String stock_label;

    @ExcelProperty("广告图标")
    private Object advert_icon;

    @ExcelProperty("好评率")
    private String praise_rate;

    @ExcelProperty("好评率数字")
    private String praise_rate_num;

    @ExcelProperty("订阅")
    private Integer subscribe;

    @ExcelProperty("标签信息")
    private List<Object> label_info;

    @ExcelProperty("封面URL")
    private String cover_url;

    @ExcelProperty("视频时间")
    private Object video_time;

    @ExcelProperty("长宽比")
    private Double length_width_ratio;

    @ExcelProperty("美团活动类型")
    private String wmActivityType;

    @ExcelProperty("销售类型")
    private Integer sale_type;

    @ExcelProperty("商品下标签")
    private Object product_under_label;

    @ExcelProperty("月销量排序")
    private Integer month_saled_sort;

    @ExcelProperty("预售配送时间")
    private Integer presale_delivery_time;

    @ExcelProperty("是否选中")
    private Integer selected;

    @ExcelProperty("统一价格")
    private UnifyPrice unify_price;

    @ExcelProperty("动态活动标签")
    private List<DynamicActLabel> dynamic_act_labels;

    @ExcelProperty("总库存标签")
    private String total_stock_label;

    @ExcelProperty("价格隐藏")
    private Integer price_hidden;

    @ExcelProperty("配送时间")
    private Object shipping_time;

    @ExcelProperty("配送时间字符串")
    private String shipping_time_str;

    @ExcelProperty("兑换价格")
    private Double exchange_price;

    @ExcelProperty("不显示推荐区域")
    private Boolean not_show_recommend_region;

    @ExcelProperty("认证状态")
    private Integer auth_status;

    @ExcelProperty("商品类型")
    private Integer product_type;

    @ExcelProperty("商品来源")
    private Integer product_source;

    @ExcelProperty("类型图片映射")
    private Map<String, Object> type_picture_map;

    @ExcelProperty("SKU信息开关")
    private Boolean sku_info_switch;
}

/**
 * 标准分类对象
 */
@Data
class StandardCategory {
    @ExcelProperty("分类ID")
    private Integer id;

    @ExcelProperty("分类名称")
    private String name;

    @ExcelProperty("分类级别")
    private Integer level;

    @ExcelProperty("是否设置名称")
    private Boolean setName;

    @ExcelProperty("是否设置级别")
    private Boolean setLevel;

    @ExcelProperty("是否设置ID")
    private Boolean setId;
}

/**
 * SKU对象
 */
@Data
class Sku {
    @ExcelProperty("SKU ID")
    private Long id;

    @ExcelProperty("SPU ID")
    private Long spu_id;

    @ExcelProperty("CSPU分组值")
    private String cspu_grouping_value;

    @ExcelProperty("规格")
    private String spec;

    @ExcelProperty("SKU信息开关")
    private Boolean sku_info_switch;

    @ExcelProperty("SKU名称")
    private String name;

    @ExcelProperty("规格总数")
    private Double spec_total_count;

    @ExcelProperty("SKU描述")
    private String description;

    @ExcelProperty("SKU图片")
    private String picture;

    @ExcelProperty("类型图片映射")
    private Map<String, Object> type_picture_map;

    @ExcelProperty("价格")
    private Double price;

    @ExcelProperty("原价")
    private Double origin_price;

    @ExcelProperty("包装数量")
    private Double box_num;

    @ExcelProperty("包装价格")
    private Double box_price;

    @ExcelProperty("百分比")
    private Double percentage;

    @ExcelProperty("阶梯盒子")
    private LadderBox ladderBox;

    @ExcelProperty("最小订单数")
    private Integer min_order_count;

    @ExcelProperty("SKU状态")
    private Integer status;

    @ExcelProperty("库存")
    private Integer stock;

    @ExcelProperty("会员价")
    private Double member_price;

    @ExcelProperty("兑换价格")
    private Double exchange_price;

    @ExcelProperty("SPU活动标签")
    private String activity_tag_for_spu;

    @ExcelProperty("SPU活动策略")
    private ActivityPolicyForSpu activity_policy_for_spu;

    @ExcelProperty("SPU活动标签ID")
    private String activity_tag_id_for_spu;

    @ExcelProperty("SPU活动类型")
    private Integer activity_type_for_spu;

    @ExcelProperty("UPC编码")
    private String upccode;

    @ExcelProperty("限制")
    private Integer restrict;

    @ExcelProperty("订阅")
    private Integer subscribe;

    @ExcelProperty("实际库存")
    private Integer real_stock;

    @ExcelProperty("活动库存")
    private Integer activity_stock;

    @ExcelProperty("活动UUID")
    private String activityUuid;

    @ExcelProperty("分享活动UUID")
    private String shareActivityUuid;

    @ExcelProperty("促销信息")
    private String promotion_info;

    @ExcelProperty("促销")
    private Promotion promotion;

    @ExcelProperty("活动文本")
    private String activity_act_text;

    @ExcelProperty("库存标签")
    private String stock_label;

    @ExcelProperty("标签列表")
    private Object labelList;

    @ExcelProperty("统一价格")
    private UnifyPrice unify_price;

    @ExcelProperty("动态活动标签")
    private List<DynamicActLabel> dynamic_act_labels;

    @ExcelProperty("总库存标签")
    private String total_stock_label;

    @ExcelProperty("品牌附近信息")
    private BrandNearbyInfo brand_nearby_info;

    @ExcelProperty("认证状态")
    private Integer auth_status;

    @ExcelProperty("商品类型")
    private Integer product_type;

    @ExcelProperty("商品来源")
    private Integer product_source;

    @ExcelProperty("供应POI SKU")
    private Object supply_poi_sku;

    @ExcelProperty("组合规格")
    private String combine_spec;
}

/**
 * 阶梯价格盒子
 */
@Data
class LadderBox {
    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("阶梯数量")
    private Integer ladderNum;

    @ExcelProperty("阶梯价格")
    private Double ladderPrice;
}

/**
 * SPU活动策略
 */
@Data
class ActivityPolicyForSpu {
    @ExcelProperty("按数量折扣")
    private DiscountByCount discount_by_count;
}

/**
 * 按数量折扣
 */
@Data
class DiscountByCount {
    @ExcelProperty("数量")
    private Integer count;

    @ExcelProperty("折扣")
    private Double discount;
}

/**
 * 促销信息
 */
@Data
class Promotion {
    @ExcelProperty("优惠券")
    private Object coupon;

    @ExcelProperty("标签图片")
    private String label_pic;

    @ExcelProperty("促销文本")
    private String promotion_text;

    @ExcelProperty("配送折扣")
    private String delivery_discount;

    @ExcelProperty("SKU信息颜色")
    private String sku_info_color;

    @ExcelProperty("宽度")
    private Integer width;

    @ExcelProperty("高度")
    private Integer height;

    @ExcelProperty("标签类型")
    private Integer label_type;

    @ExcelProperty("服务器当前时间")
    private Object sever_current_time;

    @ExcelProperty("活动最近结束时间")
    private Object act_recent_end_time;

    @ExcelProperty("活动类型文本")
    private Object activity_type_text;

    @ExcelProperty("闪购")
    private Object flash_sale;

    @ExcelProperty("方案URL")
    private String scheme_url;

    @ExcelProperty("按钮文本")
    private Object button_text;

    @ExcelProperty("活动类型")
    private Object activity_type;

    @ExcelProperty("领取状态")
    private Integer receive_status;

    @ExcelProperty("状态")
    private Object status;

    @ExcelProperty("背景颜色")
    private Object background_color;

    @ExcelProperty("品牌带样式")
    private Integer brand_belt_style;

    @ExcelProperty("促销样式类型")
    private Integer promotion_style_type;

    @ExcelProperty("标签领取优惠券回调")
    private Object label_receive_coupon_callback;
}

/**
 * 统一价格对象
 */
@Data
class UnifyPrice {
    @ExcelProperty("价格")
    private Double price;

    @ExcelProperty("价格字符串")
    private String price_str;

    @ExcelProperty("划线价")
    private Double underlined_price;

    @ExcelProperty("划线价字符串")
    private String underlined_price_str;

    @ExcelProperty("活动信息")
    private ActivityInfo activity_info;

    @ExcelProperty("实际价格信息")
    private ActualPriceInfo actual_price_info;

    @ExcelProperty("POI会员信息")
    private PoiVipInfo poi_vip_info;

    @ExcelProperty("价格版本")
    private Integer price_version;
}

/**
 * 活动信息
 */
@Data
class ActivityInfo {
    @ExcelProperty("SKU ID")
    private Long sku_id;

    @ExcelProperty("最小单价SKU ID")
    private Integer min_unit_price_sku_id;

    @ExcelProperty("SPU ID")
    private Long spu_id;

    @ExcelProperty("活动价格")
    private Double activity_price;

    @ExcelProperty("最小单价")
    private Double min_unit_price;

    @ExcelProperty("最小单价字符串")
    private String min_unit_price_str;

    @ExcelProperty("单价数量")
    private Integer unit_price_count;

    @ExcelProperty("单价名称")
    private Object unit_price_name;

    @ExcelProperty("最小单价文本")
    private Object min_unit_price_text;

    @ExcelProperty("活动价格字符串")
    private String activity_price_str;

    @ExcelProperty("活动价格后缀")
    private Object activity_price_suffix;

    @ExcelProperty("活动单价")
    private Double activity_unit_price;

    @ExcelProperty("活动单价字符串")
    private String activity_unit_price_str;

    @ExcelProperty("活动单价文本")
    private Object activity_unit_price_text;

    @ExcelProperty("复合价格单位")
    private Object compound_price_unit;

    @ExcelProperty("活动标签后缀")
    private Object activity_tag_suffix;

    @ExcelProperty("活动类型")
    private Integer activity_type;

    @ExcelProperty("活动ID")
    private Long act_id;

    @ExcelProperty("活动价格来源")
    private Integer activity_price_source;

    @ExcelProperty("第二活动价格")
    private Double secondary_activity_price;

    @ExcelProperty("第二活动价格字符串")
    private String secondary_activity_price_str;

    @ExcelProperty("第二活动价格后缀")
    private Object secondary_activity_price_suffix;

    @ExcelProperty("第二活动单价")
    private Double secondary_activity_unit_price;

    @ExcelProperty("第二活动单价字符串")
    private String secondary_activity_unit_price_str;

    @ExcelProperty("第二活动单价文本")
    private Object secondary_activity_unit_price_text;

    @ExcelProperty("第二活动价格来源")
    private Integer secondary_activity_price_source;

    @ExcelProperty("SG补贴价格")
    private Double sg_subsidy_price;

    @ExcelProperty("SG补贴价格字符串")
    private String sg_subsidy_price_str;

    @ExcelProperty("SG补贴整数价格字符串")
    private String sg_subsidy_int_price_str;

    @ExcelProperty("SG补贴状态")
    private Integer sg_subsidy_status;

    @ExcelProperty("第三活动价格")
    private Double third_activity_price;

    @ExcelProperty("第三活动价格字符串")
    private String third_activity_price_str;

    @ExcelProperty("第三活动价格来源")
    private Integer third_activity_price_source;

    @ExcelProperty("活动开始时间")
    private Long activity_start_time;

    @ExcelProperty("活动结束时间")
    private Long activity_end_time;

    @ExcelProperty("每单配额")
    private Integer quota_per_order;

    @ExcelProperty("划线价格")
    private Double underline_price;

    @ExcelProperty("划线价格字符串")
    private String underline_price_str;

    @ExcelProperty("最小订单数")
    private Integer min_order_count;

    @ExcelProperty("最小订单划线价")
    private Double min_order_underline_price;

    @ExcelProperty("最小订单划线价字符串")
    private String min_order_underline_price_str;

    @ExcelProperty("活动价格前缀")
    private Object activity_price_prefix;

    @ExcelProperty("版本")
    private Integer version;

    @ExcelProperty("单位")
    private String unit;

    @ExcelProperty("AB测试")
    private Map<String, String> ab_test;

    @ExcelProperty("商品详情")
    private Object item_details;

    @ExcelProperty("折扣力度")
    private Double discount_strength;

    @ExcelProperty("活动价格标签")
    private Integer activity_price_tag;

    @ExcelProperty("划线价单价文本")
    private Object unit_price_of_underline_text;

    @ExcelProperty("活动价单价文本")
    private Object unit_price_of_activity_text;

    @ExcelProperty("第二活动价单价文本")
    private Object unit_price_of_secondary_activity_text;

    @ExcelProperty("单价规格")
    private Object unit_price_spec;

    @ExcelProperty("单价规格数量")
    private Double unit_price_spec_count;

    @ExcelProperty("命中SG通用实验")
    private Boolean hit_sg_general_experiment;
}

/**
 * 实际价格信息
 */
@Data
class ActualPriceInfo {
    @ExcelProperty("SKU ID")
    private Long sku_id;

    @ExcelProperty("实际价格")
    private Double actual_price;

    @ExcelProperty("实际价格字符串")
    private String actual_price_str;

    @ExcelProperty("实际价格文本")
    private String actual_price_text;

    @ExcelProperty("SG实际价格长文本")
    private String sg_actual_price_long_text;

    @ExcelProperty("SG实际价格短文本")
    private String sg_actual_price_short_text;

    @ExcelProperty("单位文本")
    private String unit_text;

    @ExcelProperty("SKU数量")
    private Integer sku_count;

    @ExcelProperty("计算类型")
    private Integer cal_type;

    @ExcelProperty("活动详情")
    private List<ActivityDetail> activity_details;
}

/**
 * 活动详情
 */
@Data
class ActivityDetail {
    @ExcelProperty("活动ID")
    private Long act_id;

    @ExcelProperty("活动ID")
    private Long activity_id;

    @ExcelProperty("活动类型")
    private Integer act_type;

    @ExcelProperty("可用优惠券")
    private Object available_coupons;

    @ExcelProperty("商品编号")
    private Integer item_number;

    @ExcelProperty("数量")
    private Integer count;

    @ExcelProperty("每实例订单限制")
    private Integer order_limit_per_instance;

    @ExcelProperty("国家补贴详情")
    private Object nation_subsidy_detail;
}

/**
 * POI会员信息
 */
@Data
class PoiVipInfo {
    @ExcelProperty("是否为POI会员")
    private Boolean is_poi_vip;
}

/**
 * 动态活动标签
 */
@Data
class DynamicActLabel {
    @ExcelProperty("圆角半径")
    private List<String> corner_radius;

    @ExcelProperty("规则ID")
    private Integer rule_id;

    @ExcelProperty("子标签")
    private List<SubTag> sub_tags;

    @ExcelProperty("点击回调信息")
    private ClickCallbackInfo click_callback_info;

    @ExcelProperty("点击曝光信息")
    private ClickExposeInfo click_expose_info;

    @ExcelProperty("标签高度")
    private String tag_height;

    @ExcelProperty("使用动态高度")
    private String use_dynamic_height;

    @ExcelProperty("边框颜色")
    private String border_color;
}

/**
 * 子标签
 */
@Data
class SubTag {
    @ExcelProperty("字体粗细")
    private String font_weight;

    @ExcelProperty("原价")
    private Integer original_price;

    @ExcelProperty("减价")
    private Integer reduce_price;

    @ExcelProperty("分类ID")
    private Integer category_id;

    @ExcelProperty("字体大小")
    private String font_size;

    @ExcelProperty("文本")
    private String text;

    @ExcelProperty("文本颜色")
    private String text_color;

    @ExcelProperty("类型")
    private Integer type;

    @ExcelProperty("水平填充")
    private String horizontal_padding;

    @ExcelProperty("宽度")
    private String width;

    @ExcelProperty("对齐方式")
    private String alignment;

    @ExcelProperty("URL")
    private String url;

    @ExcelProperty("高度")
    private String height;
}

/**
 * 点击回调信息
 */
@Data
class ClickCallbackInfo {
    @ExcelProperty("数据")
    private String data;

    @ExcelProperty("类型")
    private String type;
}

/**
 * 点击曝光信息
 */
@Data
class ClickExposeInfo {
    @ExcelProperty("优惠券ID")
    private String coupon_id;

    @ExcelProperty("活动ID")
    private String activity_id;

    @ExcelProperty("索引")
    private String index;

    @ExcelProperty("状态")
    private String status;
}

/**
 * 品牌附近信息
 */
@Data
class BrandNearbyInfo {
    @ExcelProperty("附近POI数量")
    private Integer nearby_poi_count;
}

/**
 * 分享提示
 */
@Data
class ShareTip {
    @ExcelProperty("渠道")
    private List<Integer> channels;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("描述图标")
    private String description_icon;

    @ExcelProperty("分享按钮图标")
    private String share_button_icon;

    @ExcelProperty("活动ID")
    private Integer activity_id;

    @ExcelProperty("活动ID字符串")
    private String activity_id_str;

    @ExcelProperty("活动类型")
    private Integer activity_type;

    @ExcelProperty("小程序ID")
    private String miniProgramId;

    @ExcelProperty("分享信息")
    private ShareInfo share_info;
}

/**
 * 分享信息
 */
@Data
class ShareInfo {
    @ExcelProperty("图标")
    private String icon;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("内容")
    private String content;

    @ExcelProperty("URL")
    private String url;

    @ExcelProperty("微信URL")
    private String weixin_url;

    @ExcelProperty("分享标签图片")
    private String share_label_img;
}

/**
 * 活动策略
 */
@Data
class ActivityPolicy {
    @ExcelProperty("按数量折扣")
    private DiscountByCount discount_by_count;
}

/**
 * 日志字段
 */
@Data
class LogField {
    @ExcelProperty("商品标签类型列表")
    private List<Object> product_label_type_list;

    @ExcelProperty("推荐标签类型")
    private Integer recommend_label_type;
}

}