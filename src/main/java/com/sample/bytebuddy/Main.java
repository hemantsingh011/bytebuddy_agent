package com.sample.bytebuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {
        premain("", ByteBuddyAgent.install());
        SampleController sampleController = new SampleController();
        sampleController.bar();
        sampleController.barWithArg("sampleArgument");
        sampleController.barWithMultipleArg("sampleArgument", 1);
        sampleController.barWithMultipleArgAndReturn("sampleArgument", 2);
    }

    public static void premain(String arguments, Instrumentation instrumentation) {
        System.out.println("==============================agent start!======================================");
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform((builder, type, classLoader, module) -> builder
                        .method(ElementMatchers.any())
                        .intercept(MethodDelegation.to(AccessInterceptor.class))
                ).installOn(instrumentation);
        System.out.println("==============================agent end!======================================");
    }

    public static class AccessInterceptor {
        @RuntimeType
        public static Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> callable) throws Exception {
            System.out.println(String.format("Capture: Method invoked! Class name %s --> %s" , method.getDeclaringClass(), method.getName()));
            Arrays.stream(args).forEach(arg -> System.out.println(" Argument " + arg));
            return callable.call();
        }
    }


}
