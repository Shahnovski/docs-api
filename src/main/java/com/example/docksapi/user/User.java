package com.example.docksapi.user;

import com.example.docksapi.doc.Doc;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    Long id;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    Set<Role> roles = new HashSet<>();

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @OneToMany(mappedBy = "docAuthor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Doc> docs;

}
