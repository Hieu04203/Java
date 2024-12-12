package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.respone.ResultPaginationDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> creatCompany(@Valid @RequestBody Company postCompany) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(postCompany));

    }

    @GetMapping("/companies/{id}")
    @ApiMessage("fetch company by id")
    public ResponseEntity<Company> fetchCompanyById(@PathVariable("id") long id) {
        Optional<Company> cOptional = this.companyService.findById(id);
        return ResponseEntity.ok().body(cOptional.get());
    }

    @GetMapping("/companies")
    @ApiMessage("fetch company")
    public ResponseEntity<ResultPaginationDTO> getCompany(

            @Filter Specification<Company> spec,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleGetCompany(spec, pageable));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) throws IdInvalidException {
        if (id > 20) {
            throw new IdInvalidException("Id khong ton tai");
        }
        this.companyService.handleDeleteCompany(id);
        return null;
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company putCom) {
        return ResponseEntity.ok().body(this.companyService.updateCompany(putCom));
    }

}
