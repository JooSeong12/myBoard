package com.example.myBoard.repository;

import com.example.myBoard.constant.Gender;
import com.example.myBoard.entity.Users;
import net.bytebuddy.asm.Advice;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
//@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")


class UsersRepositoryTest {
    @Autowired
    UsersRepository usersRepository;
    @Test
    void findByName테스트(){
        String findName = "Elvina";
        usersRepository.findByName(findName).forEach(users -> System.out.println(users));
    }

    @Test
    void findTop3LikeColor테스트(){
        String color = "Pink";
        usersRepository.findTop3ByLikeColor(color).forEach(users -> System.out.println(users));
    }

    @Test
    void findByGenderAndLikeColor테스트(){
        String color = "Pink";
        usersRepository.findByGenderAndLikeColor(Gender.Male, color).forEach(users -> System.out.println(users));
    }

    @Test
    void findByCreatedAtAfter테스트(){
        usersRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(30L)).forEach(users -> System.out.println(users));
    }
@Test
    void findByIdBetween테스트(){
        Long startId = 1L;
        Long endId = 10L;
        usersRepository.findByIdBetween(startId, endId).forEach(users -> System.out.println(users));
    }
    @Test
    void findByIdIsNotNull테스트(){
        System.out.println("--- InNotNull Count : " + usersRepository.findByIdIsNotNull().stream().count());
    }
    @Test
    void findByLikeColorIn테스트(){
        usersRepository.findByLikeColorIn(Lists.newArrayList("Pink","Blue")).forEach(users -> System.out.println(users));
    }
    @Test
    void stringSearch테스트(){
        usersRepository.findByNameStartingWith("D")
                .forEach(users -> System.out.println("D로 시작 : " + users));
        usersRepository.findByNameEndingWith("s")
                .forEach(users -> System.out.println("s로 끝 : " + users));

        usersRepository.findByNameLike("%k%")
                .forEach(users -> System.out.println("k 포함 : " + users));
    }
    @Test
    void findByIdBetweenOrderByNameDesc테스트(){

        usersRepository.findByIdBetweenOrderByNameDesc(1L, 5L).forEach(users -> System.out.println(users));
    }
    @Test
    void findByLikeColorAndSort테스트(){
        usersRepository.findByLikeColor("Orange"
                        , Sort.by(Sort.Order.desc("gender"),
                                Sort.Order.asc("createdAt")))
                .forEach(users -> System.out.println(users));
    }
    @Test
    void findTop10OrderByNameAscCreatedAtDesc테스트(){
        usersRepository.findTop10ByLikeColorOrderByGenderDescCreatedAtAsc("Orange")
                .forEach(users -> System.out.println(users));
    }

    //전체 페이징
    @Test
    void pagingTest(){
        System.out.println("페이지=0, 페이지 당 리스트 수 : 5");
        usersRepository.findAll(
                PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent().forEach(users -> System.out.println(users));

        System.out.println("페이지=1, 페이지 당 리스트 수 : 5");
        usersRepository.findAll(
                        PageRequest.of(1, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent().forEach(users -> System.out.println(users));

        System.out.println("페이지=2, 페이지 당 리스트 수 : 5");
        usersRepository.findAll(
                        PageRequest.of(2, 5, Sort.by(Sort.Order.desc("id"))))
                .getContent().forEach(users -> System.out.println(users));
    }

    @Test
    void pagingTest2(){
        Pageable pageable = PageRequest.of(30, 10);
        Page<Users> result = usersRepository.findByIdGreaterThanEqualOrderByIdDesc(200L, pageable);
        result.getContent().forEach(users -> System.out.println(users));
        // 총 페이지 수
        System.out.println("Total Pages : " + result.getTotalPages());
        // 전체 데이터 개수
        System.out.println("Total Contents Count : " + result.getTotalElements());
        // 현재 페이지의 번호
        System.out.println("Current Page Number : " + result.getNumber());
        // 다음 페이지 존재여부 확인
        System.out.println("Next Page? " + result.hasNext());
        // 시작 페이지인지 확인
        System.out.println("Is first page? " + result.isFirst());
        //
    }

    //문제1테스트
    @Test
    void findByIdContains문제1테스트(){
        List<Users> usersList = usersRepository.findByNameLikeOrNameLike("%w%", "%m%");
        for(Users users : usersList){
            if(users.getGender().equals(Gender.Female)){
                System.out.println(users);
            }
        }
    }
    //문제1 두번째 방법 테스트
    @Test
    void findByNameContainsAndGenderOrNameContainsAndGender테스트()
    {
        usersRepository.findByNameContainsAndGenderOrNameContainsAndGender("w", Gender.Female, "m", Gender.Female)
                .forEach(users -> System.out.println(users));
    }

    //문제2 테스트
    @Test
    void findByEmailContains테스트(){
        List<Users> result = usersRepository.findByEmailContains("net");
        System.out.println("카운트 =========================== " + result.stream().count());
    }
    //문제3 테스트
    @Test
    void findByUpdatedAtGreaterThanEqualAndNameLike테스트(){
        usersRepository.findByUpdatedAtGreaterThanEqualAndNameLike(LocalDateTime.now().minusMonths(1L), "E%").forEach(users -> System.out.println(users));
    }
    //문제4 테스트
    @Test
    void findFirst10ByOrderByCreatedAtDesc테스트(){
        usersRepository.findFirst10ByOrderByCreatedAtDesc().forEach(users -> System.out.println(users.getId() + users.getName() + users.getGender() + users.getCreatedAt()));
    }
    //문제5 테스트
    @Test
    void email테스트(){
        usersRepository.findByLikeColorAndGender("Red", Gender.Male).
                forEach(users -> System.out.println(users.getEmail().substring(0, users.getEmail().indexOf("@"))));
    }
    //문제5 두번째 방법
    @Test
    void 문제5(){
        List<Users> users = usersRepository.findByLikeColorAndGender("Red", Gender.Male);
        for(Users users1 : users){
            String email = users1.getEmail();
            email = email.substring(0, email.indexOf("@"));
            System.out.println("Email Account : " + email);
        }
    }
    //문제6 테스트
    @Test
    void findByUpdatedAtBefore테스트(){
        List<Users> list = usersRepository.findAll();
       list = list.stream()
                .filter(user -> user.getUpdatedAt().isBefore(user.getCreatedAt())).collect(Collectors.toList());
        System.out.println(list.toString());
    }
    //문제 6 두번째 방법
    @Test
    void 문제6(){
        List<Users> list = usersRepository.findAll();
        for(Users user : list){
            if(user.getUpdatedAt().isBefore(user.getCreatedAt())){
                System.out.println(user);
            }
        }
    }
    //문제7 테스트
    @Test
    void findByEmailLikeAndGenderOrderByUpdatedAtDesc테스트(){
        String email = "edu";
        usersRepository.findByEmailContainsAndGenderOrderByUpdatedAtDesc(email, Gender.Female).forEach(users -> System.out.println(users));
    }
    //문제8 테스트
    @Test
    void findByNameOrderByLikeColorAsc테스트(){
        usersRepository.findAllByOrderByLikeColorAsc(Sort.by(Sort.Order.desc("name"))).forEach(users -> System.out.println(users));
    }
    //문제9 테스트
    @Test
    void findAllByOrderByUpdatedAtDesc테스트(){
        System.out.println("페이지=0, 페이지 당 리스트 수 : 10");
        usersRepository.findAll(
                        PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdAt"))))
                .getContent().forEach(users -> System.out.println(users));
    }
    //문제10 테스트
    @Test
    void findByGenderOrderByIdDesc테스트(){
        Pageable pageable = PageRequest.of(1, 3);
        Page<Users> result = usersRepository.findByGenderOrderByIdDesc(Gender.Male, pageable);
        result.getContent().forEach(users -> System.out.println(users));
    }
    //문제11 테스트
    @Test
    void findByUpdatedAt테스트(){
        usersRepository.findByCreatedAtBetween(LocalDateTime.now().minusMonths(1L).with(TemporalAdjusters.firstDayOfMonth()),
                LocalDateTime.now().minusMonths(1L).with(TemporalAdjusters.lastDayOfMonth())).forEach(users -> System.out.println(users));
    }

    //문제11 두번재 방법
    @Test
    void 문제11(){
        //기준일
        LocalDate baseDate = LocalDate.now().minusMonths(1L);
        //시작일
        LocalDateTime startDate = baseDate.withDayOfMonth(1).atTime(0,0,0);
        //종료일
        LocalDateTime lastDate = baseDate.withDayOfMonth(baseDate.lengthOfMonth()).atTime(23,59,59);
        //검색
        usersRepository.findByCreatedAtBetween(startDate, lastDate).forEach(users -> System.out.println(users));
    }
}