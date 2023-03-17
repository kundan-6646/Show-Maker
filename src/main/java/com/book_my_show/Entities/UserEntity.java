package com.book_my_show.Entities;

import javax.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int age;
    private String name;
    private String address;

    @Column(unique = true, nullable = false)
    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<TicketEntity> bookedTickets = new ArrayList<>();
}
