package com.example.schedule.schedule.service;

import com.example.schedule.schedule.dto.ScheduleRequestDto;
import com.example.schedule.schedule.dto.ScheduleResponseDto;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 유저가 없습니다.")
                );

        Schedule schedule = new Schedule(
                user,
                requestDto.getTitle(),
                requestDto.getContent()
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getSchedules() {

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(
            Long id,
            ScheduleRequestDto requestDto
    ) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 일정이 존재하지 않습니다.")
                );

        schedule.update(
                requestDto.getTitle(),
                requestDto.getContent()
        );

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        scheduleRepository.delete(schedule);
    }
}