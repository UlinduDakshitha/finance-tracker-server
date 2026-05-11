package com.financetracker.backend.controller;
import com.financetracker.backend.dto.response.DashboardResponse;
import com.financetracker.backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardResponse> getDashboardSummary(
            @RequestHeader("X-User-Email") String email
    ) {
        return ResponseEntity.ok(dashboardService.getDashboard(email));
    }
}
