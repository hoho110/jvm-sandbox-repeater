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
 * <p>
 *
 * @author zhaoyb1990
 */
@MetaInfServices(InvokePlugin.class)
public class GRpcPlugin extends AbstractInvokePluginAdapter {

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
        return new GRpcProcessor(getType());
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
