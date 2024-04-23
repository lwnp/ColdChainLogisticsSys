package com.xzit.common.sys.constant;

public interface MQConstant {
    String EMAIL_EXCHANGE="email-out-0";
    String NOTICE_EXCHANGE="notice-out-0";
    String CHECK_TITLE="有新货物待审核";
    String CHECK_CONTENT="提交了货物审核，详情请见管理页";
    String CHECK_RESULT_SUCCESS="审核通过通知";
    String CHECK_RESULT_SUCCESS_CONTENT="您提交的货物审核已通过,见详情页";
    String CHECK_RESULT_FAIL="审核失败通知";
    String CHECK_RESULT_FAIL_CONTENT="您提交的货物审核未通过,请修改,见详情页";
    String IOT_EXCHANGE="iot-out-0";
    String USER_PAID_TITLE="订单%s支付成功";
    String USER_PAID_CONTENT="请尽快通知相应人员运输,订单安排见管理页";
    String COURIER_ARRANGE_TITLE="订单%s任务状态已更新";
    String COURIER_ARRANGE_CONTENT="请查看自己任务";
    String WAREHOUSE_EXCHANGE="warehouse-out-1";
}
