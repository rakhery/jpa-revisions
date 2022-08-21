package fr.m2i.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * TODO: Vous devez implémenter les mapping JPA pour l'entité {@link Movie}
 * - spécifier un id
 * - spécifier le nom de la table: "movie"
 * - configurer id en tant colonne qui s'auto incrémente
 * - spécifier explicitement chaque nom de colonne ("id", "name", "director", "duration")
 * - spécifier une contrainte "not null" pour les champs {@link Movie#name} and {@link Movie#director}
 */

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @Column(name = "id_cinema")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="director",nullable = false)
    @NotNull
    private String director;
    @Column(name = "duration")
    @NotNull
    private Integer durationSeconds;
}
