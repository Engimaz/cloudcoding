package cc.cloudcoding.flink.mysql;


import cc.cloudcoding.flink.common.ReadInfoUtil;
import cc.cloudcoding.flink.common.DataBaseEnum;
import cc.cloudcoding.flink.common.DataChangeInfo;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.source.SourceRecord;

/**
 * mysql反序列化器
 */
public class MysqlDeserialization implements DebeziumDeserializationSchema<DataChangeInfo> {

    /**
     * 反序列化数据,转为变更JSON对象
     *
     * @param sourceRecord
     * @param collector
     * @return void
     * @author lei
     * @date 2022-08-25 14:44:31
     */
    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<DataChangeInfo> collector) {
        DataChangeInfo dataChangeInfo = ReadInfoUtil.getDataChangeInfo(sourceRecord, DataBaseEnum.MYSQL);
        // 输出数据
        collector.collect(dataChangeInfo);
    }


    @Override
    public TypeInformation<DataChangeInfo> getProducedType() {
        return TypeInformation.of(DataChangeInfo.class);
    }
}
