package kz.tempest.tpapp.fileReader.dtos;

import jakarta.validation.constraints.NegativeOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XmlNodeItem extends NodeItem {
    private String name;
    private Object value;
    private HashMap<String, Object> attributes = new HashMap<>();
    private List<NodeItem> children = new ArrayList<>();
}
