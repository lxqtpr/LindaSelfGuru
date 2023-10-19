package dev.lxqtpr.lindaSelfGuru.Domain.Songs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.lxqtpr.lindaSelfGuru.Domain.Categories.CategoryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Libs.LibraryEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

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

    private String fileName;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
