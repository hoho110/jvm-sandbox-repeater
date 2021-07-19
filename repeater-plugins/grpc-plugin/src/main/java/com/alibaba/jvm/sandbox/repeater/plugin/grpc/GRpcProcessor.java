package com.alibaba.jvm.sandbox.repeater.plugin.grpc;

import com.alibaba.jvm.sandbox.api.event.BeforeEvent;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.api.DefaultInvocationProcessor;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Identity;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.Invocation;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.InvokeType;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * <p>
 *
 * @author zhaoyb1990
 */
class GRpcProcessor extends DefaultInvocationProcessor {

    GRpcProcessor(InvokeType type) {
        super(type);
    }
}
