package vn.hoidanit.jobhunter.service;

import vn.hoidanit.jobhunter.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);

    }

    public Company fetchCompanyById(long id) {
        Optional<Company> comOptional = this.companyRepository.findById(id);
        if (comOptional.isPresent()) {
            return comOptional.get();
        }
        return null;
    }

    public ResultPaginationDTO handleGetCompany(Specification<Company> spec,
            Pageable pageable) {
        Page<Company> pCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pCompany.getNumber() + 1);
        mt.setPageSizes(pCompany.getSize());

        mt.setPages(pCompany.getTotalPages());
        mt.setTotals(pCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pCompany.getContent());
        return rs;
    }

    public void handleDeleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }

    public Company updateCompany(Company putCom) {
        Optional<Company> companyOptional = this.companyRepository.findById(putCom.getId());
        if (companyOptional.isPresent()) {
            Company companyCurrent = companyOptional.get();
            companyCurrent.setName(putCom.getName());
            companyCurrent.setDescription(putCom.getDescription());
            companyCurrent.setAddress(putCom.getAddress());
            companyCurrent.setLogo(putCom.getLogo());
            // companyCurrent.setCreatedAt(putCom.getCreatedAt());
            // companyCurrent.setUpdatedAt(putCom.getUpdatedAt());
            // companyCurrent.setCreatedBy(putCom.getCreatedBy());
            // companyCurrent.setUpdatedAt(putCom.getUpdatedAt());
            return this.companyRepository.save((companyCurrent));
        }
        return null;
    }
}
