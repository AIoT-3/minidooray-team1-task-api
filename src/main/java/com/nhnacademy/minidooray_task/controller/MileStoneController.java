package com.nhnacademy.minidooray_task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nhnacademy.minidooray_task.dto.MileStoneCreateRequest;
import com.nhnacademy.minidooray_task.dto.MileStoneUpdateRequest;
import com.nhnacademy.minidooray_task.dto.MileStoneDto;
import com.nhnacademy.minidooray_task.service.MileStoneService;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
@RequiredArgsConstructor
public class MileStoneController {

    private final MileStoneService mileStoneService;

    @PostMapping
    public ResponseEntity<MileStoneDto> createMileStone(@RequestBody MileStoneCreateRequest request) {
        MileStoneDto response = mileStoneService.createMileStone(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MileStoneDto> getMileStone(@PathVariable Long id) {
        MileStoneDto response = mileStoneService.getMileStone(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MileStoneDto>> getAllMileStones() {
        List<MileStoneDto> response = mileStoneService.getAllMileStones();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MileStoneDto> updateMileStone(
            @PathVariable Long id,
            @RequestBody MileStoneUpdateRequest request) {
        MileStoneDto response = mileStoneService.updateMileStone(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMileStone(@PathVariable Long id) {
        mileStoneService.deleteMileStone(id);
        return ResponseEntity.noContent().build();
    }
}