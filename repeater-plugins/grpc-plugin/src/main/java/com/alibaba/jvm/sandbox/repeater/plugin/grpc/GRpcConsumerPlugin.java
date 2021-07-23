package com.alibaba.jvm.sandbox.repeater.plugin.grpc;

import com.alibaba.jvm.sandbox.api.event.Event.Type;
import com.alibaba.jvm.sandbox.repeater.plugin.api.InvocationProcessor;
import com.alibaba.jvm.sandbox.repeater.plugin.core.impl.AbstractInvokePluginAdapter;
import com.alibaba.jvm.sandbox.repeater.plugin.core.model.EnhanceModel;
import com.alibaba.jvm.sandbox.repeater.plugin.domain.InvokeType;
import com.alibaba.jvm.sandbox.repeater.plugin.spi.InvokePlugin;
import com.google.common.collect.Lists;
import org.kohsuke.MetaInfServices;

import java.util.List;

/**
 * {@link GRpcConsumerPlugin} grpc provider
 * <p>
 * 拦截io.grpc.stub.ClientCalls#blockingUnaryCall等入口进行录制
 * </p>
 * @author wangmeng
 */
@MetaInfServices(InvokePlugin.class)
public class GRpcConsumerPlugin extends AbstractInvokePluginAdapter {

    @Override
    protected List<EnhanceModel> getEnhanceModels() {
        EnhanceModel em = EnhanceModel.builder()
                .classPattern("io.grpc.stub.ClientCalls")
                .methodPatterns(EnhanceModel.MethodPattern.transform(
                        "asyncUnaryCall",
                        "asyncServerStreamingCall",
                        "asyncClientStreamingCall",
                        "asyncBidiStreamingCall",
                        "blockingUnaryCall",
                        "blockingServerStreamingCall",
                        "futureUnaryCall"))
                .watchTypes(Type.BEFORE, Type.RETURN, Type.THROWS)
                .build();
        return Lists.newArrayList(em);
    }

    @Override
    protected InvocationProcessor getInvocationProcessor() {
        return new GRpcConsumerProcessor(getType());
    }

    @Override
    public InvokeType getType() {
        return InvokeType.GRPC;
    }

    @Override
    public String identity() {
        return "grpc";
    }

    @Override
    public boolean isEntrance() {
        return false;
    }

}
