package model.service;

import model.domain.entity.Statistic;

import java.util.List;

public interface StatisticService extends Service {
    List<Statistic> getStatisticByUser(long userId);
}
