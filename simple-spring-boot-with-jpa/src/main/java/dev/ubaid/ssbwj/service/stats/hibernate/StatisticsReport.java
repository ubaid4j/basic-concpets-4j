package dev.ubaid.ssbwj.service.stats.hibernate;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class StatisticsReport {
    private static final Logger log = LoggerFactory.getLogger(StatisticsReport.class);
    
    private static final MetricRegistry metricRegistry = new MetricRegistry();
    
    private static final Histogram connectionCountHistogram = metricRegistry.histogram("connectionCount");

    private static final Timer transactionTimer = metricRegistry.timer("transactionTimer");
    
    private static final Slf4jReporter logReporter = Slf4jReporter
            .forRegistry(metricRegistry)
            .outputTo(log)
            .build();
    
    public void transactionTime(long nanos) {
        transactionTimer.update(nanos, TimeUnit.NANOSECONDS);
    }
    
    public void connectionCount(long count) {
        connectionCountHistogram.update(count);
    }
    
    public void generate() {
        logReporter.report();
    }
}
