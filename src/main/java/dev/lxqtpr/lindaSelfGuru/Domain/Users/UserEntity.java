package dev.lxqtpr.lindaSelfGuru.Domain.Users;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.LibraryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Notes.NotesEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Projects.ProjectEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String password;

    @Column(unique = true)
    private String email;
    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LibraryEntity> library;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectEntity> projects;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<CategoryEntity> categories;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<NotesEntity> notes;
}
