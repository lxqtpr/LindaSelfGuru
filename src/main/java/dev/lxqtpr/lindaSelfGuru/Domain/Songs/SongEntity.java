package dev.lxqtpr.lindaSelfGuru.Domain.Songs;

import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String songName;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
