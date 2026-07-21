package com.example.schedule.schedule.controller;

import com.example.schedule.schedule.dto.ScheduleRequestDto;
import com.example.schedule.schedule.dto.ScheduleResponseDto;
import com.example.schedule.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto requestDto
    ) {
        ScheduleResponseDto responseDto =
                scheduleService.createSchedule(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules() {

        return ResponseEntity.ok(
                scheduleService.getSchedules()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                scheduleService.getSchedule(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                scheduleService.updateSchedule(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id
    ) {
        scheduleService.deleteSchedule(id);

        return ResponseEntity.noContent().build();
    }
}