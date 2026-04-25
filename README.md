# CSC3350 — Company Z Employee Management System

Java console application backed by MySQL. Implements the full user story:
secure HR-admin vs. general-employee login, employee CRUD, salary increase by
percentage within a range, and four payroll/HR reports.

---

## 1. Prerequisites

- JDK 17+ (`java -version`, `javac -version`)
- MySQL 8.x running locally (or any reachable MySQL host)
- The MySQL connector jar in `lib/` (already included: `lib/mysql-connector-j-9.1.0.jar`)

---

## 2. One-time setup

### 2.1 Create the database and load data

From the repo root, in a terminal:

```
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/populate.sql
```

`schema.sql` drops and recreates the `employee_management` database, so it is
safe to re-run. `populate.sql` seeds states, cities, addresses, employees,
divisions, job titles, payroll, and login users.

### 2.2 Create a non-root MySQL user for the app

In any MySQL client, as `root`:

```sql
CREATE USER 'companyz'@'localhost' IDENTIFIED BY 'ChangeMe123!';
GRANT SELECT, INSERT, UPDATE, DELETE ON employee_management.*
  TO 'companyz'@'localhost';
FLUSH PRIVILEGES;
```

### 2.3 Point the app at your DB

Edit `src/DBConnection.java` lines 6–8:

```java
private static final String URL = "jdbc:mysql://localhost:3306/employee_management";
private static final String USER = "companyz";
private static final String PASSWORD = "ChangeMe123!";
```

---

## 3. Build and run

From the repo root:

```
javac -cp "lib/*" -d out src/*.java
java  -cp "out;lib/*" Main
```

The classpath separator is `;` on Windows (even from Git Bash) and `:` on macOS/Linux.

### 3.1 Seeded login accounts

| Role     | Username   | Password   |
|----------|------------|------------|
| HR Admin | `admin`    | `admin123` |
| Employee | `employee` | `user123`  |

---

## 4. Deliverable map (what to demo / submit)

> **Important dates:** Deliverables 4 and 5 are due **2026-04-26 11:59 PM**.
> Deliverables 1–3 were individual and are already past their due dates; this
> README documents how the running code satisfies them so the SDD can cite it.

### Deliverable 1 (03/08, individual, 150 pts)

| Item | Where it lives | Notes |
|------|----------------|-------|
| 1.1 Use case diagram | `docs/uml/use-case.png` *(add)* | Actors: HR Admin, Employee, MySQL DB |
| 1.2 Sequence diagram (employee searches by empID) | `docs/uml/seq-employee-search.png` *(add)* | Show success path and not-found error |
| 1.3 PK/FK connections | `sql/schema.sql` lines 44, 72–73, 80–81, 97 | All six FKs present |
| 1.4 3NF tables (addresses, cities, states) | `sql/schema.sql` lines 6–32 | All 50 states seeded in `populate.sql` |
| 1.5 Schema diagram from DBMS | `docs/uml/schema.png` *(generate from dBeaver / Workbench)* | After loading `schema.sql` |
| 1.6 Indexes | `sql/schema.sql` lines 47–48, 100–101 | `last_name`, `hire_date`, `pay_date`, payroll `empID` |

### Deliverable 2 (03/15, group, 100 pts)

Programming tasks and pass/fail test cases. Live in this repo as runnable
operations on the seeded DB:

- **2.2.a Update employee data (general):**
  Run as admin → option **3 (Update employee)** → enter `1003` → press Enter
  through every field except change Email to `jordan.lee@new.com` and Salary to
  `78000`. Re-run option **2 (Search employee → 1 by empID → 1003)** to
  verify changes persisted (PASS).
- **2.2.b Search employee for deletion:**
  Admin → option **4 (Delete employee)** → enter `9999` (no match) → expect
  "No match for employee 9999." (PASS-not-found path).
  Then enter a valid empID, answer `n` at confirm → expect "Delete cancelled."
  (PASS-cancel path). Then enter a valid empID, answer `y` → row deleted (PASS-delete).
- **2.2.c Bulk salary increase by % when salary < given amount:**
  Admin → option **5 (Increase salary by % within range)** → min `0`, max
  `60000`, percent `10` → expect `1 employee salaries updated.` Verify by
  searching empID `1002` (salary should rise from 58000 to 63800).

### Deliverable 3 (03/29, individual, 150 pts)

| Item | Where in code | Notes |
|------|---------------|-------|
| 3.a Sequence diagram — increase salary by % within range | `docs/uml/seq-salary-range.png` *(add)* | Inputs: min salary, max salary, percent |
| 3.b Sequence diagram — add new employee (auto empID) | `docs/uml/seq-add-employee.png` *(add)* | `EmployeeDAO.addEmployee()` uses `RETURN_GENERATED_KEYS` |

### Deliverable 4 (04/26, group, 100 pts) — SDD PDF

Assemble the SDD from the provided template. Each section maps to:

1. Use case + sequence diagrams (Deliverable 1.1–1.2, 3.a–3.b)
2. Schema design + ER diagram (Deliverable 1.3–1.6, screenshot from `docs/uml/schema.png`)
3. Class design — list and describe each class in `src/`:
   - `Main` — console entry point, role-gated menu
   - `Login`, `User` — authentication and role check (HR_ADMIN vs EMPLOYEE)
   - `DBConnection` — single JDBC connection factory
   - `EmployeeDAO` — search (empID/name/DOB/SSN), view, add, update, delete, bulk raise
   - `PayrollDAO`, `PayrollService`, `PayrollUI` — payroll input + history
   - `ReportsDAO`, `ReportsService`, `ReportsUI` — the four reports
4. Security — login table `app_user`, role gating in `Main.handleAdminChoice` /
   `handleEmployeeChoice`, no shared root account
5. Test cases — Deliverable 2.2.a/b/c steps above, with expected output
6. Table of contents (auto-generate in Word/Google Docs before PDF export)

### Deliverable 5 (04/26, group, 200 pts) — Demo video (10–20 min)

Record one continuous take. Suggested order — every numbered step is one menu
action:

**Setup shot (≤1 min):** show `sql/schema.sql` + `sql/populate.sql` in editor,
then the running app at the login prompt.

**As HR Admin (`admin` / `admin123`):**
1. Option **1 — Add employee.** Add a new hire (e.g., `Taylor Reed`,
   `taylor.reed@companyz.com`, hire date today, salary `70000`, SSN
   `555-55-5555`). Note the autogenerated empID.
2. Option **2 — Search (edit).** Demo all four modes: by empID, by name
   (`Lee`), by DOB (`1990-09-22`), by SSN (`333-33-3333`).
3. Option **3 — Update employee.** Update the employee added in step 1 — change
   their salary and email.
4. Option **5 — Increase salary by % within range.** Min `60000`, max `80000`,
   percent `5`. Re-search to show the change.
5. Option **6 — Payroll.** Enter a payroll record for an employee.
6. Option **7 — Reports.** Demo all four:
   - 6.b Total pay for month by job title
   - 6.c Total pay for month by division
   - 6.d New hires within a date range
7. Option **4 — Delete employee.** Delete the employee added in step 1, with
   the confirm prompt.
8. Option **0 — Exit.**

**As Employee (`employee` / `user123`):**
9. Option **1 — View my information.** Shows demographic + address.
10. Option **2 — View my pay history.** Sorted most recent first (Deliverable 6.a).
11. Option **0 — Exit.**

**Wrap (≤30 s):** show `src/` directory listing to demonstrate cohesion (no
monolithic file — Deliverable 5.4).

Export as MP4. Upload **one** copy per team.

### Deliverable 5.4 — source ZIP

Zip **only** the contents of `src/` (and `sql/` + `lib/` so graders can build).
Do not zip `out/` or `.git/`.

---

## 5. Known limitations (for the SDD "future work" section)

- `addEmployee` inserts the base `employees` row only. Address, division, and
  job-title assignment are seeded via `populate.sql` and would be added in a
  follow-up screen.
- `app_user.password_hash` is seeded with plaintext for demo; production would
  hash with bcrypt/Argon2.
- `DBConnection` reads credentials from source. A future revision should read
  from a properties file or environment variables.
