package com.company.simpleservice.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "gender", nullable = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ClientStatus clientStatus = ClientStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_dttm", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_dttm")
    private LocalDateTime updatedAt;
}
