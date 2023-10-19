package dev.lxqtpr.lindaSelfGuru.Domain.User;

import dev.lxqtpr.lindaSelfGuru.Domain.Libs.LibraryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LibraryEntity> library;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
