package fr.m2i.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * todo:
 * - Implémenter un constructeur sans arguments
 * - Implémenter les getter et les setter
 * - Implementer equals() qui vérifie si l'instance est valide et vérifie uniquement le champs id
 * - Implementer hashCode() qui retourne la valeur 31 (hard codé)
 * - Initialiser le champs {@link Author#books} avec une nouvel instance de {@link HashSet}
 * - Implémenter la méthode utilitaire {@link Author#addBook(Book)} qui établie la relation des deux cotés
 * - Implémenter la méthode utilitaire {@link Author#removeBook(Book)} qui supprime la relation des deux cotés
 * 
 * - Configurer l'entité JPA
 * - Specifier le nom de la table: "author"
 * - Configurer un id auto généré
 * - Configurer la colonne "first_name" pour la rendre obligatoire {@link Author#firstName}
 * - Configurer la colonne "last_name" pour la rendre obligatoire {@link Author#lastName}
 * 
 * - Configurer la relation many-to-many entre {@link Author} et {@link Book}
 * - Configurer les cascades pour cette relation avec {@link CascadeType#PERSIST} and {@link CascadeType#MERGE}
 * - Configurer la table de jointure (JoinTable) avec le nom "author_book"
 * - Configurer la clé étrangère (foreign key)"book_id" qui réference la table book
 * - Configurer la clé étrangère (foreign key)"author_id" qui référence la table author
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable( name = "author_book",
            joinColumns = @JoinColumn( name = "author_id" ),
            inverseJoinColumns = @JoinColumn( name = "book_id" ) )
    private Set<Book> books=new HashSet<>();



    public void addBook(Book book) {
        books.add(book);

    }

    public void removeBook(Book book) {
        if(books!=null){
            book.getAuthors().remove(this);
            books.remove(book);

        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return id.equals(author.id);
    }
   //31 * i == (i << 5) - i
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName== null) ? 0 : firstName.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }
}

