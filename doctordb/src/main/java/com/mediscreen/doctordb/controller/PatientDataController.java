package com.mediscreen.doctordb.controller;

import com.mediscreen.doctordb.model.PatientData;
import com.mediscreen.doctordb.service.PatientDataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patHistory/")
public class PatientDataController {

    private final PatientDataService dataService;

    @Autowired
    public PatientDataController(PatientDataService dataService) {
        this.dataService = dataService;
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<PatientData> getPatientDataById(@PathVariable int id) {
        return ResponseEntity.ok(dataService.getPatientDataById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<PatientData> addHistory(@RequestParam int patId, @RequestParam String e) {
        return ResponseEntity.ok(dataService.addHistory(patId,e));
    }
}
