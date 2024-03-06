package com.xzit.common.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonUserApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(isValidOperator("19850530878"));
    }
    private boolean isValidOperator(String phone) {
        // 中国电信号段
        String[] telecomSegments = {"133", "1349", "1410", "149", "153", "162", "1700", "1701", "1702", "173", "1740", "1741", "177", "180", "181", "189", "190", "191", "193", "199"};
        // 中国联通号段
        String[] unicomSegments = {"130", "131", "132", "140", "145", "146", "155", "156", "166", "167", "1704", "1705", "1706", "1707", "171", "175", "176", "185", "186", "196"};
        // 中国移动号段
        String[] mobileSegments = {"1340", "1341", "1342", "1343", "1344", "1345", "1346", "1347", "1348", "135", "136", "137", "138", "139", "1440", "147", "148", "150", "151", "152", "157", "158", "159", "165", "1703", "1704", "1705", "1706", "1707", "1708", "1709", "171", "172", "178", "182", "183", "184", "187", "188", "195", "197", "198"};
        // 中国广电号段
        String[] broadcastSegments = {"192"};

        // 构造正则表达式
        StringBuilder regexBuilder = new StringBuilder("^(");
        appendSegments(regexBuilder, telecomSegments);
        regexBuilder.append("|");
        appendSegments(regexBuilder, unicomSegments);
        regexBuilder.append("|");
        appendSegments(regexBuilder, mobileSegments);
        regexBuilder.append("|");
        appendSegments(regexBuilder, broadcastSegments);
        regexBuilder.append(")\\d{");
        regexBuilder.append(11);
        regexBuilder.append("}$");

        // 判断手机号是否匹配任一号段
        return phone.matches(regexBuilder.toString());
    }

    // 辅助方法：将号段数组添加到正则表达式构造器
    private void appendSegments(StringBuilder regexBuilder, String[] segments) {
        for (int i = 0; i < segments.length; i++) {
            if (i > 0) {
                regexBuilder.append("|");
            }
            regexBuilder.append(segments[i]);
        }
    }

}
