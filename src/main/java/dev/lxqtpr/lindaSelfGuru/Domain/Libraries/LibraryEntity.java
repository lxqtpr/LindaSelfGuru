package dev.lxqtpr.lindaSelfGuru.Domain.Libraries;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String avatar;

    private String subtitle;

    @OneToMany(mappedBy = "library",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryEntity> categories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
