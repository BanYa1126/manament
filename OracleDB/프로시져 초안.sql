create or replace NONEDITIONABLE PROCEDURE 기숙사_방이동_프로시져 (
    p_학번 IN 학생.학번%TYPE
)
IS
    v_방번호 기숙사.방번호%TYPE;
    v_퇴사일 DATE;
BEGIN
    -- 1. 학생이 배정된 방번호와 기존 퇴실일 조회
    SELECT 방번호, 퇴사일
    INTO v_방번호, v_퇴사일
    FROM 학생
    WHERE 학번 = p_학번 AND 방번호 IS NOT NULL;

    -- 2. 학생 테이블에서 방번호 초기화 (기숙사에서 퇴실)
    UPDATE 학생
    SET 방번호 = NULL
    WHERE 학번 = p_학번;

    -- 3. 방 재 배치 호출
    기숙사_입사_프로시져(p_학번);

    -- 4. 방 재배치 후 퇴실일 업데이트
    UPDATE 학생
    SET 퇴사일 = v_퇴사일 -- 기존 퇴실일 복원
    WHERE 학번 = p_학번;

    -- 5. 결과 메시지 출력
    DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생의 방이동이 완료되었습니다.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- 학생이 기숙사에 배정되지 않았을 때 처리
        DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생은 기숙사에 배정되지 않았습니다.');
    WHEN OTHERS THEN
        RAISE;
END 기숙사_방이동_프로시져;
/

create or replace NONEDITIONABLE PROCEDURE 기숙사_입사_프로시져 (
    p_학번 IN 학생.학번%TYPE
)
IS
    v_방번호 기숙사.방번호%TYPE;
    v_현재_방번호 학생.방번호%TYPE;
BEGIN
    -- 1. 해당 학생의 현재 방번호 확인
    SELECT 방번호
    INTO v_현재_방번호
    FROM 학생
    WHERE 학번 = p_학번;

    -- 2. 이미 기숙사에 배정된 경우 처리
    IF v_현재_방번호 IS NOT NULL THEN
        DBMS_OUTPUT.PUT_LINE('입사가 불가능합니다. 학번 ' || p_학번 || '번 학생은 이미 ' || v_현재_방번호 || '번 방에 배정되어 있습니다.');
        RETURN; -- 프로시저 종료
    END IF;

    -- 3. 배정 가능한 기숙사 중 랜덤 선택
    BEGIN
        SELECT 방번호
        INTO v_방번호
        FROM (
            SELECT 방번호
            FROM 기숙사
            WHERE 배정인원 < 2
            ORDER BY DBMS_RANDOM.VALUE
        )
        WHERE ROWNUM = 1;

        -- 4. 학생 테이블에 기숙사 방번호, 외박횟수, 출입여부 업데이트
        UPDATE 학생
        SET 방번호 = v_방번호,
            입사일 = TRUNC(SYSDATE),
            외박횟수 = NVL(외박횟수, 15), -- 외박횟수가 NULL일 경우 기본값 15 설정
            출입여부 = NVL(출입여부, 0)  -- 출입여부가 NULL일 경우 기본값 0 설정
        WHERE 학번 = p_학번;

        -- 5. 기숙사 테이블의 배정인원 증가
        UPDATE 기숙사
        SET 배정인원 = 배정인원 + 1
        WHERE 방번호 = v_방번호;

        -- 6. 결과 메시지 출력
        DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생이 기숙사 ' || v_방번호 || '번 방에 배정되었습니다.');

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- 배정 가능한 기숙사가 없을 때 예외 처리
            DBMS_OUTPUT.PUT_LINE('배정 가능한 기숙사가 없습니다.');
    END;

EXCEPTION
    WHEN OTHERS THEN
        RAISE;
END 기숙사_입사_프로시져;
/

create or replace NONEDITIONABLE PROCEDURE 기숙사_퇴실_프로시져 (
    p_학번 IN 학생.학번%TYPE
)
IS
    v_방번호 기숙사.방번호%TYPE;
BEGIN
    -- 1. 학생이 배정된 방번호 조회
    SELECT 방번호
    INTO v_방번호
    FROM 학생
    WHERE 학번 = p_학번 AND 방번호 IS NOT NULL;

    -- 2. 학생 테이블에서 방번호 초기화 (기숙사에서 퇴실)
    UPDATE 학생
    SET 방번호 = NULL,
        퇴사일 = NULL,
        입사일 = NULL,
        출입여부= NULL
    WHERE 학번 = p_학번;


    -- 4. 결과 메시지 출력
    DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생이 기숙사 ' || v_방번호 || '번 방에서 퇴실되었습니다.');
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- 학생이 기숙사에 배정되지 않았을 때 처리
        DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생은 기숙사에 배정되지 않았습니다.');
    WHEN OTHERS THEN
        RAISE;
END 기숙사_퇴실_프로시져;
/

create or replace NONEDITIONABLE PROCEDURE 당직_로테이션_프로시져 IS
    v_현재시간 DATE := SYSDATE;
    v_현재조 VARCHAR2(10); -- 현재 근무 조
    v_근무형태 VARCHAR2(10); -- 평일/주말
BEGIN
    -- 1. 현재 시간에 따른 근무 조 및 근무 형태 설정
    SELECT CASE 
             -- 평일 조 설정
             WHEN TO_CHAR(v_현재시간, 'D') NOT IN ('1', '7') THEN
               CASE 
                 WHEN TO_NUMBER(TO_CHAR(v_현재시간, 'HH24')) BETWEEN 0 AND 7 THEN '1조'
                 WHEN TO_NUMBER(TO_CHAR(v_현재시간, 'HH24')) BETWEEN 8 AND 15 THEN '2조'
                 ELSE '3조'
               END
             -- 주말 조 설정
             ELSE
               CASE 
                 WHEN TO_NUMBER(TO_CHAR(v_현재시간, 'HH24')) BETWEEN 0 AND 11 THEN '1조'
                 ELSE '2조'
               END
           END AS 현재조,
           CASE 
             WHEN TO_CHAR(v_현재시간, 'D') NOT IN ('1', '7') THEN '평일'
             ELSE '주말'
           END AS 근무형태
    INTO v_현재조, v_근무형태
    FROM DUAL;

    -- 2. 모든 관리자의 당직 상태 초기화
    UPDATE 관리자
    SET 당직 = 0;

    -- 3. 현재 조에 맞는 관리자를 당직으로 설정
    UPDATE 관리자
    SET 당직 = 1
    WHERE 근무형태 = v_근무형태 AND 근무시간대 = v_현재조;

    -- 메시지 출력
    DBMS_OUTPUT.PUT_LINE('현재 시간: ' || TO_CHAR(v_현재시간, 'YYYY-MM-DD HH24:MI:SS'));
    DBMS_OUTPUT.PUT_LINE('현재 근무 형태: ' || v_근무형태 || ', 현재 조: ' || v_현재조);
    DBMS_OUTPUT.PUT_LINE('당직 관리자가 업데이트되었습니다.');
END 당직_로테이션_프로시져;
/

create or replace NONEDITIONABLE PROCEDURE 상벌점_기록_프로시져 (
    p_방번호 IN 학생.방번호%TYPE,
    p_점수 IN 상벌점.점수%TYPE DEFAULT 0 -- 기본값 0으로 설정
)
IS
    v_학번 학생.학번%TYPE;
    v_존재여부 NUMBER;
BEGIN
    -- 1. 방번호가 학생 테이블에 존재하는지 확인하고 학번 조회
    BEGIN
        SELECT 학번
        INTO v_학번
        FROM 학생
        WHERE 방번호 = p_방번호;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- 방번호가 학생 테이블에 없으면 오류 발생
            DBMS_OUTPUT.PUT_LINE('학생 ' || v_학번 || '번은 기숙사생이 아닙니다.');
            RAISE_APPLICATION_ERROR(-20990, '이 학생은 기숙사생이 아닙니다.');
    END;

    -- 2. 상벌점 테이블에 학번이 존재하는지 확인
    SELECT COUNT(*)
    INTO v_존재여부
    FROM 상벌점
    WHERE 학번 = v_학번;

    -- 3. 조건에 따라 상벌점 추가 또는 업데이트
    IF v_존재여부 > 0 THEN
        -- 상벌점 테이블에 학번이 존재하면 점수 업데이트
        UPDATE 상벌점
        SET 점수 = 점수 + p_점수
        WHERE 학번 = v_학번;

        -- 결과 메시지 출력
        DBMS_OUTPUT.PUT_LINE('학번 ' || v_학번 || '번 학생의 상벌점이 ' || p_점수 || '점 추가되었습니다.');
    ELSE
        -- 상벌점 테이블에 학번이 없으면 새 레코드 삽입
        INSERT INTO 상벌점 (학번, 점수)
        VALUES (v_학번, p_점수);

        -- 결과 메시지 출력
        DBMS_OUTPUT.PUT_LINE('학번 ' || v_학번 || '번 학생의 상벌점이 새로 추가되었습니다. 점수: ' || p_점수);
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        RAISE;
END 상벌점_기록_프로시져;
/

create or replace NONEDITIONABLE PROCEDURE 외박_신청_취소_프로시져 (
    p_학번 IN 학생.학번%TYPE
)
IS
    v_출입시간 TIMESTAMP; -- 최근 외출 기록의 시간
    v_외박여부 NUMBER;    -- 외박 여부 확인 변수
    v_신청날짜 DATE;      -- 외출 기록의 날짜
BEGIN
    -- 1. 최근 외출 기록의 시간, 외박 여부, 신청 날짜 가져오기
    BEGIN
        SELECT 시간, 외박여부, TRUNC(시간)
        INTO v_출입시간, v_외박여부, v_신청날짜
        FROM 출입
        WHERE 학번 = p_학번 AND 상태 = '외출'
        ORDER BY 시간 DESC
        FETCH FIRST 1 ROWS ONLY;

        -- 2. 외박 여부가 1이 아닌 경우 예외 발생
        IF v_외박여부 != 1 THEN
            RAISE_APPLICATION_ERROR(
                -20002,
                '외박 신청 취소가 불가능합니다. 학번 ' || p_학번 || '번 학생의 최근 외출 기록은 외박으로 처리되지 않았습니다.'
            );
        END IF;

        -- 3. 신청 날짜가 오늘이 아닌 경우 예외 발생
        IF v_신청날짜 != TRUNC(SYSDATE) THEN
            RAISE_APPLICATION_ERROR(
                -20003,
                '외박 신청 취소가 불가능합니다. 학번 ' || p_학번 || '번 학생의 외박 신청은 당일이 아닙니다.'
            );
        END IF;

        -- 4. 외박여부를 0으로 업데이트
        UPDATE 출입
        SET 외박여부 = 0
        WHERE 학번 = p_학번 AND 시간 = v_출입시간;

        -- 5. 학생 테이블의 외박횟수 복구
        UPDATE 학생
        SET 외박횟수 = 외박횟수 + 1
        WHERE 학번 = p_학번;

        -- 결과 메시지 출력
        DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생의 외박 신청이 취소되었습니다.');
        DBMS_OUTPUT.PUT_LINE('취소된 외박 기록 시간: ' || v_출입시간);

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- 최근 외출 기록이 없는 경우 예외 처리
            RAISE_APPLICATION_ERROR(
                -20001,
                '외박 신청 취소가 불가능합니다. 학번 ' || p_학번 || '번 학생의 외출 기록이 없습니다.'
            );
    END;

EXCEPTION
    WHEN OTHERS THEN
        -- 기타 예외 처리
        DBMS_OUTPUT.PUT_LINE('오류 발생: ' || SQLERRM);
END 외박_신청_취소_프로시져;
/

create or replace NONEDITIONABLE PROCEDURE 외박_신청_프로시져 (
    p_학번 IN 학생.학번%TYPE
)
IS
    v_외박횟수 학생.외박횟수%TYPE; -- 학생의 현재 외박횟수
    v_출입시간 TIMESTAMP;          -- 최근 외출 기록 시간
    v_외박여부 NUMBER;             -- 외박 여부 확인 변수
BEGIN
    -- 1. 학생의 외박 횟수 확인
    SELECT 외박횟수
    INTO v_외박횟수
    FROM 학생
    WHERE 학번 = p_학번;

    -- 2. 외박 횟수가 0회이면 신청 불가
    IF v_외박횟수 <= 0 THEN
        RAISE_APPLICATION_ERROR(
            -20001,
            '외박 신청이 불가능합니다. 학번 ' || p_학번 || '번 학생의 외박 횟수가 부족합니다.'
        );
    END IF;

    -- 3. 최근 외출 기록의 시간과 외박 여부 가져오기
    BEGIN
        SELECT 시간, 외박여부
        INTO v_출입시간, v_외박여부
        FROM 출입
        WHERE 학번 = p_학번 AND 상태 = '외출'
        ORDER BY 시간 DESC
        FETCH FIRST 1 ROWS ONLY;

        -- 4. 외박 여부가 이미 처리된 경우 예외 발생
        IF v_외박여부 = 1 THEN
            RAISE_APPLICATION_ERROR(
                -20002,
                '외박 신청이 불가능합니다. 학번 ' || p_학번 || 
                '번 학생의 최근 외출 기록은 이미 외박으로 처리되었습니다.'
            );
        END IF;

        -- 5. 해당 출입 기록의 외박 여부를 1로 업데이트
        UPDATE 출입
        SET 외박여부 = 1
        WHERE 학번 = p_학번 AND 시간 = v_출입시간;

        -- 6. 학생 테이블에서 외박 횟수 감소
        UPDATE 학생
        SET 외박횟수 = 외박횟수 - 1
        WHERE 학번 = p_학번;

        -- 결과 메시지 출력
        DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생의 외박 신청이 승인되었습니다.');
        DBMS_OUTPUT.PUT_LINE('최근 외출 시간: ' || v_출입시간 || '에 외박 여부가 1로 변경되었습니다.');

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- 최근 외출 기록이 없는 경우 예외 처리
            RAISE_APPLICATION_ERROR(
                -20003,
                '외박 신청이 불가능합니다. 학번 ' || p_학번 || '번 학생의 최근 외출 기록이 없습니다.'
            );
    END;

EXCEPTION
    WHEN OTHERS THEN
        -- 기타 예외 처리
        DBMS_OUTPUT.PUT_LINE('오류 발생: ' || SQLERRM);
END 외박_신청_프로시져;
/

create or replace NONEDITIONABLE PROCEDURE 외박초기화_프로시저 IS
BEGIN
    -- 1. 외박 횟수 초기화 (기존 외박 횟수가 있는 학생만)
    UPDATE 학생
    SET 외박횟수 = 15
    WHERE 외박횟수 IS NOT NULL AND 방번호 IS NOT NULL;

    -- 2. 퇴사한 학생 (방번호가 NULL인 경우)의 외박횟수를 NULL로 설정
    UPDATE 학생
    SET 외박횟수 = NULL
    WHERE 방번호 IS NULL;

    -- 메시지 출력 (옵션)
    DBMS_OUTPUT.PUT_LINE('외박 횟수가 초기화되었으며, 퇴사한 학생의 외박횟수는 NULL로 설정되었습니다.');
END 외박초기화_프로시저;
/

create or replace NONEDITIONABLE PROCEDURE 출입_기록_프로시져 (
    p_학번 IN 출입.학번%TYPE
)
IS
    v_관리자번호 관리자.관리자번호%TYPE; -- 현재 당직 중인 관리자 번호
    v_출입여부 학생.출입여부%TYPE;       -- 학생의 출입 여부 (0: 외출, 1: 기숙사 내)
    v_상태 출입.상태%TYPE;              -- 기록할 상태 ("출입" 또는 "외출")
BEGIN
    -- 1. 현재 당직 중인 관리자 조회
    SELECT 관리자번호
    INTO v_관리자번호
    FROM 관리자
    WHERE 당직 = 1
    FETCH FIRST 1 ROWS ONLY;

    -- 2. 학생의 출입여부 조회
    SELECT 출입여부
    INTO v_출입여부
    FROM 학생
    WHERE 학번 = p_학번;

    -- 3. 출입 여부에 따라 상태 결정
    IF v_출입여부 = 0 THEN
        v_상태 := '출입'; -- 외출 상태라면 "출입"으로 기록
    ELSE
        v_상태 := '외출'; -- 기숙사 내라면 "외출"로 기록
    END IF;

    -- 4. 출입 기록 추가
    INSERT INTO 출입 (시간, 상태, 외박여부, 학번, 관리자번호)
    VALUES (SYSTIMESTAMP, v_상태, 0, p_학번, v_관리자번호);

    -- 5. 학생 테이블의 출입여부 업데이트 (상태 반전)
    UPDATE 학생
    SET 출입여부 = CASE WHEN 출입여부 = 0 THEN 1 ELSE 0 END
    WHERE 학번 = p_학번;

    -- 메시지 출력
    DBMS_OUTPUT.PUT_LINE('학번 ' || p_학번 || '번 학생의 출입 상태가 "' || v_상태 || '"로 기록되었습니다.');
    DBMS_OUTPUT.PUT_LINE('관리자번호: ' || v_관리자번호);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- 예외 처리: 당직 관리자가 없거나 학번이 없는 경우
        RAISE_APPLICATION_ERROR(-20001, '출입 기록 중 오류 발생: 당직 관리자 또는 학번이 없습니다.');
    WHEN OTHERS THEN
        -- 기타 예외 처리
        DBMS_OUTPUT.PUT_LINE('오류 발생: ' || SQLERRM);
END 출입_기록_프로시져;
/

create or replace NONEDITIONABLE TRIGGER 상벌점_자동퇴출_트리거
AFTER INSERT OR UPDATE OF 점수 ON 상벌점
FOR EACH ROW
BEGIN
    -- 디버깅 메시지 추가
    DBMS_OUTPUT.PUT_LINE('상벌점_자동퇴출_트리거 실행: 학번=' || :NEW.학번 || ', 점수=' || :NEW.점수);

    -- 상벌점이 -15점 이하인 경우, 학생을 기숙사에서 퇴출
    IF :NEW.점수 <= -15 THEN
        BEGIN
            -- 학생 테이블에서 방번호를 NULL로 업데이트
            UPDATE 학생
            SET 방번호 = NULL
            WHERE 학번 = :NEW.학번;

            -- 성공 메시지 출력
            DBMS_OUTPUT.PUT_LINE('학번 ' || :NEW.학번 || '번 학생이 상벌점 ' || :NEW.점수 || '점으로 인해 기숙사에서 퇴출되었습니다.');
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                -- 학생이 기숙사에 배정되지 않았을 때 처리
                DBMS_OUTPUT.PUT_LINE('학번 ' || :NEW.학번 || '번 학생은 기숙사에 배정되지 않았습니다.');
        END;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        -- 예외 처리
        DBMS_OUTPUT.PUT_LINE('오류 발생: ' || SQLERRM);
        RAISE; -- 오류를 다시 발생시켜 실행 중단
END;
/

create or replace NONEDITIONABLE TRIGGER 학생_기숙사_입사_트리거
BEFORE UPDATE OF 방번호 ON 학생
FOR EACH ROW
DECLARE
    v_기존점수 상벌점.점수%TYPE; -- 기존 상벌점을 저장할 변수
BEGIN
    -- 방번호가 NULL에서 값으로 변경될 때만 실행
    IF :OLD.방번호 IS NULL AND :NEW.방번호 IS NOT NULL THEN
        BEGIN
            -- 1. 상벌점 확인
            SELECT 점수
            INTO v_기존점수
            FROM 상벌점
            WHERE 학번 = :NEW.학번;

            -- 2. 상벌점이 -15점 이하일 경우 입사 차단
            IF v_기존점수 <= -15 THEN
            DBMS_OUTPUT.PUT_LINE('학번 ' || :NEW.학번 || '번 학생의 벌점이 15점이상이므로 입사가 거부되었습니다.');
                RAISE_APPLICATION_ERROR(
                    -20001,
                    '입사가 불가능합니다. 학번 ' || :NEW.학번 || '번 학생의 기존 상벌점은 ' || v_기존점수 || '점입니다.'
                );
            END IF;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                -- 3. 상벌점이 없는 경우 기본값 추가
                INSERT INTO 상벌점 (학번, 점수)
                VALUES (:NEW.학번, 0);

                DBMS_OUTPUT.PUT_LINE('학번 ' || :NEW.학번 || '번 학생의 상벌점이 없어서 기본값 0으로 추가되었습니다.');
        END;
    END IF;
END 학생_기숙사_입사_트리거;
/

create or replace NONEDITIONABLE TRIGGER 학생_기숙사_퇴출_트리거
BEFORE UPDATE OF 방번호 ON 학생
FOR EACH ROW
WHEN (NEW.방번호 IS NULL AND OLD.방번호 IS NOT NULL)
DECLARE
    v_방번호 기숙사.방번호%TYPE;
BEGIN
    -- 디버깅 메시지 추가: 조건 충족 여부 확인
    DBMS_OUTPUT.PUT_LINE('트리거 실행: OLD 방번호=' || :OLD.방번호 || ', NEW 방번호=' || :NEW.방번호);

    v_방번호 := :OLD.방번호;

    -- 학생의 출입여부를 초기화
    :NEW.출입여부 := NULL;
    :NEW.입사일 := NULL;
    :NEW.퇴사일 := NULL;

    -- 기숙사 테이블의 배정인원 감소
    UPDATE 기숙사
    SET 배정인원 = 배정인원 - 1
    WHERE 방번호 = v_방번호;

    -- 성공 메시지 출력
    DBMS_OUTPUT.PUT_LINE('학번 ' || :NEW.학번 || '번 학생이 기숙사에서 퇴실되어 출입여부가 초기화되었습니다.');
    DBMS_OUTPUT.PUT_LINE('방번호 ' || v_방번호 || '에서 학번' ||':NEW.학번|| 학생이 퇴실하였습니다.');
EXCEPTION
    WHEN OTHERS THEN
        -- 예외 처리 및 오류 메시지 출력
        DBMS_OUTPUT.PUT_LINE('오류 발생: ' || SQLERRM);
        RAISE; -- 오류를 다시 발생시켜 실행 중단
END;
/


--스케줄러
BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
        job_name        => '외박초기화',
        job_type        => 'PLSQL_BLOCK',
        job_action      => 'BEGIN 외박초기화_프로시저; END;',
        start_date      => SYSDATE, -- 현재 시간 기준 시작
        repeat_interval => 'FREQ=MONTHLY; BYMONTHDAY=1', -- 매달 1일에 실행
        enabled         => TRUE
    );
END;
/

BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
        job_name        => '당직_로테이션_작업',
        job_type        => 'PLSQL_BLOCK',
        job_action      => 'BEGIN 당직_로테이션_프로시져; END;',
        start_date      => SYSTIMESTAMP, -- 현재 시간 기준 시작
        repeat_interval => 'FREQ=HOURLY; INTERVAL=1', -- 1시간마다 실행
        enabled         => TRUE
    );

    DBMS_OUTPUT.PUT_LINE('당직 로테이션 작업이 생성되었습니다.');
END;
/


BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
        job_name        => '퇴실_자동_스케줄러',
        job_type        => 'PLSQL_BLOCK',
        job_action      => '
            DECLARE
                CURSOR c_퇴사학생 IS
                    SELECT 학번
                    FROM 학생
                    WHERE TRUNC(퇴사일) = TRUNC(SYSDATE); -- 퇴사일이 오늘인 학생
            BEGIN
                -- 퇴사일이 오늘인 학생 처리
                FOR r_학생 IN c_퇴사학생 LOOP
                    BEGIN
                        -- 퇴실 프로시저 호출
                        기숙사_퇴실_프로시져(r_학생.학번);
                    EXCEPTION
                        WHEN OTHERS THEN
                            DBMS_OUTPUT.PUT_LINE(''학번 '' || r_학생.학번 || '' 처리 중 오류 발생: '' || SQLERRM);
                    END;
                END LOOP;
            END;
        ',
        start_date      => SYSTIMESTAMP,
        repeat_interval => 'FREQ=DAILY; BYHOUR=12; BYMINUTE=0; BYSECOND=0', -- 매일 오후 12시
        enabled         => TRUE
    );
END;
/
