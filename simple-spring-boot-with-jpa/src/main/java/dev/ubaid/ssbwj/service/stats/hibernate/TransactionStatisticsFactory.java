package dev.ubaid.ssbwj.service.stats.hibernate;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.spi.StatisticsFactory;
import org.hibernate.stat.spi.StatisticsImplementor;
import org.springframework.stereotype.Component;

//@Component
public class TransactionStatisticsFactory implements StatisticsFactory {
    @Override
    public StatisticsImplementor buildStatistics(SessionFactoryImplementor sessionFactory) {
        return new TransactionStatistics(sessionFactory);
    }
}
