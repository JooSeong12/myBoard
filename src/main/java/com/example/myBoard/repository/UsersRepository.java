package com.example.myBoard.repository;

import com.example.myBoard.constant.Gender;
import com.example.myBoard.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    // 이름으로 검색
    List<Users> findByName(String name);

    // Pink 색상 데이터 중 상위 3개 데이터만 가져오기
    List<Users> findTop3ByLikeColor(String color);

    //Gender와 color로 테이블 검색
    List<Users> findByGenderAndLikeColor(Gender gender, String color);

    // 범위로 검색(After, Before) --- 날짜/시간 데이터에 한정해서 사용
    List<Users> findByCreatedAtAfter(LocalDateTime searchDate);

    //범위로 검색(between)
    List<Users> findByIdBetween(Long startId, Long endId);

    //null, not null 사용
    List<Users> findByIdIsNotNull();

    // in 사용
    List<Users> findByLikeColorIn(List<String> color);

    //문자열 사용
    List<Users> findByNameStartingWith(String name);
    List<Users> findByNameEndingWith(String name);

    List<Users> findByNameLike(String name);

    //sort하고 순서대로 출력하기
    List<Users> findByIdBetweenOrderByNameDesc(Long start, Long end);

    //sort 별도로 처리 하는 법
    //Orange 색상 검색해서 Gender 오름차순, createdAt 내림차순 정렬
    List<Users> findByLikeColor(String color, Sort sort);

    //sort 별도로 처리 하는 법
    //Orange 색상 검색해서 Gender 오름차순, createdAt 내림차순 정렬
    List<Users> findTop10ByLikeColorOrderByGenderDescCreatedAtAsc(String color);

    //페이징 처리
    //주어진 ID보다 큰 데이터를 내림차순으로 검색 후 페이징 처리(id = 200, 5번째 페이지, 한페이지당 10개씩)
    Page<Users> findByIdGreaterThanEqualOrderByIdDesc(Long id, Pageable paging);

    //문제 1
    List<Users> findByNameLikeOrNameLike(String name, String name2);

    //문제 1 두번째방법
    List<Users> findByNameContainsAndGenderOrNameContainsAndGender(String name, Gender gender1, String name2, Gender gender2);
    // 문제 2
    List<Users> findByEmailContains(String email);
    //문제 3
    List<Users> findByUpdatedAtGreaterThanEqualAndNameLike(LocalDateTime updateDate, String name);
    //문제 4
    List<Users> findFirst10ByOrderByCreatedAtDesc();
    //문제 5
    List<Users> findByLikeColorAndGender(String color, Gender gender);
    //문제 6
    List<Users> findByUpdatedAtBefore(LocalDateTime createdAt);
    //문제 7
    List<Users> findByEmailContainsAndGenderOrderByUpdatedAtDesc(String email, Gender gender);
    //문제 8
    List<Users> findAllByOrderByLikeColorAsc(Sort sort);
    //문제 9
    List<Users> findAllByOrderByUpdatedAtDesc(Pageable paging);
    //문제 10
    Page<Users> findByGenderOrderByIdDesc(Gender gender, Pageable paging);
    //문제 11
    List<Users> findByCreatedAtBetween(LocalDateTime firstTime, LocalDateTime lastTime);
}
