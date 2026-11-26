CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_roles_name UNIQUE (name)
);

CREATE TABLE service_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(150) NOT NULL,
    active BIT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_service_types_code UNIQUE (code)
);

CREATE TABLE clients (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_code VARCHAR(50) NOT NULL,
    business_name VARCHAR(200) NOT NULL,
    trade_name VARCHAR(200),
    pan VARCHAR(10),
    contact_person VARCHAR(150),
    phone VARCHAR(30),
    email VARCHAR(254),
    address TEXT,
    state VARCHAR(100),
    status VARCHAR(30) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_clients_client_code UNIQUE (client_code),
    CONSTRAINT uk_clients_pan UNIQUE (pan)
);

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(30),
    active BIT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE client_contacts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(254),
    designation VARCHAR(100),
    is_primary BIT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_contacts_client FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE client_registrations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    gstin VARCHAR(15) NOT NULL,
    state_code VARCHAR(2) NOT NULL,
    registration_type VARCHAR(50) NOT NULL,
    registration_date DATE,
    registration_status VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_client_registrations_gstin UNIQUE (gstin),
    CONSTRAINT fk_client_registrations_client FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE client_services (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    service_type_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    active BIT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_services_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_client_services_type FOREIGN KEY (service_type_id) REFERENCES service_types (id)
);

CREATE TABLE client_assignments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    assigned_by_user_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    active BIT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_assignments_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_client_assignments_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_client_assignments_assigned_by FOREIGN KEY (assigned_by_user_id) REFERENCES users (id)
);

CREATE TABLE compliance_obligations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    registration_id BIGINT,
    service_type_id BIGINT NOT NULL,
    assigned_to_user_id BIGINT,
    financial_year VARCHAR(20) NOT NULL,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    review_status VARCHAR(30),
    filed_at DATETIME(6),
    acknowledgement_reference VARCHAR(150),
    remarks TEXT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_compliance_obligations_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_compliance_obligations_registration FOREIGN KEY (registration_id) REFERENCES client_registrations (id),
    CONSTRAINT fk_compliance_obligations_service FOREIGN KEY (service_type_id) REFERENCES service_types (id),
    CONSTRAINT fk_compliance_obligations_assignee FOREIGN KEY (assigned_to_user_id) REFERENCES users (id)
);

CREATE TABLE tasks (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    obligation_id BIGINT,
    assigned_to_user_id BIGINT NOT NULL,
    created_by_user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    priority VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    due_at DATETIME(6),
    completed_at DATETIME(6),
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_tasks_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_tasks_obligation FOREIGN KEY (obligation_id) REFERENCES compliance_obligations (id),
    CONSTRAINT fk_tasks_assignee FOREIGN KEY (assigned_to_user_id) REFERENCES users (id),
    CONSTRAINT fk_tasks_creator FOREIGN KEY (created_by_user_id) REFERENCES users (id)
);

CREATE TABLE documents (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    registration_id BIGINT,
    obligation_id BIGINT,
    uploaded_by_user_id BIGINT NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    storage_key VARCHAR(1024) NOT NULL,
    mime_type VARCHAR(150),
    file_size BIGINT,
    checksum_sha256 VARCHAR(64),
    tax_period VARCHAR(30),
    verification_status VARCHAR(30) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_documents_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_documents_registration FOREIGN KEY (registration_id) REFERENCES client_registrations (id),
    CONSTRAINT fk_documents_obligation FOREIGN KEY (obligation_id) REFERENCES compliance_obligations (id),
    CONSTRAINT fk_documents_uploader FOREIGN KEY (uploaded_by_user_id) REFERENCES users (id)
);

CREATE TABLE document_requests (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    obligation_id BIGINT,
    requested_by_user_id BIGINT NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    description TEXT,
    due_date DATE,
    status VARCHAR(30) NOT NULL,
    fulfilled_document_id BIGINT,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_document_requests_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_document_requests_obligation FOREIGN KEY (obligation_id) REFERENCES compliance_obligations (id),
    CONSTRAINT fk_document_requests_requester FOREIGN KEY (requested_by_user_id) REFERENCES users (id),
    CONSTRAINT fk_document_requests_document FOREIGN KEY (fulfilled_document_id) REFERENCES documents (id)
);

CREATE TABLE follow_ups (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    obligation_id BIGINT,
    assigned_to_user_id BIGINT,
    channel VARCHAR(30) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    notes TEXT,
    follow_up_at DATETIME(6) NOT NULL,
    outcome TEXT,
    next_follow_up_at DATETIME(6),
    status VARCHAR(30) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_follow_ups_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_follow_ups_obligation FOREIGN KEY (obligation_id) REFERENCES compliance_obligations (id),
    CONSTRAINT fk_follow_ups_assignee FOREIGN KEY (assigned_to_user_id) REFERENCES users (id)
);

CREATE TABLE import_batches (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT,
    uploaded_by_user_id BIGINT NOT NULL,
    import_type VARCHAR(50) NOT NULL,
    filename VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    total_rows INTEGER NOT NULL,
    successful_rows INTEGER NOT NULL,
    failed_rows INTEGER NOT NULL,
    error_report_key VARCHAR(1024),
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_import_batches_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_import_batches_uploader FOREIGN KEY (uploaded_by_user_id) REFERENCES users (id)
);

CREATE TABLE reconciliation_runs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    registration_id BIGINT NOT NULL,
    created_by_user_id BIGINT NOT NULL,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    purchase_import_id BIGINT,
    gstr2b_import_id BIGINT,
    status VARCHAR(30) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_reconciliation_runs_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_reconciliation_runs_registration FOREIGN KEY (registration_id) REFERENCES client_registrations (id),
    CONSTRAINT fk_reconciliation_runs_creator FOREIGN KEY (created_by_user_id) REFERENCES users (id),
    CONSTRAINT fk_reconciliation_runs_purchase_import FOREIGN KEY (purchase_import_id) REFERENCES import_batches (id),
    CONSTRAINT fk_reconciliation_runs_gstr2b_import FOREIGN KEY (gstr2b_import_id) REFERENCES import_batches (id)
);

CREATE TABLE reconciliation_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    reconciliation_run_id BIGINT NOT NULL,
    invoice_number VARCHAR(100),
    supplier_gstin VARCHAR(15),
    invoice_date DATE,
    purchase_taxable_value DECIMAL(19,4),
    portal_taxable_value DECIMAL(19,4),
    purchase_tax_amount DECIMAL(19,4),
    portal_tax_amount DECIMAL(19,4),
    match_status VARCHAR(30) NOT NULL,
    remarks TEXT,
    PRIMARY KEY (id),
    CONSTRAINT fk_reconciliation_items_run FOREIGN KEY (reconciliation_run_id) REFERENCES reconciliation_runs (id)
);

CREATE TABLE billing_invoices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    invoice_number VARCHAR(100) NOT NULL,
    invoice_date DATE NOT NULL,
    due_date DATE NOT NULL,
    subtotal DECIMAL(19,4) NOT NULL,
    tax_amount DECIMAL(19,4) NOT NULL,
    total_amount DECIMAL(19,4) NOT NULL,
    status VARCHAR(30) NOT NULL,
    notes TEXT,
    created_by_user_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_billing_invoices_client_number UNIQUE (client_id, invoice_number),
    CONSTRAINT fk_billing_invoices_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_billing_invoices_creator FOREIGN KEY (created_by_user_id) REFERENCES users (id)
);

CREATE TABLE invoice_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    invoice_id BIGINT NOT NULL,
    description VARCHAR(500) NOT NULL,
    quantity DECIMAL(12,3) NOT NULL,
    unit_price DECIMAL(19,4) NOT NULL,
    tax_rate DECIMAL(5,2) NOT NULL,
    line_total DECIMAL(19,4) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_invoice_items_invoice FOREIGN KEY (invoice_id) REFERENCES billing_invoices (id)
);

CREATE TABLE payments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    client_id BIGINT NOT NULL,
    received_date DATE NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    payment_mode VARCHAR(30) NOT NULL,
    reference_number VARCHAR(150),
    notes TEXT,
    recorded_by_user_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payments_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_payments_recorder FOREIGN KEY (recorded_by_user_id) REFERENCES users (id)
);

CREATE TABLE payment_allocations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    payment_id BIGINT NOT NULL,
    invoice_id BIGINT NOT NULL,
    allocated_amount DECIMAL(19,4) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_allocations_payment FOREIGN KEY (payment_id) REFERENCES payments (id),
    CONSTRAINT fk_payment_allocations_invoice FOREIGN KEY (invoice_id) REFERENCES billing_invoices (id)
);

CREATE TABLE notifications (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    read_at DATETIME(6),
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE audit_logs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id VARCHAR(100),
    details JSON,
    ip_address VARCHAR(45),
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_audit_logs_user FOREIGN KEY (user_id) REFERENCES users (id)
);