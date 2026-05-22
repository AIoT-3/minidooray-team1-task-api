package com.nhnacademy.minidooray_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nhnacademy.minidooray_task.entity.MileStone;
import com.nhnacademy.minidooray_task.dto.MileStoneCreateRequest;
import com.nhnacademy.minidooray_task.dto.MileStoneUpdateRequest;
import com.nhnacademy.minidooray_task.dto.MileStoneDto;
import com.nhnacademy.minidooray_task.repository.MileStoneRepository;
import com.nhnacademy.minidooray_task.exception.MileStoneNotFoundException;
import com.nhnacademy.minidooray_task.exception.MileStoneAlreadyExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MileStoneService {

    private final MileStoneRepository mileStoneRepository;

    @Transactional
    public MileStoneDto createMileStone(MileStoneCreateRequest request) {
        if (mileStoneRepository.existsByName(request.getName())) {
            throw new MileStoneAlreadyExistsException(request.getName());
        }

        MileStone mileStone = new MileStone(request.getName());
        MileStone savedMileStone = mileStoneRepository.save(mileStone);

        return new MileStoneDto(savedMileStone.getId(), savedMileStone.getName());
    }

    public MileStoneDto getMileStone(Long id) {
        MileStone mileStone = mileStoneRepository.findById(id)
                .orElseThrow(() -> new MileStoneNotFoundException(id));

        return new MileStoneDto(mileStone.getId(), mileStone.getName());
    }

    public List<MileStoneDto> getAllMileStones() {
        return mileStoneRepository.findAll().stream()
                .map(mileStone -> new MileStoneDto(mileStone.getId(), mileStone.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public MileStoneDto updateMileStone(Long id, MileStoneUpdateRequest request) {
        MileStone mileStone = mileStoneRepository.findById(id)
                .orElseThrow(() -> new MileStoneNotFoundException(id));

        if (mileStoneRepository.existsByName(request.getName())) {
            throw new MileStoneAlreadyExistsException(request.getName());
        }

        mileStone.updateName(request.getName());
        return new MileStoneDto(mileStone.getId(), mileStone.getName());
    }

    @Transactional
    public void deleteMileStone(Long id) {
        if (!mileStoneRepository.existsById(id)) {
            throw new MileStoneNotFoundException(id);
        }

        mileStoneRepository.deleteById(id);
    }
}