package com.xzit.common.logistics.constant;

public interface LogisticConstant {
    Integer MAX_ADDRESS=5;
    String APP_KEY="Y34BZ-PL7K4-UGSU6-KXDZQ-JR5EO-XSFMR";
    String ADDRESS_TO_LOCATION_API="https://apis.map.qq.com/ws/geocoder/v1/?address={address}&key={key}";
    String PICK_UP="快递员%s已经揽收";
    String IMAGE_SEPARATOR="/logisticImage/";
    String ARRIVE ="货物到达%s,确认人%s";
    String RELEASE="货物已离开%s,下一站是%s,确认人%s";
    String DELIVERY="货物已离开%s,开始配送,请保持手机畅通,确认人%s";
    String COMPLETE="配送完毕,配送员%s";
    String OVER="订单完成,已关闭";
}
