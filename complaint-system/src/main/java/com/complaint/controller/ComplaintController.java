package com.complaint.controller;

import com.complaint.entity.Complaint;
import com.complaint.repository.ComplaintRepository;
import com.complaint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepo;

    @PostMapping("/submit")
    public ResponseEntity<Complaint> submitComplaint(@RequestBody Complaint complaint) {
        return ResponseEntity.ok(complaintRepo.save(complaint));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Complaint>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(complaintRepo.findByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintRepo.findAll());
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Long id, @RequestBody String status) {
        return complaintRepo.findById(id)
                .map(comp -> {
                    comp.setStatus(status);
                    return ResponseEntity.ok(complaintRepo.save(comp));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
