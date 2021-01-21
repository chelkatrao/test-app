package uz.chelkatrao.testapp.domain;

import lombok.Getter;
import lombok.Setter;
import uz.chelkatrao.testapp.domain.base.AbstractAuditingEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "q_file")
public class FileStorage extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "fileSequenceGenerator")
    @SequenceGenerator(name = "fileSequenceGenerator", allocationSize = 1)
    private Long id;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "extension")
    private String extension;

    @Column(name = "fileSize")
    private Long fileSize;

    @Column(name = "hash_id")
    private String hashId;

    @Column(name = "name")
    private String name;

    @Column(name = "upload_path")
    private String uploadPath;

}