package com.weather.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Session {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id" )
    private User user; // TODO сделать уникальным?

    @Column(name = "expires_at")
    private LocalDate expiresAt;
}
