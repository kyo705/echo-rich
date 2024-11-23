package com.echoandrich.task.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @DisplayName("[직업 조회 : 존재하는 직업인 경우]")
    @Test
    public void findById() {

        //given
        String jobId = "AC_ACCOUNT";

        //when
        Optional<Job> job = jobRepository.findById(jobId);

        //then
        assertThat(job).isPresent();
        assertThat(job.get().getJobTitle()).isEqualTo("Public Accountant");
        assertThat(job.get().getMinSalary()).isEqualTo(BigDecimal.valueOf(4200));
        assertThat(job.get().getMaxSalary()).isEqualTo(BigDecimal.valueOf(9000));
    }

    @DisplayName("[직업 조회 : 존재하지 않는 직업인 경우]")
    @Test
    public void findByIdWithNotExistingJobId() {

        //given
        String jobId = "not_existing_job_id";

        //when
        Optional<Job> job = jobRepository.findById(jobId);

        //then
        assertThat(job).isNotPresent();
    }
}