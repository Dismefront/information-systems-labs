# Refactoring Plan for Information Systems Labs

## Phase 1: Infrastructure and Database

### Goal: Establish a robust and reproducible project infrastructure to ensure stable deployment and improve data integrity.

### Database and JPA
1. Audit the current database schema:
   - Analyze tables, relationships, constraints, and data types
   - Identify potential issues and bottlenecks
2. Standardize table and column naming according to JPA conventions
3. Add missing indexes for frequently queried fields
4. Implement missing integrity constraints (FK, UNIQUE, CHECK)
5. Remove duplication between SQL functions and JPA methods
6. Return Optional instead of null in repositories:
   - Replace `findById(...).orElse(null)` patterns with proper Optional handling
   - Throw ResourceNotFoundException in service layers when appropriate

### Deployment
1. Implement multi-stage Docker builds:
   - Create separate images for Spring Boot backend and React frontend with Nginx
   - Configure docker-compose to run services together with PostgreSQL and MinIO
2. Update database connection strings to use service names instead of localhost
3. Implement database migrations using Flyway:
   - Move SQL scripts to migration files
   - Disable `ddl-auto=update` in favor of controlled migrations

### Logging and Monitoring
1. Add health checks for all services (PostgreSQL, MinIO, Backend)
2. Configure proper logging with appropriate levels (INFO, WARN, ERROR)

## Phase 2: Backend Logic, API, and Security

### Goal: Establish a clear API contract, unify error handling, improve security model, and enhance backend performance.

### Security Improvements
1. Simplify authentication model:
   - Consolidate to a single authentication entry point using AuthenticationManager + JWT
   - Remove redundant CustomAuthenticationProvider
   - Rely on UserDetailsServiceImpl + PasswordEncoder (BCrypt) and DaoAuthenticationProvider
2. Replace SHA-384 PasswordEncoder with BCrypt:
   - Remove deprecated MessageDigestPasswordEncoder
   - Use BCryptPasswordEncoder exclusively
3. Update SecurityConfig annotations:
   - Replace deprecated @EnableGlobalMethodSecurity with newer alternatives
4. Implement proper DTOs for controller responses:
   - Replace exposed entity objects with dedicated DTOs
5. Externalize secrets and credentials to environment variables
6. Define clear authorization policies:
   - Distinguish between HttpSecurity.requestMatchers and @PreAuthorize usage
   - Explicitly authorize critical methods (data mutations, file operations)
   - Validate user permissions for ID-based endpoints

### Controller Layer
1. Remove try/catch blocks from controllers for exception logging:
   - Utilize existing @ControllerAdvice GlobalExceptionHandler
2. Return specific response types instead of ResponseEntity<?>
3. Standardize RESTful URL patterns and method naming
4. Add DTO validation instead of manual null checks
5. Standardize response and error formats for REST controllers
6. Use appropriate HTTP status codes (201 Created, 204 No Content, etc.)

### Service Layer
1. Separate authentication and user management into distinct services
2. Review and configure @Transactional annotations appropriately

### Exception Handling
1. Prohibit use of IllegalArgumentException/IllegalStateException for business errors
2. Define exception hierarchy: NotFoundException, ValidationException, BusinessException

### Performance Optimization
1. Add Spring Cache for frequently accessed data
2. Implement cache invalidation strategies for data modifications

## Phase 3: Frontend and Documentation

### Goal: Improve frontend maintainability and readability, and make the API transparent and self-documenting.

### Frontend Improvements
1. Decompose monolithic components:
   - Split large components into smaller, focused ones with single responsibilities
   - Extract reusable UI logic into shared components
2. Clearly separate concerns between UI layers:
   - UI components (presentation)
   - Services (API interactions)
3. Eliminate code smells:
   - Remove duplication
   - Reduce component and function sizes
   - Simplify complex logic

### Documentation
1. Add OpenAPI/Swagger documentation:
   - Integrate springdoc-openapi-ui
   - Annotate all controllers with @Operation, @Tag, @ApiResponse
   - Document all DTOs with @Schema
2. Set up client generation from OpenAPI specification
3. Create Postman collection for API testing
4. Add markdown documentation with architectural overview and module descriptions