package com.example.user.service.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "User_DB")
public class UserEntity {
    @Id
    @Column(unique = true, nullable = false)
    private String id = UUID.randomUUID().toString();
    @Column(unique = true, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String emailId;

    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @Column( nullable = false)
    private String mobileNumber;

    @Column(nullable = true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;
  //  private Wishlist wishlist;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
