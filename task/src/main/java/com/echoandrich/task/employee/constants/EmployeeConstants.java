package com.echoandrich.task.employee.constants;

public class EmployeeConstants {

    public static final String EMPLOYEE_PATH_URI = "/api/employees/{employeeId}";
    public static final String DEPARTMENT_SALARY_INCREASE_PATH_URI = "/api/employees/departments/{departmentId}/salaryIncrease";

    public static final Integer DEFAULT_EMPLOYEE_PAGING_SIZE = 100;

    public static final String NOT_EXISTING_EMPLOYEE_MESSAGE = "존재하지 않는 employee 입니다.";
    public static final String SALARY_TOO_SMALL_MESSAGE = "변경하려는 부서의 최소 급여에 미치지 못합니다.";
    public static final String SALARY_TOO_BIG_MESSAGE = "변경하려는 부서의 최대 급여를 초과합니다.";

}
