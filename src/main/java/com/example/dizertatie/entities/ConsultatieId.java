package com.example.dizertatie.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ConsultatieId implements Serializable {
    private Long pacientId;
    private Long medicId;
}
