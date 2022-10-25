package com.osekiller.projet;

import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.*;
import com.osekiller.projet.service.CompanyService;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

    private PasswordEncoder passwordEncoder;
    static boolean alreadySetup;

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private StudentRepository studentRepository;
    private ManagerRepository managerRepository;
    private RoleRepository roleRepository;
    private StudentService studentService;
    private CompanyService companyService;
    private OfferRepository offerRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        //Si il y a des chose à setup au lancement du serveur c'est ici
        initializeRoles();
        initializeCompanies();
        bootStrapOffers();
        initializeManagers();
        initializeStudents();
        alreadySetup = true;
    }

    @Transactional
    void initializeRoles(){
        for(ERole role : ERole.values()){
            createRoleIfNotFound(role);
        }
    }

    @Transactional
    void bootStrapOffers() {
        Optional<Company> company = companyRepository.findByEmail("testcompany@osk.com") ;
        if (company.isPresent()) {
            Offer offer1 = new Offer(company.get(), "test1", 1.0, LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
            Offer offer2 = new Offer(company.get(), "test2", 1.0, LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
            offer2.setAccepted(true);
            Offer offer3 = new Offer(company.get(), "test3", 1.0, LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
            offer3.setAccepted(true);
            Offer offer4 = new Offer(company.get(), "test4", 1.0, LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
            Offer offer5 = new Offer(company.get(), "test5", 1.0, LocalDate.of(2002, 12, 14), LocalDate.of(2002, 12, 16)) ;
            offer5.setAccepted(true);
            offerRepository.save(offer1) ;
            offerRepository.save(offer2) ;
            offerRepository.save(offer3) ;
            offerRepository.save(offer4) ;
            offerRepository.save(offer5) ;
        }

    }

    @Transactional
    void initializeCompanies(){
        Company testCompany = new Company("Test Company","testcompany@osk.com", passwordEncoder.encode("123"));
        testCompany.setEnabled(true);
        createCompanyIfNotFound(testCompany);
    }

    @Transactional
    void initializeManagers(){
        Manager testManager = new Manager("Test Manager","testmanager@osk.com", passwordEncoder.encode("123"));
        testManager.setEnabled(true);
        createManagerIfNotFound(testManager);
    }

    @Transactional
    void initializeStudents(){
        Student testStudent1 = new Student("Test Student 1","teststudent1@osk.com", passwordEncoder.encode("123"));
        Student testStudent2 = new Student("Test Student 2","teststudent2@osk.com", passwordEncoder.encode("123"));
        Student testStudent3 = new Student("Test Student 3","teststudent3@osk.com", passwordEncoder.encode("123"));
        testStudent1.setEnabled(true);
        createStudentIfNotFound(testStudent1);
        createStudentIfNotFound(testStudent2);
        createStudentIfNotFound(testStudent3);
    }

    void createCompanyIfNotFound(Company company){
        if(companyRepository.findByEmail(company.getEmail()).isPresent()) return;
        Role companyRole = roleRepository.findByName(ERole.COMPANY.name()).orElseThrow(EntityNotFoundException::new);
        company.setRole(companyRole);
        companyRepository.save(company);
    }

    void createStudentIfNotFound(Student student){
        if(studentRepository.findByEmail(student.getEmail()).isPresent()) return;
        Role studentRole = roleRepository.findByName(ERole.STUDENT.name()).orElseThrow(EntityNotFoundException::new);
        student.setRole(studentRole);
        if (LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 5, 31))) {
            student.setSessionYear(2020);
        }
        else {
            student.setSessionYear(2020);
        }
        studentRepository.save(student);
    }

    void createManagerIfNotFound(Manager manager){
        if(managerRepository.findByEmail(manager.getEmail()).isPresent()) return;
        Role managerRole = roleRepository.findByName(ERole.MANAGER.name()).orElseThrow(EntityNotFoundException::new);
        manager.setRole(managerRole);
        managerRepository.save(manager);
    }

    void createRoleIfNotFound(ERole role) {
        if(roleRepository.findByName(role.name()).isPresent()) return;

        roleRepository.save(new Role(role.name()));
    }
}
