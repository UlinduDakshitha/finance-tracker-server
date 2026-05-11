package com.financetracker.backend.controller;
import com.financetracker.backend.dto.response.DashboardResponse;
import com.financetracker.backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getDashboardSummary(Authentication authentication) {
        return ResponseEntity.ok(dashboardService.getDashboard(authentication.getName()));
    }
}
