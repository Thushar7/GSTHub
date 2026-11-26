package com.example.GSTHub.Repository;

import com.example.GSTHub.Model.Client;
import com.example.GSTHub.Model.ClientRegistration;
import com.example.GSTHub.Model.ComplianceObligation;
import com.example.GSTHub.Model.Notification;
import com.example.GSTHub.Model.Role;
import com.example.GSTHub.Model.ServiceType;
import com.example.GSTHub.Model.Task;
import com.example.GSTHub.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class RepositoryLayerTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private ComplianceObligationRepository complianceObligationRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void findUserByEmail() {
        User user = createUser("analyst@gstflow.test");

        assertThat(userRepository.findByEmail("analyst@gstflow.test"))
                .isPresent()
                .get()
                .extracting(User::getId)
                .isEqualTo(user.getId());
    }

    @Test
    void findClientByClientCode() {
        Client client = createClient("GST-001");

        assertThat(clientRepository.findByClientCode("GST-001"))
                .isPresent()
                .get()
                .extracting(Client::getId)
                .isEqualTo(client.getId());
    }

    @Test
    void findRegistrationByGstin() {
        Client client = createClient("GST-002");
        ClientRegistration registration = new ClientRegistration();
        registration.setClient(client);
        registration.setGstin("29ABCDE1234F1Z5");
        registration.setStateCode("29");
        registration.setRegistrationType("REGULAR");
        registration.setRegistrationStatus("ACTIVE");
        clientRegistrationRepository.save(registration);

        assertThat(clientRegistrationRepository.findByGstin("29ABCDE1234F1Z5"))
                .isPresent();
    }

    @Test
    void findTasksAssignedToUser() {
        User user = createUser("staff@gstflow.test");
        Client client = createClient("GST-003");

        Task task = new Task();
        task.setClient(client);
        task.setAssignedTo(user);
        task.setCreatedBy(user);
        task.setTitle("Prepare GSTR-3B");
        task.setPriority("HIGH");
        task.setStatus("PENDING");
        task.setDueAt(LocalDateTime.now().plusDays(2));
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);

        assertThat(taskRepository.findByAssignedToId(user.getId(), Pageable.unpaged()).getContent())
                .extracting(Task::getTitle)
                .contains("Prepare GSTR-3B");
    }

    @Test
    void findOverdueComplianceObligations() {
        Client client = createClient("GST-004");
        ServiceType serviceType = new ServiceType();
        serviceType.setCode("GSTR3B");
        serviceType.setName("GSTR-3B");
        serviceType.setActive(true);
        serviceTypeRepository.save(serviceType);

        ComplianceObligation obligation = new ComplianceObligation();
        obligation.setClient(client);
        obligation.setServiceType(serviceType);
        obligation.setFinancialYear("2026-27");
        obligation.setPeriodStart(LocalDate.of(2026, 10, 1));
        obligation.setPeriodEnd(LocalDate.of(2026, 10, 31));
        obligation.setDueDate(LocalDate.now().minusDays(1));
        obligation.setStatus("PENDING");
        obligation.setCreatedAt(LocalDateTime.now());
        obligation.setUpdatedAt(LocalDateTime.now());
        complianceObligationRepository.save(obligation);

        assertThat(complianceObligationRepository
                .findOverdue(LocalDate.now(), Pageable.unpaged()).getContent())
                .extracting(ComplianceObligation::getId)
                .contains(obligation.getId());
    }

    @Test
    void findUnreadNotifications() {
        User user = createUser("notifications@gstflow.test");
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("GST due");
        notification.setMessage("An obligation is due soon.");
        notification.setNotificationType("DEADLINE");
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);

        assertThat(notificationRepository
                .findByUserIdAndReadAtIsNull(user.getId(), Pageable.unpaged()).getContent())
                .extracting(Notification::getTitle)
                .contains("GST due");
    }

    private User createUser(String email) {
        Role role = new Role();
        role.setName("ANALYST-" + email);
        roleRepository.save(role);

        User user = new User();
        user.setRole(role);
        user.setName("GST Staff");
        user.setEmail(email);
        user.setPasswordHash("test-hash");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private Client createClient(String code) {
        Client client = new Client();
        client.setClientCode(code);
        client.setBusinessName("GSTFlow Test Client");
        client.setStatus("ACTIVE");
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }
}
