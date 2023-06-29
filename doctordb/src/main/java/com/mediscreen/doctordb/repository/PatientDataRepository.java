package com.mediscreen.doctordb.repository;

import com.mediscreen.doctordb.model.PatientData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDataRepository extends MongoRepository<PatientData, Integer> {

}
