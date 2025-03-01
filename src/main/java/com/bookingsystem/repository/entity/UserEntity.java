package com.bookingsystem.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import static com.bookingsystem.constants.EntityConstants.*;

@Entity
@Table(name = TABLE_USER)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = COLUMN_USERNAME, nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = COLUMN_EMAIL, nullable = false, unique = true, length = 100)
    private String email;
}
