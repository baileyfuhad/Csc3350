import java.util.List;

public class PayrollService {

    private PayrollDAO dao;

    public PayrollService() {
        this.dao = new PayrollDAO();
    }

    public List<String> getEmployeePayHistory(int empId) {
        return dao.getPayHistory(empId);
    }
}
