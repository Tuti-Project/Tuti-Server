package com.tuti.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

import com.tuti.auth.infrastructure.JwtTokenProvider;
import com.tuti.auth.infrastructure.OAuthProvider;
import com.tuti.fixtures.MemberFixtures;
import com.tuti.member.domain.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = RestController.class))
@Import({JwtTokenProvider.class, OAuthProvider.class})
public abstract class WebMVCTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    protected HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        when(memberRepository.findById(any()))
                .thenReturn(Optional.of(MemberFixtures.정우()));
    }
}
