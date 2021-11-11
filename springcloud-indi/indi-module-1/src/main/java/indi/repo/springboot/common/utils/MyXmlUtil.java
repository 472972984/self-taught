package indi.repo.springboot.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 对 hutool 工具类解析xml做升级操作
 * @author ChenHQ
 * @date: create in 2021/8/25
 */
public class MyXmlUtil extends XmlUtil {


    /**
     * XML节点转换为Map
     *
     * @param node   XML节点
     * @param result 结果Map类型
     * @return XML数据转换后的Map
     * @since 4.0.8
     */
    public static Map<String, Object> xmlToMap(Node node, Map<String, Object> result) {
        if (null == result) {
            result = new HashMap<>(16);
        }
        final NodeList nodeList = node.getChildNodes();
        final int length = nodeList.getLength();
        Node childNode;
        Element childEle;
        for (int i = 0; i < length; ++i) {
            childNode = nodeList.item(i);
            if (false == isElement(childNode)) {
                continue;
            }

            childEle = (Element) childNode;
            //node节点名称
            String eleNodeName = childEle.getNodeName();

            //map中原数据
            final Object value = result.get(eleNodeName);

            //拿到属性数据
            final Map<String,Object> objectMap = new HashMap<>(16);
            NamedNodeMap attributes = childEle.getAttributes();
            int attLength = attributes.getLength();
            if (attLength > 0) {
                for (int j = 0; j < attLength; j++) {
                    String nodeName = attributes.item(j).getNodeName();
                    String nodeValue = attributes.item(j).getNodeValue();
                    objectMap.put(nodeName, nodeValue);
                }
            }

            Object newValue;
            if (childEle.hasChildNodes()) {
                // 子节点继续递归遍历
                final Map<String, Object> map = xmlToMap(childEle);
                //递归返回不为空
                if (MapUtil.isNotEmpty(map)) {
                    if (MapUtil.isNotEmpty(objectMap)) {
                        objectMap.putAll(map);
                        newValue = objectMap;
                    } else {
                        //赋值
                        newValue = map;
                    }
                } else {
                    //如果为空 获取文本内容
                    newValue = childEle.getTextContent();
                }
            } else {
                newValue = childEle.getTextContent();
            }

            //如果内容还为空
            if(Objects.isNull(newValue) || newValue.toString().length() == 0) {
                //设置属性集合内容
                newValue = objectMap;
            }

            if (null != newValue) {
                if (null != value) {
                    //原集合里面有数据
                    if (value instanceof List) {
                        //原集合里面是集合类型
                        ((List<Object>) value).add(newValue);
                        result.put(eleNodeName, value);
                    } else {
                        result.put(childEle.getNodeName(), CollUtil.newArrayList(value, newValue));
                    }
                } else {
                    //原集合里面没有数据
                    result.put(eleNodeName, newValue);
                }
            }
        }
        return result;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param xmlStr XML字符串
     * @return XML数据转换后的Map
     * @since 4.0.8
     */
    public static Map<String, Object> xmlToMap(String xmlStr) {
        return xmlToMap(xmlStr, new HashMap<>(16));
    }


    /**
     * XML格式字符串转换为Map
     *
     * @param node XML节点
     * @return XML数据转换后的Map
     * @since 4.0.8
     */
    public static Map<String, Object> xmlToMap(Node node) {
        return xmlToMap(node, new HashMap<>(16));
    }

    /**
     * XML格式字符串转换为Map<br>
     * 只支持第一级别的XML，不支持多级XML
     *
     * @param xmlStr XML字符串
     * @param result 结果Map类型
     * @return XML数据转换后的Map
     * @since 4.0.8
     */
    public static Map<String, Object> xmlToMap(String xmlStr, Map<String, Object> result) {
        final Document doc = parseXml(xmlStr);
        final Element root = getRootElement(doc);
        root.normalize();

        return xmlToMap(root, result);
    }

    /**
     * xml格式解析成bean对象
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String xml, Class<T> bean) {
        Document doc = parseXml(xml);
        Map<String, Object> map = xmlToMap(doc.getFirstChild());
        if (null != map && map.size() == 1) {
            String simpleName = bean.getSimpleName();
            if (map.containsKey(simpleName)) {
                return BeanUtil.toBean(map.get(simpleName), bean);
            }
        }

        return BeanUtil.toBean(map, bean);
    }
}
