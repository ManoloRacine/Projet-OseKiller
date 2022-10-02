package com.osekiller.projet;

import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Role;
import com.osekiller.projet.model.user.Manager;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.user.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

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


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        //Si il y a des chose à setup au lancement du serveur c'est ici
        initializeRoles();
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
    void initializeStudents() {
        createStudentIfNotFound(new Student("Test Student 1","teststudent1@osk.com", passwordEncoder.encode("testPass123")));
        createStudentIfNotFound(new Student("Test Student 2","teststudent2@osk.com", passwordEncoder.encode("testPass123")));
        createStudentIfNotFound(new Student("Test Student 3","teststudent3@osk.com", passwordEncoder.encode("testPass123")));
    }

    void createStudentIfNotFound(Student student) {
        if(studentRepository.findByEmail(student.getEmail()).isPresent()) return;
        student.setEnabled(true);
        Role managerRole = roleRepository.findByName(ERole.MANAGER.name()).orElseThrow(EntityNotFoundException::new);
        student.setRole(managerRole);
        studentRepository.save(student);
    }

    @Transactional
    void initializeManagers(){
        createManagerIfNotFound(new Manager("Test Manager","testmanager@osk.com", passwordEncoder.encode("testPass123")));
    }

    void createManagerIfNotFound(Manager manager){
        if(managerRepository.findByEmail(manager.getEmail()).isPresent()) return;
        manager.setEnabled(true);
        Role managerRole = roleRepository.findByName(ERole.MANAGER.name()).orElseThrow(EntityNotFoundException::new);
        manager.setRole(managerRole);
        managerRepository.save(manager);
    }

    void createRoleIfNotFound(ERole role) {
        if(roleRepository.findByName(role.name()).isPresent()) return;

        roleRepository.save(new Role(role.name()));
    }
}
