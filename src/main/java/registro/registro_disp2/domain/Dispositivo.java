package registro.registro_disp2.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Dispositivos")
//@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dispositivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",length = 100)
    private Long id;

    @Column
    private String tipo;

    @Column
    private String marca;

    @Column
    private String serial;

    @OneToMany(mappedBy = "dispositivo")
    private Set<DispositivoAsignado> dispositivosAsignados;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
    
    @PrePersist
    protected void onCreate() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

}
