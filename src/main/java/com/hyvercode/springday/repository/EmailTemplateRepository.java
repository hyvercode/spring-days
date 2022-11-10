package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.EmailTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends CrudRepository<EmailTemplate,String> {

  Optional<EmailTemplate> findByEmailCode(String emailCode);
}
