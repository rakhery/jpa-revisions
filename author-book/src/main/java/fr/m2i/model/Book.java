package fr.m2i.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * todo:
 * - Implémenter un constructeur sans arguments
 * - Implémenter les getter et les setter
 * - Implémenter equals() et hashCode() en se basant sur le champs {@link Book#isbn} (@EqualsAndHashCode)
 * - Initialiser le champs {@link Book#authors} avec une nouvelle instance de {@link HashSet}
 * 
 * - Configurer l'entité JPA
 * - Spécifier le nom de la table: "book"
 * - Configurer un id auto généré
 * - Configurer la colonne "name" pour la rendre obligatoire {@link Book#name}
 * - Configurer la colonne "isbn"  pour la rendre obligatoire {@link Book#isbn}
 * 
 * - Configurer la relation many-to-many mappé du coté de l'entité {@link Author}
 */

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name",nullable = false)
    private String name;
    @Column(name = "isbn",nullable = false)
    private String isbn;
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}

