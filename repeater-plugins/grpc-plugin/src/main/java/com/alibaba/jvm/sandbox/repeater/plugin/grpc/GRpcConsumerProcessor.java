package com.alibaba.jvm.sandbox.repeater.plugin.grpc;

import com.alibaba.jvm.sandbox.api.event.BeforeEvent;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultInvocationProcessor;
import com.alibaba.jvm.sandbox.repeater.plugin.core.util.LogUtil;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Identity;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.InvokeType;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.HashMap;

/**
 * {@link GRpcConsumerProcessor} grpc消费端调用处理
 * <p>
 * @author wangmeng
 */
class GRpcConsumerProcessor extends DefaultInvocationProcessor {

    GRpcConsumerProcessor(InvokeType type) {
        super(type);
    }

    @Override
    public Identity assembleIdentity(BeforeEvent event) {
        Object methodDescriptor = event.argumentArray[1];
        if (methodDescriptor == null) {
            return new Identity(InvokeType.GRPC.name(), "Unknown", "Unknown", new HashMap<String, String>(1));
        }
        try {
            String serviceName = (String) MethodUtils.invokeMethod(methodDescriptor, "getServiceName");
            String fullMethodName = (String) MethodUtils.invokeMethod(methodDescriptor, "getFullMethodName");
            return new Identity(InvokeType.GRPC.name(),serviceName,fullMethodName.replace(serviceName+"/",""),null);
        } catch (Exception e) {
            // ignore
            LogUtil.error("error occurred when assemble grpc request", e);
        }
        return new Identity(InvokeType.GRPC.name(), "Unknown", "Unknown", new HashMap<String, String>(1));
    }

    @Override
    public Object[] assembleRequest(BeforeEvent event) {
        return new Object[]{event.argumentArray[3]};
    }
}
