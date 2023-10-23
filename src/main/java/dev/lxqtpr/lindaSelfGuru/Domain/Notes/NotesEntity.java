package dev.lxqtpr.lindaSelfGuru.Domain.Notes;

import dev.lxqtpr.lindaSelfGuru.Domain.Phrases.PhraseEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserEntity;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String title;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhraseEntity> phrases;

}
