package dev.lxqtpr.lindaSelfGuru.Domain.Phrases;

import dev.lxqtpr.lindaSelfGuru.Domain.Notes.NotesEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhrasesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private NotesEntity note;

    private String text;

    private Long time;

    private Long silence;
}
