package com.ExpenseTrackerApp.ExpenseTrackerApp.repository;

import com.ExpenseTrackerApp.ExpenseTrackerApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
