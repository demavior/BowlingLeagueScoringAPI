package com.acs576.g2.bowlingleaguescoringapi.repository;

import com.acs576.g2.bowlingleaguescoringapi.entity.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
    List<GameScore> findByPlayerId(Long playerId);
    List<GameScore> findByTeamId(Long teamId);
    List<GameScore> findByPlayerIdAndGameId(Long playerId, Long gameId);
    List<GameScore> findByGameId(Long gameId);

    List<GameScore> findAllByTeamIdAndGameId(Long teamId, Long game);
}