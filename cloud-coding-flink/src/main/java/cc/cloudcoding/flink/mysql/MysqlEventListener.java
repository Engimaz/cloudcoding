package cc.cloudcoding.flink.mysql;


import cc.cloudcoding.flink.common.DataChangeInfo;
import cc.cloudcoding.flink.common.DataChangeSink;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * mysql事件监听器
 */
@Component
public class MysqlEventListener implements ApplicationRunner {

    private final DataChangeSink dataChangeSink;

    public MysqlEventListener(DataChangeSink dataChangeSink) {
        this.dataChangeSink = dataChangeSink;
    }

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);
            env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                    3, // 尝试重启的次数
                    Time.of(10, TimeUnit.SECONDS) // 间隔
            ));
            MySqlSource<DataChangeInfo> mySqlSource = buildDataChangeSource();
            DataStream<DataChangeInfo> streamSource = env
                    .fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "mysql-source")
                    .setParallelism(1);
            streamSource.addSink(dataChangeSink);
            try {
                env.execute("cloud-coding-article-flink-mysql");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }

    /**
     * 构造变更数据源
     */
    private MySqlSource<DataChangeInfo> buildDataChangeSource() {
        return MySqlSource.<DataChangeInfo>builder()
                .hostname("159.75.147.232")
                .port(3306)
                .databaseList("init_database","cloud-coding-article")
                .tableList("init_database.cdc","cloud-coding-article.article")
                .username("root")
                .password("root")

                /**initial初始化快照,即全量导入后增量导入(检测更新数据写入)
                 * latest:只进行增量导入(不读取历史变化)
                 * timestamp:指定时间戳进行数据导入(大于等于指定时间错读取数据)
                 * 注：点击查看源码发现目前只支持initial以及latest了
                 */
                .startupOptions(StartupOptions.latest())
                .deserializer(new MysqlDeserialization())
                .serverTimeZone("GMT+8")
                .build();
    }
}
