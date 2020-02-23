package com.sample.bytebuddy;

public class SampleController {
    public void bar() {
        System.out.println("Real: bar");
        test1();
    }

    private void test1() {
        System.out.println("Real: private method invoked test1");

    }

    public void barWithArg(String arg) {
        System.out.println("Real: bar with args: " + arg);
    }

    public void barWithMultipleArg(String arg, Integer arg1) {
        System.out.println("Real: bar with different type of args " + arg + " arg1 " + arg1);
    }

    public String barWithMultipleArgAndReturn(String arg, Integer arg1) {
        String returnValue = "hjj";
        System.out.println("Real: bar with args " + arg + " arg1 " + arg1 + " return value " + returnValue);
        return returnValue;
    }
}

