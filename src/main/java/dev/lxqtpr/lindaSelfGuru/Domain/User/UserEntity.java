package dev.lxqtpr.lindaSelfGuru.Domain.User;

import dev.lxqtpr.lindaSelfGuru.Domain.Libs.LibraryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.NotesEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Projects.ProjectEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongEntity;
import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Not;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectEntity> projects;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<NotesEntity> notes;
}
