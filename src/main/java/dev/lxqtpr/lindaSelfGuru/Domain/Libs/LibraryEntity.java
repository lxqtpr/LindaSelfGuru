package dev.lxqtpr.lindaSelfGuru.Domain.Libs;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String avatar;

    private String subtitle;

    @OneToMany(mappedBy = "library")
    private List<CategoryEntity> categories;

    @ManyToOne
    @JoinColumn(name = "creater_Id")
    private UserEntity user;
}
