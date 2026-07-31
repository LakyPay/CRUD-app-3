package user.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table (name = "users")
@Data
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private int age;
    @Column (name = "created_at")
    private LocalDateTime createdAt;

    public User(){

    }

    public User(String name, String email, int age, LocalDateTime createdAt){
        this.age = age;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }
}
