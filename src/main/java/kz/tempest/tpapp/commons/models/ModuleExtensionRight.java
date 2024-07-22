package kz.tempest.tpapp.commons.models;

import jakarta.persistence.*;
import kz.tempest.tpapp.commons.enums.Right;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ModuleExtensionRight {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module")
    private ModuleInfo module;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "extension")
    private ExtensionInfo extension;
    @Column(name = "`right`")
    @Enumerated(EnumType.STRING)
    private Right right = Right.WRITE;
}
