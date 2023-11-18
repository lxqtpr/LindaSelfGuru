package dev.lxqtpr.lindaSelfGuru.Domain.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    @Query(value = """
             SELECT exists(
                           SELECT 1
                           FROM library_entity
                           WHERE user_id = :userId
                             AND id = :libraryId)
            """, nativeQuery = true)
    boolean isLibraryOwner(@Param("userId") Long userId, @Param("libraryId") Long libraryId);
    @Query(value = """
             SELECT exists(
                           SELECT 1
                           FROM project_entity
                           WHERE user_id = :userId
                             AND id = :projectId)
            """, nativeQuery = true)
    boolean isProjectOwner(@Param("userId") Long userId, @Param("projectId") Long projectId);

    @Query(value = """
             SELECT exists(
                           SELECT 1
                           FROM category_entity
                           WHERE user_id = :userId
                             AND id = :categoryId)
            """, nativeQuery = true)
    boolean isCategoryOwner(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    @Query(value = """
             SELECT exists(
                           SELECT 1
                           FROM category_entity
                           WHERE user_id = :userId
                             AND id = :noteId)
            """, nativeQuery = true)
    boolean isNoteOwner(@Param("userId") Long userId, @Param("noteId") Long noteId);
    Optional<UserEntity> findByEmail(String email);
}
