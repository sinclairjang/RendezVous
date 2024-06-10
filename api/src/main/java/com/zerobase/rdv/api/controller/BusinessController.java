package com.zerobase.rdv.api.controller;

import com.zerobase.rdv.api.controller.dto.BusinessDto;
import com.zerobase.rdv.api.service.BusinessService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController()
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    // 매장 등록
    @PostMapping("/register")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> register(@RequestBody BusinessDto businessDto) {
        businessService.registerBusiness(businessDto);

        return ResponseEntity.ok().build();
    }

    // 점주 당일 예약 정보 확인
    @GetMapping("/reservation/today")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getTodayReservations(
            final Pageable pageable) {
        return ResponseEntity.ok(
                businessService.getReservations(
                        LocalDate.now().atStartOfDay(),
                        LocalDateTime.now(),
                        pageable));
    }

    // 점주 요청 기간 내 예약 정보 확인
    @GetMapping("/reservation")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getReservations(
            @RequestParam(name = "from") LocalDateTime from,
            @RequestParam(name = "to") LocalDateTime to,
                final Pageable pageable) {
        return ResponseEntity.ok(
                businessService.getReservations(
                        from, to, pageable));
    }
}
