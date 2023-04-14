

-- user 테이블 설계해 보기
CREATE TABLE user_tb(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) not null UNIQUE,
    password VARCHAR(30) not null,
    fullname VARCHAR(50) not null,
    created_at TIMESTAMP not null DEFAULT NOW()
);

-- 사용자의 계좌 정보 테이블 설계
CREATE TABLE account_tb(
	id int AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(30) not null UNIQUE,
    password VARCHAR(20) not null,
    balance BIGINT not null COMMENT '계좌 잔액',
    user_id int,
    created_at TIMESTAMP not null DEFAULT now()    
);

-- 입금 내역 저장
-- 출금 내역 저장
-- 사용자 간의 이체 내역 저장

-- 사용자들 history 테이블 설계
-- bigint -> 8바이트 크기의 정수형
-- 조(10의 12승)  -- 경(10의 16승)  -- 해(10의 20승)
-- 자(10의 24승)  -- 양(10의 28승)
CREATE TABLE history_tb(
	id  int AUTO_INCREMENT PRIMARY KEY COMMENT '거래 내역 ID',
    amount BIGINT not null comment '거래 금액',
    w_account_id int comment '출금 계좌 ID',
    d_account_id int comment '입금 계좌 ID',
    w_balance BIGINT comment '출금 요청된 계좌의 잔액',
    d_balance BIGINT comment '입금 요청된 계좌의 잔액',
    created_at TIMESTAMP not null DEFAULT now()
);