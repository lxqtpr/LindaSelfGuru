package dev.lxqtpr.lindaSelfGuru.Domain.Libs;

import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibraryEntity {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "library")
    ArrayList<SongEntity> songs;
}
