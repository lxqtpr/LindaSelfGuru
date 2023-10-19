package dev.lxqtpr.lindaSelfGuru.Domain.Categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.LibraryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private LibraryEntity library;

    @OneToMany(mappedBy = "category")
    private List<SongEntity> songs;
}
