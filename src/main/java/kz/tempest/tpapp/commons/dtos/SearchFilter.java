package kz.tempest.tpapp.commons.dtos;

import kz.tempest.tpapp.commons.enums.Language;
import kz.tempest.tpapp.commons.utils.SortUtil;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Map;

@Data
public class SearchFilter extends PageFilter {
    private Map<String, Object> filter;
    private Map<String, Sort.Direction> sort;


    public Pageable getPageable(Class iClass, Language language) {
        return PageRequest.of(
            getPageNumber() - 1,
            getCountInPage(),
            Sort.by(SortUtil.getSortOrders(iClass, sort, language))
        );
    }
}
