package com.echoandrich.task.employee.service;

import com.echoandrich.task.department.repository.Department;
import com.echoandrich.task.department.repository.DepartmentRepository;
import com.echoandrich.task.employee.SetupEmployee;
import com.echoandrich.task.employee.dto.EmployeeDto;
import com.echoandrich.task.employee.dto.EmployeeUpdatingDto;
import com.echoandrich.task.employee.repository.Employee;
import com.echoandrich.task.employee.repository.EmployeeRepository;
import com.echoandrich.task.job.Job;
import com.echoandrich.task.job.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.echoandrich.task.department.constants.DepartmentConstants.NOT_EXISTING_DEPARTMENT_MESSAGE;
import static com.echoandrich.task.employee.constants.EmployeeConstants.*;
import static com.echoandrich.task.job.JobConstants.NOT_EXISTING_JOB_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @DisplayName("[특정 사원 조회 : 사원이 존재하는 경우]")
    @Test
    void findById() {

        //given
        Integer employeeId = 100;

        Employee employee = SetupEmployee.employee(employeeId);

        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        //when
        EmployeeDto employeeDto = employeeService.findById(employeeId);

        //then
        assertThat(employeeDto.getEmployeeId()).isEqualTo(employee.getEmployeeId());
        assertThat(employeeDto.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeDto.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeDto.getEmail()).isEqualTo(employee.getEmail());
        assertThat(employeeDto.getPhoneNumber()).isEqualTo(employee.getPhoneNumber());
        assertThat(employeeDto.getHireDate()).isEqualTo(employee.getHireDate());
        assertThat(employeeDto.getJobId()).isEqualTo(employee.getJobId());
        assertThat(employeeDto.getSalary()).isEqualTo(employee.getSalary());
        assertThat(employeeDto.getCommissionPct()).isEqualTo(employee.getCommissionPct());
        assertThat(employeeDto.getManagerId()).isEqualTo(employee.getManagerId());
        assertThat(employeeDto.getDepartmentId()).isEqualTo(employee.getDepartmentId());
    }

    @DisplayName("[특정 사원 조회 : 사원이 존재하지 않는 경우]")
    @Test
    void findByIdWithInvalidId() {

        //given
        Integer employeeId = 100;

        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() ->employeeService.findById(employeeId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[특정 사원 정보 업데이트 : 존재하지 않는 사원일 경우]")
    @Test
    void updateWithInvalidEmployee() {

        //given
        Integer employeeId = 100;
        EmployeeUpdatingDto updatingConditions = new EmployeeUpdatingDto();

        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> employeeService.update(employeeId, updatingConditions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EXISTING_EMPLOYEE_MESSAGE);
    }

    @DisplayName("[특정 사원 정보 업데이트 : 특정 사원의 이름, 이메일, 전화번호, 급여 등 기본적인 값을 수정할 경우]")
    @MethodSource("com.echoandrich.task.employee.SetupEmployee#getDefaultUpdatingConditions")
    @ParameterizedTest
    void updateWithDefaultUpdatingConditions(EmployeeUpdatingDto updatingConditions) {

        //given
        Integer employeeId = 100;
        Employee employee = SetupEmployee.employee(employeeId);

        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        EmployeeDto employeeDto = employeeService.update(employeeId, updatingConditions);

        //then
        if(updatingConditions.getFirstName() != null) {
            assertThat(employeeDto.getFirstName()).isEqualTo(updatingConditions.getFirstName());
        }
        if(updatingConditions.getLastName() != null) {
            assertThat(employeeDto.getLastName()).isEqualTo(updatingConditions.getLastName());
        }
        if(updatingConditions.getEmail() != null) {
            assertThat(employeeDto.getEmail()).isEqualTo(updatingConditions.getEmail());
        }
        if(updatingConditions.getPhoneNumber() != null) {
            assertThat(employeeDto.getPhoneNumber()).isEqualTo(updatingConditions.getPhoneNumber());
        }
        if(updatingConditions.getSalary() != null) {
            assertThat(employeeDto.getSalary()).isEqualTo(updatingConditions.getSalary());
        }
        if(updatingConditions.getCommissionPct() != null) {
            assertThat(employeeDto.getCommissionPct()).isEqualTo(updatingConditions.getCommissionPct());
        }
    }

    @DisplayName("[특정 사원 정보 업데이트 : 업데이트하려는 직업이 존재하지 않는 값일 경우]")
    @Test
    void updateWithInvalidJobId() {

        //given
        Integer employeeId = 100;
        String jobId = "notExistingJobId";
        EmployeeUpdatingDto updatingConditions = new EmployeeUpdatingDto();
        updatingConditions.setJobId(jobId);

        Employee employee = SetupEmployee.employee(employeeId);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
        given(jobRepository.findById(jobId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() ->employeeService.update(employeeId, updatingConditions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EXISTING_JOB_MESSAGE);
    }

    @DisplayName("[특정 사원 정보 업데이트 : 업데이트하려는 직업의 최소 급여보다 현재 급여가 작은 경우]")
    @Test
    void updateWithTooSmallSalary() {

        //given
        Integer employeeId = 100;
        String jobId = "notExistingJobId";
        EmployeeUpdatingDto updatingConditions = new EmployeeUpdatingDto();
        updatingConditions.setJobId(jobId);

        Employee employee = SetupEmployee.employee(employeeId);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        Job job = SetupEmployee.job(jobId);
        job.setMinSalary(BigDecimal.valueOf(employee.getSalary().doubleValue() + 100));
        given(jobRepository.findById(jobId)).willReturn(Optional.of(job));

        //when & then
        assertThatThrownBy(() ->employeeService.update(employeeId, updatingConditions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(SALARY_TOO_SMALL_MESSAGE);
    }

    @DisplayName("[특정 사원 정보 업데이트 : 업데이트하려는 직업의 최대 급여보다 현재 급여가 큰 경우]")
    @Test
    void updateWithTooBigSalary() {

        //given
        Integer employeeId = 100;
        String jobId = "notExistingJobId";
        EmployeeUpdatingDto updatingConditions = new EmployeeUpdatingDto();
        updatingConditions.setJobId(jobId);

        Employee employee = SetupEmployee.employee(employeeId);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        Job job = SetupEmployee.job(jobId);
        job.setMinSalary(BigDecimal.valueOf(employee.getSalary().doubleValue() - 500));
        job.setMaxSalary(BigDecimal.valueOf(employee.getSalary().doubleValue() - 100));
        given(jobRepository.findById(jobId)).willReturn(Optional.of(job));

        //when & then
        assertThatThrownBy(() ->employeeService.update(employeeId, updatingConditions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(SALARY_TOO_BIG_MESSAGE);
    }

    @DisplayName("[특정 사원 정보 업데이트 : 업데이트하려는 직업의 조건이 만족된 경우]")
    @Test
    void updateWithValidJobConditions() {

        //given
        Integer employeeId = 100;
        String jobId = "notExistingJobId";
        EmployeeUpdatingDto updatingConditions = new EmployeeUpdatingDto();
        updatingConditions.setJobId(jobId);

        Employee employee = SetupEmployee.employee(employeeId);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        Job job = SetupEmployee.job(jobId);
        job.setMinSalary(BigDecimal.valueOf(employee.getSalary().doubleValue() - 500));
        job.setMaxSalary(BigDecimal.valueOf(employee.getSalary().doubleValue() + 100));
        given(jobRepository.findById(jobId)).willReturn(Optional.of(job));

        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        EmployeeDto employeeDto = employeeService.update(employeeId, updatingConditions);

        // then
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getJobId()).isEqualTo(job.getJobId());
    }

    @DisplayName("[특정 사원 정보 업데이트 : 업데이트하려는 부서가 존재하지 않는 경우")
    @Test
    void updateWithInvalidDepartmentId() {

        //given
        Integer employeeId = 100;
        Integer departmentId = 50;
        EmployeeUpdatingDto updatingConditions = new EmployeeUpdatingDto();
        updatingConditions.setDepartmentId(departmentId);

        Employee employee = SetupEmployee.employee(employeeId);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        given(departmentRepository.findById(departmentId)).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() ->employeeService.update(employeeId, updatingConditions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NOT_EXISTING_DEPARTMENT_MESSAGE);
    }

    @DisplayName("[특정 사원 정보 업데이트 : 업데이트하려는 부서가 존재하지 않는 경우")
    @Test
    void updateWithValidDepartmentId() {

        //given
        Integer employeeId = 100;
        Integer departmentId = 50;
        EmployeeUpdatingDto updatingConditions = new EmployeeUpdatingDto();
        updatingConditions.setDepartmentId(departmentId);

        Employee employee = SetupEmployee.employee(employeeId);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        Department department = SetupEmployee.department(departmentId);
        given(departmentRepository.findById(departmentId)).willReturn(Optional.of(department));

        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        EmployeeDto employeeDto = employeeService.update(employeeId, updatingConditions);

        //then
        assertThat(employeeDto.getDepartmentId()).isEqualTo(department.getDepartmentId());
        assertThat(employeeDto.getManagerId()).isEqualTo(department.getManagerId());
    }
}