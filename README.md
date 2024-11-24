# echo-rich 

## 요구 사항 분석

1. 특정 사원의 현재 정보 조회 가능한 API 구현
   - employee_id 로 employee 조회


2. 특정 사원의 이력 정보 조회 가능한 API 구현
    - employee_id 로 job_history 조회
    - start_date 최신 순으로 페이징 처리 (unique index)


3. 사원 정보를 업데이트 할 수 있는 API 구현
   - 특정 employee_id 에 대해 사원 정보를 수정
   - 일부분만 수정 가능하도록


4. 부서 및 위치 정보 조회 가능한 API 구현
    - 전체 부서 및 위치 조회
    - department_id 내림차순으로 페이징 처리 


5. 특정 부서의 급여를 특정 비율로 인상 할 수 있는 할 수 있는 API 구현
   - 특정 부서에 해당하는 employees 전체 조회
   - 해당 employees 에 salary 를 특정 비율로 증가


6. RDBMS 스키마와 별개로 공공 데이터 포털(www.data.go.kr) 등에서 임의의 API 선택 후 조회 가능하도록 커스터마이징 된 API 구현
   - ktx 열차 조회 기능
   - 출발지, 도착지 입력하면 데이터 조회하는 기능 개발