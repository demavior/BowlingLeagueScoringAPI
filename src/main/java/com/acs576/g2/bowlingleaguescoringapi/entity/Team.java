package com.acs576.g2.bowlingleaguescoringapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Team entity in the database.
 */
@Entity
@Table(name = "bl_team", uniqueConstraints = {
        @UniqueConstraint(name = "uc_team_name_league_id", columnNames = {"name"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY) // to define many teams can belong to one league
    @JoinColumn(name = "league_id") // league_id is the foreign key in the team table
    private League league; // to define relationship with League entity

    @ManyToMany(mappedBy = "teams")
    private Set<Game> games = new HashSet<>();

    @ManyToMany(mappedBy = "teams")
    private Set<Player> players = new HashSet<>();

}