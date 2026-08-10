package dev.cruzs.gustavo.service_bancary.schedulingWork.adapters.out.cache;

import dev.cruzs.gustavo.service_bancary.schedulingWork.application.ports.out.SchedulingCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class SchedulingCacheAdapter implements SchedulingCache {
  private static final String CACHE_KEY = "Scheduling-pending";
  private final StringRedisTemplate stringRedisTemplate;

  public SchedulingCacheAdapter(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public void save(UUID SchedulingId, LocalDateTime scheduledDate) {
    double score = scheduledDate.toEpochSecond(ZoneOffset.UTC);
    stringRedisTemplate.opsForZSet().add(CACHE_KEY, SchedulingId.toString(), score);
  }

  @Override
  public List<UUID> findByScheduledDate(LocalDateTime scheduledDate) {
    double maxScore = scheduledDate.toEpochSecond(ZoneOffset.UTC);
    Set<String> ids = stringRedisTemplate.opsForZSet().rangeByScore(CACHE_KEY, 0, maxScore);

    if (ids == null || ids.isEmpty()) return List.of();

    return ids.stream().map(UUID::fromString).toList();
  }

  @Override
  public void deleteAllById(List<UUID> SchedulingIds) {
    if (SchedulingIds == null || SchedulingIds.isEmpty()) return;
    stringRedisTemplate.opsForZSet().remove(CACHE_KEY, SchedulingIds.stream().map(UUID::toString).toArray());
  }

  @Override
  public void deleteById(UUID SchedulingId) {
    stringRedisTemplate.opsForZSet().remove(CACHE_KEY, SchedulingId.toString());
  }
}
