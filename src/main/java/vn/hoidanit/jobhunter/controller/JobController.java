package vn.hoidanit.jobhunter.controller;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.respone.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.jobs.ResCreateJobDTO;
import vn.hoidanit.jobhunter.domain.response.jobs.ResUpdateJobDTO;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    @ApiMessage("Create a jobs")
    public ResponseEntity<ResCreateJobDTO> create(@Valid @RequestBody Job jobs) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.jobService.create(jobs));
    }

    @PutMapping("/jobs")
    @ApiMessage("Update a jobs")
    public ResponseEntity<ResUpdateJobDTO> update(@Valid @RequestBody Job jobs) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(jobs.getId());
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        return ResponseEntity.ok()
                .body(this.jobService.update(jobs, currentJob.get()));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("Delete a jobs by id")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        this.jobService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/jobs/{id}")
    @ApiMessage("Get a jobs by id")
    public ResponseEntity<Job> getJob(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.fetchJobById(id);
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }
        return ResponseEntity.ok().body(currentJob.get());
    }

    @GetMapping("/jobs")
    @ApiMessage("Get jobs with pagination")
    public ResponseEntity<ResultPaginationDTO> getAllJob(
            @Filter Specification<Job> spec,
            Pageable pageable) {
        return ResponseEntity.ok().body(this.jobService.fetchAll(spec, pageable));
    }
}