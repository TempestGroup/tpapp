package kz.tempest.tpapp.commons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.tempest.tpapp.commons.utils.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Base64;

@Data
@Table(name = "files")
@Entity(name = "files")
@NoArgsConstructor
@AllArgsConstructor
public class IFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "format", nullable = false)
    private String format;
    @JsonIgnore
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB", nullable = false)
    private byte[] file;

    public IFile(String name, String format, byte[] file) {
        this.name = name;
        this.format = format;
        this.file = file;
    }

    public IFile(String name, byte[] file) {
        this.name = name;
        this.format = FileUtil.getFileFormat(name);
        this.file = file;
    }

}
