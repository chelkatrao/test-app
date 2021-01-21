package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.base.AbstractAuditingEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "q_title_page")
public class QuizTitlePage extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "titlePageSequenceGenerator")
    @SequenceGenerator(name = "titlePageSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Column(name = "description", length = 3000)
    private String description;

    @OneToOne
    @JoinColumn(name = "fileStorage")
    private FileStorage fileStorage;

    @OneToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

}
