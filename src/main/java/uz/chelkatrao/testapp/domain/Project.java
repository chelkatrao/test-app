package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.base.AbstractAuditingEntity;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Table(name = "t_project")
@Entity
public class Project extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "projectSequenceGenerator")
    @SequenceGenerator(name = "projectSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 1000, nullable = false)
    private String name;

    @Column(name = "description", length = 3000)
    private String description;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Quiz> quizzes;

}
