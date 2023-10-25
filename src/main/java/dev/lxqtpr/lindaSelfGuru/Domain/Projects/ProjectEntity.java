package dev.lxqtpr.lindaSelfGuru.Domain.Projects;

import dev.lxqtpr.lindaSelfGuru.Domain.Notes.NotesEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Songs.SongEntity;
import dev.lxqtpr.lindaSelfGuru.Domain.Users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "note_id")
    private NotesEntity note;

    private String voiceRecording;
}
