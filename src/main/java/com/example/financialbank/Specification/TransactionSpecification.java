package com.example.financialbank.Specification;

import com.example.financialbank.dto.TransactionFilterDTO;
import com.example.financialbank.model.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


public class TransactionSpecification {
    public static Specification<Transaction> filterBy(TransactionFilterDTO filter){
        return(root, query, cb) -> {
                List<Predicate> predicate = new ArrayList<>();

            if (filter.getType() != null) {
                predicate.add(cb.equal(root.get("type"), filter.getType()));
            }

            if (filter.getStatus() != null) {
                predicate.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            if (filter.getStartDate() != null) {
                predicate.add(cb.greaterThanOrEqualTo(root.get("dateCreation"), filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                predicate.add(cb.lessThanOrEqualTo(root.get("dateCreation"), filter.getEndDate()));
            }

            return cb.and(predicate.toArray(new Predicate[0]));

        };
    }
}
