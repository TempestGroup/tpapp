package kz.tempest.tpapp.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageFilter {
    private int pageNumber;
    private int countInPage;
}
