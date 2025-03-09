package com.afcruz.book_network.feedback;

import com.afcruz.book_network.common.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "_feedback")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback extends AbstractEntity {
    private Double note;
    private String comment;
}
