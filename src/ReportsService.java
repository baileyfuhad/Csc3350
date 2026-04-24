import java.util.List;

public class ReportsService {

    private ReportsDAO dao;

    public ReportsService() {
        this.dao = new ReportsDAO();
    }

    public List<String> getPayByJobTitle(String month) {
        return dao.totalPayByJobTitle(month);
    }

    public List<String> getPayByDivision(String month) {
        return dao.totalPayByDivision(month);
    }

    public List<String> getNewHires(String start, String end) {
        return dao.newHires(start, end);
    }
}
