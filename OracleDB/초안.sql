--ALTER session set "_ORACLE_SCRIPT"=true;
SET SERVEROUTPUT ON;


--DROP USER Admin CASCADE; -- 기존 사용자 삭제(현재 접속되어 있으면 삭제 안 됨)
--	-- CASCADE option : 관련 스키마 개체들도 함께 삭제.  Default는 No Action
--CREATE USER Admin IDENTIFIED BY 1234  -- 사용자 ID : Admin, 비밀번호 : 1234
--    DEFAULT TABLESPACE USERS
--    TEMPORARY TABLESPACE TEMP;
--GRANT connect, resource, dba TO Admin; -- 권한 부여

-- 테이블 삭제 (DROP) 문
DROP TABLE 출입 CASCADE CONSTRAINTS;
DROP TABLE 상벌점 CASCADE CONSTRAINTS;
DROP TABLE 학생 CASCADE CONSTRAINTS;
DROP TABLE 기숙사 CASCADE CONSTRAINTS;
DROP TABLE 관리자 CASCADE CONSTRAINTS;

-- 기숙사 테이블
CREATE TABLE 기숙사 (
    방번호 INT PRIMARY KEY, -- 기숙사 방 번호
    배정인원 INT NOT NULL -- 방에 배정된 인원 수
);

-- 학생 테이블
CREATE TABLE 학생 (
    학번 INT PRIMARY KEY, -- 학생 고유번호
    이름 VARCHAR(50) NOT NULL, -- 학생 이름
    주소 VARCHAR(100), -- 학생 주소
    연락처 VARCHAR(20), -- 학생 연락처
    입사일 DATE, -- 기숙사 입사일
    퇴사일 DATE, -- 기숙사 퇴사일
    출입여부 NUMBER(1) CHECK (출입여부 IN (0, 1)), -- 0: 외출, 1: 출입
    외박횟수 INT, -- 외박 횟수
    방번호 INT, -- 기숙사 방 번호
    FOREIGN KEY (방번호) REFERENCES 기숙사(방번호)
);

-- 상벌점 테이블
CREATE TABLE 상벌점 (
    학번 INT PRIMARY KEY, -- 학생 고유번호 (기본키)
    점수 INT NOT NULL, -- 상벌점 점수
    FOREIGN KEY (학번) REFERENCES 학생(학번)
);

-- 관리자 테이블
CREATE TABLE 관리자 (
    관리자번호 INT PRIMARY KEY, -- 관리자 고유번호
    이름 VARCHAR(50) NOT NULL, -- 관리자 이름
    나이 INT, -- 관리자 나이
    입사년도 INT, -- 입사 년도
    근무년수 INT, -- 근무 경력(년)
    사무실전화번호 VARCHAR(20), -- 사무실 전화번호
    사무실이메일 VARCHAR(50), -- 사무실 이메일
    당직 NUMBER(1) CHECK (당직 IN (0, 1)), -- 0: 비당직, 1: 당직
    근무형태 VARCHAR(10) NOT NULL, -- 근무 형태: "평일", "주말"
    근무시간대 VARCHAR(10) NOT NULL -- 근무 시간대: "1조", "2조", "3조"
);

-- 출입 테이블
CREATE TABLE 출입 (
    시간 TIMESTAMP PRIMARY KEY, -- 출입 또는 외출 시간
    상태 VARCHAR(10) NOT NULL, -- 상태: "출입" 또는 "외출"
    외박여부 NUMBER(1) CHECK (외박여부 IN (0, 1)), -- 0: FALSE, 1: TRUE
    학번 INT NOT NULL, -- 학생 고유번호 (외래키)
    관리자번호 INT, -- 출입 기록을 관리한 관리자 (외래키)
    FOREIGN KEY (학번) REFERENCES 학생(학번),
    FOREIGN KEY (관리자번호) REFERENCES 관리자(관리자번호)
);


-- 관리자 기본 데이터 삽입
INSERT INTO 관리자 (
    관리자번호, 이름, 나이, 입사년도, 근무년수, 사무실전화번호, 사무실이메일, 근무형태, 근무시간대
) VALUES (1, '김철수', 45, 2010, 14, '02-1234-5678', 'office1@example.com', '평일', '1조');

INSERT INTO 관리자 (
    관리자번호, 이름, 나이, 입사년도, 근무년수, 사무실전화번호, 사무실이메일, 근무형태, 근무시간대
) VALUES (2, '박영희', 38, 2015, 9, '02-1234-5678', 'office2@example.com', '평일', '2조');

INSERT INTO 관리자 (
    관리자번호, 이름, 나이, 입사년도, 근무년수, 사무실전화번호, 사무실이메일, 근무형태, 근무시간대
) VALUES (3, '이민호', 32, 2020, 4, '02-1234-5678', 'office3@example.com', '평일', '3조');

INSERT INTO 관리자 (
    관리자번호, 이름, 나이, 입사년도, 근무년수, 사무실전화번호, 사무실이메일, 근무형태, 근무시간대
) VALUES (4, '정수연', 40, 2012, 12, '02-1234-5678', 'office4@example.com', '주말', '1조');

INSERT INTO 관리자 (
    관리자번호, 이름, 나이, 입사년도, 근무년수, 사무실전화번호, 사무실이메일, 근무형태, 근무시간대
) VALUES (5, '최다정', 35, 2018, 6, '02-1234-5678', 'office5@example.com', '주말', '2조');


-- 학생 기본 데이터 삽입
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (1, '박도윤', '경상북도 포항시 26-13', '010-2594-9671');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (2, '김예준', '인천시 연수구 38-2', '010-6882-2663');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (3, '조지우', '경기도 수원시 46-50', '010-4392-6168');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (4, '박예준', '광주시 서구 69-43', '010-6393-4620');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (5, '김주원', '대구시 북구 31-16', '010-9762-8635');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (6, '정도윤', '대구시 북구 36-48', '010-2092-8054');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (7, '박민준', '대전시 유성구 33-35', '010-8443-6482');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (8, '이하준', '광주시 서구 99-49', '010-6969-7910');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (9, '임하준', '광주시 서구 47-14', '010-7013-2926');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (10, '최현우', '대전시 유성구 7-9', '010-3729-6529');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (11, '이도현', '서울시 강남구 4-38', '010-1454-3249');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (12, '장도윤', '서울시 강남구 48-1', '010-8548-5433');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (13, '정지우', '인천시 연수구 45-37', '010-9980-5384');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (14, '최지우', '대구시 북구 97-11', '010-1088-7772');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (15, '윤주원', '대전시 유성구 86-20', '010-6945-7149');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (16, '강주원', '대전시 유성구 33-20', '010-9157-5054');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (17, '이하준', '경기도 수원시 16-7', '010-5477-3467');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (18, '강주원', '경상북도 포항시 81-7', '010-9991-1546');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (19, '김도현', '경상북도 포항시 61-17', '010-7909-2813');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (20, '윤예준', '경기도 수원시 53-49', '010-5633-9470');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (21, '강도윤', '서울시 강남구 49-50', '010-7738-9138');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (22, '김도현', '대구시 북구 30-16', '010-4603-5780');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (23, '조도윤', '인천시 연수구 61-45', '010-6601-9046');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (24, '조하준', '서울시 강남구 49-4', '010-9409-1964');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (25, '임예준', '대구시 북구 26-27', '010-9802-5822');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (26, '김지우', '인천시 연수구 19-41', '010-1306-8968');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (27, '이도윤', '경기도 수원시 35-10', '010-1461-6646');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (28, '이건우', '인천시 연수구 24-13', '010-9248-6103');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (29, '조도윤', '광주시 서구 99-44', '010-2561-2559');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (30, '임현우', '광주시 서구 5-18', '010-1948-6414');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (31, '김지우', '대전시 유성구 13-42', '010-5708-5958');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (32, '이하준', '서울시 강남구 86-46', '010-7569-4318');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (33, '강지우', '대전시 유성구 68-47', '010-5503-2902');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (34, '정건우', '경상북도 포항시 70-41', '010-7043-7978');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (35, '정건우', '대전시 유성구 61-36', '010-5885-8377');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (36, '이서준', '경상북도 포항시 66-21', '010-1688-7298');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (37, '최건우', '대구시 북구 80-44', '010-1153-7846');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (38, '정도윤', '광주시 서구 55-14', '010-7081-3371');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (39, '조현우', '광주시 서구 64-14', '010-7505-4561');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (40, '김현우', '경기도 수원시 36-44', '010-9938-7444');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (41, '박하준', '대구시 북구 27-28', '010-5244-9021');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (42, '최주원', '인천시 연수구 48-33', '010-6682-1515');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (43, '이하준', '대전시 유성구 78-27', '010-1505-9177');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (44, '박하준', '서울시 강남구 15-21', '010-1423-3299');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (45, '장현우', '경기도 수원시 79-43', '010-9897-9199');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (46, '이현우', '광주시 서구 36-21', '010-7887-3675');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (47, '최서준', '광주시 서구 46-9', '010-4650-5051');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (48, '윤민준', '경기도 수원시 29-23', '010-4148-3464');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (49, '장주원', '경상북도 포항시 32-4', '010-1167-3050');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (50, '정건우', '부산시 해운대구 15-19', '010-8322-2449');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (51, '이도현', '경기도 수원시 58-41', '010-8553-5680');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (52, '조도윤', '부산시 해운대구 90-24', '010-9145-2166');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (53, '정민준', '인천시 연수구 15-15', '010-5981-6747');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (54, '조하준', '서울시 강남구 79-16', '010-1173-8790');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (55, '최지우', '부산시 해운대구 43-22', '010-1446-3508');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (56, '장하준', '경상북도 포항시 29-47', '010-4672-2167');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (57, '조지우', '경상북도 포항시 20-15', '010-6904-6362');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (58, '강지우', '대전시 유성구 45-14', '010-4532-9990');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (59, '임서준', '광주시 서구 44-1', '010-3536-6503');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (60, '조민준', '부산시 해운대구 28-4', '010-1435-3761');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (61, '임도현', '대구시 북구 6-12', '010-1855-4137');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (62, '김현우', '대구시 북구 34-1', '010-7083-2677');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (63, '최주원', '대구시 북구 37-39', '010-3349-7544');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (64, '김하준', '경기도 수원시 30-33', '010-3434-2756');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (65, '임주원', '서울시 강남구 7-45', '010-6173-1581');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (66, '조주원', '서울시 강남구 84-44', '010-2938-5763');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (67, '정민준', '경상북도 포항시 100-35', '010-3836-7003');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (68, '이서준', '경상북도 포항시 52-19', '010-8059-7451');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (69, '조도현', '광주시 서구 34-38', '010-1411-7617');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (70, '장주원', '서울시 강남구 90-25', '010-9983-3194');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (71, '조도윤', '경상북도 포항시 60-32', '010-2806-6183');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (72, '장주원', '서울시 강남구 76-14', '010-7867-7321');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (73, '조주원', '부산시 해운대구 74-47', '010-5845-9917');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (74, '조예준', '서울시 강남구 8-6', '010-6897-8706');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (75, '정도윤', '광주시 서구 57-32', '010-1307-6840');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (76, '윤지우', '경기도 수원시 3-3', '010-4403-3499');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (77, '이예준', '대구시 북구 71-14', '010-5351-9740');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (78, '김건우', '부산시 해운대구 17-8', '010-8246-3053');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (79, '임현우', '대구시 북구 71-43', '010-8226-8586');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (80, '강지우', '부산시 해운대구 44-16', '010-7544-4035');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (81, '최주원', '경기도 수원시 18-44', '010-3037-6832');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (82, '정지우', '부산시 해운대구 1-48', '010-8452-7095');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (83, '김서준', '경상북도 포항시 27-43', '010-5635-3790');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (84, '최도윤', '경기도 수원시 27-43', '010-1218-8299');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (85, '윤도현', '대구시 북구 78-11', '010-3674-5759');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (86, '임예준', '인천시 연수구 76-18', '010-3946-6865');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (87, '강주원', '인천시 연수구 90-27', '010-3295-4313');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (88, '조서준', '경상북도 포항시 8-45', '010-6322-1817');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (89, '조도윤', '인천시 연수구 26-9', '010-9051-4016');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (90, '강예준', '서울시 강남구 82-47', '010-1497-5935');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (91, '이현우', '경기도 수원시 42-10', '010-1039-8439');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (92, '정예준', '경상북도 포항시 25-46', '010-9231-8499');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (93, '정도윤', '대구시 북구 52-41', '010-1568-2927');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (94, '최지우', '대전시 유성구 84-47', '010-4946-9623');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (95, '임지우', '서울시 강남구 27-40', '010-8404-1047');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (96, '김예준', '경기도 수원시 19-33', '010-9822-8889');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (97, '장하준', '대구시 북구 37-30', '010-2186-1059');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (98, '박건우', '서울시 강남구 51-40', '010-6288-1985');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (99, '최도현', '경기도 수원시 70-23', '010-8906-3988');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (100, '김서준', '서울시 강남구 55-12', '010-5497-1736');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (101, '강서준', '경상북도 포항시 27-4', '010-3911-1321');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (102, '조지우', '경상북도 포항시 15-30', '010-2845-6533');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (103, '박지우', '인천시 연수구 35-24', '010-1922-6201');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (104, '강도윤', '경상북도 포항시 54-5', '010-3997-3921');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (105, '박건우', '대구시 북구 83-17', '010-2944-2545');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (106, '최주원', '인천시 연수구 77-39', '010-8476-2427');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (107, '임건우', '대구시 북구 29-31', '010-3429-3519');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (108, '윤도현', '광주시 서구 16-39', '010-9581-8337');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (109, '조민준', '경상북도 포항시 12-37', '010-2306-7688');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (110, '장민준', '광주시 서구 78-35', '010-5458-6529');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (111, '장건우', '광주시 서구 23-2', '010-6912-7086');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (112, '임현우', '인천시 연수구 76-9', '010-7911-5527');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (113, '임서준', '대구시 북구 1-4', '010-1117-5135');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (114, '조서준', '광주시 서구 48-28', '010-9140-1789');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (115, '정도현', '경상북도 포항시 90-7', '010-1781-8021');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (116, '최서준', '경기도 수원시 74-5', '010-4755-6357');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (117, '장지우', '경기도 수원시 89-31', '010-3511-8879');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (118, '박도윤', '경상북도 포항시 55-33', '010-5431-7341');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (119, '김건우', '부산시 해운대구 99-6', '010-2461-1656');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (120, '정서준', '부산시 해운대구 41-4', '010-5687-6649');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (121, '정도윤', '부산시 해운대구 37-13', '010-7593-3551');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (122, '김주원', '부산시 해운대구 51-12', '010-7097-6127');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (123, '조서준', '부산시 해운대구 6-3', '010-1895-8958');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (124, '김주원', '서울시 강남구 6-44', '010-3261-1311');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (125, '임도윤', '인천시 연수구 22-7', '010-9733-3822');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (126, '박지우', '부산시 해운대구 81-32', '010-2748-6484');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (127, '이서준', '인천시 연수구 37-15', '010-7854-6483');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (128, '김주원', '대구시 북구 13-41', '010-1288-8486');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (129, '박예준', '대구시 북구 39-33', '010-4628-2231');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (130, '강도현', '서울시 강남구 80-42', '010-2612-4978');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (131, '장지우', '서울시 강남구 42-48', '010-9794-8929');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (132, '장주원', '경상북도 포항시 99-26', '010-2025-4784');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (133, '윤하준', '인천시 연수구 72-6', '010-1309-1343');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (134, '임건우', '부산시 해운대구 57-34', '010-9451-9988');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (135, '최도윤', '부산시 해운대구 74-2', '010-2971-3812');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (136, '장지우', '경상북도 포항시 74-5', '010-7220-3793');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (137, '장도현', '서울시 강남구 97-31', '010-8697-8760');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (138, '정하준', '대전시 유성구 8-35', '010-8414-2942');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (139, '정민준', '광주시 서구 67-3', '010-5370-2052');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (140, '조서준', '경상북도 포항시 71-10', '010-5637-3663');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (141, '임주원', '대구시 북구 75-16', '010-1992-5540');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (142, '김하준', '서울시 강남구 83-36', '010-1571-8239');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (143, '윤건우', '경기도 수원시 10-7', '010-3885-3267');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (144, '김도현', '부산시 해운대구 50-33', '010-5864-2527');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (145, '조주원', '경기도 수원시 45-31', '010-8166-8260');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (146, '윤하준', '경상북도 포항시 67-21', '010-5387-9701');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (147, '정주원', '대구시 북구 29-40', '010-4122-9004');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (148, '강현우', '인천시 연수구 42-9', '010-7785-8058');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (149, '박지우', '서울시 강남구 85-43', '010-1811-2500');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (150, '윤민준', '대전시 유성구 68-45', '010-7147-1600');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (151, '김도윤', '광주시 서구 85-34', '010-6320-1960');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (152, '강주원', '인천시 연수구 97-44', '010-8891-5633');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (153, '임도윤', '경상북도 포항시 63-20', '010-1442-6314');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (154, '김건우', '경기도 수원시 17-18', '010-2414-4531');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (155, '박예준', '경기도 수원시 70-3', '010-7529-8024');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (156, '박예준', '경기도 수원시 97-34', '010-4302-5262');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (157, '이도윤', '대구시 북구 31-42', '010-6690-7510');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (158, '임도현', '광주시 서구 9-40', '010-2028-7544');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (159, '이현우', '인천시 연수구 17-2', '010-5768-5727');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (160, '정지우', '대전시 유성구 38-29', '010-1889-2831');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (161, '정서준', '경기도 수원시 22-18', '010-4518-2879');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (162, '이민준', '광주시 서구 55-21', '010-8791-9201');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (163, '임예준', '서울시 강남구 75-2', '010-4071-6833');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (164, '정서준', '경상북도 포항시 46-7', '010-7842-8452');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (165, '정도현', '경상북도 포항시 25-48', '010-2835-9158');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (166, '김건우', '광주시 서구 24-27', '010-1863-3885');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (167, '최도윤', '부산시 해운대구 13-29', '010-5356-5553');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (168, '박지우', '광주시 서구 18-47', '010-4793-9061');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (169, '조주원', '대전시 유성구 12-14', '010-4329-4709');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (170, '윤현우', '인천시 연수구 84-17', '010-9095-2328');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (171, '최주원', '경기도 수원시 89-38', '010-7601-2376');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (172, '조건우', '부산시 해운대구 7-31', '010-2351-1813');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (173, '조지우', '대전시 유성구 99-44', '010-3368-1215');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (174, '박도현', '대구시 북구 79-48', '010-7571-9440');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (175, '김현우', '대구시 북구 36-49', '010-3496-5336');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (176, '조도윤', '부산시 해운대구 68-29', '010-7246-3644');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (177, '김서준', '서울시 강남구 48-29', '010-7757-3578');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (178, '윤서준', '서울시 강남구 62-12', '010-7559-4077');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (179, '장건우', '인천시 연수구 98-46', '010-9809-9411');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (180, '최지우', '부산시 해운대구 75-14', '010-2444-9047');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (181, '정도현', '경상북도 포항시 99-44', '010-1735-4944');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (182, '이현우', '광주시 서구 43-30', '010-6045-8928');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (183, '김도현', '서울시 강남구 68-3', '010-5747-6526');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (184, '장예준', '경상북도 포항시 98-16', '010-8228-7675');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (185, '윤도현', '경기도 수원시 72-29', '010-2108-2537');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (186, '이서준', '대전시 유성구 20-33', '010-3711-3949');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (187, '강예준', '대구시 북구 41-28', '010-8916-3561');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (188, '이도윤', '인천시 연수구 88-50', '010-1300-6041');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (189, '강현우', '경기도 수원시 18-31', '010-6252-5622');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (190, '조민준', '광주시 서구 16-36', '010-7042-9485');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (191, '임도현', '서울시 강남구 41-15', '010-1824-9342');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (192, '조예준', '서울시 강남구 27-43', '010-6522-2701');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (193, '윤민준', '대구시 북구 11-21', '010-6669-3927');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (194, '임지우', '경기도 수원시 50-28', '010-8602-5978');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (195, '최서준', '부산시 해운대구 9-23', '010-6029-5055');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (196, '박하준', '대전시 유성구 82-5', '010-5969-3441');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (197, '정지우', '대전시 유성구 99-2', '010-5031-2895');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (198, '강도현', '서울시 강남구 79-37', '010-4211-8076');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (199, '강주원', '서울시 강남구 62-8', '010-4265-5921');
INSERT INTO 학생 (학번, 이름, 주소, 연락처) VALUES (200, '강주원', '경상북도 포항시 78-24', '010-7222-1047');


-- 기숙사 기본 데이터
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (201, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (202, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (203, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (204, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (205, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (206, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (207, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (208, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (209, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (210, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (301, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (302, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (303, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (304, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (305, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (306, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (307, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (308, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (309, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (310, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (401, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (402, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (403, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (404, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (405, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (406, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (407, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (408, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (409, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (410, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (501, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (502, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (503, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (504, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (505, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (506, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (507, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (508, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (509, 0);
INSERT INTO 기숙사 (방번호, 배정인원) VALUES (510, 0);
