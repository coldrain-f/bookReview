package edu.bookreview.repository;

import edu.bookreview.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<SampleEntity, Long> {

}
