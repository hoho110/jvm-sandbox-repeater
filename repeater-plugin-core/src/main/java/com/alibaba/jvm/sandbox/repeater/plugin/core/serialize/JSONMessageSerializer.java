package com.alibaba.jvm.sandbox.repeater.plugin.core.serialize;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.util.JsonFormat;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * JSON面向GRPC Message检测并序列化
 * @author wangmeng
 */
public class JSONMessageSerializer {
    private final static Logger log = LoggerFactory.getLogger(JSONMessageSerializer.class);
    public static String serialize(Object object) {
        if(isPb(object.getClass())){
            try {
                return (String) (MethodUtils.invokeMethod(JsonFormat.printer(),"print",object));
            } catch (Exception e) {
                log.error("error parse invocation request", e);
            }
        }
        return JSON.toJSONString(object);
    }
    private static boolean isPb(Class valueClass) {
        Method getParserForType = null;
        try {
            getParserForType = valueClass.getMethod("getParserForType");
        } catch (NoSuchMethodException e) {
            return false;
        }
        if (getParserForType == null) {
            return false;
        }
        return true;
    }
}