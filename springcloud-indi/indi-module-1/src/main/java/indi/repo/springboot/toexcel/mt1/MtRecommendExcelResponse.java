package indi.repo.springboot.toexcel.mt1;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class MtRecommendExcelResponse {

    @JsonIgnore
    private int category_type;

    @JsonIgnore
    private String tag;

    @ExcelProperty("名称")
    private String name;

    @JsonIgnore
    private String icon;

    @JsonIgnore
    private String big_pic_url;

    @JsonIgnore
    private int current_page;

    @JsonIgnore
    private boolean has_next_page;

    @ExcelProperty("商品总数")
    private int product_count;

    @JsonIgnore
    private List<Long> allSortedSpuId;

    @JsonIgnore
    private int type;

    @JsonIgnore
    private int selected;

    @JsonIgnore
    private int sequence;

    @JsonIgnore
    private String activity_tag;

    @ExcelProperty("商品列表")
    private List<Product> spus;

    @Data
    public static class Product {
        @ExcelProperty("标准分类")
        private List<StandardCategory> standardCategorys;

        @JsonIgnore
        private List<Object> search_in_shop_recommend_tags;

        @ExcelProperty("商品ID")
        private long id;

        @JsonIgnore
        private long cspu_id;

        @ExcelProperty("商品名称")
        private String name;

        @ExcelProperty("最低价格")
        private double min_price;

        @ExcelProperty("会员价格")
        private double member_price;

        @ExcelProperty("推荐数")
        private int praise_count;

        @ExcelProperty("好评数")
        private int praise_num;

        @ExcelProperty("好评内容")
        private String praise_content;

        @JsonIgnore
        private int tread_num;

        @ExcelProperty("新好评数")
        private int praise_num_new;

        @ExcelProperty("单位")
        private String unit;

        @ExcelProperty("描述")
        private String description;

        @ExcelProperty("图片")
        private String picture;

        @ExcelProperty("月销量")
        private int month_saled;

        @ExcelProperty("月销量内容")
        private String month_saled_content;

        @ExcelProperty("想买人数")
        private int want_to_Buy;

        @ExcelProperty("想买内容")
        private String want_to_buy_content;

        @JsonIgnore
        private int status;

        @JsonIgnore
        private boolean cspu_sold_out;

        @JsonIgnore
        private int cspu_type;

        @JsonIgnore
        private String status_description;

        @JsonIgnore
        private List<Object> status_remind_list;

        @ExcelProperty("商品属性")
        private List<Attribute> attrs;

        @ExcelProperty("SKU标签")
        private String sku_label;

        @ExcelProperty("规格列表")
        private List<Sku> skus;

        @JsonIgnore
        private List<Object> skus_copy_info;

        @JsonIgnore
        private String tag;

        @JsonIgnore
        private int sequence;

        @JsonIgnore
        private String promotion_info;

        @JsonIgnore
        private Promotion promotion;

        @JsonIgnore
        private String activity_tag;

        @ExcelProperty("分享提示")
        private ShareTip share_tip;

        @JsonIgnore
        private int activity_type;

        @ExcelProperty("活动策略")
        private ActivityPolicy activity_policy;

        @ExcelProperty("好友好评内容")
        private String friends_praise_content;

        @ExcelProperty("好友昵称好评内容")
        private String friends_nickname_praise_content;

        @JsonIgnore
        private String product_label_picture;

        @JsonIgnore
        private List<ProductLabelPicture> product_label_picture_list;

        @JsonIgnore
        private List<Object> product_picture_label_list;

        @JsonIgnore
        private LogField log_field;

        @JsonIgnore
        private String label_text;

        @JsonIgnore
        private String activity_tag_id;

        @ExcelProperty("是否免费获取")
        private boolean is_freeget;

        @ExcelProperty("免费获取链接")
        private String freeget_url;

        @JsonIgnore
        private String activityUuid;

        @JsonIgnore
        private String activity_act_text;

        @JsonIgnore
        private String rank_num_label;

        @JsonIgnore
        private String shareActivityUuid;

        @JsonIgnore
        private String stock_label;

        @ExcelProperty("好评率")
        private String praise_rate;

        @ExcelProperty("好评率数值")
        private String praise_rate_num;

        @JsonIgnore
        private int subscribe;

        @JsonIgnore
        private List<Object> label_info;

        @JsonIgnore
        private String cover_url;

        @JsonIgnore
        private double length_width_ratio;

        @JsonIgnore
        private int sale_type;

        @JsonIgnore
        private int month_saled_sort;

        @JsonIgnore
        private int presale_delivery_time;

        @ExcelProperty("卖点列表")
        private List<String> selling_points;

        @ExcelProperty("是否选中")
        private int selected;

        @ExcelProperty("统一价格")
        private UnifyPrice unify_price;

        @JsonIgnore
        private List<DynamicActLabel> dynamic_act_labels;

        @JsonIgnore
        private String total_stock_label;

        @ExcelProperty("价格是否隐藏")
        private int price_hidden;

        @ExcelProperty("兑换价格")
        private double exchange_price;

        @ExcelProperty("是否显示推荐区域")
        private boolean not_show_recommend_region;

        @JsonIgnore
        private int auth_status;

        @JsonIgnore
        private int product_type;

        @JsonIgnore
        private int product_source;

        @JsonIgnore
        private Object type_picture_map;

        @JsonIgnore
        private boolean sku_info_switch;
    }

    @Data
    public static class StandardCategory {
        @ExcelProperty("分类ID")
        private long id;

        @ExcelProperty("分类名称")
        private String name;

        @ExcelProperty("分类级别")
        private int level;

        @JsonIgnore
        private boolean setId;

        @JsonIgnore
        private boolean setLevel;

        @JsonIgnore
        private boolean setName;
    }

    @Data
    public static class Attribute {
        @ExcelProperty("属性名称")
        private String name;

        @ExcelProperty("属性值列表")
        private List<AttributeValue> values;
    }

    @Data
    public static class AttributeValue {
        @ExcelProperty("属性值ID")
        private long id;

        @ExcelProperty("属性值")
        private String value;

        @JsonIgnore
        private ValueStyle style;

        @JsonIgnore
        private int mode;
    }

    @Data
    public static class ValueStyle {
        @ExcelProperty("背景颜色")
        private String back_ground_color;

        @ExcelProperty("文字颜色")
        private String text_color;

        @ExcelProperty("选择框颜色")
        private String select_box_color;

        @ExcelProperty("文字左侧图标")
        private String text_left_icon;

        @ExcelProperty("显示条件")
        private int show_condition;
    }

    @Data
    public static class Sku {
        @ExcelProperty("SKU ID")
        private long id;

        @JsonIgnore
        private long spu_id;

        @JsonIgnore
        private String cspu_grouping_value;

        @JsonIgnore
        private String spec;

        @ExcelProperty("组合规格")
        private String combine_spec;

        @ExcelProperty("SKU信息开关")
        private boolean sku_info_switch;

        @ExcelProperty("SKU名称")
        private String name;

        @ExcelProperty("规格总数")
        private int spec_total_count;

        @ExcelProperty("规格总数单位")
        private String spec_total_count_unit;

        @ExcelProperty("SKU描述")
        private String description;

        @ExcelProperty("SKU图片")
        private String picture;

        @JsonIgnore
        private Object type_picture_map;

        @ExcelProperty("SKU价格")
        private double price;

        @ExcelProperty("SKU原价")
        private double origin_price;

        @JsonIgnore
        private int box_num;

        @JsonIgnore
        private double box_price;

        @JsonIgnore
        private double percentage;

        @JsonIgnore
        private LadderBox ladderBox;

        @JsonIgnore
        private int min_order_count;

        @JsonIgnore
        private int status;

        @ExcelProperty("SKU库存")
        private int stock;

        @ExcelProperty("SKU会员价")
        private double member_price;

        @ExcelProperty("SKU兑换价")
        private double exchange_price;

        @JsonIgnore
        private String activity_tag_for_spu;

        @JsonIgnore
        private ActivityPolicy activity_policy_for_spu;

        @ExcelProperty("SPU活动标签ID")
        private String activity_tag_id_for_spu;

        @JsonIgnore
        private int activity_type_for_spu;

        @JsonIgnore
        private String upccode;

        @JsonIgnore
        private int restrict;

        @JsonIgnore
        private int subscribe;

        @ExcelProperty("实际库存")
        private int real_stock;

        @ExcelProperty("活动库存")
        private int activity_stock;

        @JsonIgnore
        private String activityUuid;

        @ExcelProperty("分享活动UUID")
        private String shareActivityUuid;

        @ExcelProperty("促销信息")
        private String promotion_info;

        @JsonIgnore
        private Promotion promotion;

        @JsonIgnore
        private String activity_act_text;

        @JsonIgnore
        private String stock_label;

        @ExcelProperty("SKU统一价格")
        private UnifyPrice unify_price;

        @ExcelProperty("动态活动标签")
        private List<DynamicActLabel> dynamic_act_labels;

        @ExcelProperty("总库存标签")
        private String total_stock_label;

        @ExcelProperty("品牌附近信息")
        private BrandNearbyInfo brand_nearby_info;

        @ExcelProperty("认证状态")
        private int auth_status;

        @ExcelProperty("产品类型")
        private int product_type;

        @ExcelProperty("产品来源")
        private int product_source;
    }

    @Data
    public static class LadderBox {
        @ExcelProperty("阶梯状态")
        private int status;

        @ExcelProperty("阶梯数量")
        private int ladderNum;

        @ExcelProperty("阶梯价格")
        private double ladderPrice;
    }

    @Data
    public static class Promotion {
        @ExcelProperty("标签图片")
        private String label_pic;

        @ExcelProperty("促销文本")
        private String promotion_text;

        @ExcelProperty("配送折扣")
        private String delivery_discount;

        @ExcelProperty("SKU信息颜色")
        private String sku_info_color;

        @ExcelProperty("宽度")
        private int width;

        @ExcelProperty("高度")
        private int height;

        @ExcelProperty("标签类型")
        private int label_type;

        @ExcelProperty("方案链接")
        private String scheme_url;

        @ExcelProperty("领取状态")
        private int receive_status;

        @ExcelProperty("品牌带样式")
        private int brand_belt_style;

        @ExcelProperty("促销样式类型")
        private int promotion_style_type;
    }

    @Data
    public static class UnifyPrice {
        @ExcelProperty("价格")
        private double price;

        @ExcelProperty("价格字符串")
        private String price_str;

        @ExcelProperty("划线价格")
        private double underlined_price;

        @ExcelProperty("划线价格字符串")
        private String underlined_price_str;

        @ExcelProperty("活动信息")
        private ActivityInfo activity_info;

        @ExcelProperty("实际价格信息")
        private ActualPriceInfo actual_price_info;

        @JsonIgnore
        private PoiVipInfo poi_vip_info;

        @JsonIgnore
        private int price_version;
    }

    @Data
    public static class ActivityInfo {
        @JsonIgnore
        private long sku_id;

        @JsonIgnore
        private long min_unit_price_sku_id;

        @JsonIgnore
        private long spu_id;

        @ExcelProperty("活动价格")
        private double activity_price;

        @ExcelProperty("最小单价")
        private double min_unit_price;

        @ExcelProperty("最小单价字符串")
        private String min_unit_price_str;

        @ExcelProperty("单价数量")
        private int unit_price_count;

        @ExcelProperty("活动价格字符串")
        private String activity_price_str;

        @ExcelProperty("活动单价")
        private double activity_unit_price;

        @ExcelProperty("活动单价字符串")
        private String activity_unit_price_str;

        @JsonIgnore
        private int activity_type;

        @JsonIgnore
        private long act_id;

        @ExcelProperty("活动价格来源")
        private int activity_price_source;

        @ExcelProperty("次要活动价格")
        private double secondary_activity_price;

        @ExcelProperty("次要活动价格字符串")
        private String secondary_activity_price_str;

        @ExcelProperty("次要活动单价")
        private double secondary_activity_unit_price;

        @ExcelProperty("次要活动单价字符串")
        private String secondary_activity_unit_price_str;

        @ExcelProperty("次要活动价格来源")
        private int secondary_activity_price_source;

        @ExcelProperty("补贴价格")
        private double sg_subsidy_price;

        @ExcelProperty("补贴价格字符串")
        private String sg_subsidy_price_str;

        @ExcelProperty("补贴整价字符串")
        private String sg_subsidy_int_price_str;

        @ExcelProperty("补贴状态")
        private int sg_subsidy_status;

        @ExcelProperty("第三活动价格")
        private double third_activity_price;

        @ExcelProperty("第三活动价格字符串")
        private String third_activity_price_str;

        @ExcelProperty("第三活动价格来源")
        private int third_activity_price_source;

        @ExcelProperty("活动开始时间")
        private long activity_start_time;

        @ExcelProperty("活动结束时间")
        private long activity_end_time;

        @ExcelProperty("每单限额")
        private int quota_per_order;

        @ExcelProperty("划线价格")
        private double underline_price;

        @JsonIgnore
        private String underline_price_str;

        @ExcelProperty("最小订单数")
        private int min_order_count;

        @ExcelProperty("最小订单划线价格")
        private double min_order_underline_price;

        @JsonIgnore
        private String min_order_underline_price_str;

        @JsonIgnore
        private int version;

        @ExcelProperty("单位")
        private String unit;

        @JsonIgnore
        private AbTest ab_test;

        @ExcelProperty("折扣力度")
        private double discount_strength;

        @JsonIgnore
        private int activity_price_tag;

        @JsonIgnore
        private int unit_price_spec_count;

        @JsonIgnore
        private boolean hit_sg_general_experiment;
    }

    @Data
    public static class AbTest {
        @ExcelProperty("新价格4+")
        private String price_new4plus;

        @ExcelProperty("新价格3专业版")
        private String price_new3pro;

        @ExcelProperty("新价格4")
        private String price_new4;

        @ExcelProperty("新价格4专业版")
        private String price_new4pro;

        @ExcelProperty("啤酒同品组展示")
        private String BEER_same_item_groupshow_inshop;

        @ExcelProperty("分组编号")
        private String fzbhmsdsj;
    }

    @Data
    public static class ActualPriceInfo {
        @ExcelProperty("SKU ID")
        private long sku_id;

        @ExcelProperty("实际价格")
        private double actual_price;

        @ExcelProperty("实际价格字符串")
        private String actual_price_str;

        @ExcelProperty("实际价格文本")
        private String actual_price_text;

        @ExcelProperty("补贴实际价格长文本")
        private String sg_actual_price_long_text;

        @ExcelProperty("补贴实际价格短文本")
        private String sg_actual_price_short_text;

        @ExcelProperty("单位文本")
        private String unit_text;

        @ExcelProperty("SKU数量")
        private int sku_count;

        @ExcelProperty("计算类型")
        private int cal_type;

        @ExcelProperty("活动详情")
        private List<ActivityDetail> activity_details;
    }

    @Data
    public static class ActivityDetail {
        @ExcelProperty("活动ID")
        private long act_id;

        @ExcelProperty("活动ID")
        private long activity_id;

        @ExcelProperty("活动类型")
        private int act_type;

        @ExcelProperty("商品数量")
        private int item_number;

        @ExcelProperty("数量")
        private int count;

        @ExcelProperty("每实例订单限制")
        private int order_limit_per_instance;
    }

    @Data
    public static class PoiVipInfo {
        @ExcelProperty("是否POI会员")
        private boolean is_poi_vip;
    }

    @Data
    public static class DynamicActLabel {
        @ExcelProperty("圆角半径")
        private List<String> corner_radius;

        @ExcelProperty("规则ID")
        private int rule_id;

        @ExcelProperty("背景颜色")
        private String background_color;

        @ExcelProperty("子标签")
        private List<SubTag> sub_tags;

        @ExcelProperty("点击曝光信息")
        private ClickExposeInfo click_expose_info;

        @ExcelProperty("标签高度")
        private String tag_height;

        @ExcelProperty("使用动态高度")
        private String use_dynamic_height;

        @ExcelProperty("边框颜色")
        private String border_color;

        @ExcelProperty("点击回调信息")
        private ClickCallbackInfo click_callback_info;
    }

    @Data
    public static class SubTag {
        @ExcelProperty("宽度")
        private String width;

        @ExcelProperty("对齐方式")
        private String alignment;

        @ExcelProperty("类型")
        private int type;

        @ExcelProperty("链接")
        private String url;

        @ExcelProperty("高度")
        private String height;

        @ExcelProperty("字体粗细")
        private String font_weight;

        @ExcelProperty("原价")
        private double original_price;

        @ExcelProperty("减价")
        private double reduce_price;

        @ExcelProperty("分类ID")
        private long category_id;

        @ExcelProperty("字体大小")
        private String font_size;

        @ExcelProperty("垂直内边距")
        private String vertical_padding;

        @ExcelProperty("文本")
        private String text;

        @ExcelProperty("文本颜色")
        private String text_color;

        @ExcelProperty("水平内边距")
        private String horizontal_padding;
    }

    @Data
    public static class ClickExposeInfo {
        @ExcelProperty("活动ID")
        private String activity_id;

        @ExcelProperty("索引")
        private String index;

        @ExcelProperty("状态")
        private String status;

        @ExcelProperty("优惠券ID")
        private String coupon_id;
    }

    @Data
    public static class ClickCallbackInfo {
        @ExcelProperty("数据")
        private String data;

        @ExcelProperty("类型")
        private String type;
    }

    @Data
    public static class BrandNearbyInfo {
        @ExcelProperty("附近POI数量")
        private int nearby_poi_count;
    }

    @Data
    public static class ShareTip {
        @ExcelProperty("渠道列表")
        private List<Integer> channels;

        @ExcelProperty("描述")
        private String description;

        @ExcelProperty("描述图标")
        private String description_icon;

        @ExcelProperty("分享按钮图标")
        private String share_button_icon;

        @ExcelProperty("活动ID")
        private int activity_id;

        @ExcelProperty("活动ID字符串")
        private String activity_id_str;

        @ExcelProperty("活动类型")
        private int activity_type;

        @ExcelProperty("小程序ID")
        private String miniProgramId;

        @ExcelProperty("分享信息")
        private ShareInfo share_info;
    }

    @Data
    public static class ShareInfo {
        @ExcelProperty("图标")
        private String icon;

        @ExcelProperty("标题")
        private String title;

        @ExcelProperty("内容")
        private String content;

        @ExcelProperty("链接")
        private String url;

        @ExcelProperty("微信链接")
        private String weixin_url;

        @ExcelProperty("分享标签图片")
        private String share_label_img;
    }

    @Data
    public static class ActivityPolicy {
        @ExcelProperty("按数量折扣")
        private DiscountByCount discount_by_count;
    }

    @Data
    public static class DiscountByCount {
        @ExcelProperty("数量")
        private int count;

        @ExcelProperty("折扣")
        private double discount;
    }

    @Data
    public static class ProductLabelPicture {
        @ExcelProperty("图片链接")
        private String picture_url;

        @ExcelProperty("宽度")
        private int width;

        @ExcelProperty("高度")
        private int height;

        @ExcelProperty("标签类型")
        private int label_type;
    }

    @Data
    public static class LogField {
        @ExcelProperty("产品标签类型列表")
        private List<Integer> product_label_type_list;

        @ExcelProperty("推荐标签类型")
        private int recommend_label_type;
    }
}