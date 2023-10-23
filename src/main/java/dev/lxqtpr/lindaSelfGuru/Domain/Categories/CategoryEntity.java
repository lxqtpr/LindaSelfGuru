package dev.lxqtpr.lindaSelfGuru.Domain.Categories;

import dev.lxqtpr.lindaSelfGuru.Domain.Libraries.LibraryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne()
    @JoinColumn(name = "library_id")
    private LibraryEntity library;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongEntity> songs;
}
