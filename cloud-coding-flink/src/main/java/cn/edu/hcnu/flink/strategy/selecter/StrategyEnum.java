package cn.edu.hcnu.flink.strategy.selecter;

import cn.edu.hcnu.flink.common.DataChangeInfo;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;



@Getter

public enum StrategyEnum {

;


    private final String tableName;
    private final Class varClass;
    private final String handlerName;

    StrategyEnum(String tableName, Class varClass, String handlerName) {
        this.tableName = tableName;
        this.varClass = varClass;
        this.handlerName = handlerName;
    }

    public static StrategyHandleSelector getSelector(DataChangeInfo dataChangeInfo) {
        if (dataChangeInfo == null) {
            return null;
        }
        String tableName = dataChangeInfo.getTableName();
        StrategyHandleSelector selector = new StrategyHandleSelector();
        for (StrategyEnum strategyEnum : values()) {
            if (strategyEnum.getTableName().equals(tableName)) {
                selector.setHandlerName(strategyEnum.handlerName);
                selector.setOperatorTime(dataChangeInfo.getOperatorTime());
                selector.setOperatorType(dataChangeInfo.getOperatorType());
                JSONObject jsonObject = JSON.parseObject(dataChangeInfo.getData());
                selector.setData(BeanUtil.copyProperties(jsonObject, strategyEnum.varClass));
                return selector;
            }
        }
        System.out.println(" 没有找到合适的表 " );
        return null;
    }
}
